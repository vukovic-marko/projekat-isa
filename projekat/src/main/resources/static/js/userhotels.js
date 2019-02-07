
$(document).ready(function() {
	// TODO
	$('.search-button').click(function(){
		let search = {};
		search.dateOfArrival = $('#sDateOfArrival').val();
		search.dateOfDeparture = $('#sDateOfDeparture').val();
		search.hotel = {};
		search.hotel.name = $('#sname').val();
		search.hotel.address = $('#saddress').val();
		search.hotel.destination = {};
		search.hotel.destination.city = $('#scity').val();
		search.hotel.destination.country = $('#scountry').val();

		$.ajax({
			url: '/hotel/search',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(search),
			success: function(data) {
				alert('ok');
				console.log(data);
			}
		});
	});
});


function showHotel() {
	addServicesModal();

	$('#items').empty();

	$('#items').append("<div class=\"card mx-auto\" style=\"width: 40rem;\">\r\n" + 
			"  <div class=\"card-header\">\r\n" + 
			"    <h4>Pretraga hotela</h4>\r\n" + 
			"  </div>\r\n" + 
			"  <div class=\"card-body\">\r\n" + 
//			"    <h5 class=\"card-title\">U izradi</h5>\r\n" + 
//			"    <p class=\"card-text\">**</p>\r\n" + 
//			"    <a href=\"#\" class=\"btn btn-primary\">**</a>\r\n" + 
			"					<div class=\"form-row\">\r\n" + 
			"						<div class=\"form-group col\">\r\n" + 
			"							<input\r\n" + 
			"								type=\"date\" class=\"form-control\" id=\"sDateOfArrival\"\r\n" + 
			"								name=\"dateOfArrival\" >\r\n" + 
			"						</div>\r\n" + 
			"						<div class=\"form-group col col-md-auto\">\r\n" + 
			"							<label>-</label>\r\n" + 
			"						</div>\r\n" + 
			"						<div class=\"form-group col\">\r\n" + 
			"							<input type=\"date\" \r\n" + 
			"								class=\"form-control\" name=\"dateOfDeparture\" id=\"sDateOfDeparture\">\r\n" + 
			"						</div>\r\n" + 
			"					</div>" +
			"					<div class=\"form-row\">\r\n" + 
			"						<div class=\"form-group col\">\r\n" + 
			"							<input type=\"text\" \r\n" + 
			"								class=\"form-control\" name=\"name\" placeholder=\"Naziv hotela\" id=\"sname\">\r\n" + 
			"						</div>\r\n" + 
			"					</div>" +
			"					<div class=\"form-row\">\r\n" + 
			"						<div class=\"form-group col-md-6\">\r\n" + 
			"							<input type=\"text\" \r\n" + 
			"								class=\"form-control\" name=\"address\" placeholder=\"Adresa hotela\" id=\"saddress\">\r\n" + 
			"						</div>\r\n" + 
			"						<div class=\"form-group col-md-3\">\r\n" + 
			"							<input type=\"text\" \r\n" + 
			"								class=\"form-control\" name=\"city\" placeholder=\"Grad\" id=\"scity\">\r\n" + 
			"						</div>\r\n" + 
			"						<div class=\"form-group col-md-3\">\r\n" + 
			"							<input type=\"text\" \r\n" + 
			"								class=\"form-control\" name=\"country\" placeholder=\"Drzava\" id=\"scountry\">\r\n" + 
			"						</div>\r\n" + 
			"					</div>" +
			"					<div class=\"form-row\">\r\n" + 
			"						<div class=\"form-group mx-auto\">\r\n" + 
			" 							<button class=\"btn btn-primary search-button\" href=\"#\">Pretrazi</button>" +
			"						</div>\r\n" + 
			"					</div>" +
			"  </div>\r\n" + 
	"</div><br/>");

	$('#items').append('<!-- prikaz hotela -->' +
			'<div class="row justify-content-center" id="hotelcards">' +
	'</div>');
	$.ajax({
		url: '/hotel/all',
		type: 'get',
		success: function(data) {
			$('#hotelcards').empty();
			$.each(data, function(i, v) {
				c = createHotel(v);
				$('#hotelcards').append(c.html);

				showOnMap(c,v);
			});

			$('.hotelServiceModalLink').once('click', function(e){
				let hotelId = e.target.attributes[1].value;
				loadHotel(hotelId, false);
				//localStorage.setItem("hotelId", JSON.stringify(hotelId));
			});
		}
	});
}

