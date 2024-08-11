function deleteUser(userId) {
    fetch('/admin/deleteuser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: userId })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Użytkownik został usunięty pomyślnie.");
            } else {
                alert("Błąd podczas usuwania użytkownika.");
            }
        })
        .catch(error => {
            console.error("Błąd:", error);
            alert("Wystąpił błąd podczas komunikacji z serwerem.");
        });
}

function resetPassword(userId) {
    fetch('/admin/resetpassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: userId })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Hasło zostało zresetowane pomyślnie.");
            } else {
                alert("Błąd podczas resetowania hasła.");
            }
        })
        .catch(error => {
            console.error("Błąd:", error);
            alert("Wystąpił błąd podczas komunikacji z serwerem.");
        });
}
