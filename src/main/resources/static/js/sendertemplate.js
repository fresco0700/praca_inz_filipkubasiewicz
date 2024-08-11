function openEditModal(id) {
    document.getElementById('editId').value = id;

    document.getElementById('editModal').style.display = 'flex';
}

function openAddModal(){
    document.getElementById('addModal').style.display = 'flex';
}
function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}
function closeAddModal() {
    document.getElementById('addModal').style.display = 'none';
}

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



document.addEventListener("DOMContentLoaded", function() {
    const templates = document.querySelectorAll('.post-container');
    const lists = {};

    templates.forEach(template => {
        const title = template.querySelector('.post-title').textContent;
        const [master, slave, ...rest] = title.match(/\[(.*?)\]/g).map(s => s.replace(/[\[\]]/g, ''));
        const tileName = rest.join('');

        if (!lists[master]) {
            lists[master] = {};
        }
        if (!lists[master][slave]) {
            lists[master][slave] = [];
        }
        lists[master][slave].push({
            element: template,
            tileName: tileName
        });
    });

    Object.keys(lists).forEach(master => {
        const masterDiv = document.createElement('div');
        masterDiv.className = 'master-list';
        masterDiv.innerHTML = `<h3 class="toggle">${master} <span class="arrow">&#9654;</span></h3>`;
        const masterUl = document.createElement('ul');
        masterUl.style.display = 'none';
        masterDiv.appendChild(masterUl);

        Object.keys(lists[master]).forEach(slave => {
            const slaveDiv = document.createElement('div');
            slaveDiv.className = 'slave-list';
            slaveDiv.innerHTML = `<h4 class="toggle">${slave} <span class="arrow">&#9654;</span></h4>`;
            const slaveUl = document.createElement('ul');
            slaveUl.style.display = 'none';
            slaveDiv.appendChild(slaveUl);

            lists[master][slave].forEach(item => {
                const li = document.createElement('li');
                li.appendChild(item.element);
                slaveUl.appendChild(li);
            });

            masterUl.appendChild(slaveDiv);

            slaveDiv.querySelector('h4').addEventListener('click', function() {
                const arrow = this.querySelector('.arrow');
                slaveUl.style.display = slaveUl.style.display === 'none' ? 'block' : 'none';
                arrow.innerHTML = slaveUl.style.display === 'none' ? '&#9654;' : '&#9660;';
            });
        });

        document.body.appendChild(masterDiv);

        masterDiv.querySelector('h3').addEventListener('click', function() {
            const arrow = this.querySelector('.arrow');
            masterUl.style.display = masterUl.style.display === 'none' ? 'block' : 'none';
            arrow.innerHTML = masterUl.style.display === 'none' ? '&#9654;' : '&#9660;';
        });
    });
});

document.getElementById('selectedContacts').addEventListener('change', function() {
    var selectedOptions = Array.from(this.selectedOptions).map(option => option.value);
    document.getElementById('contactIds').value = selectedOptions.join(',');
});
