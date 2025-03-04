app.controller("TicketingCtrl", function ($scope, $routeParams) {
  $scope.step = 1;
  $scope.movies = list_movies;
  $scope.tickets = list_tickets;

  $scope.cities = [
    "TP HCM",
    "Hà Nội",
    "Đà Nẵng",
    "An Giang",
    "Bến Tre",
    "Cà Mau",
    "Khánh Hòa",
    "Nghệ An",
  ];

  $scope.showtimes = [
    { date: "10/02", times: ["09:00", "13:00", "17:00", "19:00"] },
    { date: "11/02", times: ["09:00", "13:00", "17:00", "19:00"] },
    // Add more dates and times as needed
  ];

  $scope.seats = [];
  for (let row = 1; row <= 8; row++) {
    let seatRow = [];
    for (let col = 1; col <= 12; col++) {
      seatRow.push({
        id: String.fromCharCode(64 + row) + col,
        type: row <= 6 ? "standard" : "vip",
        status: "available",
      });
    }
    $scope.seats.push(seatRow);
  }

  $scope.foods = list_foods;

  // Hàm trả về phim theo id
  $scope.getMovieByID = function (id) {
    for (var i = 0; i < $scope.movies.length; i++) {
      if ($scope.movies[i].id === id) {
        return $scope.movies[i];
      }
    }
    return null; // Nếu không tìm thấy phim với id đó
  };

  $scope.selectedCity = "";
  $scope.selectedMovie = $scope.getMovieByID($routeParams.id);
  $scope.selectedShowtime = { date: "", time: "" };
  $scope.selectedSeats = [];
  $scope.selectedFoods = [];
  $scope.total = 0;
  $scope.foodTotal = 0;

  $scope.selectCity = function (city) {
    $scope.selectedCity = city;
  };

  $scope.isSelectedCity = function (city) {
    return $scope.selectedCity === city;
  };

  $scope.selectMovie = function () {
    // Functionality to handle movie selection
  };

  $scope.selectShowtime = function (date, time) {
    $scope.selectedShowtime = { date, time };
  };

  $scope.isSelectedShowtime = function (date, time) {
    return (
      $scope.selectedShowtime.date === date &&
      $scope.selectedShowtime.time === time
    );
  };

  $scope.selectSeat = function (seat) {
    if (!seat.selected) {
      seat.selected = true;
      $scope.selectedSeats.push(seat);
      $scope.total += seat.type === "standard" ? 60000 : 80000;
    } else {
      seat.selected = false;
      let index = $scope.selectedSeats.indexOf(seat);
      $scope.selectedSeats.splice(index, 1);
      $scope.total -= seat.type === "standard" ? 60000 : 80000;
    }
  };

  $scope.addFood = function (food) {
    food.quantity += 1;
    if ($scope.selectedFoods.indexOf(food) === -1) {
      $scope.selectedFoods.push(food);
    }
    $scope.foodTotal += food.price;
  };

  $scope.removeFood = function (food) {
    if (food.quantity > 0) {
      food.quantity -= 1;
      if (food.quantity === 0) {
        let index = $scope.selectedFoods.indexOf(food);
        $scope.selectedFoods.splice(index, 1);
      }
      $scope.foodTotal -= food.price;
    }
  };

  $scope.nextStep = function () {
    $scope.step += 1;
  };

  $scope.previousStep = function () {
    $scope.step -= 1;
  };

  $scope.confirmBooking = function () {
    // Functionality to handle booking confirmation
    $scope.saveToHistory();
  };

  $scope.generateRandomId = function () {
    var characters =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    var idLength = 6;
    var randomId = "";

    for (var i = 0; i < idLength; i++) {
      var randomIndex = Math.floor(Math.random() * characters.length);
      randomId += characters[randomIndex];
    }

    return randomId;
  };

  $scope.saveToHistory = () => {
    var newTicket = {
      ticketID: $scope.generateRandomId(),
      movie: $scope.selectedMovie,
      buy_time: new Date(),
      showTime: {
        date: $scope.selectedShowtime.date,
        time: $scope.selectedShowtime.time,
      },
      city: $scope.selectedCity,
      seat: $scope.selectedSeats,
      foods: $scope.selectedFoods,
    };
    list_tickets.push(newTicket);
    $scope.tickets = list_tickets;
    alert("Đặt vé thành công!");
    console.log(list_tickets);

    /*
    $scope.tickets.push(
      $scope.selectedMovie.id,
      ,
      $scope.selectedCity,
      $scope.selectedSeats
    );
    */
  };

  $scope.tickets = list_tickets;

  /*
  $http.get("/db/db_tickets.js").then(function (response) {
    $scope.list_product = response.data;
  });
    
  */

  //Phân trang
  $scope.begin = 0;
  $scope.currentPage = 0;
  $scope.ticketsPerPage = 8;
  $scope.pageCount = Math.ceil($scope.tickets.length / $scope.ticketsPerPage);

  $scope.filteredtickets = $scope.tickets;

  // Bộ lọc phim
  $scope.filtertickets = function (type) {
    var now = new Date();
    if (type === "nowShowing") {
      $scope.filteredtickets = $scope.tickets.filter(
        (movie) => movie.release_date <= now
      );
    } else if (type === "comingSoon") {
      $scope.filteredtickets = $scope.tickets.filter(
        (movie) => movie.release_date > now
      );
    }
    // Cập nhật lại số trang sau khi lọc
    $scope.pageCount = Math.ceil(
      $scope.filteredtickets.length / $scope.ticketsPerPage
    );
    $scope.begin = 0;
    $scope.currentPage = 0;
  };

  // Chuyển trang
  $scope.last = function () {
    $scope.currentPage = $scope.pageCount - 1;
    $scope.begin = $scope.currentPage * $scope.ticketsPerPage;
  };

  $scope.first = function () {
    $scope.currentPage = 0;
    $scope.begin = 0;
  };

  $scope.next = function () {
    if ($scope.currentPage < $scope.pageCount - 1) {
      $scope.currentPage++;
      $scope.begin += $scope.ticketsPerPage;
    } else {
      $scope.first();
    }
  };

  $scope.prev = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
      $scope.begin -= $scope.ticketsPerPage;
    } else {
      $scope.last();
    }
  };

  $scope.setPage = function (page) {
    $scope.currentPage = page;
    $scope.begin = page * $scope.ticketsPerPage;
  };
});
