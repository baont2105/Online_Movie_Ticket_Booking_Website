document.addEventListener("DOMContentLoaded", function () {
    let selectedSeats = []; // Danh sách ghế đã chọn
    let total = 0; // Tổng tiền

    document.querySelectorAll(".seat").forEach(seat => {
        seat.addEventListener("click", function () {
            toggleSeat(this); // Xử lý chọn/bỏ chọn ghế
            updateSelectedSeats(); // Cập nhật danh sách ghế đã chọn
        });
    });

    function toggleSeat(seatElement) {
        let seatId = seatElement.getAttribute("data-seat-id"); // ID ghế trong DB
        let seatCode = seatElement.getAttribute("data-id"); // Mã ghế (A1, B2, ...)
        let seatType = seatElement.getAttribute("data-type");
        let price = seatType === "VIP" ? 20000 : 0;

        let seatIndex = selectedSeats.findIndex(s => s.id === seatId); // Tìm theo seatId

        if (seatIndex !== -1) {
            // Nếu đã chọn ghế này rồi -> Bỏ chọn
            selectedSeats.splice(seatIndex, 1); // Xóa khỏi danh sách
            total -= price + 60000;
            seatElement.classList.remove("selected");
        } else {
            // Nếu chưa chọn -> Thêm vào danh sách
            selectedSeats.push({ id: seatId, code: seatCode, type: seatType, price: price });
            total += price + 60000;
            seatElement.classList.add("selected");
        }

        console.log("Danh sách ghế đã chọn:", selectedSeats);
    }

    function updateSelectedSeats() {
        let aside = document.querySelector("#selected-seats");
        let totalPrice = document.querySelector("#total-price");

        // Xóa nội dung cũ và cập nhật danh sách ghế mới
        aside.innerHTML = selectedSeats.map(seat => `<div>${seat.code} - ${seat.type}</div>`).join("");

        // Cập nhật tổng tiền
        totalPrice.textContent = total.toLocaleString() + " VND";

        // Lưu danh sách **id ghế** vào input ẩn (không phải mã ghế)
        document.getElementById("selectedSeats").value = selectedSeats.map(s => s.id).join(",");
    }
});
