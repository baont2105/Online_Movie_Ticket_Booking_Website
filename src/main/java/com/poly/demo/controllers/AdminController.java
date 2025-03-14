package com.poly.demo.controllers;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.RoomService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
		return "dashboard";
	}

	// ======================= TÀI KHOẢN =======================
	@Autowired
	private UserService userService;

	@GetMapping("/accounts-manager")
	public String AccountsManager(Model model) {
		addUserInfoToModel(model);

		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);

		return "accounts-manager";
	}

	@Autowired
	private ShowtimeService showtimeService;

	// ======================= SUẤT CHIẾU =======================
	@GetMapping("/showtime-manager")
	public String ShowtimeManager(Model model) {
		addUserInfoToModel(model);

		List<Showtime> showtimes = showtimeService.getAllShowtime();
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("rooms", roomService.findAllRooms());
		model.addAttribute("showtimes", showtimes);
		model.addAttribute("showtime", new Showtime());
		return "showtime-manager";
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


	@GetMapping("/showtime-manager/edit/{id}")
	public String showEditShowtimeForm(@PathVariable Long id, Model model) {
		addUserInfoToModel(model);

		Showtime showtime = showtimeService.getShowtimeById(id);
		if (showtime != null) {
			model.addAttribute("showtime", showtime);
			return "showtime-form"; // Trang chỉnh sửa suất chiếu
		}
		return "redirect:/admin/showtime-manager";
	}

	@PostMapping("/showtime-manager/edit/{id}")
	public String updateShowtime(@PathVariable Long id, @ModelAttribute Showtime showtime) {
		Showtime existingShowtime = showtimeService.getShowtimeById(id);
		if (existingShowtime != null) {
			existingShowtime.setMovie(showtime.getMovie());
			existingShowtime.setBranch(showtime.getBranch());
			existingShowtime.setStartTime(showtime.getStartTime());
			existingShowtime.setPrice(showtime.getPrice());

			showtimeService.addShowtime(existingShowtime);
		}
		return "redirect:/admin/showtime-manager";
	}

	@GetMapping("/showtime-manager/delete/{id}")
	public String deleteShowtime(@PathVariable Long id) {
		showtimeService.deleteShowtime(id);
		return "redirect:/admin/showtime-manager";
	}

	// ======================= CHI NHÁNH =======================
	@Autowired
	private BranchService branchService;

	@GetMapping("/branch-manager")
	public String BranchManager( Model model) {
		addUserInfoToModel(model);

		List<Branch> branches = branchService.getAllBranches();
		model.addAttribute("branches", branches);
		model.addAttribute("branch", new Branch());
		return "branch-manager";

	}


	@GetMapping("/branch-manager/add")
	public String showAddBranchForm(@ModelAttribute Branch branch ,Model model) {
	    addUserInfoToModel(model); // Thêm thông tin user nếu có
	    branchService.addBranch(branch);
	    return "branch-form"; // Trả về template form thêm chi nhánh
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
	public String listRooms(Model model) {
		addUserInfoToModel(model);
		List<Room> rooms = roomService.findAllRooms();
		model.addAttribute("rooms", rooms);
		return "room-manager";
	}

	@PostMapping("/room-manager/save")
	public String saveRoom(@ModelAttribute Room room) {
		roomService.saveRoom(room);
		return "redirect:/admin/room-manager";
	}

	@GetMapping("/room-manager/edit/{id}")
	public String showEditForm(@PathVariable Integer id, Model model) {
		Room room = roomService.findRoomById(id).orElse(null);
		if (room == null)
			return "redirect:/admin/rooms";
		model.addAttribute("room", room);
		return "room-manager";
	}

	@GetMapping("/room-manager/delete/{id}")
	public String deleteRoom(@PathVariable Integer id) {
		roomService.deleteRoom(id);
		return "redirect:/admin/room-manager";
	}

	// ================================= VÉ ==================================
	@Autowired
	private TicketService ticketService;

	@GetMapping("/tickets-manager")
	public String TicketsManager(Model model) {
		addUserInfoToModel(model);

		List<Ticket> tickets = ticketService.getAllTicket();
		model.addAttribute("tickets", tickets);

		return "tickets-manager";
	}

	// ============================ PHIM ======================================
	@GetMapping("/movies-manager")
	public String MoviesManager(Model model) {
		addUserInfoToModel(model);
		List<Movie> movies = movieService.getAllMovies();
		model.addAttribute("movies", movies);
		model.addAttribute("movie", new Movie()); // Để dùng trong form thêm mới
		return "movies-manager";
	}

	// THÊM PHIM
	@PostMapping("/movies-manager/add")
	public String addMovie(@ModelAttribute Movie movie, Model model) {
	    addUserInfoToModel(model); // Thêm thông tin user vào model
	    movie.setThumbnail(movie.getThumbnail().isEmpty() ? "absolute_cinema.jpg" : movie.getThumbnail()); // Ảnh mặc định nếu rỗng
	    movieService.addMovie(movie); // Lưu phim vào database
	    return "redirect:/admin/movies-manager"; // Chuyển hướng về trang danh sách phim
	}


	// CHỈNH SỬA PHIM - HIỂN THỊ FORM
	@GetMapping("/movies-manager/edit/{id}")
	public String showEditMovieForm(@PathVariable Long id, Model model) {
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
	public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
		movieService.updateMovie(id, movie);
		return "redirect:/admin/movies-manager";
	}

	// XÓA PHIM
	@GetMapping("/movies-manager/delete/{id}")
	public String deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
		return "redirect:/admin/movies-manager";
	}
}
