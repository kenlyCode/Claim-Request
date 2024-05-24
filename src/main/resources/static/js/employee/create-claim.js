$().ready(function () {
});

function goBack() {
    window.history.back();
}

const dateOutput = document.getElementById("dateOutput");
const dayOutput = document.getElementById("dayOutput");
const fromOutput = document.getElementById("fromOutput");
const toOutput = document.getElementById("toOutput");
const hourOutput = document.getElementById("totalOutput");
const remarkOutput = document.getElementById("remarkOutput");
const status = document.getElementById("status");
document.getElementById("dateInput").addEventListener("change", function () {
    let date = this.value;
    let day = new Date(date).toLocaleDateString("en-US", {weekday: "long"});
    document.getElementById("dayInput").value = day;
});

let claimsRequestDTO = [];
let createClaimsRequestDTO = [];
let checkBolleanDate = true;
let checkBolleanDateAdvance = true;
let checkBolleanDateAdvanceCross = true;
let checkBolleanHours = true;
let checkBolleanFrom = true;
let checkBolleanTo = true;
let checkBoolean = true
let dateInput;
let booleanModal = true;
// let dayInput;
let fromInput;
let toInput;
let hoursInput;
let count = 0;

// let remark;

function closemodal() {
    document.getElementById('dateNull').classList.add('d-none');
    document.getElementById('dateError').classList.add('d-none');
    document.getElementById('fromTimeNull').classList.add('d-none');
    document.getElementById('fromTimeError').classList.add('d-none');
    document.getElementById('fromTimeCK').classList.add('d-none');
    document.getElementById('toTimeNull').classList.add('d-none');
    document.getElementById('toTimeError').classList.add('d-none');
    document.getElementById('toTimeCK').classList.add('d-none');
    document.getElementById('hoursNull').classList.add('d-none');
    document.getElementById('hoursInvalid').classList.add('d-none');

    document.getElementById('dateInput').value = '';
    document.getElementById('dayInput').value = '';
    document.getElementById('fromInput').value = '';
    document.getElementById('toInput').value = '';
    document.getElementById('hoursInput').value = '';
    document.getElementById('remark').value = '';
}

function checkDate() {
    dateInput = document.getElementById('dateInput').value;
    if (dateInput === '') {
        document.getElementById('dateNull').classList.remove('d-none');
        checkBolleanDate = false;
    } else {
        document.getElementById('dateNull').classList.add('d-none');
        checkBolleanDate = true;
    }
}

function checkFrom() {
    fromInput = document.getElementById('fromInput').value;
    toInput = document.getElementById('toInput').value;
    if (fromInput === '') {
        document.getElementById('fromTimeNull').classList.remove('d-none');
        checkBolleanFrom = false;
    } else {
        document.getElementById('fromTimeNull').classList.add('d-none');
        checkBolleanFrom = true;
    }
    let timefromInput = fromInput.split(":");
    let timetoInput = toInput.split(":");
    let timefromMinutes = parseInt(timefromInput[0]) * 60 + parseInt(timefromInput[1]);
    let timetoMinutes = parseInt(timetoInput[0]) * 60 + parseInt(timetoInput[1]);
    if (timefromMinutes >= timetoMinutes) {
        checkBolleanFrom = false;
        document.getElementById('fromTimeCK').classList.remove('d-none');
        document.getElementById('toTimeCK').classList.remove('d-none');
        document.getElementById('dateError').classList.add('d-none');

    } else {
        checkBolleanFrom = true;
        document.getElementById('fromTimeCK').classList.add('d-none');
        document.getElementById('toTimeCK').classList.add('d-none');
    }

}

function checkTo() {

    toInput = document.getElementById('toInput').value;
    if (toInput === '') {
        document.getElementById('toTimeNull').classList.remove('d-none');
        checkBolleanTo = false;
    } else {
        document.getElementById('toTimeNull').classList.add('d-none');
        checkBolleanTo = true;
    }


}

document.getElementById("toInput").addEventListener("change", function () {
    caculatorHours();
});
document.getElementById("fromInput").addEventListener("change", function () {
    caculatorHours();
});

