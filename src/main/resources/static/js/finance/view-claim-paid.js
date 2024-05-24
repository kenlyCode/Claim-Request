
$('#status-paid').DataTable({
    pagingType: 'simple_numbers',
});

// const currentPath = window.location.pathname;
// const pathElements = currentPath.split('/');
// const lastElement = pathElements[pathElements.length - 1];
$(document).ready(function () {

    function PaidViewAll(staffId) {

    $('#status-paid').DataTable({
        ajax: {
            url: 'http://localhost:9090/api/claim/paid/staff/' + staffId + '/finance',
            dataSrc: ''
        },
        "bDestroy": true,
        columns: [
            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>' + row.id + '</span>';
                }
            },
            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>' + row.staffDTO.name + '</span>';
                }},

            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>' + row.projectDTO.name + '</span>';
                }},
            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<p class="mb-0">'+'From '+ row.projectDTO.fromDate.slice(0, 10)+ '</p>'+
                        '<p class="mb-0">'+'To '+ row.projectDTO.toDate.slice(0, 10)+'</p>'
                        ;
                }},



            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>' + row.date.slice(0, 10) + '</span>';
                }},

            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>' + row.day + '</span>';
                }},

            {data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<span>'+ row.fromTime +'-'+ row.toTime +'</span>';
                }},
            {data:'totalHours'},
            {
                data: null,
                orderable: false,
                render: function (data, type, row) {
                    return '<input type="checkbox" class="row-checkbox w-100" value="' + row.id + '">';
                }
            }
        ]
    });


    $('#downloadFile').on('click', function () {
            var selectedIds = [];
            $('.row-checkbox:checked').each(function () {
                selectedIds.push($(this).val());
            });

            // Here you can perform actions with the selected IDs, like downloading data related to those IDs
            console.log('Selected IDs:', selectedIds);
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
                PaidViewAll(staffId);
            }
        }
    });


});





