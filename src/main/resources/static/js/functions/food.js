document.addEventListener("DOMContentLoaded", function () {
    const foodQuantities = document.querySelectorAll(".food-quantity");
    const selectedFoodsList = document.getElementById("selectedFoodsList");
    const totalPriceEl = document.getElementById("totalPrice");
    const confirmButton = document.getElementById("confirmButton");

    function updateSelectedFoods() {
        selectedFoodsList.innerHTML = "";
        let totalPrice = 0;
        let hasSelected = false;

        foodQuantities.forEach(input => {
            const quantity = parseInt(input.value);
            if (quantity > 0) {
                hasSelected = true;
                const foodName = input.dataset.foodName;
                const foodPrice = parseInt(input.dataset.foodPrice);
                const totalItemPrice = quantity * foodPrice;
                totalPrice += totalItemPrice;

                const li = document.createElement("li");
                li.classList.add("d-flex", "justify-content-between", "mb-2");
                li.innerHTML = `<span>${foodName} (x${quantity})</span> <strong>${totalItemPrice} VND</strong>`;
                selectedFoodsList.appendChild(li);
            }
        });

        totalPriceEl.textContent = totalPrice.toLocaleString();
        return hasSelected; // Trả về true nếu có món đã chọn
    }

    // Xử lý sự kiện khi nhấn nút "Tiếp tục"
    confirmButton.addEventListener("click", function (event) {
        const hasSelected = updateSelectedFoods(); // Kiểm tra lại số lượng món
        if (!hasSelected) {
            event.preventDefault();
            alert("Bạn chưa chọn món nào!");
        }
    });

    // Sự kiện tăng/giảm số lượng
    document.querySelectorAll(".btn-increase").forEach(button => {
        button.addEventListener("click", function () {
            let input = this.previousElementSibling;
            if (input.value < 10) {
                input.value = parseInt(input.value) + 1;
                updateSelectedFoods();
            }
        });
    });

    document.querySelectorAll(".btn-decrease").forEach(button => {
        button.addEventListener("click", function () {
            let input = this.nextElementSibling;
            if (input.value > 0) {
                input.value = parseInt(input.value) - 1;
                updateSelectedFoods();
            }
        });
    });

    // Cập nhật khi nhập số lượng món thủ công
    foodQuantities.forEach(input => {
        input.addEventListener("input", () => {
            if (input.value < 0) input.value = 0;
            if (input.value > 10) input.value = 10;
            updateSelectedFoods();
        });
    });

    updateSelectedFoods(); // Gọi lần đầu để cập nhật danh sách
});
