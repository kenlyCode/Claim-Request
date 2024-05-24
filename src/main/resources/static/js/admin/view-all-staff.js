// Table in viewAll
$('#allStaff').DataTable({
    pagingType: 'simple_numbers'
});

$(document).ready(function () {
    $('#allStaff').DataTable({
        ajax: {
            url: 'http://localhost:9090/api/staff/admin',
            dataSrc: ''
        },
        "bDestroy": true,
        columns: [
            {data: 'id'},
            {data: 'name'},
            {data: 'department'},
            {data: 'rank'},
            {data: 'email'},
            {
                data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<button value="' + row.id + '" id="updateBtn" onclick="redirectToView(' + row.id + ')" class="btn btn-primary">View</button>';
                }
            }
        ]
    });
});

function redirectToView(id) {
    window.location.href = "view-staff/" + id; // Thay thế edit-view-url bằng URL của trang edit view
}







