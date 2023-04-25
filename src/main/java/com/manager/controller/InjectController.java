package com.manager.controller;

import com.manager.model.UserAccount;
import com.manager.service.UserService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final UserService userService;

    public InjectController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String injectData() {
        UserAccount admin = new UserAccount();
        admin.setEmail("admin@i.ua");
        admin.setPassword("1234");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setRole("ROLE_ADMIN");
        if (userService.checkEmail(admin.getEmail())) {
            return "Admin is already exist";
        }
        userService.save(admin);
        return "Done!";
    }
}
