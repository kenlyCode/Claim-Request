$().ready(function () {
});

function goBack() {
    window.history.back();
}

let claimsRequestDTOS = [];
document.getElementById("dateInput").addEventListener("change", function () {
    let date = this.value;
    let day = new Date(date).toLocaleDateString("en-US", {weekday: "long"});
    document.getElementById("dayInput").value = day;
});

const currentPath = window.location.pathname;
const pathElements = currentPath.split('/');
const lastElement = pathElements[pathElements.length - 1];
let claimsRequestUpdate;
let staffIdUpdate;

function getInfoClaimsRequest() {
    $.ajax({
        url: "http://localhost:9090/api/claim/" + lastElement + "/employee",
        type: "GET",
        dataType: "json",
        success: function (response) {
            document.getElementById("status").innerText = response.status.slice(7);
            document.getElementById("statusFull").innerText = response.status;
            document.getElementById("claimsRequestId").innerText = response.id;
            document.getElementById("staffId").innerText = response.staffDTO.id;
            document.getElementById("staffName").innerText = response.staffDTO.name;
            document.getElementById("staffDepartment").innerText = response.staffDTO.department;
            document.getElementById("projectId").innerText = response.projectDTO.id;
            document.getElementById("projectName").innerText = response.projectDTO.name;
            document.getElementById("roleInProject").innerText = response.staffDTO.role;
            document.getElementById("fromDateProject").innerText = response.projectDTO.fromDate;
            document.getElementById("toDateProject").innerText = response.projectDTO.toDate;

            document.getElementById("projectDuration").innerText = response.projectDTO.fromDate.slice(0, 10) + ' -> ' + response.projectDTO.toDate.slice(0, 10);
            document.getElementById('dateInput').value = response.date.slice(0, 10);
            document.getElementById('dayInput').value = response.day;
            document.getElementById('fromInput').value = response.fromTime;
            document.getElementById('toInput').value = response.toTime;
            document.getElementById('hoursInput').value = response.totalHours;
            document.getElementById('remark').value = response.remarks;
            staffIdUpdate = document.getElementById("staffId").innerText;
            claimsRequestUpdate = response;
            getAllClaimsRequest();

        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });
}


getInfoClaimsRequest()

function getAllClaimsRequest() {

    $.ajax({
        url: "http://localhost:9090/api/claim/staff/" + staffIdUpdate + "/employee",
        type: "GET",
        dataType: "json",
        success: function (response) {
            response.forEach(rowDTO => {
                if (rowDTO.id == document.getElementById("claimsRequestId").innerText) {
                    return;
                }
                claimsRequestDTOS.push(rowDTO);
            });
            const jsonStr = JSON.stringify(claimsRequestDTOS);

            // Set JSON string as value of hidden field
            document.getElementById('hiddenField').value = jsonStr;
        },
        error: function (xhr, status, error) {
            // Handle Error from server
        }
    });
}

let checkBolleanDate = true;
let checkBolleanDateAdvance = true;
let checkBolleanHours = true;
let checkBolleanFrom = true;
let checkBolleanTo = true;
let checkBoolean = true;
let booleanUpdate = false;
let dateInput;

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
        // document.getElementById('dateError').classList.add('d-none');

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
        claimsRequestDTOS.forEach(rowDTO => {
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

const dateOutput = document.getElementById("dateOutput");
const dayOutput = document.getElementById("dayOutput");
const fromOutput = document.getElementById("fromOutput");
const toOutput = document.getElementById("toOutput");
const hourOutput = document.getElementById("totalOutput");
const remarkOutput = document.getElementById("remarkOutput");
const statusOutput = document.getElementById("statusFull");

// document.getElementById('dateInput').addEventListener('input', checkDate);
// document.getElementById('dateInput').addEventListener('change', checkDate);
document.getElementById("submitBtn-modal").addEventListener("click", function () {
    dateValue = document.getElementById('dateInput').value;
    fromTimeValue = document.getElementById('fromInput').value;
    toTimeValue = document.getElementById('toInput').value;
    let date = document.getElementById("dateInput").value;
    let day = document.getElementById("dayInput").value;
    let from = document.getElementById("fromInput").value;
    let to = document.getElementById("toInput").value;
    let hours = document.getElementById("hoursInput").value;
    let remark = document.getElementById("remark").value;

    checkDateAdvance();
    checkDate();
    checkFrom();
    // checkHours();
    checkTo();
    checkDateCross();

    checkBoolean = checkBolleanTo && checkBolleanHours && checkBolleanDate && checkBolleanDateAdvance && checkBolleanFrom && checkBolleanDateAdvanceCross;
    if (checkBoolean) {
        booleanUpdate = true;
        $('#myModal').modal('hide');
        dateOutput.innerText = date;
        dayOutput.innerText = day;
        fromOutput.innerText = from;
        toOutput.innerText = to;
        hourOutput.innerText = hours;
        remarkOutput.innerText = remark;
    }
});


document.getElementById("submitDraft").addEventListener("click", function () {
    let claimsRequest = {
        id: parseInt(document.getElementById("claimsRequestId").innerText),
        status: statusOutput.innerText,
        date: dateOutput.innerText,
        day: dayOutput.innerText,
        fromTime: fromOutput.innerText,
        toTime: toOutput.innerText,
        totalHours: hourOutput.innerText,
        remarks: remarkOutput.innerText,
        staffDTO: {
            id: parseInt(document.getElementById("staffId").innerText)
        },
        projectDTO: document.getElementById("projectId").innerText ?
            {id: parseInt(document.getElementById("projectId").innerText)} :
            null
    };
    if (booleanUpdate) {
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
    } else {
        window.location.href = "/employee/view-claim-draft";
    }
});

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

document.getElementById("toInput").addEventListener("change", function () {
    caculatorHours();
});
document.getElementById("fromInput").addEventListener("change", function () {
    caculatorHours();
});