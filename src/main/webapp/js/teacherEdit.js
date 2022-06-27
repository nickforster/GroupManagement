/**
 * view-controller for teacherEdit.html
 */
document.addEventListener("DOMContentLoaded", () => {
    readTeacher();

    document.getElementById("teacherEditForm").addEventListener("submit", saveTeacher);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a teacher
 */
function saveTeacher(event) {
    event.preventDefault();

    const teacherForm = document.getElementById("teacherEditForm");
    const formData = new FormData(teacherForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/teacher/";
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
                showMessage("Saved teacher", "info");
                window.location.href = "./teacherList.html";
                return response;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a teacher
 */
function readTeacher() {
    const id = getQueryParam("id");
    fetch("./resource/teacher/read?id=" + id)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showTeacher(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a teacher
 * @param data the teacher-data
 */
function showTeacher(data) {
    const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("firstName").value = data.firstName;
    document.getElementById("lastName").value = data.lastName;

    const locked = (userRole === "user");
    lockForm("teacherEditForm", locked)
}

/**
 * redirects to the teacherList
 * @param event the click-event
 */
function cancelEdit(event) {
    window.location.href = "./teacherList.html";
}