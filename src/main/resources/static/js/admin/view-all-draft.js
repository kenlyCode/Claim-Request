
$('#admin-draft').DataTable({
    pagingType: 'simple_numbers',
});

$(document).ready(function () {

    function getAllClaimsRequest(staffId) {

        $('#admin-draft').DataTable({
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