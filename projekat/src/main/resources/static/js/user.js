//za mape sam uzeo yandex
//odustao sam od njegovog geocode servise jer nece da mi nadje ulice u budjanvcima :@
//tako da se za geokodiranje koristi here
//a za prikaz mape yandex

function getToken() {
	return localStorage.getItem('jwtToken');
}
function refreshToken() {
	$.ajax({
		url : '/user/refresh',
		type : 'post',
		success : function(data) {
			localStorage.setItem('jwtToken', data.accessToken);
		}
	});
}

$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if(settings.url.includes('https'))
		return;
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);

});

$(document).ready(

function() {
	setInterval(refreshToken, 60000); // svaki min
	// LOGOUT
	  $('#logout').click(function () {
	        localStorage.setItem('jwtToken', null);
	        window.location.href = '/';
	    });
	  
	    $('#rent').click(function () {
	        location.hash = 'rentacar';
	    });
	    
	    $(window).on('hashchange', function () {
	        // alert('Changed');
	        if (location.hash === '#rentacar') {
	            showRentACar();
	        } else if (location.hash === '#mycompany') {
	            myCompanyRACAdmin();
	        }else if (location.hash === '#mycars') {
	            myCarsRACAdmin();
	            }
	    });
	
	    
	    $(window).trigger('hashchange');
});


function showRentACar(){
	
	 $.ajax({
         url: 'rentacar/',
         type: 'get',
         success: function (data) {
        	 $('#items').empty();
 			$('#items').append('<div class="row" id="row"></div>')
 			for (let i = 0; i < data.length; i++) {
 				let c = createRAC(data[i]);
 				$('#row').append(c.html);
 				showOnMap(c,data[i]);
 			}
 			
        	 
         }
	 });
	
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
			for (let j = 0;  j < data.branchOffices.length; j++){
				let bo=data.branchOffices[j];
				let adr=bo.address+' '+bo.location.city+' '+bo.location.country;
				$.ajax({
					url:'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='+adr.replace(/ /g,'+'),
					// url: 'https://geocode-maps.yandex.ru/1.x/?apikey=68f27d0d-4416-406a-8570-ec7b43f271a6&format=json&geocode='+adr.replace(/ /g,'+')+'&lang=en-US',
			         type: 'get',
			         async:false,
			         success: function (mi){
			        	 let pos=mi.Response.View[0].Result[0].Location.DisplayPosition;
			        	let pm = new ymaps.Placemark([pos.Latitude,pos.Longitude],
	 							{ hintContent: adr, balloonContent: adr });
			       	map.geoObjects.add(pm);
			         }
					
				});
			}
		 });
}

function createRAC(data) {
	let adr=data.address +' '+data.location.city+' '+data.location.country;
	let html = '<div class="col-md-4 text-center">';
	html += '<div class="rac-company card border border-dark">';
	html += '<div class="card-header"><h3 "class=" card-title rac-name">' + data.name + '</h3>'+'</div>';
	html += '<div class="card-body">';
		html += '<h3 "class="rac-address">' + adr + '</h3>';
		html += '<textarea readonly style="width: -webkit-fill-available;" class="rac-description"> ' + data.description + '</textarea>';
		html+='</div>';
		
		html += '<div  class="card-footer"><div class="map" id="map'+data.id+'" style="height: 250px"></div>';
		html +='<button class="btn btn-primary">Automobili</button>'
	html += '	</div></div></div>';

	
	
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
