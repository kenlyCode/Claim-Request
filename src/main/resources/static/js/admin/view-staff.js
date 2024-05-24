$().ready(function () {
});

const currentPath = window.location.pathname;
const pathElements = currentPath.split('/');
const lastElement = pathElements[pathElements.length - 1];

function getOneInfoStaff() {
    $.ajax({
        url: 'http://localhost:9090/api/staff/' + lastElement + '/admin',
        type: "GET",
        dataType: "json",
        success: function (response) {
            document.getElementById("id").value  = response.id;
            document.getElementById("name").value  = response.name;
            document.getElementById("department").value  = response.department;
            document.getElementById("rank").value = response.rank;
            document.getElementById("salary").value = response.salary;
            document.getElementById("email").value = response.email;
        },
        error: function (xhr, status, error){
        }
    });
}
getOneInfoStaff()

document.addEventListener("DOMContentLoaded", function() {
// Khi form được submit
    document.getElementById("oneStaffView").onsubmit = function(e) {
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