$.fn.once = function(a, b) {
    return this.each(function() {
        $(this).off(a).on(a,b);
    });
};

function createHotel(data) {
 	let adr=data.address +' '+data.destination.city+' '+data.destination.country; 
 	
	let html ="<div class=\"card\" style=\"width: 400px;margin: 5px 5px 5px 5px;\">" +
		    "<div class=\"map map-container-5\" id=\"map"+data.id+"\" style=\"height: 250px\"></div>" +
		    "<div class=\"card-body\">" +
		      "<h5 class=\"card-title\">" + data.name + "</h5>" +
		      "<p class=\"card-text\">" + data.address + ", " + data.destination.city + ", " + data.destination.country + "</p>" +
		      "<p class=\"card-text\">" + data.promoDescription + "</p>" +
		      "<p class=\"card-text\"><a class=\"hotelServiceModalLink\" meta-id=\"" + data.id + "\" data-toggle=\"modal\" href=\"#hotelServiceModal\">Dodatne usluge</a></p>" +
		      "<p class=\"card-text\"><a role=\"button\" class=\"btn btn-primary hotelReservationLink\" meta-id=\"" + data.id + "\" href=\"#\">Rezervisi</a></p>" +
		      //"<div class=\"card-footer\"><div class=\"map\" id=\"map"+data.id+"\" style=\"height: 250px\"></div>" +
		    "</div>" +
		  "</div>";
	
	$('.hotelReservationLink').once('click', function(e){
		let hotelId = e.target.attributes[2].value;
		
		let sobe = new Map();
		drawReservationModal();
		$('#hotelReservationModal thead').empty();
		$('#hotelReservationModal tbody').empty();
		
		$('.addRoomToReservationButton').once('click', function() {
			let soba = $('#ressize').val();
			if (sobe.get(soba) == null) {
				sobe.set(soba, 1);
			} else {
				let help = sobe.get(soba);
				help++;
				sobe.set(soba, help);
			}
			//console.log(sobe);
			$('#hotelReservationModal thead').empty();
			$('#hotelReservationModal tbody').empty();
			$('#hotelReservationModal thead').append("<tr><th>Velicina sobe</th><th>Broj soba</th></tr>");
			for (var [key, value] of sobe) {
				$('#hotelReservationModal tbody').append("<tr><td>" + key + "</td><td>" + value + "</td></tr>");
			}
		});
		
		$('#hotelReservationModal').modal('show');
		$('.showAvailableRoomsButton').once('click', function(e){
			let size = $('#ressize').val();
			let date = [];
			date.push($('#resDateOfArrival').val());
			date.push($('#resDateOfDeparture').val());
			
			let data = {};
			data.roomConfigurations = Array.from(sobe.keys());
			data.numberOfRooms = Array.from(sobe.values());
			data.dateOfArrival = $('#resDateOfArrival').val();
			data.dateOfDeparture = $('#resDateOfDeparture').val();
			data.hotelId = hotelId;
			localStorage.setItem("hotelId", JSON.stringify(hotelId));
			
			localStorage.setItem("dateOfArrival", JSON.stringify(data.dateOfArrival));
			localStorage.setItem("dateOfDeparture", JSON.stringify(data.dateOfDeparture));
			
						
			$.ajax({
				url: '/hotel/showavailablerooms',
				type: 'post',
				data: JSON.stringify(data),
				contentType: 'application/json',
				success: function(e) {
					//console.log(e);
					drawAvailableRoomsModal();
					$('#roomCards').empty();
					$.each(e, function(i,v) {
						let cena = -1;
						
						var date1 = new Date(data.dateOfArrival);
						var date2 = new Date(data.dateOfDeparture);
						var timeDiff = Math.abs(date2.getTime() - date1.getTime());
						var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
						
						console.log(diffDays);
						
						$.each(v.roomPrices, function (ii, vv) {
							if (vv.startDate <= data.dateOfArrival && vv.endDate >= data.dateOfDeparture)
								cena = vv.price;
						});
						
						let ukupnaCena = cena * diffDays;
						
						$('#roomCards').append(
								"<div class=\"card\">" +
							    "	<div class=\"card-body\">" +
							    "		<h5 class=\"card-title\"><input meta-size=\"" + v.size + "\" meta-floorNumber=\"" + v.floorNumber + "\" meta-cena=\"" + cena + "\" meta-ukupnaCena=\"" + ukupnaCena + "\" type=\"checkbox\" value=\"" + v.roomNumber + "\"/> " + v.roomNumber + "</h5>" +
							    "		<p class=\"card-text\">Broj kreveta: " + v.size + ", Sprat: " + v.floorNumber  + "</p>" +
							    "		<p class=\"card-text\">Cena nocenja: " + cena + ", Ukupna cena: " + ukupnaCena + "</p>" +
							    "	</div>" +
							  	"</div><br />");
					});
					
					

					$('#additionalServicesReservationLink').once("click", function(e) {
						$('#availableRoomsModal').modal('toggle');
						loadHotel(data.hotelId, true);
						$('#hotelServiceModal .btn-danger').once("click", function(e){
							$('#hotelReservationModal').modal('hide');
							$('#availableRoomsModal').modal('toggle');
							$('#hotelServiceModal .btn-danger').off("click");
						});
						
						
						e.preventDefault();
					});
					
					$('#addHotelReservationToCart').once("click", function(){
						let roomCards = $('#roomCards .card input');
						let rooms = [];
						let roomsForStorage = [];
						$.each(roomCards, function(i,v) {
							if (v.checked == true) {
//								console.log(v.attributes['meta-size'].value);
//								console.log(v.attributes['meta-floorNumber'].value);
//								console.log(v.attributes['meta-cena'].value);
//								console.log(v.attributes['meta-ukupnaCena'].value);
//								console.log(v);
								rooms.push(v.value);
								let rroom = {};
								rroom.size = v.attributes['meta-size'].value;
								rroom.floorNumber = v.attributes['meta-floorNumber'].value;
								rroom.cena = v.attributes['meta-cena'].value;
								rroom.ukupnaCena = v.attributes['meta-ukupnaCena'].value;
								roomsForStorage.push(rroom);
							}
						});
						
						let servicesCards = $('#hotelServiceModal input');
						let services = [];
						let servicesForStorage = [];
						$.each(servicesCards, function(i, v) {
							if (v.checked == true) {
								services.push(v.value);
								let sservice = {};
								sservice.name = v.attributes['meta-name'].value;
								sservice.price = v.attributes['meta-price'].value;
								servicesForStorage.push(sservice);
							}
								
						});
						
						//console.log(rooms);
						//console.log(services);
						
						let hotelCart = {};
						hotelCart.roomNumbers = rooms;
						hotelCart.services = services;
						hotelCart.dateOfArrival = JSON.parse(localStorage.getItem("dateOfArrival"));
						localStorage.removeItem("dateOfArrival");
						hotelCart.dateOfDeparture = JSON.parse(localStorage.getItem("dateOfDeparture"));
						localStorage.removeItem("dateOfDeparture");
						hotelCart.hotelId = JSON.parse(localStorage.getItem("hotelId"));
						localStorage.removeItem("hotelId");
						
						//console.log(hotelCart);
						
						if (rooms.length == 0) {
							alert('Niste izabrali nijednu sobu.')
						} else {
							// ok
							localStorage.setItem("hotelCart", JSON.stringify(hotelCart));
							localStorage.setItem("roomsForStorage", JSON.stringify(roomsForStorage));
							localStorage.setItem("servicesForStorage", JSON.stringify(servicesForStorage));
							//localStorage.removeItem("hotelCart");
							$('#availableRoomsModal').modal('toggle');
							
							if ($('#hotelcart').length == 0)
								$('.logged-out-navbar .nav-item').append("<button id=\"hotelcart\" class=\"btn btn-primary\">Korpa</button>");
							
							$('#hotelcart').once("click", function() {
								console.log(JSON.parse(localStorage.getItem("hotelCart")));
//								console.log("---");
//								console.log(JSON.parse(localStorage.getItem("roomsForStorage")));
								console.log(JSON.parse(localStorage.getItem("servicesForStorage")));
								
								
								let hotelRes = JSON.parse(localStorage.getItem("hotelCart"));
								console.log(hotelRes.dateOfArrival);
								console.log(hotelRes.dateOfDeparture);
								console.log(hotelRes.hotelId + " hotelId");
								
								drawHotelCartModal();
								
								$('#dateCart').append("<h4>" + hotelRes.dateOfArrival + "  -  " + hotelRes.dateOfDeparture + "</h4><br />");
								//$('#hotelCartModal .modal-body').append(hotelRes.dateOfArrival + " - " + hotelRes.dateOfDeparture);
								
								let finalnaCena = 0;
								
								let roomsForStorage = JSON.parse(localStorage.getItem("roomsForStorage"));
								$.each(roomsForStorage, function(a, b) {
									$('#roomCardsCart').append(
											"<div class=\"card\">" +
										    "	<div class=\"card-body\">" +
										    "		<h5 class=\"card-title\">Soba: " + hotelRes.roomNumbers[a] + "</h5>" +
										    "		<p class=\"card-text\">Broj kreveta: " + b.size + ", Sprat: " + b.floorNumber  + "</p>" +
										    "		<p class=\"card-text\">Cena nocenja: " + b.cena + ", Ukupna cena: " + b.ukupnaCena + "</p>" +
										    "	</div>" +
										  	"</div><br />");
									finalnaCena += parseInt(b.ukupnaCena);
								});
								
								let servicesForStorage = JSON.parse(localStorage.getItem("servicesForStorage"));
								$.each(servicesForStorage, function(a,b) {
									$('#servicesCart').append(
											"<div class=\"card\">" +
										    "	<div class=\"card-body\">" +
										    "		<h5 class=\"card-title\">Soba: " + b.name + "</h5>" +
										    "		<p class=\"card-text\">Cena: " + b.price + "</p>" +
										    "	</div>" +
										  	"</div><br />");
									finalnaCena += parseInt(b.price);
								});
								
								$('#finalPriceCart').append("<h4>Ukupna cena: " + finalnaCena  + "</h4><br/>");
								
								$('#hotelCartModal').modal('show');
								
								$('#makeHotelReservationButton').once("click", function() {
									let r = JSON.parse(localStorage.getItem("hotelCart"));
									console.log(r);
									$.ajax({
										url: '/hotel/addhotelreservation',
										type: 'post',
										contentType: 'application/json',
										data: JSON.stringify(r),
										success: function() {
											alert('Rezervacija uspesno dodata!');
											$('#hotelCartModal').modal('hide');
										}
									});
									
									//alert('make res!!!');
								});
							});
							
						}
					});
					
					$('#hotelReservationModal').modal('toggle');
					$('#availableRoomsModal').modal('show');
				}
			});
		});
	});
	
	let ret={};
	ret.html=html;
	ret.id='map'+data.id;
	ret.name=data.name;
	ret.adr=adr;
	$.ajax({
		 url: 'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='+adr.replace(/ /g,'+'),
         type: 'get',
         async:false,
         success: function (mi){
        	 ret.pos=mi.Response.View[0].Result[0].Location.DisplayPosition;
         }
		
	});
	return ret;
}

