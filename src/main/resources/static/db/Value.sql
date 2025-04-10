use Prime_Cinema_DB;

--BẢNG NGƯỜI DÙNG
INSERT INTO Users (name, username, password, email, phone_number, role, visible) VALUES
(N'Nguyễn Thái Bảo', 'thaibaoo2105', '$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa', 
'thaibaoo2105@gmail.com', '0362768611', 'ADMIN', 1),
(N'Nhân Viên', 'staff', '$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa', 
'staff@gmail.com', '0362768611', 'STAFF', 1),
(N'Người Dùng', 'user', '$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa', 
'user@gmail.com', '0362768611', 'USER', 1),
(N'TESTER', 'tester', '$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa', 
'tester@gmail.com', '0362768611', 'ADMIN', 1),
(N'Quản Trị Viên', 'admin', '$2a$10$B4WNFxY26ZqvRckrMXIS0ud2bimwdYUlxzhQJGups1dajH55FWEDa', 
'admin@gmail.com', '0362768611', 'ADMIN', 1);
go

-- BẢNG THỂ LOẠI
INSERT INTO Categories (name) VALUES 
(N'Hành động'), -- 1
(N'Phiêu lưu'), -- 2
(N'Kinh dị'), -- 3
(N'Giả tưởng'), -- 4
(N'Khoa học viễn tưởng'), -- 5
(N'Hài hước'), -- 6
(N'Tâm lý'), -- 7
(N'Lãng mạn'), -- 8
(N'Hình sự'), -- 9
(N'Chiến tranh'), -- 10
(N'Hoạt hình'), -- 11
(N'Tài liệu'), -- 12
(N'Gia đình'), -- 13
(N'Thể thao'),	-- 14
(N'Nhạc kịch'), -- 15
(N'Siêu anh hùng'); -- 16


-- BẢNG PHIM
INSERT INTO Movies (name, category_id, tags, duration, release_date, end_date, view_count, country, producer, director, actors, description, thumbnail, trailer)
VALUES 
(N'NHÀ GIA TIÊN', 13, N'18+', 117, '2025-02-21', '2025-03-18', 20000, N'Việt Nam', NULL, N'Huỳnh Lập', N'Huỳnh Lập, Phương Mỹ Chi, NSƯT Hạnh Thuý', 
 N'Nhà Gia Tiên xoay quanh câu chuyện đa góc nhìn về các thế hệ khác nhau trong một gia đình, có hai nhân vật chính là Gia Minh (Huỳnh Lập) và Mỹ Tiên (Phương Mỹ Chi)….', 
 N'nhagiatien.jpg', N'https://www.youtube.com/embed/hXGozmNBwt4?si=LJsGaQ1j-I-ZjWgL'),

(N'LẠC TRÔI', 11, N'13+', 84, '2025-03-07', '2025-04-03', 300000, N'Việt Nam', NULL, N'Gints Zilbalodis', N'Meo Meo, Gâu Gâu, Capybara Chuột Lang Nước', 
 N'Trước bối cảnh hậu tận thế, chú mèo xám nhút nhát, vốn luôn sợ nước phải rời bỏ vùng an toàn khi căn nhà thân yêu bị cuốn trôi...', 
 N'lactroi.png', N'https://www.youtube.com/embed/BRV1UmEszeI?si=FDyFevJ1tO5v6XKy'),

