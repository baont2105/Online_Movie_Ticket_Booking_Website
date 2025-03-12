let selectedSeats = [];

function selectSeat(seatElement) {
    let seatId = seatElement.getAttribute("data-seat-id");

    // Kiểm tra nếu ghế đã chọn thì bỏ chọn, ngược lại chọn
    if (selectedSeats.includes(seatId)) {
        selectedSeats = selectedSeats.filter(id => id !== seatId);
        seatElement.classList.remove("selected");
    } else {
        selectedSeats.push(seatId);
        seatElement.classList.add("selected");
    }

    // Cập nhật danh sách ghế vào input ẩn
    document.getElementById("selectedSeats").value = selectedSeats.join(",");
}
