function getToken() {
    return localStorage.getItem('jwtToken');
}

$(document).ready(function() {

    // Logout dugme u navigaciji
    $('.navbar-nav').append(
        '<li class="nav-item">' + ' <button id="logout"'
        + '  class="btn btn-primary">Odjavi se</button> </li>');

    $('#logout').click(function () {
        localStorage.setItem('jwtToken', null);
        window.location.href = '/index.html';
    });

    $(document).ajaxSend(function (event, jqxhr, settings) {
        var token = getToken();
        if (token != null)
            jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
    });

    $(document).on("click", "a", function (event) {
        event.preventDefault();
        $.get($(this).href);
    });

    $("#navbarContent").load("/get/navigationbar", 'html', function (event) {

        console.log(event);
    });
});