package com.poly.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.demo.entity.User;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketVoucher;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserService userService;

	// Hàm thêm user vào model
	private void addUserInfoToModel(Model model) {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) principal;
	        String username = userDetails.getUsername(); // Lấy username từ UserDetails

	        // Lấy thông tin User từ database
	        Optional<User> userOptional = userService.findByUsername(username);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            model.addAttribute("user", user);
	            model.addAttribute("name", user.getName()); // Gửi tên đến Thymeleaf
	            return;
	        }
	    }
	    
	    // Nếu không tìm thấy user hoặc chưa đăng nhập
	    model.addAttribute("user", null);
	    model.addAttribute("name", null);
	}


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

		// Thêm danh sách đồ ăn/thức uống và voucher cho mỗi vé
		Map<Integer, List<TicketFood>> foodItemsMap = new HashMap<>();
		Map<Integer, List<TicketVoucher>> voucherMap = new HashMap<>();

		// Duyệt qua từng vé để lấy danh sách đồ ăn/thức uống và voucher
		for (Ticket ticket : tickets) {
			foodItemsMap.put(ticket.getTicketId(), ticketService.getFoodItemsByTicketId(ticket.getTicketId()));
			voucherMap.put(ticket.getTicketId(), ticketService.getVouchersByTicketId(ticket.getTicketId()));
		}

		model.addAttribute("tickets", tickets);
		model.addAttribute("user", user); // Gửi thông tin user đến Thymeleaf
		model.addAttribute("foodItemsMap", foodItemsMap);
		model.addAttribute("voucherMap", voucherMap);

		return "ticket/my-tickets"; // Trả về trang Thymeleaf hiển thị danh sách vé
	}

	@PostMapping("/check-in")
	public String checkInTicket(@RequestParam("ticketId") Long ticketId, @RequestParam("staffId") Integer staffId,
			Model model) {
		// Lấy thông tin nhân viên từ UserService
		Optional<User> staffOpt = userService.getUserById(staffId);

		if (staffOpt.isEmpty()) {
			model.addAttribute("error", "Mã nhân viên không tồn tại!");
			return "redirect:/ticket/my-tickets"; // Trả về trang vé
		}

		User staff = staffOpt.get();

		// Kiểm tra quyền của nhân viên
		if (!staff.getRole().equalsIgnoreCase("STAFF")) {
			model.addAttribute("error", "Người dùng không có quyền check-in!");
			return "redirect:/ticket/my-tickets";
		}

		// Lấy thông tin vé
		Optional<Ticket> ticketOpt = ticketService.getTicketById(ticketId);

		if (ticketOpt.isEmpty()) {
			model.addAttribute("error", "Không tìm thấy vé!");
			return "redirect:/ticket/my-tickets";
		}

		Ticket ticket = ticketOpt.get();

		// Kiểm tra trạng thái vé
		if ("CHECKED_IN".equals(ticket.getTicketStatus())) {
			model.addAttribute("error", "Vé đã được check-in trước đó!");
			return "redirect:/ticket/my-tickets";
		}

		// Cập nhật trạng thái vé
		ticket.setTicketStatus("CHECKED_IN");
		ticketService.updateTicket(ticket);

		model.addAttribute("success", "Check-in thành công!");
		return "redirect:/ticket/my-tickets";
	}
}
