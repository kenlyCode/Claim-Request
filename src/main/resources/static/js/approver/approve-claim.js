// Table in viewAll
$('#vettingTable').DataTable({
    pagingType: 'simple_numbers'
});

var staffId;

$(document).ready(function () {
    function getAllClaimsRequest(staffId){
        $('#vettingTable').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/pending-approved/staff/'+ staffId + '/approver' ,
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
                getAllClaimsRequest(staffId);
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

function sendRequest(status) {
    let claimsRequest = {
        id: parseInt(document.getElementById("idModal").value),
        status: status,
        date: document.getElementById("dateModal").value,
        day: document.getElementById("dayModal").value,
        fromTime: document.getElementById("fromTimeModal").value,
        toTime: document.getElementById("toTimeModal").value,
        totalHours: document.getElementById("totalHoursModal").value,
        remarks: document.getElementById("remarksModal").value,
        staffDTO: {
            id: parseInt(document.getElementById("staffDTOIdModal").value),
            name: document.getElementById("staffDTONameModal").value
        },
        projectDTO: {
            id: parseInt(document.getElementById("projectDTOIdModal").value),
            name: document.getElementById("projectDTONameModal").value
        }
    };

    $.ajax({
        url: "http://localhost:9090/api/claim",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(claimsRequest),
        success: function (response) {
            if (status === "STATUS_CANCELLED") {
                alert("Rejected successfully!");
            } else if (status === "STATUS_APPROVED") {
                alert("Approved successfully!");
            } else if (status === "STATUS_DRAFT") {
                alert("Return Draft successfully!");
            }
            window.location.href = "approve-claim";
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });
}

document.getElementById("rejectBtn").addEventListener("click", function () {
    sendRequest("STATUS_CANCELLED");
});

document.getElementById("approveBtn").addEventListener("click", function () {
    sendRequest("STATUS_APPROVED");
});

document.getElementById("returnBtn").addEventListener("click", function () {
    sendRequest("STATUS_DRAFT");
});