(N'CƯỚI MA', 3, N'18+', 97, '2025-02-28', '2025-05-02', 500000, N'Việt Nam', NULL, N'Azhar Kinoi Lubis', N'Taskya Namya, Wafda Saifan Lubis, Arla Ailani', 
 N'Mỗi ngày trong cuộc sống Ranti đều xoay quanh bởi sự cay nghiệt của mẹ chồng cùng những lời vu cáo của em dâu khiến cô vô cùng thống khổ. Mọi việc trở nên bùng nổ khi họ lần lượt phớt lờ….', 
 N'cuoima.png', N'https://www.youtube.com/embed/ZkW-Zb-EICA?si=UNVVeKr0gfo-_ELk'),

 (N'MỘT BỘ PHIM MINECRAFT', 4, N'13+', 99, '2025-04-04', '2025-05-15', 13210000, N'Mỹ', N'Warner BroS.', N'Jared Hess', 
 N'Jason Momoa, Jack Black, Emma Myers, Sebastian Eugene Hansen, Danielle Brooks,...', 
 N'Chào mừng bạn đến với thế giới của Minecraft, nơi sự sáng tạo không chỉ giúp bạn chế tạo mà còn là yếu tố quan trọng để sống sót! 
 Bốn kẻ lạc lõng - Garrett "The Garbage Man" Garrison (Momoa), Henry (Hansen), Natalie (Myers) và Dawn (Brooks) - bất ngờ gặp rắc rối 
 khi họ bị kéo qua cánh cửa bí ẩn dẫn đến Overworld: một thế giới kỳ lạ được tạo bởi những khối lập phương và phát triển nhờ vào trí tưởng tượng. 
 Để trở về nhà, họ cần phải làm chủ được thế giới này (và bảo vệ nó khỏi những thực thể tà ác như Piglins và Thây Ma) trong khi dấn thân vào chuyến phiêu lưu màu nhiệm 
 với một thợ chế tạo chuyên nghiệp và khó lường - Steve (Black). Chuyến hành trình này sẽ thách thức sự can đảm của cả năm người, thúc đẩy họ tìm lại với những phẩm chất 
 làm nên sự đặc biệt của riêng mình,... đồng thời là những kỹ năng cần thiết để trở lại với thế giới thật.', 
 N'minecraft.jpg', N'https://www.youtube.com/embed/8B1EtVPBSMw?si=to5ItcX-24bLHhO8'),

 (N'BÍ KÍP LUYỆN RỒNG', 5, N'16+', 99, '2025-06-13', '2025-07-29', 1300000, N'Mỹ', NULL, N'Dean DeBlois', N'Mason Thames, Nico Parker, Gerard Butler', 
 N'Câu chuyện về một chàng trai trẻ với ước mơ trở thành thợ săn rồng, nhưng định mệnh lại đưa đẩy anh đến tình bạn bất ngờ với một chú rồng.', 
 N'bikipluyenrong.png', N'https://www.youtube.com/embed/8eeOsDjJh8U?si=UXzjv49og6DKmjnI'),

(N'NÀNG BẠCH TUYẾT', 15, N'16+', 99, '2025-03-21', '2025-05-18', 1200000, N'Mỹ', NULL, N'Marc Webb', N'Gal Gadot, Rachel Zegler, Andrew Burnap', 
 N'Bản chuyển thể bộ phim hoạt hình nổi tiếng "Bạch Tuyết Và Bảy Chú Lùn" của Disney năm 1937.', 
 N'nangbachtuyet.png', N'https://www.youtube.com/embed/8eeOsDjJh8U?si=UXzjv49og6DKmjnI'),

(N'GIAO HÀNG CHO MA', 3, N'16+', 104, '2025-02-14', '2025-04-08', 900000, N'Thái Lan', NULL, N'Nitivat Cholvanichsiri', 
 N'Mario Maurer, Sarocha Jankimha, Marut Chuensomboon, Puwanet Seechompu', 
 N'Nat, một người giao hàng trẻ, tình cờ quen một cô gái xinh đẹp tên Pie, nhưng khi Pie biến mất một cách bí ẩn, Nat và hai người bạn giao hàng khác đã đi tìm cô ấy.….', 
 N'giaohangcho.png', N'https://www.youtube.com/embed/pmBC4bEWmgE?si=kmpEuZg0MlNiam1m'),

 (N'CHỐT ĐƠN!', 7, N'13+', 99, '2025-06-20', '2025-08-07', 1400000, N'Việt Nam', NULL, N'Bảo Nhân - Namcito', 
 N'Thuỳ Tiên, Quyền Linh, Hồng Đào, Hồng Vân, Khương Lê, Lê Lộc, Mai Bảo Vinh', 
 N'Chốt Đơn! xoay quanh cuộc sống tất bật của Hoàng Linh - nữ “chiến thần” chốt đơn trẻ đang thành công và chú An (Quyền Linh) 
  một nhân viên văn phòng kiêm tài xế xe ôm công nghệ. Giữa những mơ hồ, thay đổi, áp lực của xã hội lên hai thế hệ, Hoàng Linh và chú An.', 
 N'chotdon.png', N'https://www.youtube.com/embed/KHfhsOeFR4w?si=flic-HjahLoLDY7C'),

