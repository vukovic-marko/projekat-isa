
function getToken() {
	return localStorage.getItem('jwtToken');
}

$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

function loadUsers() {
	$.ajax({
		url: 'user/all',
		type: 'get'
	}).then(function(data) {
		$('.table-system-admin tbody').empty();
		$.each(data, function(index, value) {
			var str = "<tr><td>" + value.id + "</td><td><a class=\"editUserRoles\" href=\"javascript:void(0)\" meta-username=\"" + value.username + "\">" + value.username + "</a>";
		    var exists = new Array(5).fill(false);
		    var checked = new Array(5).fill("");
		    $.each(value.authorities, function (idx, val) {
				if (val.authority == "ROLE_USER") { exists[0] = true; checked[0] = " checked"; return false;}
				if (val.authority == "ROLE_SYSTEM_ADMIN") {exists[1] = true; checked[1] = " checked"; return false;}
				if (val.authority == "ROLE_AIRPORT_ADMIN") {exists[2] = true; checked[2] = " checked"; return false;}
				if (val.authority == "ROLE_HOTEL_ADMIN") {exists[3] = true; checked[3] = " checked"; return false;}
				if (val.authority == "ROLE_RENT_A_CAR_ADMIN") {exists[4] = true; checked[4] = " checked"; return false;}
			});

		    str += "</td><td>";
		    console.log(checked);
		    str += "<input meta-username=\"" + value.username + "\" type=\"radio\" name=\"radio"+ index +"\"" +checked[0]+ " meta-kind=\"0\"></td><td>";
		    str += "<input meta-username=\"" + value.username + "\" type=\"radio\" name=\"radio"+ index +"\"" +checked[1]+ " meta-kind=\"1\"></td><td>";
		    str += "<input meta-username=\"" + value.username + "\" type=\"radio\" name=\"radio"+ index +"\"" +checked[2]+ " meta-kind=\"2\"></td><td>";
		    str += "<input meta-username=\"" + value.username + "\" type=\"radio\" name=\"radio"+ index +"\"" +checked[3]+ " meta-kind=\"3\"></td><td>";
		    str += "<input meta-username=\"" + value.username + "\" type=\"radio\" name=\"radio"+ index +"\"" +checked[4]+ " meta-kind=\"4\"></td></tr>";
			
			$('.table-system-admin tbody').append(str);
		});
	});
}

function addSysAdminButtons() {
	$('.system-admin-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="logout"'
					+ '  class="btn btn-primary">Odjavi se</button> </li>');
	
	$('#logout').click(function() {
		localStorage.setItem('jwtToken', null);
		window.location.href = '/index.html';
	});
}

$(document).ready(function() {
	loadUsers();
	
	addSysAdminButtons();
});


$(window).resize(adjust_body_offset);
adjust_body_offset();

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}