function checkHours() {

    hoursInput = document.getElementById('hoursInput').value;

    // Kiểm tra nếu giá trị nhập không phải là một số
    if (isNaN(hoursInput)) {
        document.getElementById('hoursInvalid').classList.remove('d-none');
        document.getElementById('hoursNull').classList.add('d-none');
        checkBolleanHours = false;
    } else {
        document.getElementById('hoursInvalid').classList.add('d-none');

        // Kiểm tra nếu giá trị nhập là rỗng
        if (hoursInput === '') {
            document.getElementById('hoursNull').classList.remove('d-none');
            document.getElementById('hoursInvalid').classList.add('d-none');
            checkBolleanHours = false;
        } else {
            document.getElementById('hoursNull').classList.add('d-none');
            checkBolleanHours = true;
        }
    }
}

function checkDateAdvance() {

    let dateClaimsRequest = Date.parse(document.getElementById('dateInput').value);
    let fromDateProject = Date.parse(document.getElementById('fromDateProject').innerText);
    let toDateProject = Date.parse(document.getElementById('toDateProject').innerText);
    if (dateClaimsRequest > toDateProject || dateClaimsRequest < fromDateProject) {
        document.getElementById('dateError').classList.remove('d-none');
        checkBolleanDateAdvance = false;

    } else {
        document.getElementById('dateError').classList.add('d-none');
        checkBolleanDateAdvance = true;

    }
}

let dateValue;
let fromTimeValue;
let toTimeValue;

function checkDateCross() {

    if (dateValue) {
        let shouldReturn = false;
        claimsRequestDTO.forEach(rowDTO => {
            if (shouldReturn) return;

            let timeFromClaimsRequest = Date.parse(dateValue + ' ' + fromTimeValue);
            let timeToClaimsRequest = Date.parse(dateValue + ' ' + toTimeValue);

            let date = new Date(rowDTO.date);
            let fromtime = new Date(`1970-01-01T${rowDTO.fromTime}`);
            date.setHours(fromtime.getHours());
            date.setMinutes(fromtime.getMinutes());
            let timefrom = date.getTime();
            // Tạo chuỗi thời gian từ các trường date và from của mỗi đối tượng
            // let timefrom = Date.parse(`${rowDTO.date} ${rowDTO.fromTime}`);
            let date2 = new Date(rowDTO.date);
            let totime = new Date(`1970-01-01T${rowDTO.toTime}`);
            date2.setHours(totime.getHours());
            date2.setMinutes(totime.getMinutes());
            // let timeto = Date.parse(`${rowDTO.date} ${rowDTO.toTime}`);
            let timeto = date2.getTime();

            if (timeFromClaimsRequest >= timeto || timeToClaimsRequest <= timefrom) {

                document.getElementById('fromTimeError').classList.add('d-none');
                document.getElementById('toTimeError').classList.add('d-none');

                checkBolleanDateAdvanceCross = true;

            } else {
                document.getElementById('fromTimeError').classList.remove('d-none');
                document.getElementById('toTimeError').classList.remove('d-none');
                document.getElementById('fromTimeCK').classList.add('d-none');
                document.getElementById('toTimeCK').classList.add('d-none');
                checkBolleanDateAdvanceCross = false;
                shouldReturn = true;
            }

        });
    }


}

document.getElementById('dateInput').addEventListener('input', checkDate);
document.getElementById('dateInput').addEventListener('change', checkDate);
// document.getElementById('dateInput').addEventListener('input', checkDateAdvance);
// document.getElementById('dateInput').addEventListener('change', checkDateAdvance);
// document.getElementById('dateInput').addEventListener('input', checkDateCross);
// document.getElementById('dateInput').addEventListener('change', checkDateCross);
// document.getElementById('fromInput').addEventListener('input', checkFrom);
// document.getElementById('fromInput').addEventListener('change', checkFrom);
// document.getElementById('toInput').addEventListener('input', checkTo);
// document.getElementById('toInput').addEventListener('change', checkTo);
// document.getElementById('hoursInput').addEventListener('input', checkHours);
// document.getElementById('hoursInput').addEventListener('change', checkHours);