(N'ÂM DƯƠNG LỘ', 3, N'18+', 99, '2025-03-28', '2025-05-21', 1700000, N'Việt Nam', NULL, N'Hoàng Tuấn Cường', 
 N'Bạch Công Khanh, Lan Thy, Minh Hoàng, Tuấn Dũng, Đại Nghĩa, Hạnh Thúy', 
 N'Vì mưu sinh, một cử nhân thất nghiệp lén cha chở một thi thể nữ về Tây Nguyên ngay giữa đêm khuya. 
  Trên hành trình, anh buộc phải đối mặt với một thế giới siêu nhiên đầy rẫy vong linh, 
  một cô gái bí ẩn anh đem lòng tơ tưởng và một sự thật hắc ám gắn liền với chiếc xe cứu thương mà anh đang cầm lái.', 
 N'amduonglo.png', N'https://www.youtube.com/embed/n-5oC3oyXKE?si=hoiKpfZObnDyMQDY'),

(N'BUỔI HẸN HÒ KINH HOÀNG', 3, N'16+', 99, '2025-04-11', '2025-06-17', 1900000, N'Mỹ', NULL, N'Christopher Landon', 
 N'Brandon Sklenar, Meghann Fahy, Violett Beane', 
 N'Buổi hẹn hò đầu tiên sau nhiều năm của Violet nhanh chóng trở thành ác mộng khi cô nhận được những tin nhắn ẩn danh kỳ lạ. 
  Kẻ gửi đe dọa giết con trai và em gái cô nếu cô không làm theo yêu cầu của hắn. 
  Trong tuyệt vọng, Violet bị ép đến giới hạn cuối cùng: giết Henry, người đàn ông đang ngồi trước mặt cô.', 
 N'buoihenhokinhhoang.png', N'https://www.youtube.com/embed/UvbCVGP1WOQ?si=NLsfXCDoJBS8wWkb');
go

--BÀNG CHI NHÁNH
INSERT INTO Branches (name, address, phone_number, email, city) VALUES
(N'Prime Cinema Quang Trung', N'123 Quang Trung, Gò Vấp, TP. Hồ Chí Minh', '02812345678', 'quangtrung@primecinema.vn', N'TP. Hồ Chí Minh'),
(N'Prime Cinema Trịnh Văn Bô', N'456 Trịnh Văn Bô, Nam Từ Liêm, Hà Nội', '02487654321', 'trinhvanbo@primecinema.vn', N'Hà Nội');
GO

-- BẢNG PHÒNG CHIẾU
-- Thêm phòng chiếu
INSERT INTO Rooms (name, branch_id) VALUES
(N'Room 1', 1),
(N'Room 2', 1),
(N'Room 3', 2),
(N'Room 4', 2);
GO


-- BẢNG GHẾ
-- Room 1 (Chi nhánh TP. Hồ Chí Minh)
INSERT INTO Seats (room_id, row_number, seat_number, seat_type, price) VALUES
(1, 'A', 1, 'VIP', 20000), (1, 'A', 2,'VIP', 20000), (1, 'A', 3,'VIP', 20000), (1, 'A', 4,'VIP', 20000), (1, 'A', 5,'VIP', 20000),
(1, 'A', 6, 'VIP', 20000), (1, 'A', 7,'VIP', 20000), (1, 'A', 8,'VIP', 20000), (1, 'A', 9,'VIP', 20000), (1, 'A', 10,'VIP', 20000),
(1, 'B', 1,'Standard', 0), (1, 'B', 2,'Standard', 0), (1, 'B', 3,'Standard', 0), (1, 'B', 4,'Standard', 0), (1, 'B', 5,'Standard', 0),
(1, 'B', 6,'Standard', 0), (1, 'B', 7,'Standard', 0), (1, 'B', 8,'Standard', 0), (1, 'B', 9,'Standard', 0), (1, 'B', 10,'Standard', 0),
(1, 'C', 1,'Standard', 0), (1, 'C', 2,'Standard', 0), (1, 'C', 3,'Standard', 0), (1, 'C', 4,'Standard', 0), (1, 'C', 5,'Standard', 0),
(1, 'C', 6,'Standard', 0), (1, 'C', 7,'Standard', 0), (1, 'C', 8,'Standard', 0), (1, 'C', 9,'Standard', 0), (1, 'C', 10,'Standard', 0),
(1, 'D', 1,'Standard', 0), (1, 'D', 2,'Standard', 0), (1, 'D', 3,'Standard', 0), (1, 'D', 4,'Standard', 0), (1, 'D', 5,'Standard', 0),
(1, 'D', 6,'Standard', 0), (1, 'D', 7,'Standard', 0), (1, 'D', 8,'Standard', 0), (1, 'D', 9,'Standard', 0), (1, 'D', 10,'Standard', 0),
(1, 'E', 1,'Standard', 0), (1, 'E', 2,'Standard', 0), (1, 'E', 3,'Standard', 0), (1, 'E', 4,'Standard', 0), (1, 'E', 5,'Standard', 0),
(1, 'E', 6,'Standard', 0), (1, 'E', 7,'Standard', 0), (1, 'E', 8,'Standard', 0), (1, 'E', 9,'Standard', 0), (1, 'E', 10,'Standard', 0);
GO

