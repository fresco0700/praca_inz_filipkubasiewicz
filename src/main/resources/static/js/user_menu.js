var userMenu = document.getElementById("menu");
var username = document.getElementById("username");


userMenu.addEventListener("mouseenter", function() {
userMenu.style.display = "block";
});

userMenu.addEventListener("mouseleave", function() {
userMenu.style.display = "none";
});

function showModal() {
	document.getElementById('passwordModal').style.display = 'flex';
}

function hideModal(event) {
	event.preventDefault();
	document.getElementById('passwordModal').style.display = 'none';
}

