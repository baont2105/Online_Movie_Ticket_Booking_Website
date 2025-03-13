document.addEventListener("DOMContentLoaded", function () {
    const foodQuantities = document.querySelectorAll(".food-quantity");
    const selectedFoodsList = document.getElementById("selectedFoodsList");
    const totalPriceEl = document.getElementById("totalPrice");
    const confirmButton = document.getElementById("confirmButton");

    function updateSelectedFoods() {
        let selectedText = "";
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

                selectedText += `${foodName} (x${quantity}) - ${totalItemPrice.toLocaleString()} VND\n`;
            }
        });

        selectedFoodsList.value = selectedText; // Cập nhật danh sách trong textarea
        totalPriceEl.textContent = totalPrice.toLocaleString() + " VND";

        return hasSelected;
    }

    // Kiểm tra trước khi tiếp tục
    confirmButton.addEventListener("click", function (event) {
        if (!updateSelectedFoods()) {
            event.preventDefault();
            alert("Bạn chưa chọn món nào!");
        }
    });

    // Xử lý sự kiện tăng/giảm số lượng
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
            input.value = Math.max(0, Math.min(10, input.value));
            updateSelectedFoods();
        });
    });

    updateSelectedFoods(); // Gọi lần đầu để cập nhật danh sách
});