INSERT INTO Seats (room_id, row_number, seat_number, seat_type, price) VALUES
(2, 'A', 1, 'VIP', 20000), (2, 'A', 2,'VIP', 20000), (2, 'A', 3,'VIP', 20000), (2, 'A', 4,'VIP', 20000), (2, 'A', 5,'VIP', 20000),
(2, 'A', 6, 'VIP', 20000), (2, 'A', 7,'VIP', 20000), (2, 'A', 8,'VIP', 20000), (2, 'A', 9,'VIP', 20000), (2, 'A', 10,'VIP', 20000),
(2, 'B', 1,'Standard', 0), (2, 'B', 2,'Standard', 0), (2, 'B', 3,'Standard', 0), (2, 'B', 4,'Standard', 0), (2, 'B', 5,'Standard', 0),
(2, 'B', 6,'Standard', 0), (2, 'B', 7,'Standard', 0), (2, 'B', 8,'Standard', 0), (2, 'B', 9,'Standard', 0), (2, 'B', 10,'Standard', 0),
(2, 'C', 1,'Standard', 0), (2, 'C', 2,'Standard', 0), (2, 'C', 3,'Standard', 0), (2, 'C', 4,'Standard', 0), (2, 'C', 5,'Standard', 0),
(2, 'C', 6,'Standard', 0), (2, 'C', 7,'Standard', 0), (2, 'C', 8,'Standard', 0), (2, 'C', 9,'Standard', 0), (2, 'C', 10,'Standard', 0),
(2, 'D', 1,'Standard', 0), (2, 'D', 2,'Standard', 0), (2, 'D', 3,'Standard', 0), (2, 'D', 4,'Standard', 0), (2, 'D', 5,'Standard', 0),
(2, 'D', 6,'Standard', 0), (2, 'D', 7,'Standard', 0), (2, 'D', 8,'Standard', 0), (2, 'D', 9,'Standard', 0), (2, 'D', 10,'Standard', 0),
(2, 'E', 1,'Standard', 0), (2, 'E', 2,'Standard', 0), (2, 'E', 3,'Standard', 0), (2, 'E', 4,'Standard', 0), (2, 'E', 5,'Standard', 0),
(2, 'E', 6,'Standard', 0), (2, 'E', 7,'Standard', 0), (2, 'E', 8,'Standard', 0), (2, 'E', 9,'Standard', 0), (2, 'E', 10,'Standard', 0);
GO
INSERT INTO Seats (room_id, row_number, seat_number, seat_type, price) VALUES
(3, 'A', 1, 'VIP', 20000), (3, 'A', 2,'VIP', 20000), (3, 'A', 3,'VIP', 20000), (3, 'A', 4,'VIP', 20000), (3, 'A', 5,'VIP', 20000),
(3, 'A', 6, 'VIP', 20000), (3, 'A', 7,'VIP', 20000), (3, 'A', 8,'VIP', 20000), (3, 'A', 9,'VIP', 20000), (3, 'A', 10,'VIP', 20000),
(3, 'B', 1,'Standard', 0), (3, 'B', 2,'Standard', 0), (3, 'B', 3,'Standard', 0), (3, 'B', 4,'Standard', 0), (3, 'B', 5,'Standard', 0),
(3, 'B', 6,'Standard', 0), (3, 'B', 7,'Standard', 0), (3, 'B', 8,'Standard', 0), (3, 'B', 9,'Standard', 0), (3, 'B', 10,'Standard', 0),
(3, 'C', 1,'Standard', 0), (3, 'C', 2,'Standard', 0), (3, 'C', 3,'Standard', 0), (3, 'C', 4,'Standard', 0), (3, 'C', 5,'Standard', 0),
(3, 'C', 6,'Standard', 0), (3, 'C', 7,'Standard', 0), (3, 'C', 8,'Standard', 0), (3, 'C', 9,'Standard', 0), (3, 'C', 10,'Standard', 0),
(3, 'D', 1,'Standard', 0), (3, 'D', 2,'Standard', 0), (3, 'D', 3,'Standard', 0), (3, 'D', 4,'Standard', 0), (3, 'D', 5,'Standard', 0),
(3, 'D', 6,'Standard', 0), (3, 'D', 7,'Standard', 0), (3, 'D', 8,'Standard', 0), (3, 'D', 9,'Standard', 0), (3, 'D', 10,'Standard', 0),
(3, 'E', 1,'Standard', 0), (3, 'E', 2,'Standard', 0), (3, 'E', 3,'Standard', 0), (3, 'E', 4,'Standard', 0), (3, 'E', 5,'Standard', 0),
(3, 'E', 6,'Standard', 0), (3, 'E', 7,'Standard', 0), (3, 'E', 8,'Standard', 0), (3, 'E', 9,'Standard', 0), (3, 'E', 10,'Standard', 0);
GO

