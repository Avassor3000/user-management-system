package com.manager.controller;

import com.manager.model.UserAccount;
import com.manager.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        UserAccount admin = userService.findByEmail(email);
        if (admin != null) {
            model.addAttribute("admin", admin);
        }
    }

    @GetMapping()
    public String home() {
        return "admin/home";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<UserAccount> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/list";
    }

    @GetMapping("/{id}")
    public String list(@RequestParam("id") int id, Model model) {
        UserAccount userById = userService.findById(id);
        if (userById != null) {
            model.addAttribute("userById", userById);
            return "admin/view";
        }
        return "admin/home";
    }

    @PostMapping("/{id}/updateStatus")
    public String updateStatus(@PathVariable("id") int id,
                               @RequestParam("status") boolean status) {
        UserAccount userById = userService.findById(id);
        if (userById != null) {
            userById.setStatus(status);
            userService.update(userById);
        }
        return "admin/home";
    }

    @GetMapping("/{id}/edit")
    public String getByIdToEdit(@RequestParam("id") int id, Model model) {
        UserAccount userById = userService.findById(id);
        if (userById != null) {
            model.addAttribute("userById", userById);
            return "admin/edit";
        }
        return "admin/home";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,
                         @RequestParam("firstName") @Valid String firstName,
                         @RequestParam("lastName") @Valid String lastName,
                         @RequestParam("role") String role,
                         @RequestParam("status") boolean status) {
        UserAccount userById = userService.findById(id);
        if (userById != null) {
            userById.setFirstName(firstName);
            userById.setLastName(lastName);
            userById.setRole(role);
            userById.setStatus(status);
            userService.update(userById);
        }
        return "admin/home";
    }

    @GetMapping("/new")
    public String register() {
        return "admin/new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserAccount user, RedirectAttributes redirAttrs) {
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
        return "redirect:/admin";
    }
}
