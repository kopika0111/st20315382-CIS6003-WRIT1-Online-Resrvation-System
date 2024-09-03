document.getElementById('toggle-nav').addEventListener('click', function() {
    var sidebar = document.getElementById('sidebar');
    var content = document.getElementById('content');

    if (sidebar.classList.contains('nav-hidden')) {
        sidebar.classList.remove('nav-hidden');
        content.classList.remove('content-shown');
    } else {
        sidebar.classList.add('nav-hidden');
        content.classList.add('content-shown');
    }
});

function confirmLogout() {
    if (confirm("Are you sure you want to log out?")) {
        window.location.href = "logout.jsp";
    }
}