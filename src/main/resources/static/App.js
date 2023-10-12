const URL = "http://localhost:8080/api/users";

function getAllUsers() {
    fetch(URL)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        })
}

function getAdminPage() {
    fetch(URL).then(response => response.json()).then(user =>
        loadTable(user))
}

function loadTable(listAllUsers) {
    let res = '';
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(r => r.name.substring(5))}</td>
                <td>
                    <button class="btn btn-info text-white" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button>
                </td>
            </tr>`
    }
    document.getElementById('users-table').innerHTML = res;
}

getAdminPage();

// созданеи user
document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault()
    let roles = document.getElementById('role_select')
    let rolesAddUser = []
    let rolesAddUserValue = ''
    for (let i = 0; i < roles.options.length; i++) {
        if (roles.options[i].selected) {
            rolesAddUser.push({id: roles.options[i].value, name: 'ROLE_' + roles.options[i].innerHTML})
            rolesAddUserValue += roles.options[i].innerHTML
        }
    }
    fetch(URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            username: document.getElementById('newName').value,
            lastName: document.getElementById('newLastName').value,
            age: document.getElementById('newAge').value,
            email: document.getElementById('newEmail').value,
            password: document.getElementById('newPassword').value,
            roles: rolesAddUser
        })
    })
        .then((response) => {
            if (response.ok) {
                getAllUsers()
                document.getElementById("usersTable").click()
            }
        })
})

// Удаление пользователя
function deleteModal(id) {
    fetch(URL + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            document.getElementById('deleteId').value = u.id;
            document.getElementById('deleteName').value = u.username;
            document.getElementById('deleteLastName').value = u.lastName;
            document.getElementById('deleteAge').value = u.age;
            document.getElementById('deleteEmail').value = u.email;
            document.getElementById("deleteRole").value = u.roles.map(r => r.name.substring(5));
        })
    });
}

async function deleteUser() {
    const id = document.getElementById("deleteId").value
    console.log(id)
    let urlDel = URL + "/" + id;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getAllUsers()
    })
}

// редактирование

function editModal(id) {
    fetch(URL + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {

            document.getElementById('editId').value = u.id;
            document.getElementById('editName').value = u.username;
            document.getElementById('editLastName').value = u.lastName;
            document.getElementById('editAge').value = u.age;
            document.getElementById('editEmail').value = u.email;
            document.getElementById('editPassword').value = "****";

        })
    });
}

async function editUser() {
    const form_ed = document.getElementById("editForm");
    let idValue = document.getElementById("editId").value;
    let nameValue = document.getElementById("editName").value;
    let lastNameValue = document.getElementById("editLastName").value;
    let ageValue = document.getElementById("editAge").value;
    let emailValue = document.getElementById("editEmail").value;
    let passwordValue = document.getElementById("editPassword").value;
    let listOfRole = [];
    for (let i = 0; i < form_ed.roles.options.length; i++) {
        if (form_ed.roles.options[i].selected) {
            let tmp = {};
            tmp["id"] = form_ed.roles.options[i].value
            listOfRole.push(tmp);
        }
    }
    let user = {
        id: idValue,
        username: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: listOfRole
    }
    await fetch(URL + '/' + user.id, {
        method: "PATCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getAllUsers()
}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}