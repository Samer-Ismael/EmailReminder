const API_BASE_URL = `${window.location.protocol}//${window.location.hostname}:8090/users`;

// Event listeners
document.getElementById("fetchAppointmentsBtn").addEventListener("click", fetchNextDayAppointments);
document.getElementById("fetchTodayAppointmentsBtn").addEventListener("click", fetchTodayAppointments);
document.getElementById("searchBtn").addEventListener("click", searchUserByName);
document.getElementById("addUserForm").addEventListener("submit", addUser);

// Fetch next day appointments
async function fetchNextDayAppointments() {
    try {
        const response = await fetch(`${API_BASE_URL}/next-day-appointments`);
        const users = await response.json();
        const appointmentsList = document.getElementById("appointmentsList");
        appointmentsList.innerHTML = users.length > 0
            ? users.map(user => `
                                <li>
                                    Name: ${user.name} <br>
                                    ID: ${user.id} <br>
                                    Email: ${user.email} <br>
                                    Appointment: ${user.appointment} at ${user.time}
                                </li>
                            `).join("")
            : "<li>No appointments for tomorrow.</li>";
    } catch (error) {
        console.error("Error fetching next-day appointments:", error);
    }
}

// Fetch today appointments
async function fetchTodayAppointments() {
    try {
        const response = await fetch(`${API_BASE_URL}/today`);
        const users = await response.json();
        const todayAppointmentsList = document.getElementById("todayAppointmentsList");
        todayAppointmentsList.innerHTML = users.length > 0
            ? users.map(user => `
                                <li>
                                    Name: ${user.name} <br>
                                    ID: ${user.id} <br>
                                    Email: ${user.email} <br>
                                    Appointment: ${user.appointment} at ${user.time}
                                </li>
                            `).join("")
            : "<li>No appointments for today.</li>";
    } catch (error) {
        console.error("Error fetching today's appointments:", error);
    }
}

// Search users by name
async function searchUserByName() {
    const name = document.getElementById("searchName").value;
    try {
        const response = await fetch(`${API_BASE_URL}/search?name=${name}`);
        const users = await response.json();
        const searchResults = document.getElementById("searchResults");
        searchResults.innerHTML = users.length > 0
            ? users.map(user => `
                  <li>
                      Name: ${user.name} <br>
                      ID: ${user.id} <br>
                      Email: ${user.email} <br>
                      Appointment: ${user.appointment} at ${user.time}
                  </li>
              `).join("")
            : "<li>No users found with that name.</li>";
    } catch (error) {
        console.error("Error searching for users:", error);
    }
}

// Event listener for searching users by appointment date
document.getElementById("searchByDateBtn").addEventListener("click", searchUsersByDate);

// Search users by appointment date
async function searchUsersByDate() {
    const date = document.getElementById("searchDate").value;
    if (!date) {
        alert("Please select a date.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/appointments?date=${date}`);
        const users = await response.json();
        const searchByDateResults = document.getElementById("searchByDateResults");

        searchByDateResults.innerHTML = users.length > 0
            ? users.map(user => `
                                <li>
                                    Name: ${user.name} <br>
                                    ID: ${user.id} <br>
                                    Email: ${user.email} <br>
                                    Appointment: ${user.appointment} at ${user.time}
                                </li>
                            `).join("")
            : "<li>No users found for the selected date.</li>";
    } catch (error) {
        console.error("Error searching for users by date:", error);
    }
}

// Add new user
async function addUser(event) {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const appointment = document.getElementById("appointment").value;
    const time = document.getElementById("time").value;

    try {
        const response = await fetch(`${API_BASE_URL}/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, email, phone, appointment, time })
        });

        if (response.status === 201) {
            alert("User added successfully!");
        } else {
            alert("Failed to add user.");
        }
    } catch (error) {
        console.error("Error adding user:", error);
    }
}

// Event listener for delete button
document.getElementById("deleteBtn").addEventListener("click", deleteUserById);

// Delete user by ID
async function deleteUserById() {
    const id = document.getElementById("deleteId").value.trim();
    const deleteResults = document.getElementById("deleteResults");

    if (!id) {
        deleteResults.innerHTML = "<li>Please enter a valid ID.</li>";
        return;
    }

    try {
        // Send DELETE request
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: "DELETE",
        });

        // Check the response status
        if (response.ok) {
            deleteResults.innerHTML = `<li>User with ID ${id} has been deleted successfully.</li>`;
            // Optionally remove from the DOM if the user was listed
            const userElement = document.getElementById(`user-${id}`);
            if (userElement) {
                userElement.remove();
            }
        } else if (response.status === 404) {
            deleteResults.innerHTML = `<li>User with ID ${id} not found.</li>`;
        } else {
            deleteResults.innerHTML = "<li>Failed to delete the user. Please try again.</li>";
        }
    } catch (error) {
        console.error("Error deleting user:", error);
        deleteResults.innerHTML = "<li>An error occurred. Please try again later.</li>";
    }
}
document.getElementById('emailConfigBtn').addEventListener('click', function() {
    document.getElementById('emailConfigPopup').style.display = 'block';
});

document.getElementById('closePopupBtn').addEventListener('click', function() {
    document.getElementById('emailConfigPopup').style.display = 'none';
});

document.getElementById('emailConfigForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/api/config/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        document.getElementById('emailConfigPopup').style.display = 'none'; // Close popup
    })
    .catch(error => console.error('Error:', error));
});
