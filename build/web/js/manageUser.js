function confirmDelete(event) {
    event.preventDefault(); // Prevent the default action
    var link = event.target.href; // Get the href of the clicked link
    var confirmation = confirm("Are you sure you want to delete this user?");
    if (confirmation) {
        window.location.href = link; // Redirect to the delete action if confirmed
    }
}