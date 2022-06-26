/**
 * view-controller for groupEdit.html
 */
document.addEventListener("DOMContentLoaded", () => {
    readGroup();
    readTeachers();

    document.getElementById("groupEditForm").addEventListener("submit", saveGroup);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a group
 */
function saveGroup(event) {
    event.preventDefault();

    const groupForm = document.getElementById("groupEditForm");
    const formData = new FormData(groupForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/group/";
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
                showMessage("Saved group", "info");
                window.location.href = "./groupList.html";
                return response;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a group
 */
function readGroup() {
    const id = getQueryParam("id");
    fetch("./resource/group/read?id=" + id)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showGroup(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a group
 * @param data the group-data
 */
function showGroup(data) {
    //const userRole = getCookie("userRole");
    document.getElementById("id").value = data.id;
    document.getElementById("title").value = data.title;
    document.getElementById("description").value = data.description;
    document.getElementById("graduationYear").value = data.graduationYear;
    document.getElementById("teacher").value = data.teacherID; //TODO doesn't always load correctly
}

/**
 * reads all teachers as an array
 */
function readTeachers() {
    fetch("./resource/teacher/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showTeachers(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all teachers as a dropdown
 * @param data
 */
function showTeachers(data) {
    let dropdown = document.getElementById("teacher");
    data.forEach(teacher => {
        let option = document.createElement("option");
        option.text = teacher.firstName + " " + teacher.lastName;
        option.value = teacher.id;
        dropdown.add(option);
    })
}

/**
 * redirects to the groupList
 * @param event the click-event
 */
function cancelEdit(event) {
    window.location.href = "./groupList.html";
}