/**
 * view-controller for studentEdit.html
 */
document.addEventListener("DOMContentLoaded", () => {
    readStudent();
    readGroups();

    document.getElementById("studentEditForm").addEventListener("submit", saveStudent);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a student
 */
function saveStudent(event) {
    event.preventDefault();

    const studentForm = document.getElementById("studentEditForm");
    const formData = new FormData(studentForm);
    const data = new URLSearchParams(formData);
// TODO cannot update LocalDate, updates to undefined
    let method;
    let url = "./resource/student/";
    const id = getQueryParam("id");
    if (id == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Error while saving", "error");
                console.log(response);
            } else {
                showMessage("Saved student", "info");
                window.location.href = "./studentList.html";
                return response;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a student
 */
function readStudent() {
    const id = getQueryParam("id");
    fetch("./resource/student/read?id=" + id)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showStudent(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a student
 * @param data the student-data
 */
function showStudent(data) {
    //const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("firstName").value = data.firstName;
    document.getElementById("lastName").value = data.lastName;
    document.getElementById("birthDate").value = data.birthDate;
    document.getElementById("phoneNumber").value = data.phoneNumber;
    document.getElementById("group").value = data.groupID; //TODO doesn't always load correctly
}

/**
 * reads all teachers as an array
 */
function readGroups() {
    fetch("./resource/group/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showGroups(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all groups as a dropdown
 * @param data
 */
function showGroups(data) {
    let dropdown = document.getElementById("group");
    data.forEach(group => {
        let option = document.createElement("option");
        option.text = group.title;
        option.value = group.id;
        dropdown.add(option);
    })
}

/**
 * redirects to the studentList
 * @param event the click-event
 */
function cancelEdit(event) {
    window.location.href = "./studentList.html";
}