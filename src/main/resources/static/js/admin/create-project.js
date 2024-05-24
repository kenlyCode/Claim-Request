document.getElementById("createProject").addEventListener("click", function () {
    // Tạo đối tượng projectData và điền thông tin vào từ các input hoặc form tương ứng
    let projectData = {
        projectDTO: {
            // Thông tin về dự án
            id: null, // Không cần điền id vì nó sẽ được tạo tự động
            name: document.getElementById("projectName").value, // Lấy tên dự án từ input có id là "projectName"
            code: document.getElementById("projectCode").value, // Lấy mã dự án từ input có id là "projectCode"
            fromDate: new Date(document.getElementById("fromDate").value), // Lấy ngày bắt đầu từ input có id là "fromDate"
            toDate: new Date(document.getElementById("toDate").value) // Lấy ngày kết thúc từ input có id là "toDate"
        },
        workingStaffDTOS: [
            // Thông tin về nhân viên làm việc trong dự án
            {
                name: document.getElementById("staffName").value, // Lấy tên nhân viên từ input có id là "staffName"
                status: null, // Không điền status vì không có thông tin cụ thể
                role: document.getElementById("staffRole").value // Lấy vai trò của nhân viên từ input có id là "staffRole"
            }
            // Bạn có thể thêm các đối tượng nhân viên khác vào đây nếu cần
        ]
    };

    $.ajax({
        url: "http://localhost:9090/api/createProject",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(projectData),
        success: function (response) {
            alert("Submit thành công!");
            window.location.href = "http://localhost:9090/";
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi từ server
        }
    });
});
