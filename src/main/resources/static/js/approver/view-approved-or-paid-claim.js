// Table in viewAll
$('#approvedOrPaidTable').DataTable({
    pagingType: 'simple_numbers'
});

var staffId;

$(document).ready(function () {
    function getAllClaimsRequest(staffId,status){
        $('#approvedOrPaidTable').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/' + status.toLowerCase() + '/staff/'+ staffId + '/approver' ,
                dataSrc: ''
            },
            "bDestroy": true,
            columns: [
                {data: 'id'},
                {data: 'date'},
                {data: 'day'},
                {data: 'fromTime'},
                {data: 'toTime'},
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<button value="' + row.id + '" id="viewClaimBtn" onclick="getOneInfoClaim(' + row.id + ')" type="button" class="btn btn-primary addMore" data-toggle="modal" data-target="#myModal">View Detail of Claim</button>';
                    }
                }
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
                staffId = staffIdResponse;
                // Lắng nghe sự kiện thay đổi của dropdown
                $('#approvedOrPaidSelect').on('change', function() {
                    let selectedStatus = $(this).val();
                    getAllClaimsRequest(staffId, selectedStatus);
                });

                // Gọi hàm với giá trị mặc định
                let initialStatus = $('#approvedOrPaidSelect').val();
                getAllClaimsRequest(staffId, initialStatus);
            }
        }
    });
});

function getOneInfoClaim(value) {
    $.ajax({
        url: 'http://localhost:9090/api/claim/' + value + '/employee',
        type: "GET",
        dataType: "json",
        success: function (response) {
            console.log(response);
            if (response) {
                $('#idModal').val(response.id);
                $('#statusModal').val(response.status);
                $('#dateModal').val(response.date);
                $('#dayModal').val(response.day);
                $('#fromTimeModal').val(response.fromTime);
                $('#toTimeModal').val(response.toTime);
                $('#totalHoursModal').val(response.totalHours);
                $('#remarksModal').val(response.remarks);
                $('#staffDTOIdModal').val(response.staffDTO.id);
                $('#staffDTONameModal').val(response.staffDTO.name);
                $('#projectDTOIdModal').val(response.projectDTO.id);
                $('#projectDTONameModal').val(response.projectDTO.name);
            } else {
                alert("no data found for the provided id.")
            }
        },
        error: function (xhr, status, error){
        }
    });
}