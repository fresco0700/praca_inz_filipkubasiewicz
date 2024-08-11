package com.example.demo.sender;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/sendersms")
public class SenderController {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    SenderService senderService;
    @GetMapping
    public String showPage(Model model)
    {
        model.addAttribute("contacts",contactRepository.findAll());
        return "senderpage";
    }

    @PostMapping(path = "/delete-contact")
    public String deleteContact(@RequestParam("id") Long id) {
        senderService.deleteContactById(id);

        return "redirect:/sendersms/";
    }

    @PostMapping(path = "/add-contact")
    public String addContact(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("number") String number,
                             @RequestParam("company") String company,
                             @RequestParam("email") String email) {


        Contact newContact = new Contact(name,surname,number,company,email);
        contactRepository.save(newContact);
        return "redirect:/sendersms/";
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

    @PostMapping(path = "/sendsms")
    public String sendSMS(Model model,
                          @RequestParam("message") String content,
                          @RequestParam("numbers") String numbers) throws JSONException {
        String userSession = String.valueOf(getUserFromSession());
        String result = senderService.sendSmsRequestToSender(content,numbers,userSession);

        model.addAttribute("result", result);
        return "sendresult";
    }

    @PostMapping(path = "/sendtestsms")
    public String sendtestSMS(Model model,
                          @RequestParam("message") String content,
                          @RequestParam("numbers") String numbers) throws JSONException {
        String userSession = String.valueOf(getUserFromSession());
        String result = senderService.sendSmsTestRequestToSender(content,numbers,userSession);

        model.addAttribute("result", result);
        return "sendresult";
    }
}
