//Zabezpieczenie przed podwójnym przesłaniem formularza
document.querySelector('.open-modal-button').addEventListener('click', function() {
    document.querySelector('.modal-background').style.display = 'flex';
});
document.getElementById('cancel-button').addEventListener('click', function() {
    document.querySelector('.modal-background').style.display = 'none';
});


document.querySelectorAll('.edit-post-button,.add-post-button').forEach(function(button) {
    button.addEventListener('click', function() {
        var id = this.getAttribute('data-id');
        var title = this.getAttribute('data-title');
        var content = this.getAttribute('data-content');
        var state = this.getAttribute('data-state');

        if (!state) {
            state = 'Informacyjnie';
        }

        document.getElementById('postId').value = id;
        document.getElementById('title').value = title;
        document.getElementById('state').value = state;

        // Usuń istniejącą instancję ckeditora jeśli istnieje
        if (CKEDITOR.instances.editor) {
            CKEDITOR.instances.editor.destroy(true);
        }

        // utwórz nową instancję CKEditora i ustaw dane kiedy jest gotowa
        var editor = CKEDITOR.replace('editor');
        editor.on('instanceReady', function() {
            editor.setData(content);
        });

        document.querySelector('.modal-background').style.display = 'block';
    });
});

//Funkcja wyłączania przycisku
function myFunction() {
    document.getElementById("submit-button").disabled = true;
}
//Funkcja zmieniania automatycznej zmiany nocna/dzienna zależnie od godziny podczas tworzenia nowego postu
window.onload = function() {
    var date = new Date();
    var current_hour = date.getHours();

    if (current_hour >= 7 && current_hour < 19) {
        document.getElementById("shift").value = "Zmiana dzienna";
    } else {
        document.getElementById("shift").value = "Zmiana nocna";
    }
};


//Zaznaczanie kontenera na żółto jeśli był dodany przez ostatnie 24H
var now = new Date();
var postContainers = document.querySelectorAll('.post-container');

postContainers.forEach(function(postContainer) {
    var postDateTimeElement = postContainer.querySelector('.post-dateedit');
    var postDateTimeText = postDateTimeElement.innerText || postDateTimeElement.textContent;
    var postDateTimeTextSplit = postDateTimeText.split(' ');
    var postDateTimeString = postDateTimeTextSplit[postDateTimeTextSplit.length - 1] + ' ' + postDateTimeTextSplit[postDateTimeTextSplit.length - 2];
    var postDateTime = new Date(postDateTimeString);

    var diffMs = now - postDateTime;
    var diffHrs = diffMs / 1000 / 60 / 60;

    if (diffHrs < 24) {
        postContainer.style.borderColor = "yellow";
    }
});

// Robienie nazw miesięcy nad kontenerami
var postContainers = document.querySelectorAll('.post-container');
var previousMonth = null;

postContainers.forEach(function(postContainer) {
    var postDateTimeElement = postContainer.querySelector('.post-dateedit');
    var postDateTimeText = postDateTimeElement.innerText || postDateTimeElement.textContent;
    var postDateTimeTextSplit = postDateTimeText.split(' ');
    var postDateTimeString = postDateTimeTextSplit[postDateTimeTextSplit.length - 1] + ' ' + postDateTimeTextSplit[postDateTimeTextSplit.length - 2];
    var postDateTime = new Date(postDateTimeString);

    var postMonth = postDateTime.getMonth();
    if (postMonth !== previousMonth) {
        previousMonth = postMonth;

        var monthHeader = document.createElement('h2');
        monthHeader.textContent = postDateTime.toLocaleString('pl-PL', { month: 'long', year: 'numeric' });
        postContainer.parentNode.insertBefore(monthHeader, postContainer);
    }
});


//keepalive
var timeout;
var countdownTimeInSeconds = 600;
// To endpoint aby usera nie wylogowywało jak coś robi
function keepAlive() {
    $.get('/keep-alive');
    resetTimer();
}
//Związane z websocetem, czy user coś robi czy nie.
function resetTimer() {
    clearTimeout(timeout);
    timeout = setTimeout(keepAlive, countdownTimeInSeconds * 1000);
}
$(document).on('mousemove mousedown touchstart keydown', resetTimer);
resetTimer();

// Generowanie powiadomienia
console.log("Zalogowany użytkownik:", loggedInUser);
var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

//Podłączenie się pod websocet'a
stompClient.connect({}, function(frame) {
    stompClient.subscribe('/topic/notifications', function(notification) {
        var receivedNotification = notification.body;
        var usernameStartIndex = receivedNotification.indexOf("Użytkownik ") + "Użytkownik ".length;
        var usernameEndIndex = receivedNotification.indexOf(" ", usernameStartIndex);
        if (usernameEndIndex === -1) {
            usernameEndIndex = receivedNotification.length;
        }
        var username = receivedNotification.substring(usernameStartIndex, usernameEndIndex).trim();

        if (username !== loggedInUser) {
            alert(receivedNotification);
        }
    });
});


document.addEventListener('mousemove', function (e) {
    const przyciski = document.querySelectorAll('.menu-button-left');
    const odstep = 10;
    const totalHeight = Array.from(przyciski).reduce((acc, btn) => acc + btn.offsetHeight + odstep, 0);

    przyciski.forEach((przycisk, index) => {
        const offsetTop = (index * (przycisk.offsetHeight + odstep));
        const topPosition = (window.innerHeight - totalHeight) / 2 + offsetTop;
        przycisk.style.top = topPosition + 'px';

        if (e.clientX < window.innerWidth / 7) {
            przycisk.style.left = '0px';
        } else {
            przycisk.style.left = '-135px';
        }
    });
});

//Pokazanie kto polajkowal post podczas najeżdżania na liczbe lajków na poście
function showLikes(element) {
    element.nextElementSibling.style.display = 'contents';
}
//To usuwa to okienko które pojawiło się wyżej
function hideLikes(element) {
    element.nextElementSibling.style.display = 'none';
}

//Pokazywanie banere gdy polajkuje się post zależnie czy powodzenie czy nie
function showBanner(type) {
    var banner = type === 'success' ? document.getElementById('successBanner') : document.getElementById('errorBanner');
    banner.style.display = 'block';

    setTimeout(function() {
        banner.style.display = 'none';
    }, 3000);
}
//Wysylanie asynchronicznie zapytania o polajkowanie postu
document.querySelectorAll('.like-post-button').forEach(button => {
    button.addEventListener('click', function() {
        var postId = this.getAttribute('data-post-id');

        fetch('/zmianowy/likeit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'id=' + postId
        })
            .then(response => {
                if (response.ok) {
                    showBanner('success');
                } else {
                    showBanner('error');
                }
            })
            .catch(error => {
                showBanner('error');
            });
    });
});




