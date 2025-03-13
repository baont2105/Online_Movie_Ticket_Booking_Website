package com.poly.demo.controllers;

import com.poly.demo.entity.User;
import com.poly.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/account")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Lấy thông tin người dùng đăng nhập
    @GetMapping
    public String getAccountInfo(Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userOptional = userService.findByUsername(email);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
        	redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài khoản");
        }
        return "account-info"; // Tên file Thymeleaf
    }

    // Cập nhật thông tin tài khoản
    @PostMapping("/update")
    public String updateAccount(@RequestParam("id") Integer id,
                                @ModelAttribute User user,
                                RedirectAttributes redirectAttributes) {

        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(user.getName());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            userService.save(existingUser);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài khoản");
        }
        return "redirect:/account";
    }


    // Đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(@RequestParam Integer userId, 
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
        	redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/account";
        }

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công!");
        } else {
        	redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài khoản!");
        }

        return "redirect:/account";
    }
}
