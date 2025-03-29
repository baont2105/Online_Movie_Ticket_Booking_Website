package com.poly.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.FoodItem;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.User;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.FoodItemService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.SeatService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketFoodService;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/booking/")
@SessionAttributes("selectedShowtime")
public class BookingController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private ShowtimeService showtimeService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserService userService;

	@Autowired
	private FoodItemService foodItemService;

	@Autowired
	private TicketFoodService ticketFoodService;

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

	// ========================================== STEP 1
	// =============================================
	@GetMapping("/step1")
	public String showStep1(Model model) {
		addUserInfoToModel(model);

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		return "booking/step1";
	}

	@GetMapping("/step1/{id}")
	public String showStep1(@PathVariable Long id, Model model) {
		addUserInfoToModel(model);

		Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
		Branch branch = branchService.getBranchById(1)
				.orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));

		List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie),
				Optional.of(branch));

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("selectedMovie", movie);
		model.addAttribute("selectedBranch", branch);
		model.addAttribute("showtimes", showtimes);

		return "booking/step1";
	}

	@PostMapping("/select-showtime")
	public String selectShowtime(@RequestParam Long movieId, @RequestParam Integer branchId, Model model) {
		addUserInfoToModel(model);

		Movie movie = movieService.getMovieById(movieId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
		Branch branch = branchService.getBranchById(branchId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));
		List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie),
				Optional.of(branch));

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("selectedMovie", movie);
		model.addAttribute("selectedBranch", branch);
		model.addAttribute("showtimes", showtimes);

		return "booking/step1";
	}

	@PostMapping("/confirm-showtime")
	public String confirmShowtime(@RequestParam Long showtimeId, RedirectAttributes redirectAttributes) {
		Showtime showtime = showtimeService.getShowtimeById(showtimeId);
		redirectAttributes.addAttribute("showtimeId", showtimeId);
		return "redirect:/booking/step2";
	}

	// ========================================== STEP 2
	// =============================================
	@GetMapping("/step2")
	public String showStep2(@RequestParam Long showtimeId, Model model) {
		addUserInfoToModel(model);
		Showtime showtime = showtimeService.getShowtimeById(showtimeId);
		Room room = showtime.getRoom();
		List<Seat> seats = seatService.getSeatsByRoom(room);
		List<Ticket> bookedTickets = ticketService.getTicketsByShowtime(showtime);

		// Lấy danh sách ghế đã được đặt
		Set<Integer> bookedSeatIds = bookedTickets.stream().map(ticket -> ticket.getSeat().getSeatId())
				.collect(Collectors.toSet());

		model.addAttribute("showtime", showtime);
		model.addAttribute("seats", seats);
		model.addAttribute("bookedSeatIds", bookedSeatIds);

		return "booking/step2";
	}

	@PostMapping("/confirm-seats")
	public String confirmSeats(@RequestParam Long showtimeId, @RequestParam("selectedSeats") String selectedSeats,
			RedirectAttributes redirectAttributes, Model model) {

		// Kiểm tra đăng nhập
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetails)) {
			model.addAttribute("error", "Bạn chưa đăng nhập!");
			return "redirect:/login";
		}
		String username = ((UserDetails) principal).getUsername();

		// Lấy dữ liệu
		Showtime showtime = showtimeService.getShowtimeById(showtimeId);
		User user = userService.findByUsername(username).orElse(null);
		if (user == null) {
			model.addAttribute("error", "Không tìm thấy người dùng!");
			return "error-page";
		}

		List<Long> seatIds = Arrays.stream(selectedSeats.split(",")).map(Long::valueOf).collect(Collectors.toList());
		List<Seat> selectedSeatList = seatService
				.getSeatsByIds(seatIds.stream().map(String::valueOf).collect(Collectors.toList()));

		// Lưu vé vào database
		Ticket firstTicket = null; // Lưu vé đầu tiên để truyền sang Step 3
		for (Seat seat : selectedSeatList) {
			Ticket ticket = new Ticket();
			ticket.setUser(user);
			ticket.setShowtime(showtime);
			ticket.setSeat(seat);
			ticket.setPrice(seat.getPrice() + showtime.getPrice());
			ticket.setTicketStatus("NOT_CHECKED_IN");

			ticketService.saveTicket(ticket); // LƯU VÉ

			if (firstTicket == null) {
				firstTicket = ticket;
			}
		}

		// Nếu không có vé nào -> quay lại Step 2
		if (firstTicket == null) {
			model.addAttribute("error", "Vui lòng chọn ít nhất một ghế!");
			return "redirect:/booking/step2?showtimeId=" + showtimeId;
		}

		// Chuyển đến Step 3 với ticketId đầu tiên
		return "redirect:/booking/step3?ticketId=" + firstTicket.getTicketId();
	}

	// ========================================== STEP 3
	// =============================================
	@GetMapping("/step3")
	public String showStep3(@RequestParam("ticketId") Long ticketId, Model model) {
		addUserInfoToModel(model);

		Optional<Ticket> ticketOpt = ticketService.getTicketById(ticketId);
		if (ticketOpt.isEmpty()) {
			return "redirect:/booking/step2"; // Nếu ticketId không hợp lệ
		}

		Ticket ticket = ticketOpt.get();
		List<FoodItem> foodItems = foodItemService.getAllFoodItems();

		model.addAttribute("showtime", ticket.getShowtime());
		model.addAttribute("seat", ticket.getSeat());
		model.addAttribute("ticket", ticket);
		model.addAttribute("foodItems", foodItems);
		return "booking/step3";
	}

	@PostMapping("/confirm-foods")
	public String confirmFoods(@RequestParam Integer ticketId, @RequestParam Map<String, String> foodSelections,
			RedirectAttributes redirectAttributes) {
		try {
			Optional<Ticket> optionalTicket = ticketService.getTicketById(ticketId.longValue());
			if (optionalTicket.isEmpty()) {
				redirectAttributes.addFlashAttribute("error", "Vé không tồn tại!");
				return "redirect:/booking/step3?ticketId=" + ticketId;
			}

			Ticket ticket = optionalTicket.get();
			List<TicketFood> ticketFoodList = new ArrayList<>();

			for (Map.Entry<String, String> entry : foodSelections.entrySet()) {
				try {
					String key = entry.getKey().replaceAll("[^0-9]", ""); // Lọc số từ key
					if (key.isEmpty())
						continue;

					Integer foodId = Integer.parseInt(key);
					int quantity = Integer.parseInt(entry.getValue());

					if (quantity > 0) {
						FoodItem foodItem = foodItemService.getFoodItemById(foodId.longValue());
						TicketFood ticketFood = new TicketFood(ticket, foodItem, quantity);
						System.out.println(ticket.getPrice() + " + " + foodItem.getPrice());
						ticket.setPrice(ticket.getPrice() + (foodItem.getPrice() * quantity));
						ticketFoodList.add(ticketFood);
					}
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse dữ liệu: " + entry.getKey() + " - " + entry.getValue());
				}
			}

			if (!ticketFoodList.isEmpty()) {
				ticketFoodService.saveAllTicketFoods(ticketFoodList); // Lưu tất cả cùng lúc
				redirectAttributes.addFlashAttribute("message", "Lưu thành công!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Không có món ăn nào được chọn!");
			}

			return "redirect:/booking/step3?ticketId=" + ticketId;
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu dữ liệu!");
			return "redirect:/booking/step3?ticketId=" + ticketId;
		}
	}
}