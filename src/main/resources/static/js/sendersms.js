function addNumber(number) {
    const textarea = document.getElementById("selectedNumbers");
    const numbers = textarea.value.split(",").filter(n => n); // Filter out any empty strings

    if (!numbers.includes(number)) {
        numbers.push(number);
        textarea.value = numbers.join(",");
    }
}


function removeNumber(number) {
    const textarea = document.getElementById("selectedNumbers");
    const numbers = textarea.value.split(",");
    const index = numbers.indexOf(number);
    if (index > -1) {
        numbers.splice(index, 1);
    }
    textarea.value = numbers.join(",");
}


function toggleNumber(element) {
    const phoneNumber = element.getAttribute('data-phone');
    const textarea = document.getElementById("selectedNumbers");

    if (textarea.value.includes(phoneNumber)) {
        removeNumber(phoneNumber);
    } else {
        addNumber(phoneNumber);
    }
}
function changeFormAction(newAction) {
    const form = document.getElementById("smsForm");
    form.action = newAction;
}
document.getElementById("submitSmsBtn").addEventListener("click", function() {
    const form = document.getElementById("smsForm");


    form.submit();
});
document.getElementById("submittestSmsBtn").addEventListener("click", function() {
    const form = document.getElementById("smsForm");
    changeFormAction("/sendersms/sendtestsms");

    form.submit();
});




