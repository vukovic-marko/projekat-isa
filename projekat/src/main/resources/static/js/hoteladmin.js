
function getToken() {
	return localStorage.getItem('jwtToken');
}

$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

$(document).ready(function() {
	
	$.ajax({
		url: 'user/checkactivated',
		type: 'get',
		statusCode: {
			401: function() {
				alert('Potrebno je da se ponovo ulogujete');
				localStorage.setItem('jwtToken', null);
				window.location.href = '/index.html';
			}
		}
	}).then(function(data) {
		if (data == true) {
			 addHotelAdminButtons();
		} else {
			changePassword();
		}
	});
	
	$.validator.methods.phoneCheck = function(value, element) {
		return this.optional(element)
				|| /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
						.test(value);
	}
	
	
	
	
});

function changePassword() {
	$("#changePasswordModal").modal({
		show: true,
		backdrop: 'static',
		keyboard: false
	});
	
	$('#changePasswordForm')
	.validate(
			{
				rules : {
					
					changepassword : "required",
					changepassword2 : {
						required : true,
						equalTo : "#changepassword"
					}

				},
				messages : {
					changepassword : "Lozinka nije uneta",

					changepassword2 : {
						required : "Unesite ponovo lozinku",
						equalTo : "Lozinke se ne poklapaju"
					}
				}
			});
	
	$('#changePasswordButton').click(

			function() {
				if ($('#changePasswordForm').valid()) {
					var d = {};
					d.username = "";
					d.password = $('#changepassword').val();
					d.firstName = "";
					d.lastName = "";
					d.phone = "";
					d.email = "";
					d.city = "";
					role = "";

					$.ajax({
						url : '/user/changePassword',
						type : 'post',
						contentType : 'application/json',
						data : JSON.stringify(d),

						success : function(data) {
							if (data == true) {
								$("#changePasswordModal").modal('hide');

								 addHotelAdminButtons();
							} else {
								$('#regbtn-error').show().html(
										'Promena lozinke nije uspela').fadeOut(
										5000);

							}
						}
					});

				}
			});
}

function addHotelAdminButtons() {
	$('#editProfileForm')
	.validate(
			{
				rules : {
					
					name : "required",
					address : "required",
					promodescription: "required"

				},
				messages : {
					name : "Naziv hotela nije unet",
					addres: "Adresa hotela nije uneta",
					promodescription: "Promotivni opis hotela nije unet"
				}
			});
	
	$('.hotel-admin-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="logout"'
					+ '  class="btn btn-primary">Odjavi se</button> </li>');
	
	$('#logout').click(function() {
		localStorage.setItem('jwtToken', null);
		window.location.href = '/index.html';
	});
	
	$('.hotel-admin-navbar .navbar-nav')
	.append(
			'<li class="nav-item">'
					+ ' <button id="editModalShow" href="#"'
					+ '  class="btn btn-primary">Izmeni profil</button> </li>');
	
	$('.hotel-admin-navbar #editModalShow').click(function() {
		console.log('klik');
		//$('#editProfileModal').modal('show');
		$.ajax({
			url: 'hoteladmin/details',
			type: 'get'
		}).then(function(data) {
			console.log(data);
			if (data.name != null) {
				$('#editname').val(data.name);
				$('#editaddress').val(data.address);
				$('#editcity').val(data.destination.city);
				$('#editcountry').val(data.destination.country);
				$('#editpromodescription').val(data.promoDescription);
			}
			$('#editProfileModal').modal('show');
		});
	});
	
	$('#editProfileButton').click(

			function() {
				if ($('#editProfileForm').valid()) {
					var d = {};
					d.name = $('#editname').val();
					d.destination = {};
					d.destination.city = $("#editcity").val();
					d.destination.country = $("#editcountry").val();
					d.address = $('#editaddress').val();
					d.promoDescription = $('#editpromodescription').val();
					
					console.log(d);
					
					$.ajax({
						url: 'hoteladmin/edit/profile',
						type: 'post',
						contentType: 'application/json',
						data: JSON.stringify(d),
						success: function(data) {
							if (data == true) 
								$('#editProfileModal').modal('hide');
						}
					});

//					$.ajax({
//						url : '/user/changePassword',
//						type : 'post',
//						contentType : 'application/json',
//						data : JSON.stringify(d),
//
//						success : function(data) {
//							if (data == true) {
//								$("#changePasswordModal").modal('hide');
//								addSysAdminButtons();
//							} else {
//								$('#regbtn-error').show().html(
//										'Promena lozinke nije uspela').fadeOut(
//										5000);
//
//							}
//						}
//					});

				}
			});
	
}


$(window).resize(adjust_body_offset);
adjust_body_offset();

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}