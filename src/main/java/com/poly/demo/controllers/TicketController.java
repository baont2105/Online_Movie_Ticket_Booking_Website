package com.poly.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.demo.entity.User;
import com.poly.demo.entity.Ticket;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/my-tickets")
    public String listTickets(Model model) {
        // Lấy thông tin người dùng hiện tại từ SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            model.addAttribute("error", "Bạn chưa đăng nhập!");
            return "redirect:/login"; // Chuyển hướng về trang login nếu chưa đăng nhập
        }

        // Tìm User theo username
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Không tìm thấy người dùng!");
            return "error-page"; // Chuyển hướng đến trang lỗi
        }

        // Lấy danh sách vé theo userID
        List<Ticket> tickets = ticketService.getTicketByUserID(user.getId());
        model.addAttribute("tickets", tickets);
        model.addAttribute("user", user); // Gửi thông tin user đến Thymeleaf

        return "my-tickets"; // Trả về trang Thymeleaf hiển thị danh sách vé
    }
}
