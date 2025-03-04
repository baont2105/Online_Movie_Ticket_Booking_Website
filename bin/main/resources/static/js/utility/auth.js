app.service("UserService", function () {
  var users = list_accounts;
  var currentUser = null;

  this.login = function (email, password) {
    var user = users.find((u) => u.email === email && u.password === password);
    if (user) {
      currentUser = user;
      return true;
    }
    return false;
  };

  this.register = function (newUser) {
    if (users.find((u) => u.email === newUser.email)) {
      return false; // Email already exists
    }
    newUser.id = users.length + 1;
    users.push(newUser);
    return true;
  };

  this.resetPassword = function (email, newPassword) {
    var user = users.find((u) => u.email === email);
    if (user) {
      user.password = newPassword;
      return true;
    }
    return false;
  };

  this.logout = function () {
    currentUser = null;
  };

  this.isLoggedIn = function () {
    return currentUser !== null;
  };

  this.getCurrentUser = function () {
    return currentUser;
  };
});

app.controller("AuthController", function (UserService, $location) {
  var vm = this;
  vm.loginInfo = {};
  vm.registerInfo = {};
  vm.resetInfo = {};
  vm.currentView = "login";

  vm.switchView = function (view) {
    vm.currentView = view;
  };

  vm.login = function () {
    if (UserService.login(vm.loginInfo.email, vm.loginInfo.password)) {
      alert("Đăng nhập thành công");
      window.location.href = "#";
    } else {
      alert("Email hoặc mật khẩu không chính xác");
    }
  };

  vm.register = function () {
    if (vm.registerInfo.password !== vm.registerInfo.confirmPassword) {
      alert("Mật khẩu không khớp");
      return;
    }
    var newUser = {
      email: vm.registerInfo.email,
      username: vm.registerInfo.username,
      password: vm.registerInfo.password,
    };
    if (UserService.register(newUser)) {
      alert("Đăng ký thành công");
      vm.switchView("login");
    } else {
      alert("Email đã tồn tại");
    }
  };

  vm.resetPassword = function () {
    if (vm.resetInfo.newPassword !== vm.resetInfo.confirmNewPassword) {
      alert("Mật khẩu mới không khớp");
      return;
    }
    if (
      UserService.resetPassword(vm.resetInfo.email, vm.resetInfo.newPassword)
    ) {
      alert("Đặt lại mật khẩu thành công");
      vm.switchView("login");
    } else {
      alert("Email không tồn tại");
    }
  };
});

app.controller("NavController", function (UserService) {
  var vm = this;

  console.log(vm.currentUser);

  vm.isLoggedIn = function () {
    return UserService.isLoggedIn();
  };

  vm.currentUser = UserService.getCurrentUser();
  console.log(vm.currentUser);

  vm.logout = function () {
    UserService.logout();
    vm.currentUser = null;
  };
});
