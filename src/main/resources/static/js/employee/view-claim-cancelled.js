


const currentPath = window.location.pathname;
const pathElements = currentPath.split('/');
const lastElement = pathElements[pathElements.length - 1];

$('#status-cancelled').DataTable({
    pagingType: 'simple_numbers',
});

$(document).ready(function () {

    function getAllClaimsRequest(staffId) {


        $('#status-cancelled').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/cancelled-or-rejected/staff/' + staffId + '/employee',
                dataSrc: ''
            },
            "bDestroy": true,
            columns: [
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row, meta) {
                        return meta.row + 1; // Thêm 1 để bắt đầu từ số 1
                    }
                },
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.staffDTO.name + '</span>';
                    }
                },

                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.projectDTO.name + '</span>';
                    }
                },
                {data: 'day'},
                {data: 'date'},
                {data: 'fromTime'},
                {data: 'toTime'},
                {data: 'totalHours'}
            ]
        });
    }

    $.ajax({
        url: "http://localhost:9090/api/current-user",
        type: "GET",
        dataType: "json",
        success: function (staffIdResponse) {
            // Nếu staffId có sẵn, thì gọi API khác
            if (staffIdResponse !== null && staffIdResponse !== undefined) {
                var staffId = staffIdResponse;
                getAllClaimsRequest(staffId);
            }
        }
    });






});