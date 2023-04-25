package com.manager.controller;

import com.manager.model.UserAccount;
import com.manager.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        UserAccount user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("user", user);
        }
    }

    @GetMapping()
    public String home() {
        return "user/home";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<UserAccount> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String list(@RequestParam("id") int id, Model model) {
        UserAccount userById = userService.findById(id);
        if (userById != null) {
            model.addAttribute("userById", userById);
            return "user/view";
        }
        return "user/home";
    }
}
