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
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.repository.UserRepository;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.RoomService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private MovieService movieService;

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
	public String AccountsManager(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		addUserInfoToModel(model);

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		Page<User> userPage = userRepository.findAll(pageable);

		model.addAttribute("users", userPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", userPage.getTotalPages());

		return "admin/accounts-manager";
	}

	@Autowired
	private ShowtimeService showtimeService;

	// ======================= SUẤT CHIẾU =======================
	@GetMapping("/showtime-manager")
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
		return "admin/showtime-manager";
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

	@GetMapping("/branch-manager")
	public String BranchManager(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);

		Page<Branch> branchPage = branchService.getBranches(page, size);

		model.addAttribute("branches", branchPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", branchPage.getTotalPages());
		model.addAttribute("branch", new Branch());
		return "admin/branch-manager";

	}

	@GetMapping("/branch-manager/add")
	public String showAddBranchForm(@ModelAttribute Branch branch, Model model) {
		addUserInfoToModel(model); // Thêm thông tin user nếu có
		branchService.addBranch(branch);
		return "admin/branch-form"; // Trả về template form thêm chi nhánh
	}

	@PostMapping("/branch-manager/add")
	public String addBranch(@ModelAttribute Branch branch) {
		branchService.addBranch(branch);
		return "redirect:/admin/branch-manager"; // Chuyển hướng về trang quản lý chi nhánh
	}

	@GetMapping("/branch-manager/edit/{id}")
	public String showEditBranchForm(@PathVariable Integer id, Model model) {
		Optional<Branch> branch = branchService.getBranchById(id);
		if (branch.isPresent()) {
			model.addAttribute("branch", branch.get());
			return "branch-form"; // Tạo thêm file Thymeleaf branch-form.html
		}
		return "redirect:/admin/branch-manager";
	}

	@GetMapping("/branch-manager/delete/{id}")
	public String deleteBranch(@PathVariable Integer id) {
		branchService.deleteBranch(id);
		return "redirect:/admin/branch-manager";
	}

	// ======================= PHÒNG CHIẾU =======================
	@Autowired
	private RoomService roomService;

	@GetMapping("/room-manager")
	public String listRooms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);
		Page<Room> roomPage = roomService.getRooms(page, size);
		model.addAttribute("branches", branchService.getAllBranches());
		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());
		model.addAttribute("room", new Room());
		return "admin/room-manager";
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

		return "admin/tickets-manager";
	}

	// ============================ PHIM ======================================
	@GetMapping("/movies-manager")
	public String MoviesManager(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		addUserInfoToModel(model);
		Page<Movie> moviePage = movieService.getMovies(page, size);
		model.addAttribute("movies", moviePage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());
		model.addAttribute("movie", new Movie()); // Để dùng trong form thêm mới
		return "admin/movies-manager";
	}

	// THÊM PHIM
	@PostMapping("/movies-manager/add")
	public String addMovie(@ModelAttribute Movie movie, Model model) {
		addUserInfoToModel(model); // Thêm thông tin user vào model
		movie.setThumbnail(movie.getThumbnail().isEmpty() ? "absolute_cinema.jpg" : movie.getThumbnail()); // Ảnh mặc
																											// định nếu
																											// rỗng
		movieService.addMovie(movie); // Lưu phim vào database
		return "redirect:/admin/movies-manager"; // Chuyển hướng về trang danh sách phim
	}

	// CHỈNH SỬA PHIM - HIỂN THỊ FORM
	@GetMapping("/movies-manager/edit/{id}")
	public String showEditMovieForm(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);
		Optional<Movie> movie = movieService.getMovieById(id);
		if (movie.isPresent()) {
			model.addAttribute("movie", movie.get());
			return "movie-form"; // Trang chỉnh sửa phim
		}
		return "redirect:/admin/movies-manager";
	}

	// XỬ LÝ CẬP NHẬT PHIM
	@PostMapping("/movies-manager/edit/{id}")
	public String updateMovie(@PathVariable Integer id, @ModelAttribute Movie movie) {
		movieService.updateMovie(id, movie);
		return "redirect:/admin/movies-manager";
	}

	// XÓA PHIM
	@GetMapping("/movies-manager/delete/{id}")
	public String deleteMovie(@PathVariable Integer id) {
		movieService.deleteMovie(id);
		return "redirect:/admin/movies-manager";
	}
}
