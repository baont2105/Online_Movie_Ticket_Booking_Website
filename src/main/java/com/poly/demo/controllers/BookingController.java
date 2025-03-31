package com.poly.demo.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.poly.demo.entity.TicketSeat;
import com.poly.demo.entity.User;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.FoodItemService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.SeatService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketFoodService;
import com.poly.demo.service.TicketSeatService;
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
	@Autowired
	private TicketSeatService ticketSeatService;

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

	// ============================== STEP 1 ===================================
	@GetMapping("/step1")
	public String showStep1(Model model) {
		addUserInfoToModel(model);

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		return "booking/step1";
	}

	@GetMapping("/step1/{id}")
	public String showStep1(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);

		Movie movie = movieService.getMovieById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phim với ID: " + id));

		List<Branch> branches = branchService.getAllBranches();
		List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movie);

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branches);
		model.addAttribute("selectedMovie", movie);
		model.addAttribute("showtimes", showtimes);

		return "booking/step1";
	}

	@PostMapping("/select-showtime")
	public String selectShowtime(@RequestParam Integer movieId, @RequestParam Integer branchId, Model model) {
		addUserInfoToModel(model);

		Movie movie = movieService.getMovieById(movieId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phim với ID: " + movieId));
		Branch branch = branchService.getBranchById(branchId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi nhánh với ID: " + branchId));

		List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(movie, branch);

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("selectedMovie", movie);
		model.addAttribute("selectedBranch", branch);
		model.addAttribute("showtimes", showtimes);

		return "booking/step1";
	}

	@PostMapping("/confirm-showtime")
	public String confirmShowtime(@RequestParam Integer showtimeId, RedirectAttributes redirectAttributes) {
		Showtime showtime = showtimeService.getShowtimeById(showtimeId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy suất chiếu với ID: " + showtimeId));

		redirectAttributes.addAttribute("showtimeId", showtime.getShowtimeId());
		return "redirect:/booking/step2";
	}

	// ======================== STEP 2 ===========================
	@GetMapping("/step2")
	public String showStep2(@RequestParam Integer showtimeId, Model model) {
		addUserInfoToModel(model);

		// Lấy thông tin suất chiếu
		Showtime showtime = showtimeService.getShowtimeById(showtimeId)
				.orElseThrow(() -> new IllegalArgumentException("Suất chiếu không hợp lệ!"));

		// Lấy danh sách ghế của phòng chiếu
		Room room = showtime.getRoom();
		List<Seat> seats = seatService.getSeatsByRoom(room);

		// Lấy danh sách ghế đã được đặt
		List<Seat> bookedSeats = ticketSeatService.getBookedSeatsByShowtime(showtime.getShowtimeId());

		// Đưa dữ liệu vào Model
		model.addAttribute("showtime", showtime);
		model.addAttribute("seats", seats);
		model.addAttribute("bookedSeats", bookedSeats);

		return "booking/step2";
	}

	@PostMapping("/confirm-seats")
	public String confirmSeats(@RequestParam Integer showtimeId, @RequestParam("selectedSeats") String selectedSeats,
			RedirectAttributes redirectAttributes, Model model) {

		// Kiểm tra đăng nhập
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetails)) {
			model.addAttribute("error", "Bạn chưa đăng nhập!");
			return "redirect:/login";
		}
		String username = ((UserDetails) principal).getUsername();

		// Lấy dữ liệu suất chiếu và người dùng
		Showtime showtime = showtimeService.getShowtimeById(showtimeId)
				.orElseThrow(() -> new IllegalArgumentException("Suất chiếu không hợp lệ!"));

		User user = userService.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng!"));

		// Chuyển đổi danh sách ghế từ String → List<Integer>
		List<Integer> seatIds = Arrays.stream(selectedSeats.split(",")).map(Integer::parseInt) // 🔥 Đổi từ `valueOf`
																								// sang `parseInt` để
																								// tránh lỗi
				.collect(Collectors.toList());

		List<Seat> selectedSeatList = seatService.getSeatsByIds(seatIds);

		// Kiểm tra nếu không có ghế nào được chọn
		if (selectedSeatList.isEmpty()) {
			model.addAttribute("error", "Vui lòng chọn ít nhất một ghế!");
			return "redirect:/booking/step2?showtimeId=" + showtimeId;
		}

		// Tạo vé mới
		Ticket ticket = new Ticket();
		ticket.setUser(user);
		ticket.setShowtime(showtime);
		ticket.setPrice(BigDecimal.ZERO); // 🔥 Đổi từ `0.0` thành `BigDecimal.ZERO`
		ticket.setTicketStatus("NOT_CHECKED_IN");

		ticketService.saveTicket(ticket);

		// Tạo danh sách Ticket_Seat và tính tổng giá vé
		BigDecimal totalPrice = BigDecimal.ZERO; // 🔥 Đổi từ `double` sang `BigDecimal`
		List<TicketSeat> ticketSeats = new ArrayList<>();

		for (Seat seat : selectedSeatList) {
			TicketSeat ticketSeat = new TicketSeat();
			ticketSeat.setTicket(ticket);
			ticketSeat.setSeat(seat);
			ticketSeats.add(ticketSeat);

			// Tính tổng giá vé (Seat.price là Integer, cần chuyển sang BigDecimal)
			totalPrice = totalPrice.add(BigDecimal.valueOf(seat.getPrice()))
					.add(BigDecimal.valueOf(showtime.getPrice()));
		}

		// Lưu danh sách ghế vào bảng Ticket_Seat
		ticketSeatService.saveAllTicketSeats(ticketSeats); // 🔥 Đổi `saveAll()` thành `saveAllTicketSeats()`

		// Cập nhật lại giá vé tổng
		ticket.setPrice(totalPrice);
		ticketService.saveTicket(ticket);

		// Chuyển đến Step 3 với ticketId
		return "redirect:/booking/step3?ticketId=" + ticket.getTicketId();
	}

	// ========================= STEP 3 ===========================
	// ========================= STEP 3 ===========================
	@GetMapping("/step3")
	public String showStep3(@RequestParam("ticketId") Integer ticketId, Model model) {
		addUserInfoToModel(model);

		Optional<Ticket> ticketOpt = ticketService.getTicketById(ticketId);
		if (ticketOpt.isEmpty()) {
			return "redirect:/booking/step2"; // Nếu ticketId không hợp lệ
		}

		Ticket ticket = ticketOpt.get();
		List<FoodItem> foodItems = foodItemService.getAllFoodItems();

		model.addAttribute("showtime", ticket.getShowtime());
		model.addAttribute("ticket", ticket);
		model.addAttribute("foodItems", foodItems);

		// Hiển thị danh sách món ăn đã chọn
		List<TicketFood> ticketFoods = ticketFoodService.getFoodItemsByTicketId(ticketId);
		model.addAttribute("ticketFoods", ticketFoods);

		return "booking/step3";
	}

	@PostMapping("/confirm-foods")
	public String confirmFoods(@RequestParam Integer ticketId, @RequestParam Map<String, String> foodSelections,
			RedirectAttributes redirectAttributes) {
		try {
			Optional<Ticket> optionalTicket = ticketService.getTicketById(ticketId);
			if (optionalTicket.isEmpty()) {
				redirectAttributes.addFlashAttribute("error", "Vé không tồn tại!");
				return "redirect:/booking/step3?ticketId=" + ticketId;
			}

			Ticket ticket = optionalTicket.get();
			List<TicketFood> ticketFoodList = new ArrayList<>();
			BigDecimal totalFoodPrice = BigDecimal.ZERO; // Biến lưu tổng giá món ăn

			for (Map.Entry<String, String> entry : foodSelections.entrySet()) {
				try {
					String key = entry.getKey().replaceAll("[^0-9]", ""); // Lọc số từ key
					if (key.isEmpty())
						continue;

					Integer foodId = Integer.parseInt(key);
					int quantity = Integer.parseInt(entry.getValue());

					if (quantity > 0) {
						FoodItem foodItem = foodItemService.getFoodItemById(foodId).orElseThrow(
								() -> new IllegalArgumentException("Không tìm thấy món ăn với ID: " + foodId));

						TicketFood ticketFood = new TicketFood();
						ticketFood.setTicket(ticket);
						ticketFood.setFoodItem(foodItem);
						ticketFood.setQuantity(quantity);
						ticketFoodList.add(ticketFood);

						// Cập nhật giá món ăn
						totalFoodPrice = totalFoodPrice.add(foodItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
					}
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse dữ liệu: " + entry.getKey() + " - " + entry.getValue());
				}
			}

			if (!ticketFoodList.isEmpty()) {
				ticketFoodService.saveAllTicketFoods(ticketFoodList); // Lưu tất cả cùng lúc
				ticket.setPrice(ticket.getPrice().add(totalFoodPrice)); // Cập nhật tổng giá vé
				ticketService.saveTicket(ticket); // Lưu vé với giá đã cập nhật
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