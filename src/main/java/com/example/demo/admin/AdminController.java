package com.example.demo.admin;


import com.example.demo.security.ERole;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String showMenu(){
        return "adminmenu";
    }

    @GetMapping(path = "/users")
    public String showUsers(Model model){
        model.addAttribute("users", adminService.listUsers());
        return "adminusers";

    }

    @GetMapping(path = "/stats")
    public String getStats(Model model) {
        model.addAttribute("createdPosts", adminService.listCreatedPosts());
        return "adminstats";
    }

    @PostMapping(path = "/deleteuser")
    public String deleteUser(@RequestParam Long id){
        adminService.deleteUser(id);
        return "redirect:/admin/users/";
    }



    @PostMapping(path = "/resetpassword")
    public String resetPasswordUser(Model model,
                                    @RequestParam Long id){
        if (adminService.isUserExist(id)){
            String newPassword = adminService.changePassword(id);
            model.addAttribute("newpassword", newPassword);
            return "passchanged";
        }
        else return "Brak takiego usera";
    }

    @PostMapping(path = "/adduser/add")
    public String addUser(Model model,
                          @RequestParam String login,
                          @RequestParam String password,
                          @RequestParam ERole role){
        model.addAttribute("login",adminService.addUser(login,password,role));
        return "adminuseradded";
    }

    @GetMapping(path = "/adduser")
    public String showAddUser(){
        return "adminadduser";
    }

}