function addLeadingZero(number) {
    return number < 10 ? '0' + number : number;
}

function caculatorHours() {
    fromTimeValue = document.getElementById('fromInput').value;
    toTimeValue = document.getElementById('toInput').value;
    if (fromTimeValue && toTimeValue) {
        let timefromInput = fromTimeValue.split(":");
        let timetoInput = toTimeValue.split(":");
        let timefromMinutes = parseInt(timefromInput[0]) * 60 + parseInt(timefromInput[1]);
        let timetoMinutes = parseInt(timetoInput[0]) * 60 + parseInt(timetoInput[1]);
        let timeTotalHours = timetoMinutes - timefromMinutes;
        let timeHours = (timeTotalHours / 60).toFixed(2);
        // let timeMinutes = timeTotalHours - timeHours * 60;
        document.getElementById('hoursInput').value = timeHours;
        hoursInput = document.getElementById('hoursInput').value;
    }
}

document.getElementById("submitBtn-modal").addEventListener("click", function () {
    dateValue = document.getElementById('dateInput').value;
    fromTimeValue = document.getElementById('fromInput').value;
    toTimeValue = document.getElementById('toInput').value;

    // const dateInput = document.getElementById('dateInput').value;
    checkDateAdvance();
    checkDate();
    checkFrom();
    checkTo();
    checkDateCross();
    const dayInput = document.getElementById('dayInput').value;

    // const fromInput = document.getElementById('fromInput').v alue;
    // const toInput = document.getElementById('toInput').value;
    // const hoursInput = document.getElementById('hoursInput').value;
    const remark = document.getElementById('remark').value;
    checkBoolean = checkBolleanTo && checkBolleanHours && checkBolleanDate && checkBolleanDateAdvance && checkBolleanFrom && checkBolleanDateAdvanceCross;

    if (checkBoolean) {
        const rowDTO = {
            date: dateInput,
            day: dayInput,
            fromTime: fromInput,
            toTime: toInput,
            totalHours: hoursInput,
            remarks: remark,
            staffDTO: {
                id: parseInt(document.getElementById("staffId").innerText),
                name: document.getElementById("staffName").innerText
            },
            projectDTO: document.getElementById("projectId").innerText ?
                {id: parseInt(document.getElementById("projectId").innerText)} :
                null

        };

        claimsRequestDTO.push(rowDTO);
        createClaimsRequestDTO.push(rowDTO);

        const jsonString = JSON.stringify(claimsRequestDTO);

        // Set JSON string as value of hidden field
        document.getElementById('hiddenField').value = jsonString;

        // Create a new table row element
        const newRow = document.createElement('tr');

        // Create table data cells and populate with values
        const dateCell = document.createElement('td');
        dateCell.textContent = dateInput;
        newRow.appendChild(dateCell);

        const dayCell = document.createElement('td');
        dayCell.textContent = dayInput;
        newRow.appendChild(dayCell);

        const fromCell = document.createElement('td');
        fromCell.textContent = fromInput;
        newRow.appendChild(fromCell);

        const toCell = document.createElement('td');
        toCell.textContent = toInput;
        newRow.appendChild(toCell);

        const hoursCell = document.createElement('td');
        hoursCell.textContent = hoursInput;
        newRow.appendChild(hoursCell);

        const remarkCell = document.createElement('td');
        remarkCell.textContent = remark;
        newRow.appendChild(remarkCell);

        // Append the new row to the table body
        claimTable.appendChild(newRow);

        // Clear modal input values (optional)
        document.getElementById('dateInput').value = '';
        document.getElementById('dayInput').value = '';
        document.getElementById('fromInput').value = '';
        document.getElementById('toInput').value = '';
        document.getElementById('hoursInput').value = '';
        document.getElementById('remark').value = '';
        $('#myModal').modal('hide');
    }
    
});

