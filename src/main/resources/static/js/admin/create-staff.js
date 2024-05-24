document.addEventListener("DOMContentLoaded", function() {
// Khi form được submit
    document.getElementById("createStaffForm").onsubmit = function(e) {
// Lấy giá trị của mật khẩu và mật khẩu nhập lại
        var password = document.getElementById("password").value;
        var rePassword = document.getElementById("rePassword").value;

// Kiểm tra xem chúng có giống nhau không
        if (password !== rePassword) {
// Nếu không, hiển thị thông báo lỗi và ngăn form không được submit
            alert("The re-entered password does not match! Please re-enter!");
            e.preventDefault(); // Ngăn không cho form submit
        }
    };
});