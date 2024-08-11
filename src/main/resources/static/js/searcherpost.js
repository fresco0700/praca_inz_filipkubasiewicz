
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
document.getElementById('download').addEventListener('click', function() {
    html2canvas(document.body).then(canvas => {
        var link = document.createElement('a');
        var currentDate = new Date();
        var dateString = currentDate.toISOString().slice(0, 10); // format YYYY-MM-DD

        link.href = canvas.toDataURL('image/png');
        link.download = 'raport' + dateString + '.png';
        link.click();
    });
});