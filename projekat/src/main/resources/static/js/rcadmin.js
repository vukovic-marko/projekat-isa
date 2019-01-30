function getToken() {
	return localStorage.getItem('jwtToken');
}
$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});



$(document).ready(

		function() {
			addRCAButtons();
		});

$(window).resize(adjust_body_offset);
adjust_body_offset();
function addRCAButtons() {	
	
	$('.logged-out-navbar .navbar-nav').append(
			'<li class="nav-item">'
					+ ' <button id="logout"'
					+ '  class="btn btn-primary">Odjavi se</button> </li>');
	$('.logged-out-navbar .navbar-nav').append(
			'<li class="nav-item">'
					+ ' <button'
					+ '  class="btn btn-primary">Moj profil</button> </li>');
	
	
	
}



function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}