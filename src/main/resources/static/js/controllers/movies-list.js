//movie list
app.controller("moviesCtrl", function ($scope, $http) {
  $scope.movies = list_movies;

  /*
    $http.get("/db/db_movies.js").then(function (response) {
      $scope.list_product = response.data;
    });
      
    */

  //Phân trang
  $scope.begin = 0;
  $scope.currentPage = 0;
  $scope.moviesPerPage = 8;
  $scope.pageCount = Math.ceil($scope.movies.length / $scope.moviesPerPage);

  $scope.filteredMovies = $scope.movies;

  // Bộ lọc phim
  $scope.filterMovies = function (type) {
    var now = new Date();
    if (type === "nowShowing") {
      $scope.filteredMovies = $scope.movies.filter(
        
        (movie) => movie.release_date <= now
      );
    } else if (type === "comingSoon") {
      $scope.filteredMovies = $scope.movies.filter(
        (movie) => movie.release_date > now
      );
    }
    // Cập nhật lại số trang sau khi lọc
    $scope.pageCount = Math.ceil(
      $scope.filteredMovies.length / $scope.moviesPerPage
    );
    $scope.begin = 0;
    $scope.currentPage = 0;
  };

  // Chuyển trang
  $scope.last = function () {
    $scope.currentPage = $scope.pageCount - 1;
    $scope.begin = $scope.currentPage * $scope.moviesPerPage;
  };

  $scope.first = function () {
    $scope.currentPage = 0;
    $scope.begin = 0;
  };

  $scope.next = function () {
    if ($scope.currentPage < $scope.pageCount - 1) {
      $scope.currentPage++;
      $scope.begin += $scope.moviesPerPage;
    } else {
      $scope.first();
    }
  };

  $scope.prev = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
      $scope.begin -= $scope.moviesPerPage;
    } else {
      $scope.last();
    }
  };

  $scope.setPage = function (page) {
    $scope.currentPage = page;
    $scope.begin = page * $scope.moviesPerPage;
  };
});

//functions
// Tạo bộ lọc range để sử dụng trong ng-repeat
app.filter("range", function () {
  return function (input, total) {
    total = parseInt(total);
    for (var i = 0; i < total; i++) {
      input.push(i);
    }
    return input;
  };
});
