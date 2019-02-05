
function showHotel() {
	$('#hotel').click(function() {
		addServicesModal();
		
		$('#items').empty();
		
		$('#items').append("<div class=\"card\">\r\n" + 
				"  <div class=\"card-header\">\r\n" + 
				"    Pretraga hotela\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"card-body\">\r\n" + 
				"    <h5 class=\"card-title\">U izradi</h5>\r\n" + 
				"    <p class=\"card-text\">**</p>\r\n" + 
				"    <a href=\"#\" class=\"btn btn-primary\">**</a>\r\n" + 
				"  </div>\r\n" + 
				"</div><br/>");
		
		$('#items').append('<!-- prikaz hotela -->' +
			'<div class=card-columns id="hotelcards">' +
			'</div>');
		$.ajax({
			url: '/hotel/all',
			type: 'get',
			success: function(data) {
				console.log(data);
				$('#hotelcards').empty();
				$.each(data, function(i, v) {
					c = createHotel(v);
					$('#hotelcards').append(c.html);
					
					showOnMap(c,v);
				});
				
				$('.hotelServiceModalLink').once('click', function(e){
					let hotelId = e.target.attributes[1].value;
					loadHotel(hotelId);
				});
			}
		});
	});
}

$.fn.once = function(a, b) {
    return this.each(function() {
        $(this).off(a).on(a,b);
    });
};

function createHotel(data) {
 	let adr=data.address +' '+data.destination.city+' '+data.destination.country; 
 	console.log(adr);
 	
	let html ="<div class=\"card\">" +
		    //"<img class=\"card-img-top\" src=\"...\" alt=\"Card image cap\">" +
		    "<div class=\"card-body\">" +
		      "<h5 class=\"card-title\">" + data.name + "</h5>" +
		      "<p class=\"card-text\">" + data.address + ", " + data.destination.city + ", " + data.destination.country + "</p>" +
		      "<p class=\"card-text\">" + data.promoDescription + "</p>" +
		      "<p class=\"card-text\"><a class=\"hotelServiceModalLink\" meta-id=\"" + data.id + "\" data-toggle=\"modal\" href=\"#hotelServiceModal\">Dodatne usluge</a></p>" +
		      "<p class=\"card-text\"><a role=\"button\" class=\"btn btn-primary hotelReservationLink\" meta-id=\"" + data.id + "\" href=\"#\">Rezervisi</a></p>" +
		      //"<p class=\"card-text\"><small class=\"text-muted\">Last updated 3 mins ago</small></p>" +
		      //'<div  class="card-footer"><div class="map" id="map'+data.id+'" style="height: 250px"></div>' +
		      "<div class=\"card-footer\"><div class=\"map\" id=\"map"+data.id+"\" style=\"height: 250px\"></div>" +
		    "</div>" +
		  "</div>";
	
	$('.hotelReservationLink').once('click', function(e){
		let hotelId = e.target.attributes[2].value;
		
		drawReservationModal();
		
		$('#hotelReservationModal').modal('show');
		$('.showAvailableRoomsButton').once('click', function(e){
			let size = $('#ressize').val();
			let date = [];
			date.push($('#resDateOfArrival').val());
			date.push($('#resDateOfDeparture').val());
			$.ajax({
				url: '/hotel/showavailablerooms/' + hotelId + '/' + size,
				type: 'post',
				data: JSON.stringify(date),
				contentType: 'application/json',
				success: function(e) {
					console.log(e);
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

function loadHotel(id) {
	$.ajax({
		url: '/hotel/' + id + '/additionalservices',
		type: 'get',
		success: function(data) {
			$('#hotelServiceModal tbody').empty();
			$('#hotelServiceModal thead').empty();
			if (data == null || data.length == 0) {
				$('#hotelServiceModal thead').append('<tr><th>Hotel ne nudi dodatne usluge.</th></tr>');
			} else {
				$('#hotelServiceModal thead').append('<tr><th>Naziv usluge</th><th>Cena usluge</th></tr>');
				$.each(data, function(i, v) {
					$('#hotelServiceModal tbody').append('<tr>' + 
							'<td>' + v.name + '</td>' + 
							'<td>' + v.price + '</td></tr>');
				});
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
				"						<div class=\"form-group col\">\r\n" + 
				"							<input type=\"number\" \r\n" + 
				"								class=\"form-control\" name=\"size\" placeholder=\"Velicina sobe\" id=\"ressize\">\r\n" + 
				"						</div>\r\n" + 
				"					</div>" +
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

