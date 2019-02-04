
function next() {
	$('#hotel').click(function() {
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
			}
		});
	});
}

function createHotel(data) {
 	let adr=data.address +' '+data.destination.city+' '+data.destination.country; 
 	console.log(adr);
 	
	let html ="<div class=\"card\">" +
		    //"<img class=\"card-img-top\" src=\"...\" alt=\"Card image cap\">" +
		    "<div class=\"card-body\">" +
		      "<h5 class=\"card-title\">" + data.name + "</h5>" +
		      "<p class=\"card-text\">" + data.address + ", " + data.destination.city + ", " + data.destination.country + "</p>" +
		      "<p class=\"card-text\">" + data.promoDescription + "</p>" +
		      //"<p class=\"card-text\"><small class=\"text-muted\">Last updated 3 mins ago</small></p>" +
		      //'<div  class="card-footer"><div class="map" id="map'+data.id+'" style="height: 250px"></div>' +
		      "<div class=\"card-footer\"><div class=\"map\" id=\"map"+data.id+"\" style=\"height: 250px\"></div>" +
		    "</div>" +
		  "</div>";
	
	
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
         },
         error: function(e) {
        	 console.log(e);
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