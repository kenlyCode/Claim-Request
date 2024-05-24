//
// const currentPath = window.location.pathname;
// const pathElements = currentPath.split('/');
// const lastElement = pathElements[pathElements.length - 1];

$('#status-draft').DataTable({
    pagingType: 'simple_numbers',
});

$(document).ready(function () {

    function getAllClaimsRequest(staffId) {

        $('#status-draft').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/draft/staff/' + staffId + '/employee',
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
                        return '<p class="mb-0">' + 'From ' + row.projectDTO.fromDate.slice(0, 10) + '</p>' +
                            '<p class="mb-0">' + 'To ' + row.projectDTO.toDate.slice(0, 10) + '</p>'
                            ;
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
                        return '<div class="d-flex"> '+'<a href="http://localhost:9090/employee/update-claim/' + row.id + '" class="btn btn-primary mr-1">Edit</a> ' +
                            ' <div class="btn-group">\n' +
                            '  <button type="button" class="btn btn-secondary dropdown-toggle " data-toggle="dropdown" aria-expanded="false">\n' +
                            '    Choice\n' +
                            '  </button>\n' +
                            '  <div class="dropdown-menu dropdown-menu-right py-0">\n' +
                            '<button class="btn btn-success w-100 round-0 my-1" onclick="submitClaimRequest(' + row.id + ', \'' + row.date + '\', \'' + row.day + '\', \'' + row.fromTime + '\', \'' + row.toTime + '\', \'' + row.totalHours + '\', \'' + row.remarks + '\', \'' + row.staffDTO.id + '\', \'' + row.projectDTO.id + '\')" class="btn btn-primary">Submit</button>' +
                            '<button class="btn btn-danger w-100 round-0" onclick="cancellClaim(' + row.id + ', \'' + row.date + '\', \'' + row.day + '\', \'' + row.fromTime + '\', \'' + row.toTime + '\', \'' + row.totalHours + '\', \'' + row.remarks + '\', \'' + row.staffDTO.id + '\', \'' + row.projectDTO.id + '\')" class="btn btn-primary">Cancelled</button>' +
                            '  </div>\n' +
                            '</div>'+'' +
                            '</div>';
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
              getAllClaimsRequest(staffId);
            }
        }
    });

});

function submitClaimRequest(id, date, day, fromTime, toTime, totalHours, remarks, staffId, projectId) {
    let claimsRequest = {
        id: parseInt(id),
        status: "STATUS_PENDING_APPROVAL",
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

    // Tiếp tục với ajax và các xử lý khác...



    $.ajax({
        url: "http://localhost:9090/api/claim",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(claimsRequest),
        success: function (response) {
            alert("Save success!");
            window.location.href = "/employee/view-claim-draft";
            // window.location.href = "/claimsRequest/create/" + lastElement;
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });


}
function cancellClaim(id, date, day, fromTime, toTime, totalHours, remarks, staffId, projectId) {
    let Request = {
        id: parseInt(id),
        status: "STATUS_CANCELLED",
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

    // Tiếp tục với ajax và các xử lý khác...



    $.ajax({
        url: "http://localhost:9090/api/claim",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(Request),
        success: function (response) {
            alert("Save success!");
            window.location.href = "/employee/view-claim-draft";
            // window.location.href = "/claimsRequest/create/" + lastElement;
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });


}