// const currentPath = window.location.pathname;
// const pathElements = currentPath.split('/');
// const lastElement = pathElements[pathElements.length - 1];

function getAllClaimsRequest(staffId) {
    $.ajax({
        url: "http://localhost:9090/api/claim/staff/" + staffId + "/employee",
        type: "GET",
        dataType: "json",
        success: function (response) {
            response.forEach(rowDTO => {
                claimsRequestDTO.push(rowDTO);
            });
            const jsonStr = JSON.stringify(claimsRequestDTO);

            // Set JSON string as value of hidden field
            document.getElementById('hiddenField').value = jsonStr;
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });
}

function getAllInfoStaff(staffId) {
    $.ajax({
        url: "http://localhost:9090/api/staff/" + staffId + "/employee",
        type: "GET",
        dataType: "json",
        success: function (response) {
            document.getElementById("staffId").innerText = response.staffDTO.id;
            document.getElementById("staffName").innerText = response.staffDTO.name;
            document.getElementById("staffDepartment").innerText = response.staffDTO.department;
            const selectElement = document.getElementById("projectSelect");
            const projectDuration = document.getElementById("projectDuration");
            const roleInProject = document.getElementById("roleInProject");
            const projectId = document.getElementById("projectId");
            const fromDateProject = document.getElementById("fromDateProject");
            const toDateProject = document.getElementById("toDateProject");


            response.workingProjectDTOS.forEach(function (workingProjectDTO) {
                const optionElement = document.createElement("option");
                optionElement.value = workingProjectDTO.id;
                optionElement.textContent = workingProjectDTO.name;
                selectElement.appendChild(optionElement);
            });
            selectElement.addEventListener("change", function () {
                let selectedWorkingProjectId = selectElement.value;

                const selectedWorkingProject = response.workingProjectDTOS.find(function (workingProjectDTO) {
                    return workingProjectDTO.id === parseInt(selectedWorkingProjectId);
                });

                if (selectedWorkingProject) {
                    document.getElementById('projectError').classList.add('d-none');
                    document.getElementById("addClaimsRequest").setAttribute("data-toggle", "modal");
                    projectId.innerText = selectedWorkingProject.projectId;
                    roleInProject.innerText = selectedWorkingProject.role;
                    projectDuration.innerText = selectedWorkingProject.fromDate.slice(0, 10) + ' -> ' + selectedWorkingProject.toDate.slice(0, 10);
                    fromDateProject.innerText = selectedWorkingProject.fromDate;
                    toDateProject.innerText = selectedWorkingProject.toDate;
                    booleanModal = false;
                } else {
                    roleInProject.innerText = "";
                    projectDuration.innerText = "";
                    document.getElementById("addClaimsRequest").removeAttribute("data-toggle");
                    booleanModal = true;
                }
                document.getElementById('projectSelect').setAttribute('disabled', 'disabled');
            });
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });
}

document.getElementById("addClaimsRequest").addEventListener("click", function () {
    document.getElementById('saveError').classList.add('d-none');
    if (booleanModal) {
        document.getElementById('projectError').classList.remove('d-none');
    }

});

document.getElementById("submitDraft").addEventListener("click", function () {

    if (createClaimsRequestDTO.length > 0) {
        $.ajax({
            url: "http://localhost:9090/api/claim/employee",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(createClaimsRequestDTO),
            success: function (response) {
                alert("Save success!");
                window.location.href = "/employee/view-claim-draft";
                // window.location.href = "/claimsRequest/create/" + lastElement;
            },
            error: function (xhr, status, error) {
                // Handle Error from server
            }
        });
    } else {
        document.getElementById('saveError').classList.remove('d-none');
    }
});

$.ajax({
    url: "http://localhost:9090/api/current-user",
    type: "GET",
    dataType: "json",
    success: function (staffIdResponse) {
        // Nếu staffId có sẵn, thì gọi API khác
        if (staffIdResponse !== null && staffIdResponse !== undefined) {
            var staffId = staffIdResponse;
            getAllInfoStaff(staffId);
            getAllClaimsRequest(staffId);
        }
    }
});
