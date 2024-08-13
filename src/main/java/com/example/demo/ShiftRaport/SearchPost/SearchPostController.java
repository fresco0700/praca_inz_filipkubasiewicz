package com.example.demo.ShiftRaport.SearchPost;

import com.example.demo.ShiftRaport.Like;
import com.example.demo.ShiftRaport.ShiftPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
@RequiredArgsConstructor
@RequestMapping("/zmianowy/szukajwpis")
public class SearchPostController {

    private final SearchPostService searchPostService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String showPage() {
        return "szukajwpisu/szukajwpisu";
    }

    @PostMapping(path = "/undelete")
    public String handleUndelete(@RequestParam("id") Long id) {
        searchPostService.manageShiftPost(id,true);
        return "szukajwpisu/szukajwpisu";
    }
    @PostMapping(path = "/delete")
    public String handleDelete(@RequestParam("id") Long id) {
        searchPostService.manageShiftPost(id,false);
        return "szukajwpisu/szukajwpisu";
    }

    @GetMapping("/searchPosts")
    public String searchPosts(Model model,
                              @RequestParam("content") String content,
                              @RequestParam("title") String title,
                              @RequestParam(value = "startDate", defaultValue = "1900-01-01") String startDate,
                              @RequestParam(value = "endDate", defaultValue = "2100-12-31") String endDate,
                              @RequestParam(value = "status") String status,
                              @RequestParam(value = "visible",defaultValue = "wszystkie") String visible
                              ) {
        List<ShiftPost> posts;
        if (visible.equals("ukryte")){
            posts = searchPostService.findPostsByVisibility(content, title,status,startDate,endDate,false);
        } else if (visible.equals("odkryte")){
            posts = searchPostService.findPostsByVisibility(content, title,status,startDate,endDate,true);
        } else {
            posts = searchPostService.findPosts(content, title,status,startDate,endDate);
        }
        List<Map<String, Object>> postMaps = new ArrayList<>();

        for (ShiftPost post : posts) {
            Map<String, Object> postMap = objectMapper.convertValue(post, Map.class);
            List<Like> likes = post.getLikes();
            postMap.put("editedDate", post.getFormattedEditedDate());
            postMap.put("likes", likes);
            postMaps.add(postMap);
        }
        model.addAttribute("posts", postMaps);
        return "szukajwpisu/szukajwpisu";
    }
}
