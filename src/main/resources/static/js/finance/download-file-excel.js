// function downloadClaimsRequestByExcel() {
//
//     var header = [];
//     document.querySelectorAll("#status-paid thead tr th").forEach(function (th) {
//         header.push(th.textContent);
//     });
//
//     var claimsRequest = [];
//     document.querySelectorAll("#status-paid tbody tr").forEach(function (e) {
//         var row = [];
//         e.querySelectorAll("td").forEach(function (iteam) {
//             row.push(iteam.textContent);
//         });
//         claimsRequest.push(row);
//     });
//
//     var workbook = XLSX.utils.book_new();
//
//     var worksheet = XLSX.utils.aoa_to_sheet([header].concat(claimsRequest));
//
//     XLSX.utils.book_append_sheet(workbook, worksheet, "claims");
//
//     var excelFile = XLSX.write(workbook, {
//         bookType: "xlsx",
//         type: "array",
//     });
//
//     var blob = new Blob([excelFile], {
//         type: "application/octet-stream",
//     });
//
//     var link = document.createElement("a");
//     link.href = URL.createObjectURL(blob);
//     link.download = "claimsRequest.xlsx";
//     link.click();
// }
//
// document.getElementById("downloadFile").addEventListener("click", function () {
//     downloadClaimsRequestByExcel();
// });

function downloadClaimsRequestByExcel() {
    var header = [];
    document.querySelectorAll("#status-paid thead tr th").forEach(function (th) {
        header.push(th.textContent);
    });

    var claimsRequest = [];
    // Lặp qua mỗi hàng trong phần thân của bảng
    document.querySelectorAll("#status-paid tbody tr").forEach(function (row) {
        // Kiểm tra xem checkbox trong hàng đó đã được chọn hay không
        var checkbox = row.querySelector('input[type="checkbox"]');
        if (checkbox.checked) {
            var rowData = [];
            // Lặp qua mỗi ô trong hàng
            row.querySelectorAll("td").forEach(function (item) {
                rowData.push(item.textContent);
            });
            claimsRequest.push(rowData);
        }
    });

    // Tiếp tục với việc tạo tệp Excel chỉ cho các hàng đã chọn
    if (claimsRequest.length > 0) {
        var workbook = XLSX.utils.book_new();
        var worksheet = XLSX.utils.aoa_to_sheet([header].concat(claimsRequest));
        XLSX.utils.book_append_sheet(workbook, worksheet, "claims");

        var excelFile = XLSX.write(workbook, {
            bookType: "xlsx",
            type: "array",
        });

        var blob = new Blob([excelFile], {
            type: "application/octet-stream",
        });

        var link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "claimsRequest.xlsx";
        link.click();
    } else {
        // Nếu không có hàng nào được chọn, hiển thị một cảnh báo hoặc xử lý nó tùy thuộc vào
        alert("Vui lòng chọn ít nhất một hàng để tải xuống.");
    }
}

document.getElementById("downloadFile").addEventListener("click", function () {
    downloadClaimsRequestByExcel();
});
document.getElementById("click-full").addEventListener("click", function () {
    // Lặp qua tất cả các ô kiểm trên trang và chuyển chúng sang trạng thái đã chọn
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = true;
    });
});
