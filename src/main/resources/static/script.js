const API_BASE_URL = "http://localhost:8080/users";

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
            ? users.map(user => `<li>${user.name} - ${user.appointment} at ${user.time}</li>`).join("")
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
            ? users.map(user => `<li>${user.name} - ${user.appointment} at ${user.time}</li>`).join("")
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
            ? users.map(user => `<li>${user.name} - ${user.email}</li>`).join("")
            : "<li>No users found with that name.</li>";
    } catch (error) {
        console.error("Error searching for users:", error);
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
