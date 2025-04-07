package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Category;
import com.poly.demo.entity.FoodItem;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.entity.Voucher;
import com.poly.demo.repository.BranchRepository;
import com.poly.demo.repository.CategoryRepository;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.UserRepository;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.FoodItemService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.RoomService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;
import com.poly.demo.service.VoucherService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	// Thêm thông tin user vào model
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

	@GetMapping("/")
	public String AdminPage(Model model) {
		addUserInfoToModel(model);
		return "admin/dashboard";
	}

	// ======================= TÀI KHOẢN =======================
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/accounts-manager")
	public String AccountList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);

		Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
		Page<User> userPage = userRepository.findAll(pageable);

		model.addAttribute("users", userPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", userPage.getTotalPages());

		return "admin/accounts-manager/list";
	}

	@GetMapping("/accounts-manager/{id}")
	public String AccountForm(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);
		User user = id == 0 ? new User() : userRepository.findById(id).orElse(new User());
		model.addAttribute("selectedUser", user);
		return "admin/accounts-manager/form";
	}

	// Lưu (thêm hoặc cập nhật)
	@PostMapping("/accounts-manager/save")
	public String save(@ModelAttribute User user) {
		user.setPassword("$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa");
		userRepository.save(user);
		return "redirect:/admin/accounts-manager";
	}

	@GetMapping("/accounts-manager/edit/{id}")
	public String editAccount(@PathVariable("id") Integer id, Model model) {
		Optional<User> userOptional = userService.getUserById(id);
		if (userOptional.isPresent()) {
			model.addAttribute("selectedUser", userOptional.get());
			return "admin/edit-account"; // Trỏ đến file Thymeleaf
		} else {
			return "redirect:/admin/accounts-manager/form?error=UserNotFound";
		}
	}

	@PostMapping("/accounts-manager/update")
	public String updateAccount(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		try {
			userService.update(user);
			redirectAttributes.addFlashAttribute("success", "Cập nhật tài khoản thành công!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật tài khoản!");
		}
		return "redirect:/admin/accounts-manager/form";
	}

	// Xóa tài khoản
	@GetMapping("/accounts-manager/delete/{id}")
	public String delete(@PathVariable Integer id) {
		userRepository.deleteById(id);
		return "redirect:/admin/accounts-manager";
	}

	// ======================= SUẤT CHIẾU =======================
	@Autowired
	private ShowtimeService showtimeService;

	@GetMapping("/showtimes-manager")
	public String ShowtimeManager(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		addUserInfoToModel(model);

		Page<Showtime> showtimePage = showtimeService.getShowtimesPaginated(page, size);
		model.addAttribute("showtimePage", showtimePage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", showtimePage.getTotalPages());

		List<Showtime> showtimes = showtimeService.getAllShowtime();
		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("rooms", roomService.findAllRooms());
		model.addAttribute("showtimes", showtimes);
		model.addAttribute("showtime", new Showtime());
		return "admin/showtimes-manager/list";
	}

	@GetMapping("/showtime-manager/add")
	public String showAddShowtimeForm(Model model) {
		addUserInfoToModel(model);

		model.addAttribute("showtime", new Showtime());
		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("rooms", roomService.findAllRooms());

		return "showtime-form"; // Hiển thị form thêm suất chiếu
	}

	@PostMapping("/showtime-manager/add")
	public String addShowtime(@ModelAttribute Showtime showtime) {
		showtimeService.addShowtime(showtime);
		return "redirect:/admin/showtime-manager";
	}

	/*
	 * @GetMapping("/showtime-manager/edit/{id}") public String
	 * showEditShowtimeForm(@PathVariable Integer id, Model model) {
	 * addUserInfoToModel(model);
	 * 
	 * Showtime showtime = showtimeService.getShowtimeById(id); if (showtime !=
	 * null) { model.addAttribute("showtime", showtime); return "showtime-form"; //
	 * Trang chỉnh sửa suất chiếu } return "redirect:/admin/showtime-manager"; }
	 */

	@GetMapping("/showtime-manager/edit/{id}")
	public String showEditShowtimeForm(@PathVariable("id") Integer id, Model model) {
		Optional<Showtime> optionalShowtime = showtimeService.getShowtimeById(id);

		if (optionalShowtime.isPresent()) {
			model.addAttribute("showtime", optionalShowtime.get());
			return "redirect:/admin/showtime-manager"; // Trả về trang chỉnh sửa suất chiếu
		} else {
			model.addAttribute("errorMessage", "Không tìm thấy suất chiếu có ID: " + id);
			return "error"; // Trả về trang lỗi hoặc thông báo
		}
	}

	/*
	 * @PostMapping("/showtime-manager/edit/{id}") public String
	 * updateShowtime(@PathVariable Integer id, @ModelAttribute Showtime showtime) {
	 * Showtime existingShowtime = showtimeService.getShowtimeById(id); if
	 * (existingShowtime != null) { existingShowtime.setMovie(showtime.getMovie());
	 * existingShowtime.setBranch(showtime.getBranch());
	 * existingShowtime.setStartTime(showtime.getStartTime());
	 * existingShowtime.setPrice(showtime.getPrice());
	 * 
	 * showtimeService.addShowtime(existingShowtime); } return
	 * "redirect:/admin/showtime-manager"; }
	 */

	@PostMapping("/showtime-manager/edit/{id}")
	public String updateShowtime(@PathVariable("id") Integer id, @ModelAttribute Showtime updatedShowtime,
			RedirectAttributes redirectAttributes) {
		if (!showtimeService.existsById(id)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy suất chiếu có ID: " + id);
			return "redirect:/admin/showtimes"; // Chuyển hướng về danh sách suất chiếu
		}

		Showtime showtime = showtimeService.getShowtimeById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy suất chiếu có ID: " + id));

		// Cập nhật thông tin suất chiếu
		showtime.setMovie(updatedShowtime.getMovie());

		showtimeService.addShowtime(showtime);
		redirectAttributes.addFlashAttribute("successMessage", "Cập nhật suất chiếu thành công!");

		return "redirect:/admin/showtime-manager";
	}

	@GetMapping("/showtime-manager/delete/{id}")
	public String deleteShowtime(@PathVariable Integer id) {
		showtimeService.deleteShowtime(id);
		return "redirect:/admin/showtime-manager";
	}

	// ======================= CHI NHÁNH =======================
	@Autowired
	private BranchService branchService;
	@Autowired
	private BranchRepository branchRepository;

	@GetMapping("/branches-manager")
	public String BranchManager(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);

		Page<Branch> branchPage = branchService.getBranches(page, size);

		model.addAttribute("branches", branchPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", branchPage.getTotalPages());
		model.addAttribute("branch", new Branch());
		return "admin/branches-manager/list";
	}

	// Hiển thị form (thêm mới hoặc sửa)
	@GetMapping("/branches-manager/{id}")
	public String form(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);

		Branch branch = id == 0 ? new Branch() : branchRepository.findById(id).orElse(new Branch());
		model.addAttribute("selectedBranch", branch);
		return "admin/branches-manager/form";
	}

	@PostMapping("/branches-manager/save")
	public String save(@ModelAttribute Branch branch, Model model) {
		addUserInfoToModel(model);
		branchRepository.save(branch);
		return "redirect:/admin/branches-manager";
	}

	@GetMapping("/branches-manager/delete/{id}")
	public String deleteBranch(@PathVariable Integer id) {
		branchService.deleteBranch(id);
		return "redirect:/admin/branches-manager";
	}

	// ======================= PHÒNG CHIẾU =======================
	@Autowired
	private RoomService roomService;

	@GetMapping("/rooms-manager")
	public String listRooms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);
		Page<Room> roomPage = roomService.getRooms(page, size);
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());
		model.addAttribute("room", new Room());
		return "admin/rooms-manager/list";
	}

	@PostMapping("/room-manager/save")
	public String saveRoom(@ModelAttribute Room room) {
		roomService.addRoomWithSeats(room);
		return "redirect:/admin/room-manager";
	}

	@GetMapping("/room-manager/edit/{id}")
	public String showEditForm(@PathVariable Integer id, Model model) {
		Room room = roomService.findRoomById(id).orElse(null);
		if (room == null)
			return "redirect:/admin/rooms";
		model.addAttribute("room", room);
		return "admin/room-manager";
	}

	@GetMapping("/room-manager/delete/{id}")
	public String deleteRoom(@PathVariable Integer id) {
		roomService.deleteRoom(id);
		return "redirect:/admin/room-manager";
	}

	@PostMapping("/room-manager/add")
	public String addRoom(@ModelAttribute Room room) {
		roomService.addRoomWithSeats(room);
		return "redirect:/admin/room-manager";
	}

	// ================================= VÉ ==================================
	@Autowired
	private TicketService ticketService;

	@GetMapping("/tickets-manager")
	public String TicketsManager(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);

		Page<Ticket> ticketPage = ticketService.getTickets(page, size);

		model.addAttribute("tickets", ticketPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ticketPage.getTotalPages());

		return "admin/tickets-manager/list";
	}

	@PostMapping("/check-in")
	public String checkInTicket(@RequestParam("ticketId") Integer ticketId, @RequestParam("staffId") Integer staffId,
			Model model) {
		// Lấy thông tin nhân viên từ UserService
		Optional<User> staffOpt = userService.getUserById(staffId);

		if (staffOpt.isEmpty()) {
			model.addAttribute("error", "Mã nhân viên không tồn tại!");
			return "redirect:/admin/tickets-manager"; // Trả về trang vé
		}

		User staff = staffOpt.get();

		// Kiểm tra quyền của nhân viên
		if (!staff.getRole().equalsIgnoreCase("STAFF")) {
			model.addAttribute("error", "Người dùng không có quyền check-in!");
			return "redirect:/admin/tickets-manager";
		}

		// Lấy thông tin vé
		Optional<Ticket> ticketOpt = ticketService.getTicketById(ticketId);

		if (ticketOpt.isEmpty()) {
			model.addAttribute("error", "Không tìm thấy vé!");
			return "redirect:/admin/tickets-manager";
		}

		Ticket ticket = ticketOpt.get();

		// Kiểm tra trạng thái vé
		if ("CHECKED_IN".equals(ticket.getTicketStatus())) {
			model.addAttribute("error", "Vé đã được check-in trước đó!");
			return "redirect:/admin/tickets-manager";
		}

		// Cập nhật trạng thái vé
		ticket.setTicketStatus("CHECKED_IN");
		ticketService.updateTicket(ticket);

		model.addAttribute("success", "Check-in thành công!");
		return "redirect:/admin/tickets-manager";
	}

	// ==================== THỂ LOẠI ======================
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/categories-manager")
	public String CategoriesManager(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		addUserInfoToModel(model);

		Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId").ascending());
		Page<Category> categoryPage = categoryRepository.findAll(pageable);

		model.addAttribute("categories", categoryPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", categoryPage.getTotalPages());

		return "admin/categories-manager/list";
	}

	// ==================== PHIM ======================
	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping("/movies-manager")
	public String MoviesManager(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);
		Page<Movie> moviePage = movieService.getMovies(page, size);
		model.addAttribute("movies", moviePage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());
		model.addAttribute("movie", new Movie()); // Để dùng trong form thêm mới
		return "admin/movies-manager/list";
	}

	@GetMapping("/movies-manager/{id}")
	public String MovieForm(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);
		Movie movie = id == 0 ? new Movie() : movieRepository.findById(id).orElse(new Movie());
		model.addAttribute("selectedMovie", movie);
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/movies-manager/form";
	}

	// THÊM PHIM
	@PostMapping("/movies-manager/save")
	public String SaveMovie(@ModelAttribute Movie movie, Model model) {
		addUserInfoToModel(model);

		movie.setThumbnail(movie.getThumbnail().isEmpty() ? "absolute_cinema.jpg" : movie.getThumbnail());
		movieRepository.save(movie);
		return "redirect:/admin/movies-manager";
	}

	@GetMapping("/movies-manager/delete/{id}")
	public String DeleteMovie(@PathVariable Integer id) {
		movieService.deleteMovie(id);
		return "redirect:/admin/movies-manager";
	}

	// ======================= ĐỒ ĂN =======================
	@Autowired
	private FoodItemService foodItemService;

	@GetMapping("/foods-manager")
	public String foodList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);

		Pageable pageable = PageRequest.of(page, size, Sort.by("foodItemId").ascending());
		Page<FoodItem> foodPage = foodItemService.getFoodItems(pageable);

		model.addAttribute("foods", foodPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", foodPage.getTotalPages());
		model.addAttribute("food", new FoodItem()); // Để dùng trong form thêm mới

		return "admin/foods-manager/list";
	}

	// Hiển thị form (thêm mới hoặc sửa)
	@GetMapping("/foods-manager/{id}")
	public String foodForm(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);

		FoodItem foodItem = id == 0 ? new FoodItem() : foodItemService.getFoodItemById(id).orElse(new FoodItem());
		model.addAttribute("selectedFood", foodItem);
		return "admin/foods-manager/form";
	}

	@PostMapping("/foods-manager/save")
	public String saveFood(@ModelAttribute FoodItem foodItem, Model model) {
		addUserInfoToModel(model);
		foodItem.setImage(foodItem.getImage().isEmpty() ? "default.png" : foodItem.getImage());
		foodItemService.save(foodItem);
		return "redirect:/admin/foods-manager";
	}

	@GetMapping("/foods-manager/delete/{id}")
	public String deleteFood(@PathVariable Integer id) {
		foodItemService.delete(id);
		return "redirect:/admin/foods-manager";
	}

	// ======================= MÃ GIẢM GIÁ =======================

	@Autowired
	private VoucherService voucherService;

	@GetMapping("/vouchers-manager")
	public String listVouchers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);
		Pageable pageable = PageRequest.of(page, size);
		Page<Voucher> voucherPage = voucherService.getPageVouchers(pageable);

		model.addAttribute("vouchers", voucherPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", voucherPage.getTotalPages());
		return "admin/vouchers-manager/list"; // Trả về trang danh sách vouchers
	}

	@GetMapping("/vouchers-manager/{id}")
	public String VoucherForm(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);
		Voucher voucher = id == 0 ? new Voucher() : voucherService.getVoucherById(id).orElse(new Voucher());
		model.addAttribute("selectedVoucher", voucher);
		return "admin/vouchers-manager/form"; // Trả về form cho voucher
	}

	@PostMapping("/vouchers-manager/save")
	public String saveVoucher(@ModelAttribute Voucher voucher) {
		voucherService.saveVoucher(voucher);
		return "redirect:/admin/vouchers-manager"; // Quay về danh sách vouchers
	}

	@GetMapping("/vouchers-manager/delete/{id}")
	public String deleteVoucher(@PathVariable Integer id) {
		voucherService.deleteVoucher(id);
		return "redirect:/admin/vouchers-manager"; // Quay về danh sách vouchers
	}

}
