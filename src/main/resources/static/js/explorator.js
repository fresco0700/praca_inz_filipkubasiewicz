var editModal = document.getElementById("editModal");
var addModal = document.getElementById("addModal");

var editBtns = document.getElementsByClassName("edit-btn");
var closeBtns = document.getElementsByClassName("close");

for (var i = 0; i < editBtns.length; i++) {
    var btn = editBtns[i];
    btn.onclick = function(e) {
        var id = Number(e.target.dataset.id);
        var row = e.target.parentElement.parentElement;
        document.getElementById("edit-id").value = id;
        document.getElementById("edit-server").value = row.cells[2].innerText;
        document.getElementById("edit-name").value = row.cells[3].innerText;
        document.getElementById("edit-admin").value = row.cells[4].innerText;
        document.getElementById("edit-description").value = row.cells[5].innerText;
        editModal.style.display = "block";
    }
}

for (var i = 0; i < closeBtns.length; i++) {
    var closeButton = closeBtns[i];
    closeButton.onclick = function(e) {
        if (e.target.parentNode.parentNode.id === 'editModal') {
            editModal.style.display = "none";
        } else if (e.target.parentNode.parentNode.id === 'addModal') {
            addModal.style.display = "none";
        }
    }
}
var addBtn = document.getElementsByClassName("add-btn")[0];

addBtn.onclick = function() {
    addModal.style.display = "block";
}

window.onclick = function(event) {
    if (event.target == editModal) {
        editModal.style.display = "none";
    } else if (event.target == addModal) {
        addModal.style.display = "none";
    }
}
