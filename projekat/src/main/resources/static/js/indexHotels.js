
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
		      "<p class=\"card-text\"><small class=\"text-muted\">Last updated 3 mins ago</small></p>" +
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
//			for (let j = 0;  j < data.branchOffices.length; j++){
//				let bo=data.branchOffices[j];
//				let adr=bo.address+' '+bo.location.city+' '+bo.location.country;
//				$.ajax({
//					url:'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='+adr.replace(/ /g,'+'),
//					// url: 'https://geocode-maps.yandex.ru/1.x/?apikey=68f27d0d-4416-406a-8570-ec7b43f271a6&format=json&geocode='+adr.replace(/ /g,'+')+'&lang=en-US',
//			         type: 'get',
//			         async:false,
//			         success: function (mi){
//			        	 let pos=mi.Response.View[0].Result[0].Location.DisplayPosition;
//			        	 let pm = new ymaps.Placemark([pos.Latitude,pos.Longitude],
//	 							{ hintContent: adr, balloonContent: adr });
//			        	 map.geoObjects.add(pm);
//			         }
//
//				});
//			}
		 });
}

//function showOnMap(c,data){
//	 ymaps.ready(function(){
//			 
//			var map = new ymaps.Map(c.id, {
//	            center: [c.pos.Latitude,c.pos.Longitude], 
//	            zoom: 10
//	        });
//			let myPlacemark = new ymaps.Placemark([c.pos.Latitude,c.pos.Longitude],
//					{ hintContent: c.name, balloonContent: c.adr });
//			map.geoObjects.add(myPlacemark);
////			for (let j = 0;  j < data.branchOffices.length; j++){
////				let bo=data.branchOffices[j];
////				let adr=bo.address+' '+bo.location.city+' '+bo.location.country;
////				$.ajax({
////					url:'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='+adr.replace(/ /g,'+'),
////					// url: 'https://geocode-maps.yandex.ru/1.x/?apikey=68f27d0d-4416-406a-8570-ec7b43f271a6&format=json&geocode='+adr.replace(/ /g,'+')+'&lang=en-US',
////			         type: 'get',
////			         async:false,
////			         success: function (mi){
////			        	 let pos=mi.Response.View[0].Result[0].Location.DisplayPosition;
////			        	let pm = new ymaps.Placemark([pos.Latitude,pos.Longitude],
////	 							{ hintContent: adr, balloonContent: adr });
////			       	map.geoObjects.add(pm);
////			         }
////					
////				});
////			}
//		 });
//}
//
//function createHotel(data) {
//	let adr=data.address +' '+data.destination.city+' '+data.destination.country;
//	let html = '<div class="col-md-4 text-center">';
//	html += '<div class="rac-company card border border-dark">';
//	html += '<div class="card-header"><h3 "class=" card-title rac-name">' + data.name + '</h3>'+'</div>';
//	html += '<div class="card-body">';
//		html += '<h3 "class="rac-address">' + adr + '</h3>';
//		html += '<textarea readonly style="width: -webkit-fill-available;" class="rac-description"> ' + data.description + '</textarea>';
//		html+='</div>';
//		
//		html += '<div  class="card-footer"><div class="map" id="map'+data.id+'" style="height: 250px"></div>';
//		html +='<button class="btn shcars btn-primary" id="'+data.id+'">Automobili</button>'
//	html += '	</div></div></div>';
//
//	
//	
//	let ret={};
//	ret.html=html;
//	ret.id='map'+data.id;
//	ret.name=data.name;
//	ret.adr=adr;
//	$.ajax({
//		 url: 'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='+adr.replace(/ /g,'+'),
//        type: 'get',
//        async:false,
//        success: function (mi){
//       	 ret.pos=mi.Response.View[0].Result[0].Location.DisplayPosition;
//        }
//		
//	});
//	return ret;
//}