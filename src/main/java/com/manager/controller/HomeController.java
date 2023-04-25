package com.manager.controller;

import com.manager.dao.UserDao;
import com.manager.model.UserAccount;
import com.manager.service.UserService;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            UserAccount user = userDao.findByEmail(email);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute @Valid UserAccount user,
                             RedirectAttributes redirAttrs) {
        boolean f = userService.checkEmail(user.getEmail());
        if (f) {
            redirAttrs.addFlashAttribute("existMsg", "Email is already exists");
        } else {
            UserAccount userDtls = userService.save(user);
            if (userDtls != null) {
                redirAttrs.addFlashAttribute("successMsg", "Register successfully");
            } else {
                redirAttrs.addFlashAttribute("errorMsg", "Something wrong on server");
            }
        }
        return "redirect:/register";
    }
}
