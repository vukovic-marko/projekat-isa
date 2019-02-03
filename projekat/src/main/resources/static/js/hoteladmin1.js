/*
 * ovde su funckije za:
 * 	definisanje dodatnih usluga
 */

function loadAdditionalServices() {
	$.ajax({
		url: '/hoteladmin/additionalservices',
		type: 'get',
		success: function(data) {
			$('#editAdditionalServicesModal tbody').empty();
			$('#editAdditionalServicesModal thead').empty();
			if (data == null || data.length == 0) {
				$('#editAdditionalServicesModal tbody').append('Nema dodatih dodatnih usluga.');
			} else {
				$('#editAdditionalServicesModal thead').append('<tr><th>Naziv usluge</th><th>Cena usluge</th></tr>');
				$.each(data, function(i, v) {
					$('#editAdditionalServicesModal tbody').append('<tr>' + 
							'<td>' + v.name + '</td>' + 
							'<td>' + v.price + '</td></tr>');
				});
			}
			$('#editAdditionalServicesModal').modal('show');
		},
		statusCode: {
			400: function(data) {
				alert('Potrebno je kreirati profil putem kartice Profil pre rada sa dodatnim uslugama.');
			}
		}
	});
}

function addAdditionalService() {
	$('#addAdditionalServiceForm')
	.validate(
			{
				rules : {
					
					servicename : {
						required: true
					},
					serviceprice : {
						required: true
					}

				},
				messages : {
					servicename : {
						required: "Naziv usluge nije unet"
					},
					serviceprice: {
						required: "Cena usluge nije uneta"
					}
				}
			});
	
	if ($('#addAdditionalServiceForm').valid()) {
		var d = {};
		d.name = $('#addservicename').val();
		d.price = $('#addserviceprice').val();
				
		$.ajax({
			url: 'hoteladmin/addservice',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function(data) {
				loadAdditionalServices();
			}
		});
	}
}

function loadRoomPrices(roomnumber) {
	$('#addRoomPriceModal .modal-title').empty();
	$('#addRoomPriceModal .modal-title').append(roomnumber);
	
	var room = {};
	room.roomNumber = roomnumber;
	room.floorNumber = "";
	room.size = "";
	
	$.ajax({
		url: 'hoteladmin/room/prices',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(room),
		success: function(data) {
			$('#addRoomPriceModal tbody').empty();
			$('#addRoomPriceModal thead').empty();
			if (data == null || data.length == 0) {
				$('#addRoomPriceModal tbody').append('Nema dodatih cena soba.');
			} else {
				$('#addRoomPriceModal thead').append('<tr><th>Datum pocetka</th><th>Datum kraja</th><th>Cena</th></tr>');
				$.each(data, function(i, v) {
					$('#addRoomPriceModal tbody').append('<tr>' + 
							'<td>' + v.startDate + '</td>' + 
							'<td>' + v.endDate + '</td>' + 
							'<td>' + v.price + '</td></tr>');
				});
			}
			$('#editRoomsModal').modal('hide');
			$('#addRoomPriceModal').modal('show');
		},
		statusCode: {
			400: function(data) {
				alert('Potrebno je kreirati profil putem kartice Profil pre rada sa cenama soba.');
			}
		}
	});
	
	$('#addRoomPriceModal .closeAddPriceModal').click(function() {
		$('#addRoomPriceModal').modal('hide');
		$('#editRoomsModal').modal('show');
	});
	
	$('#addRoomPriceButton').click(function() {
		var roomnumber = $('#addRoomPriceModal .modal-title').text();
		addRoomPriceButton(roomnumber);
	});
}

function addRoomPriceButton() {
	$('#addRoomPriceForm')
	.validate(
			{
				rules : {
					
					startdate : {
						required: true
					},
					enddate : {
						required: true
					},
					price: {
						required: true
					}

				},
				messages : {
					startdate : {
						required: "Datum pocetka nije unet"
					},
					enddate: {
						required: "Datum zavrsetka nije unet"
					},
					price: {
						required: "Cena sobe nije uneta"
					}
				}
			});
}