function showOnMap(c,data){
	 ymaps.ready(function(){

			var map = new ymaps.Map(c.id, {
	            center: [c.pos.Latitude,c.pos.Longitude], 
	            zoom: 10
	        });
			let myPlacemark = new ymaps.Placemark([c.pos.Latitude,c.pos.Longitude],
					{ hintContent: c.name, balloonContent: c.adr });
			map.geoObjects.add(myPlacemark);
		 });
}

function addServicesModal() {
	if ($('#hotelServiceModal').length == 0) {
		$('body').append("<div class=\"modal fade\" id=\"hotelServiceModal\">\r\n" + 
				"		<div class=\"modal-dialog modal-md\">\r\n" + 
				"			<div class=\"modal-content\">\r\n" + 
				"				<div class=\"modal-header\">\r\n" + 
				"					<h4 class=\"modal-title\" id=\"editAdditionalServicesModalTitle\">Dodatne usluge</h4>\r\n" + 
				"					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"modal-body\">\r\n" + 
				"					<table class=\"table table-striped table-sm\">\r\n" + 
				"						<thead class=\"text-center\"></thead>\r\n" + 
				"						<tbody class=\"text-center\"></tbody>\r\n" + 
				"					</table>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"modal-footer\">\r\n" + 
				"					<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">Zatvori</button>\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>");
	}
}

function loadHotel(id, reservation) {
	$.ajax({
		url: '/hotel/' + id + '/additionalservices',
		type: 'get',
		success: function(data) {
			$('#hotelServiceModal tbody').empty();
			$('#hotelServiceModal thead').empty();
			if (data == null || data.length == 0) {
				$('#hotelServiceModal thead').append('<tr><th>Hotel ne nudi dodatne usluge.</th></tr>');
			} else {
				if (reservation == true) {
					$('#hotelServiceModal thead').append('<tr><th></th><th>Naziv usluge</th><th>Cena usluge</th></tr>');
					$.each(data, function(i, v) {
						$('#hotelServiceModal tbody').append('<tr>' + 
								'<td><input meta-name="' + v.name + '" meta-price="' + v.price + '" type="checkbox" value="' + v.id + '" + v.name + "\" />' +
								'<td>' + v.name + '</td>' + 
								'<td>' + v.price + '</td></tr>');
					});
				} else {
					$('#hotelServiceModal thead').append('<tr><th>Naziv usluge</th><th>Cena usluge</th></tr>');
					$.each(data, function(i, v) {
						$('#hotelServiceModal tbody').append('<tr>' + 
								'<td>' + v.name + '</td>' + 
								'<td>' + v.price + '</td></tr>');
					});
				}
			}
			$('#hotelServiceModal').modal('show');
		}
	});
}

function drawReservationModal() {
	if ($('#hotelReservationModal').length == 0) {
		$('body').append("<div class=\"modal fade\" id=\"hotelReservationModal\">\r\n" + 
				"		<div class=\"modal-dialog modal-md\">\r\n" + 
				"			<div class=\"modal-content\">\r\n" + 
				"				<div class=\"modal-header\">\r\n" + 
				"					<h4 class=\"modal-title\" id=\"hotelReservationModalTitle\">Rezervisi</h4>\r\n" + 
				"					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"modal-body\">\r\n" + 
				"					<div class=\"form-row\">\r\n" + 
				"						<div class=\"form-group col\">\r\n" + 
				"							<input\r\n" + 
				"								type=\"date\" class=\"form-control\" id=\"resDateOfArrival\"\r\n" + 
				"								name=\"dateOfArrival\" >\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"form-group col col-md-auto\">\r\n" + 
				"							<label>-</label>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"form-group col\">\r\n" + 
				"							<input type=\"date\" \r\n" + 
				"								class=\"form-control\" name=\"dateOfDeparture\" id=\"resDateOfDeparture\">\r\n" + 
				"						</div>\r\n" + 
				"					</div>" +
				"					<div class=\"form-row\">\r\n" + 
				"						<div class=\"form-group col-md-9\">\r\n" + 
				"							<input type=\"number\" \r\n" + 
				"								class=\"form-control\" name=\"size\" placeholder=\"Velicina sobe\" id=\"ressize\">\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"form-group col-md-2\">\r\n" + 
				"							<button \r\n" + 
				"								class=\"btn btn-primary addRoomToReservationButton\">Dodaj sobu</button>\r\n" + 
				"						</div>\r\n" + 
				"					</div>" +
				"					<table class=\"table table-striped table-sm\">\r\n" + 
				"						<thead class=\"text-center\"></thead>\r\n" + 
				"						<tbody class=\"text-center\"></tbody>\r\n" + 
				"					</table>" +
				"					<div class=\"form-row\">\r\n" + 
				"						<div class=\"form-group col\">\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"form-group col\">\r\n" + 
				"							<button \r\n" + 
				"								class=\"btn btn-primary showAvailableRoomsButton\">Prikazi dostupne sobe</button>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"form-group col\">\r\n" + 
				"						</div>\r\n" + 
				"					</div>" +
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>");
	}
}

function drawAvailableRoomsModal() {
	if ($('#availableRoomsModal').length == 0) {
		$('body').append("<div class=\"modal fade\" id=\"availableRoomsModal\">\r\n" + 
				"		<div class=\"modal-dialog modal-md\">\r\n" + 
				"			<div class=\"modal-content\">\r\n" + 
				"				<div class=\"modal-header\">\r\n" + 
				"					<h4 class=\"modal-title\" id=\"hotelReservationModalTitle\">Dostupne sobe</h4>\r\n" + 
				"					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"modal-body\">\r\n" + 
				" 					<div  id=\"roomCards\">\r\n" +
				" 					</div>" +
				" 					<div class=\"text-center\"><a href=\"#\" id=\"additionalServicesReservationLink\">Dodatne usluge</a></div>"	+
				"					<div class=\"text-center\"><hr /><button id=\"addHotelReservationToCart\" type=\"button\" class=\"btn btn-primary\">Dodaj u korpu</button></div>\r\n" + 
				"				</div>\r\n " +
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>");
	}
}

function drawHotelCartModal() {
	if ($('#hotelCartModal').length == 0) {
		$('body').append("<div class=\"modal fade\" id=\"hotelCartModal\">\r\n" + 
				"		<div class=\"modal-dialog modal-md\">\r\n" + 
				"			<div class=\"modal-content\">\r\n" + 
				"				<div class=\"modal-header\">\r\n" + 
				"					<h4 class=\"modal-title\" id=\"HotelCartModal\">Korpa</h4>\r\n" + 
				"					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"modal-body\">\r\n" + 
				"					<div id=\"dateCart\"></div>\r\n" +
				"					<div id=\"finalPriceCart\"</div>\r\n" +
				"					<h4>Sobe:</h4><br />" +
				" 					<div  id=\"roomCardsCart\">\r\n" +
				" 					</div><hr />" +
				"					<h4>Dodatne usluge:</h4><br/>" +
				"					<div id=\"servicesCart\"></div>\r\n" +
				"					<div class=\"text-center\"><hr /><button id=\"makeHotelReservationButton\" type=\"button\" class=\"btn btn-primary\">Rezervisi</button></div>\r\n" + 
				"				</div>\r\n " +
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>");
	}
}
