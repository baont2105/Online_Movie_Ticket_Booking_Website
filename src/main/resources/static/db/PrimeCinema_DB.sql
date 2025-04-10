-- Đảm bảo không có kết nối nào đang sử dụng database trước khi xóa
USE master;
ALTER DATABASE Prime_Cinema_DB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

-- Xóa database
DROP DATABASE IF EXISTS Prime_Cinema_DB;
GO

-- Tạo database
CREATE DATABASE Prime_Cinema_DB;
GO

USE Prime_Cinema_DB;
GO

-- Bảng Users (Tài khoản)
CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    username NVARCHAR(255) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
    phone_number NVARCHAR(20),
    role NVARCHAR(20) CHECK (role IN ('ADMIN', 'STAFF', 'USER')) NOT NULL,
    visible BIT DEFAULT 1
);

-- Bảng Categories (Thể loại phim)
CREATE TABLE Categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) UNIQUE NOT NULL
);

-- Bảng Movies (Phim)
CREATE TABLE Movies (
    movie_id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    tags NVARCHAR(255),
    duration INT NOT NULL,
    release_date DATE NOT NULL,
    end_date DATE NOT NULL,
    view_count BIGINT DEFAULT 0,
    country NVARCHAR(100),
    producer NVARCHAR(255),
    director NVARCHAR(255),
    actors NVARCHAR(MAX),
    description NVARCHAR(MAX),
    thumbnail NVARCHAR(255),
    trailer NVARCHAR(500),
    
    CONSTRAINT FK_Movies_Category FOREIGN KEY (category_id) REFERENCES Categories(category_id) ON DELETE CASCADE
);

-- Bảng Branches (Chi nhánh)
CREATE TABLE Branches (
    branch_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    address NVARCHAR(255) NOT NULL,
    phone_number NVARCHAR(20),
    email NVARCHAR(255),
    city NVARCHAR(MAX),
    visible BIT DEFAULT 1
);

-- Bảng Rooms (Phòng chiếu)
CREATE TABLE Rooms (
    room_id INT IDENTITY(1,1) PRIMARY KEY,
    branch_id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    visible BIT DEFAULT 1,
    FOREIGN KEY (branch_id) REFERENCES Branches(branch_id) ON DELETE CASCADE
);

-- Bảng Seats (Ghế)
CREATE TABLE Seats (
    seat_id INT IDENTITY(1,1) PRIMARY KEY,
    room_id INT NOT NULL,
    row_number NVARCHAR(5) NOT NULL,
    seat_number NVARCHAR(10) NOT NULL,
    seat_type NVARCHAR(50) CHECK (seat_type IN ('VIP', 'Standard')) DEFAULT 'Standard', 
    price INT NOT NULL CHECK (price >= 0),
    status NVARCHAR(20) CHECK (status IN ('AVAILABLE', 'BOOKED', 'BLOCKED')) DEFAULT 'AVAILABLE',
    visible BIT DEFAULT 1,
    FOREIGN KEY (room_id) REFERENCES Rooms(room_id) ON DELETE NO ACTION -- Tránh lỗi vòng lặp
);

-- Bảng Showtimes (Suất chiếu)
CREATE TABLE Showtimes (
    showtime_id INT IDENTITY(1,1) PRIMARY KEY,
    movie_id INT NOT NULL,
    branch_id INT NOT NULL,
    room_id INT NOT NULL,
    show_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    price INT NOT NULL CHECK (price >= 0),
    visible BIT DEFAULT 1,
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (branch_id) REFERENCES Branches(branch_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES Rooms(room_id) ON DELETE NO ACTION -- Tránh lỗi vòng lặp
);

-- Bảng Tickets (Vé)
CREATE TABLE Tickets (
    ticket_id INT IDENTITY(1,1) PRIMARY KEY,
    showtime_id INT NOT NULL,
    user_id INT NULL,
    price DECIMAL(10,2) NOT NULL,
    ticket_status NVARCHAR(50) CHECK (ticket_status IN ('NOT_CHECKED_IN', 'CHECKED_IN')) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    
    CONSTRAINT FK_Tickets_Showtime FOREIGN KEY (showtime_id) REFERENCES Showtimes(showtime_id) ON DELETE CASCADE,
    CONSTRAINT FK_Tickets_User FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE SET NULL
);

-- Bảng Ticket_Seats (Liên kết Vé - Ghế ngồi)
CREATE TABLE Ticket_Seats (
    ticket_seat_id INT IDENTITY(1,1) PRIMARY KEY,
    ticket_id INT NOT NULL,
    seat_id INT NOT NULL,
    
    CONSTRAINT FK_TicketSeats_Ticket FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id) ON DELETE CASCADE,
    CONSTRAINT FK_TicketSeats_Seat FOREIGN KEY (seat_id) REFERENCES Seats(seat_id) ON DELETE CASCADE,
    CONSTRAINT UQ_Ticket_Seat UNIQUE (ticket_id, seat_id) -- Đảm bảo mỗi ghế chỉ thuộc 1 vé duy nhất
);

-- Bảng FoodItems (Đồ ăn)
CREATE TABLE FoodItems (
    food_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    image NVARCHAR(255),
    visible BIT DEFAULT 1
);

-- Bảng Ticket_Foods (Liên kết Vé - Đồ ăn)
CREATE TABLE Ticket_Foods (
    ticket_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0), -- Đảm bảo số lượng hợp lệ
    PRIMARY KEY (ticket_id, food_id),
    FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES FoodItems(food_id) ON DELETE CASCADE
);

-- Bảng Vouchers (Mã giảm giá)
CREATE TABLE Vouchers (
    voucher_id INT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(50) UNIQUE NOT NULL,
    discount_amount INT NOT NULL CHECK (discount_amount >= 0)
);

-- Bảng Ticket_Vouchers (Liên kết Vé - Mã giảm giá)
CREATE TABLE Ticket_Vouchers (
    ticket_id INT NOT NULL,
    voucher_id INT NOT NULL,
    PRIMARY KEY (ticket_id, voucher_id),
    FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES Vouchers(voucher_id) ON DELETE CASCADE
);
