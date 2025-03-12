package com.poly.demo.controllers;

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

	//Hàm thêm user vào model
	private void addUserInfoToModel(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            model.addAttribute("user", (UserDetails) principal);
        } else {
            model.addAttribute("user", null);
        }
    }
	
	//========================================== STEP 1 =============================================
	@GetMapping("/step1")
	public String showStep1(Model model) {
		addUserInfoToModel(model);

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		return "booking_step1";
	}
	@GetMapping("/step1/{id}")
    public String showStep1(@PathVariable Long id, Model model) {
        addUserInfoToModel(model);

        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Branch branch = branchService.getBranchById(1L).orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));
        
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie), Optional.of(branch));

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("selectedMovie", movie);
        model.addAttribute("selectedBranch", branch);
        model.addAttribute("showtimes", showtimes);
        
        return "booking_step1";
    }

    @PostMapping("/select-showtime")
    public String selectShowtime(@RequestParam Long movieId, @RequestParam Long branchId, Model model) {
        addUserInfoToModel(model);
        
        Movie movie = movieService.getMovieById(movieId).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Branch branch = branchService.getBranchById(branchId).orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie), Optional.of(branch));

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("selectedMovie", movie);
        model.addAttribute("selectedBranch", branch);
        model.addAttribute("showtimes", showtimes);
        
        return "booking_step1";
    }

    @PostMapping("/confirm-showtime")
    public String confirmShowtime(@RequestParam Long showtimeId, RedirectAttributes redirectAttributes) {
        Showtime showtime = showtimeService.getShowtimeById(showtimeId);
        redirectAttributes.addAttribute("showtimeId", showtimeId);
        return "redirect:/booking/step2";
    }
	
	//========================================== STEP 2 =============================================
	@GetMapping("/step2")
    public String showStep2(@RequestParam Long showtimeId, Model model) {
		addUserInfoToModel(model);
        Showtime showtime = showtimeService.getShowtimeById(showtimeId);
        Room room = showtime.getRoom();
        List<Seat> seats = seatService.getSeatsByRoom(room);
        List<Ticket> bookedTickets = ticketService.getTicketsByShowtime(showtime);
        
        // Lấy danh sách ghế đã được đặt
        Set<Integer> bookedSeatIds = bookedTickets.stream()
                .map(ticket -> ticket.getSeat().getSeatId())
                .collect(Collectors.toSet());

        model.addAttribute("showtime", showtime);
        model.addAttribute("seats", seats);
        model.addAttribute("bookedSeatIds", bookedSeatIds);

        return "booking_step2";
    }

	@PostMapping("/confirm-seats")
	public String confirmSeats(@RequestParam Long showtimeId, 
			@RequestParam("selectedSeats") String selectedSeats, 
	                           Model model) {
		
		// Lấy thông tin người dùng hiện tại từ SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            model.addAttribute("error", "Bạn chưa đăng nhập!");
            return "redirect:/login"; // Chuyển hướng về trang login nếu chưa đăng nhập
        }

	    // Lấy thông tin suất chiếu
	    Showtime showtime = showtimeService.getShowtimeById(showtimeId);

	    // Chuyển danh sách ghế từ String thành List<Long>
	    List<Long> seatIds = Arrays.stream(selectedSeats.split(","))
	                               .map(Long::valueOf)
	                               .collect(Collectors.toList());

	    // Nếu getSeatsByIds nhận List<String>, cần chuyển đổi danh sách
	    List<String> seatIdStrings = seatIds.stream()
	                                        .map(String::valueOf)
	                                        .collect(Collectors.toList());

	    // Lấy danh sách ghế từ database
	    List<Seat> selectedSeatList = seatService.getSeatsByIds(seatIdStrings);
	    model.addAttribute("selectedSeats", selectedSeatList);

	    //tìm user
	 // Tìm User theo username
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Không tìm thấy người dùng!");
            return "error-page"; // Chuyển hướng đến trang lỗi
        }
	    
	    // Lưu vé vào database
        Ticket ticket = new Ticket();
	    for (Seat seat : selectedSeatList) {
	        ticket.setUser(user);
	        ticket.setShowtime(showtime);
	        ticket.setSeat(seat);
	        ticket.setPrice(seat.getPrice());
	        ticket.setTicketStatus("NOT_CHECKED_IN");

	       // ticketService.saveTicket(ticket); //hàm lưu vé
	    }

	    return "redirect:/booking/step3";
	}
	//========================================== STEP 3 =============================================
	@GetMapping("/step3")
    public String showStep3( Model model) {
		addUserInfoToModel(model);
        //Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
        List<FoodItem> foodItems = foodItemService.getAllFoodItems();

        //model.addAttribute("ticket", ticket);
        model.addAttribute("foodItems", foodItems);
        return "booking_step3";
    }

    @PostMapping("/confirm-foods")
    public String confirmFoods(@RequestParam Long ticketId,
                               @RequestParam Map<String, String> foodSelections, Model model) {
    	addUserInfoToModel(model);
        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);

        for (Map.Entry<String, String> entry : foodSelections.entrySet()) {
            Long foodId = Long.parseLong(entry.getKey());
            int quantity = Integer.parseInt(entry.getValue());

            if (quantity > 0) {
                FoodItem foodItem = foodItemService.getFoodItemById(foodId);
                TicketFood ticketFood = new TicketFood();
                ticketFood.setTicket(ticket.get());
                ticketFood.setFoodItem(foodItem);
                ticketFood.setQuantity(quantity);
               // ticketFoodService.saveTicketFood(ticketFood);
            }
        }
        return "redirect:/booking/step4?ticketId=" + ticketId;
    }

    
	//========================================== STEP 4 =============================================
	@GetMapping("/step4/{id}")
	public String step4(@PathVariable Long id, Model model) {
		addUserInfoToModel(model);
		return "booking_step4";
	}
	
}