INSERT INTO Seats (room_id, row_number, seat_number, seat_type, price) VALUES
(4, 'A', 1, 'VIP', 20000), (4, 'A', 2,'VIP', 20000), (4, 'A', 3,'VIP', 20000), (4, 'A', 4,'VIP', 20000), (4, 'A', 5,'VIP', 20000),
(4, 'A', 6, 'VIP', 20000), (4, 'A', 7,'VIP', 20000), (4, 'A', 8,'VIP', 20000), (4, 'A', 9,'VIP', 20000), (4, 'A', 10,'VIP', 20000),
(4, 'B', 1,'Standard', 0), (4, 'B', 2,'Standard', 0), (4, 'B', 3,'Standard', 0), (4, 'B', 4,'Standard', 0), (4, 'B', 5,'Standard', 0),
(4, 'B', 6,'Standard', 0), (4, 'B', 7,'Standard', 0), (4, 'B', 8,'Standard', 0), (4, 'B', 9,'Standard', 0), (4, 'B', 10,'Standard', 0),
(4, 'C', 1,'Standard', 0), (4, 'C', 2,'Standard', 0), (4, 'C', 3,'Standard', 0), (4, 'C', 4,'Standard', 0), (4, 'C', 5,'Standard', 0),
(4, 'C', 6,'Standard', 0), (4, 'C', 7,'Standard', 0), (4, 'C', 8,'Standard', 0), (4, 'C', 9,'Standard', 0), (4, 'C', 10,'Standard', 0),
(4, 'D', 1,'Standard', 0), (4, 'D', 2,'Standard', 0), (4, 'D', 3,'Standard', 0), (4, 'D', 4,'Standard', 0), (4, 'D', 5,'Standard', 0),
(4, 'D', 6,'Standard', 0), (4, 'D', 7,'Standard', 0), (4, 'D', 8,'Standard', 0), (4, 'D', 9,'Standard', 0), (4, 'D', 10,'Standard', 0),
(4, 'E', 1,'Standard', 0), (4, 'E', 2,'Standard', 0), (4, 'E', 3,'Standard', 0), (4, 'E', 4,'Standard', 0), (4, 'E', 5,'Standard', 0),
(4, 'E', 6,'Standard', 0), (4, 'E', 7,'Standard', 0), (4, 'E', 8,'Standard', 0), (4, 'E', 9,'Standard', 0), (4, 'E', 10,'Standard', 0);
GO


-- BẢNG SUẤT CHIẾU
-- Phim 1
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(1, 2, 3, '2025-03-10', '09:00:00', '11:00:00', 60000),
(1, 2, 4, '2025-03-10', '14:00:00', '16:00:00', 60000),
(1, 2, 3, '2025-03-10', '19:00:00', '21:00:00', 60000);

