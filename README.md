Dưới đây là nội dung file **README.md** cho dự án của bạn trên GitHub:  

---

# 🎬 **Hệ Thống Đặt Vé Xem Phim - Prime Cinema**  

## 📌 **Giới Thiệu**  
**Prime Cinema** là một hệ thống đặt vé xem phim trực tuyến, giúp người dùng dễ dàng tìm kiếm phim, đặt vé, chọn ghế và thanh toán trực tuyến. Hệ thống còn hỗ trợ quản lý suất chiếu, phim, phòng chiếu, tài khoản và nhiều chức năng khác cho quản trị viên.  

---

## 🚀 **Tính Năng Chính**  

### 🏷 **Người Dùng**  
✅ Xem danh sách phim đang chiếu, suất chiếu tại các rạp.  
✅ Đặt vé xem phim theo thời gian, rạp, ghế ngồi mong muốn.  
✅ Chọn combo đồ ăn và nhập mã giảm giá khi đặt vé.  
✅ Kiểm tra thông tin vé đã đặt.  
✅ Đăng nhập, đăng ký và quản lý tài khoản cá nhân.  

### 🎬 **Quản Trị Viên (Admin)**  
✅ Quản lý suất chiếu (thêm, sửa, xóa).  
✅ Quản lý phim (thêm, sửa, xóa).  
✅ Quản lý phòng chiếu, ghế ngồi.  
✅ Quản lý tài khoản người dùng.  
✅ Quản lý vé, chi nhánh và thực phẩm (đồ ăn, nước uống).  

---

## 🛠 **Công Nghệ Sử Dụng**  

### 📌 **Backend**  
- **Java Spring Boot** - Xử lý logic nghiệp vụ.  
- **Spring Security** - Bảo mật và phân quyền.  
- **Spring Data JPA** - Làm việc với database.  
- **SQL Server** - Cơ sở dữ liệu chính.  

### 🎨 **Frontend**  
- **Thymeleaf** - Template engine để hiển thị giao diện.  
- **Bootstrap** - Thiết kế giao diện đẹp, responsive.  
- **AJAX & jQuery** - Tương tác động.  

### 🔥 **Công Cụ Hỗ Trợ**  
- **Postman** - Test API.  
- **Lombok** - Giảm boilerplate code.  
- **Maven** - Quản lý dependencies.  
- **GitHub** - Quản lý mã nguồn.  

---

## 📂 **Cấu Trúc Dự Án**  
```bash
Prime-Cinema/
│── src/
│   ├── main/
│   │   ├── java/com/poly/demo/
│   │   │   ├── controllers/   # Controller xử lý request
│   │   │   ├── entity/        # Các entity mapping database
│   │   │   ├── repository/    # Layer giao tiếp database
│   │   │   ├── service/       # Business logic
│   │   │   ├── security/      # Cấu hình bảo mật
│   │   │   ├── PrimeCinemaApplication.java
│   │   ├── resources/
│   │   │   ├── static/        # CSS, JS, images
│   │   │   ├── templates/     # Giao diện Thymeleaf
│   │   │   ├── application.properties
│── pom.xml  # Cấu hình dependencies
│── README.md # File tài liệu dự án
```

---

## 📦 **Cài Đặt Và Chạy Dự Án**  

### 🔹 **Yêu Cầu Hệ Thống**  
- **Java 17+**  
- **Maven 3+**  
- **SQL Server**  

### 🔹 **Cách Cài Đặt**  
1️⃣ **Clone dự án**  
```bash
git clone https://github.com/yourusername/prime-cinema.git
cd prime-cinema
```
2️⃣ **Cấu hình database**  
- Tạo database `Prime_Cinema_DB` trên SQL Server.  
- Mở file `application.properties`, sửa thông tin kết nối database.  

3️⃣ **Chạy dự án**  
```bash
mvn spring-boot:run
```
4️⃣ **Truy cập ứng dụng**  
- **Trang đặt vé:** `http://localhost:8080`  
- **Trang admin:** `http://localhost:8080/admin` (Tài khoản Admin sẽ được tạo sẵn trong DB)  

---

## 🔥 **Hướng Dẫn Sử Dụng**  
### **👥 Đối với người dùng**  
1. Truy cập trang chủ, chọn phim và suất chiếu.  
2. Chọn ghế, thêm đồ ăn, nhập mã giảm giá.  
3. Xác nhận và hoàn tất đặt vé.  

### **🔧 Đối với Admin**  
- Truy cập `/admin`, đăng nhập với tài khoản quản trị.  
- Thêm/sửa/xóa suất chiếu, phim, phòng chiếu, vé, tài khoản, đồ ăn.  

---

## 🛡 **Bảo Mật & Phân Quyền**  
- **Người dùng:** Chỉ có thể đặt vé, không truy cập trang admin.  
- **Quản trị viên:** Có thể quản lý toàn bộ hệ thống.  
- **Spring Security:** Mã hóa mật khẩu với **BCrypt**.  

---

## 📌 **Tác Giả & Đóng Góp**  
- **Tác giả:** *Nguyễn Thái Bảo*  
- **Đóng góp:** Nếu bạn muốn cải thiện dự án, hãy tạo **Pull Request** hoặc mở **Issue**!  

---

## ⭐ **Liên Hệ & Hỗ Trợ**  
- Email: *thaibaoo2105@gmail.com*  
- GitHub: [baovirus](https://github.com/baovirus)  
