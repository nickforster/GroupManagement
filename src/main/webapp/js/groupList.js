/**
 * view-controller for groupList.html
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {
    readGroups()
});

/**
 * reads all groups
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
            showGroupList(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the groupList as a table
 * @param data  the books
 */
function showGroupList(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("groupList");
    tBody.innerHTML = ""


    data.forEach(group => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "admin") {
            button.innerHTML = "&#9998;";
            button.type = "button";
            button.name = "editBook";
            button.setAttribute("data-id", group.id);
            button.addEventListener("click", editBook);
            row.insertCell(-1).appendChild(button);
        }

        row.insertCell(-1).innerHTML = group.title;
        row.insertCell(-1).innerHTML = group.description;
        row.insertCell(-1).innerHTML = group.graduationYear;
        row.insertCell(-1).innerHTML = group.teacher.firstName;
        row.insertCell(-1).innerHTML = group.teacher.lastName;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteBook";
            button.setAttribute("data-id", group.id);
            button.addEventListener("click", deleteBook);
            row.insertCell(-1).appendChild(button);
        }

    });

    let tHeadings = document.getElementById("headings");
    if (userRole === "admin") {
        const th = document.createElement("th")
        tHeadings.insertBefore(th, tHeadings.firstChild)
        document.getElementById("addButton").innerHTML = "<a href='./groupEdit.html'>New Group</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event the click-event
 */
function editBook(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");
    window.location.href = "./groupEdit.html?id=" + id;
}

/**
 * deletes a group
 * @param event the click-event
 */
function deleteBook(event) {
    const button = event.target;
    const id = button.getAttribute("data-id");

    fetch("./resource/group/delete?id=" + id,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./groupList.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}