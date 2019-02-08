/*
 * ovde su funckije za:
 * 	uzimanje tokena
 * 	preomenu sifre prilikom prvog logovanja
 * 	dodavanje dugmica u nav bar
 * 	prikaz i uredjivanje profila admina
 *  dodavanje soba
 */


function getToken() {
	return localStorage.getItem('jwtToken');
}

function refreshToken(){
	$.ajax({
		   url: '/user/refresh',
           type: 'post',
           success: function (data) {
        	   localStorage.setItem('jwtToken',data.accessToken);
        	   }
	});
}

$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

$(document).ready(function() {
	setInterval(refreshToken, 60000); //svaki min
	
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
	
	
	$('#addRoomButton').click(function() {
		addRoom();
		if ($('#addRoomForm').valid()) {
			var room = {};
			room.roomNumber = $('#addroomnumber').val();
			room.floorNumber = $('#addroomfloor').val();
			room.size = $('#addsize').val();
			
			$.ajax({
				url: 'hoteladmin/addroom',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify(room),
				success: function(data) {
					if (data == true) {
						loadRooms();
					} else {
						alert('U hotelu vec postoji soba sa unetim brojem sobe.')
					}
				}
			});
			
			//console.log(room);
		}

	});
	
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

function addRoom() {
	$('#addRoomForm')
	.validate(
			{
				rules : {
					
					roomnumber : {
						required: true
					},
					roomfloor : {
						required: true
					},
					size: {
						required: true
					}

				},
				messages : {
					roomnumber : {
						required: "Broj sobe nije unet"
					},
					roomfloor: {
						required: "Sprat nije unet"
					},
					size: {
						required: "Broj kreveta nije unet"
					}
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
	
	$('.hotel-admin-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="changepass"'
					+ '  class="btn btn-primary">Promeni lozinku</button> </li>');
	
	$('#logout').click(function() {
		localStorage.setItem('jwtToken', null);
		window.location.href = '/index.html';
	});
	
	$('#changepass').click(function() {
		changePass();
	});
	
	$('.hotel-admin-navbar .navbar-nav')
	.append(
			'<li class="nav-item">'
					+ ' <button id="editModalShow" href="#"'
					+ '  class="btn btn-primary">Profil</button> </li>');
	
	$('.hotel-admin-navbar .navbar-nav')
	.append(
			'<li class="nav-item">'
					+ ' <button id="editRoomsModalShow" href="#"'
					+ '  class="btn btn-primary">Sobe</button> </li>');
	
	$('.hotel-admin-navbar .navbar-nav')
	.append(
			'<li class="nav-item">'
					+ ' <button id="editAdditionalServicesModalShow" href="#"'
					+ '  class="btn btn-primary">Dodatne Usluge</button> </li>');
	
	$('.hotel-admin-navbar #editAdditionalServicesModalShow').click(function() {
		loadAdditionalServices();
	});
	
	$('.hotel-admin-navbar #editRoomsModalShow').click(function() {
		loadRooms();
	});
	
	$('#addAdditionalServiceButton').click(function() {
		addAdditionalService();
	});	
	
	$('.hotel-admin-navbar #editModalShow').click(function() {
		//console.log('klik');
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

function loadRooms() {
	$.ajax({
		url: '/hoteladmin/rooms',
		type: 'get',
		success: function(data) {
			$('#editRoomsModal tbody').empty();
			$('#editRoomsModal thead').empty();
			if (data == null || data.length == 0) {
				$('#editRoomsModal tbody').append('Nema dodatih soba.');
			} else {
				$('#editRoomsModal thead').append('<tr><th>Broj sobe</th><th>Sprat</th><th>Broj kreveta (velicina)</th></tr>');
				$.each(data, function(i, v) {
					$('#editRoomsModal tbody').append('<tr>' + 
							'<td><a class=\"roomPriceModalShow\" href=\"#\" meta-roomnumber=\"' + v.roomNumber + '\">' + v.roomNumber + '</a></td>' + 
							'<td>' + v.floorNumber + '</td>' + 
							'<td>' + v.size + '</td></tr>');
				});
			}
			
			$('.roomPriceModalShow').click(function(e) {
				var roomnumber = e.target.attributes[2].value;
				
				loadRoomPrices(roomnumber);
			});
			$('#editRoomsModal').modal('show');
		},
		statusCode: {
			400: function(data) {
				alert('Potrebno je kreirati profil putem kartice Profil pre rada sa sobama.');
			}
		}
	});
}

function changePass() {
	$('#changePasswordModal1').modal('show');
	
	$('#changePasswordForm1')
	.validate(
			{
				rules : {
					
					changepassword1 : "required",
					changepassword12 : {
						required : true,
						equalTo : "#changepassword1"
					}

				},
				messages : {
					changepassword1 : "Lozinka nije uneta",

					changepassword12 : {
						required : "Unesite ponovo lozinku",
						equalTo : "Lozinke se ne poklapaju"
					}
				}
			});
	
	$('#changePasswordButton1').click(

			function() {
				if ($('#changePasswordForm1').valid()) {
					var d = {};
					d.username = "";
					d.password = $('#changepassword1').val();
					d.firstName = "";
					d.lastName = "";
					d.phone = "";
					d.email = "";
					d.city = "";
					role = "";
					
					console.log(d);

					$.ajax({
						url : '/user/changePassword',
						type : 'post',
						contentType : 'application/json',
						data : JSON.stringify(d),

						success : function(data) {
							if (data == true) {
								$("#changePasswordModal1").modal('hide');

								 //addHotelAdminButtons();
							} else {
								$('#regbtn-error1').show().html(
										'Promena lozinke nije uspela').fadeOut(
										5000);

							}
						}
					});

				}
			});
	
}


$(window).resize(adjust_body_offset);
adjust_body_offset();

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}