-- Phim 2
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(2, 2, 4, '2025-03-10', '10:30:00', '12:30:00', 60000),
(2, 2, 3, '2025-03-10', '15:30:00', '17:30:00', 60000),
(2, 2, 4, '2025-03-10', '20:30:00', '22:30:00', 60000);

-- Phim 3
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(3, 2, 3, '2025-03-11', '09:00:00', '11:00:00', 60000),
(3, 2, 4, '2025-03-11', '14:00:00', '16:00:00', 60000),
(3, 2, 3, '2025-03-11', '19:00:00', '21:00:00', 60000);

-- Phim 4
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(4, 2, 4, '2025-03-11', '10:30:00', '12:30:00', 60000),
(4, 2, 3, '2025-03-11', '15:30:00', '17:30:00', 60000),
(4, 2, 4, '2025-03-11', '20:30:00', '22:30:00', 60000);

-- Phim 5
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(5, 2, 3, '2025-03-12', '11:00:00', '13:00:00', 60000),
(5, 2, 4, '2025-03-12', '17:00:00', '19:00:00', 60000),
(5, 2, 3, '2025-03-12', '22:00:00', '00:00:00', 60000);

-- Phim 6
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(6, 1, 1, '2025-03-13', '09:00:00', '11:00:00', 60000),
(6, 1, 2, '2025-03-13', '14:00:00', '16:00:00', 60000),
(6, 1, 1, '2025-03-13', '19:00:00', '21:00:00', 60000);

-- Phim 7
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(7, 1, 2, '2025-03-13', '10:30:00', '12:30:00', 60000),
(7, 1, 1, '2025-03-13', '16:00:00', '18:00:00', 60000),
(7, 1, 2, '2025-03-13', '21:30:00', '23:30:00', 60000);

-- Phim 8
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(8, 1, 1, '2025-03-14', '09:30:00', '11:30:00', 60000),
(8, 1, 2, '2025-03-14', '14:30:00', '16:30:00', 60000),
(8, 1, 1, '2025-03-14', '19:30:00', '21:30:00', 60000);

-- Phim 9
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(9, 1, 2, '2025-03-14', '10:00:00', '12:00:00', 60000),
(9, 1, 1, '2025-03-14', '15:00:00', '17:00:00', 60000),
(9, 1, 2, '2025-03-14', '20:00:00', '22:00:00', 60000);

-- Phim 10
INSERT INTO Showtimes (movie_id, branch_id, room_id, show_date, start_time, end_time, price) VALUES
(10, 1, 1, '2025-03-15', '11:00:00', '13:00:00', 60000),
(10, 1, 2, '2025-03-15', '17:00:00', '19:00:00', 60000),
(10, 1, 1, '2025-03-15', '22:00:00', '00:00:00', 60000);

-- BẢNG ĐỒ ĂN
INSERT INTO FoodItems (name, description, price, image, visible) VALUES
(N'Bắp Đơn', N'1 phần bắp rang thơm ngon', 40000.00, 'single_popcorn.png', 1),
(N'Bắp Đôi', N'2 phần bắp rang thơm ngon', 60000.00, 'double_popcorn.png', 1),
(N'Khoai Tây Chiên', N'1 phần khoai tây chiên', 25000.00, 'french_fries.png', 1),
(N'Nước Ngọt Pepsi', N'1 lon nước ngọt Pepsi', 20000.00, 'pepsi.png', 1),
(N'Nước Ngọt 7 UP', N'1 lon nước ngọt 7 UP', 20000.00, '7up.png', 1),
(N'Nước Suối Aquafina', N'1 chai nước suối Aquafina', 15000.00, 'aquafina.png', 1);


-- BẢNG VOUNCHER
INSERT INTO Vouchers (code, discount_amount) VALUES
(N'WELCOME50', 50000),  -- Giảm 50,000 VNĐ cho khách hàng mới
(N'MOVIE20', 30000),    -- Giảm 30,000 VNĐ khi đặt vé xem phim
(N'COMBO15', 20000),    -- Giảm 20,000 VNĐ khi mua combo đồ ăn + vé
(N'VIP100', 100000),    -- Giảm 100,000 VNĐ (Voucher đặc biệt)
(N'WEEKEND10', 15000);  -- Giảm 15,000 VNĐ vào cuối tuần


-- BẢNG VÉ

-- BẢNG VÉ ĐỒ ĂN

-- BẢNG VÉ VOUNCHER

