package com.example.demo.ShiftRaport;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Main class to control posts
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/zmianowy")
public class ShiftController {

    private final ShiftService shiftService;
    private final NotificationService notificationService;


    @Value("${zmianowy.config.post_per_page}")
    private int post_per_page;

    @Value("${zmianowy.config.path_to_files}")
    private String path_to_files;

    @GetMapping
    public String getShiftPosts(Model model,
                                @RequestParam(defaultValue = "0") int page
                                ) {
        if (page<0){page=0;}
        List<Map<String, Object>> posts = shiftService.getShiftPostsFromDb(page,post_per_page);
        boolean hasNext = posts.size() > post_per_page;

        if (hasNext) {
            posts = posts.subList(0, post_per_page);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("page", page);
        return "posts";

    }

    public String getUserFromSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @PostMapping(path = "/addPost")
    public String handleSubmit( @RequestParam("id") Optional<Long> id,
                                @RequestParam("file") Optional<MultipartFile[]> file,
                                @RequestParam("title") String title,
                                @RequestParam("content") String content,
                                @RequestParam("dayornight") String dayornight,
                                @RequestParam("state") String state) throws IOException {

        String personalUUID = UUID.randomUUID().toString();
        List<String> filenames = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        String userSession = String.valueOf(getUserFromSession());


        file.ifPresent(files -> Arrays.stream(files)
                .forEach(multipartFile -> {
                    if (!multipartFile.getOriginalFilename().isEmpty()) {
                        String newFilename = personalUUID + "___" + multipartFile.getOriginalFilename();
                        filenames.add(newFilename);
                    }

                }));

        //Editing post
        if (id.isPresent()){
            Long idParam = id.get();
            if (!filenames.isEmpty()) {
                MultipartFile[] files = file.get();

                for (MultipartFile one_file : files) {
                    String newFilename = personalUUID + "___" + one_file.getOriginalFilename();
                    File dest = new File(path_to_files + newFilename);

                    one_file.transferTo(dest);
                }
            }
            shiftService.updateShiftPost(idParam,title,content,state,userSession,now,filenames);
            return "redirect:/zmianowy/";
        }

        if (!filenames.isEmpty()) {
            MultipartFile[] files = file.get();

            for (MultipartFile one_file : files) {
                String newFilename = personalUUID + "___" + one_file.getOriginalFilename();
                File dest = new File(path_to_files + newFilename);

                one_file.transferTo(dest);
            }

            ShiftPost newShiftPost = new ShiftPost(userSession, title, content, String.valueOf(LocalDate.now()),filenames,dayornight,state,userSession,now);

            shiftService.addNewShiftPost(newShiftPost);
            return "redirect:/zmianowy/";
        }

        //Creating new post
        ShiftPost newShiftPost = new ShiftPost(String.valueOf(getUserFromSession()), title, content, String.valueOf(formattedNow),dayornight,state,userSession,now);
        shiftService.addNewShiftPost(newShiftPost);

        notificationService.sendNotification("Użytkownik "+ userSession + " dodał nowy wpis!\nOdśwież stronę, aby go zobaczyć");
        return "redirect:/zmianowy/";
    }

    @PostMapping(path = "/delete")
    public String handleDelete(@RequestParam("id") Long id) {
        System.out.println("[INFO] "+LocalDateTime.now() + "User: " +getUserFromSession() + " ukrył wpis o id: " + id);
        shiftService.deleteShiftPost(id,false);
        return "redirect:/zmianowy/";
    }



    @PostMapping(path = "/likeit")
    public ResponseEntity<?> likePost(@RequestParam Long id) {
        String userSession = String.valueOf(getUserFromSession());
        boolean isOk = shiftService.likePost(id,userSession);
        if (isOk) {
            return ResponseEntity.ok().body(Collections.singletonMap("status", "ok"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("status", "error"));
        }
    }

    @GetMapping("/{id}/files")
    public List<String> getFilesForShiftPost(@PathVariable Long id) {
        return shiftService.getFilesForShiftPost(id);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            String actualFilename = filename.substring(filename.indexOf("___") + 3);
            Path file = Paths.get(path_to_files + filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentDisposition = "attachment; filename=\"" + actualFilename + "\"";
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                        .body(resource);
            } else {
                throw new RuntimeException("Wystąpił problem z plikiem!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
