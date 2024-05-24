
$('#status-approved').DataTable({
    pagingType: 'simple_numbers',
});

// const currentPath = window.location.pathname;
// const pathElements = currentPath.split('/');
// const lastElement = pathElements[pathElements.length - 1];
$(document).ready(function() {

    function PaidViewAllStatusApproved(staffId) {

        $('#status-approved').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/approved/staff/' + staffId + '/finance',
                dataSrc: ''
            },
            "bDestroy": true,
            columns: [
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.id + '</span>';
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
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.projectDTO.fromDate.slice(0, 10) + "->" + row.projectDTO.toDate.slice(0, 10) + '</span>';
                    }
                },


                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.date.slice(0, 10) + '</span>';
                    }
                },

                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.day + '</span>';
                    }
                },

                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<span>' + row.fromTime + '-' + row.toTime + '</span>';
                    }
                },
                {data: 'totalHours'},
                {
                    data: null,
                    orderable: false,
                    render: function (data, type, row) {
                        return '<button onclick="returnStatusPaid(' + row.id + ', \'' + row.date + '\', \'' + row.day + '\', \'' + row.fromTime + '\', \'' + row.toTime + '\', \'' + row.totalHours + '\', \'' + row.remarks + '\', \'' + row.staffDTO.id + '\', \'' + row.projectDTO.id + '\')" class="btn btn-primary">Paid</button>';

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
                var staffId = staffIdResponse;
                PaidViewAllStatusApproved(staffId);
            }
        }
    });

});

function returnStatusPaid(id, date, day, fromTime, toTime, totalHours, remarks, staffId, projectId) {
    let paidRequest = {
        id: parseInt(id),
        status: "STATUS_PAID",
        date: date,
        day: day,
        fromTime: fromTime,
        toTime: toTime,
        totalHours: totalHours,
        remarks: remarks,
        staffDTO: {
            id: parseInt(staffId)
        },
        projectDTO: projectId ?
            {id: parseInt(projectId)} :
            null
    };

    $.ajax({
        url: "http://localhost:9090/api/claim",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(paidRequest),
        success: function (response) {
            alert("Save success!");
            window.location.href = "/finance/paid-claim";
            // window.location.href = "/claimsRequest/create/" + lastElement;
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });


}









