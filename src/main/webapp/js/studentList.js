/**
 * view-controller for studentList.html
 */

document.addEventListener("DOMContentLoaded", () => {
    readStudents()
});

/**
 * reads all students
 */
function readStudents() {
    fetch("./resource/student/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showStudentList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the studentList as a table
 * @param data the students
 */
function showStudentList(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("studentList");
    tBody.innerHTML = ""


    data.forEach(student => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        button.innerHTML = "&#9998;";
        button.type = "button";
        button.name = "editStudent";
        button.setAttribute("data-id", student.id);
        button.addEventListener("click", editStudent);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = student.firstName;
        row.insertCell(-1).innerHTML = student.lastName;
        row.insertCell(-1).innerHTML = student.birthDate;
        row.insertCell(-1).innerHTML = student.phoneNumber;
        row.insertCell(-1).innerHTML = student.group.title

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteStudent";
            button.setAttribute("data-id", student.id);
            button.addEventListener("click", deleteStudent);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./studentEdit.html'>New Student</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event the click-event
 */
function editStudent(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");
    window.location.href = "./studentEdit.html?id=" + id;
}

/**
 * deletes a student
 * @param event the click-event
 */
function deleteStudent(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");

    fetch("./resource/student/delete?id=" + id,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./studentList.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}