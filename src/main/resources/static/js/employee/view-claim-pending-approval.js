
$('#staff-status-pending').DataTable({
    pagingType: 'simple_numbers',
});

// const currentPath = window.location.pathname;
// const pathElements = currentPath.split('/');
// const lastElement = pathElements[pathElements.length - 1];
$(document).ready(function () {

    function getAllClaimsRequest(staffId) {


        $('#staff-status-pending').DataTable({
            ajax: {
                url: 'http://localhost:9090/api/claim/pending-approval/staff/' + staffId + '/employee',
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


