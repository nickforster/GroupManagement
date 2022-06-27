/**
 * view-controller for teacherList.html
 */

document.addEventListener("DOMContentLoaded", () => {
    readTeachers()
});

/**
 * reads all teachers
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
            showTeacherList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the teacherList as a table
 * @param data the teachers
 */
function showTeacherList(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("teacherList");
    tBody.innerHTML = ""


    data.forEach(teacher => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        button.innerHTML = "&#9998;";
        button.type = "button";
        button.name = "editTeacher";
        button.setAttribute("data-id", teacher.id);
        button.addEventListener("click", editTeacher);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = teacher.firstName
        row.insertCell(-1).innerHTML = teacher.lastName

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteTeacher";
            button.setAttribute("data-id", teacher.id);
            button.addEventListener("click", deleteTeacher);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./teacherEdit.html'>New Teacher</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event the click-event
 */
function editTeacher(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");
    window.location.href = "./teacherEdit.html?id=" + id;
}

/**
 * deletes a teacher
 * @param event the click-event
 */
function deleteTeacher(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");

    fetch("./resource/teacher/delete?id=" + id,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./teacherList.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}