//movie detail
app.controller("movieDetailCtrl", function ($scope, $sce, $routeParams) {
  $scope.movies = list_movies;

  // Hàm trả về phim theo id
  $scope.getMovieByID = function (id) {
    for (var i = 0; i < $scope.movies.length; i++) {
      if ($scope.movies[i].id === id) {
        return $scope.movies[i];
      }
    }
    return null; // Nếu không tìm thấy phim với id đó
  };

  // Hàm trả về URL an toàn cho iframe
  $scope.getTrustedUrl = function (url) {
    return $sce.trustAsResourceUrl(url);
  };

  //get movie
  $scope.searchedMovie = $scope.getMovieByID($routeParams.id);
});
