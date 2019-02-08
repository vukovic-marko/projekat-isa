function showCart() {
	$("#removeCar").unbind('click');
	$("#removeHotel").unbind('click');
	$("#removeFlight").unbind('click');
	
$("#removeCar").click(function(){
		
	localStorage.removeItem("carReservation");
	clearModal();
	$('#cartModal').modal('toggle');
	setTimeout(function () {
		showCart();
	}, 800);
	});

$("#removeHotel").click(function(){
	localStorage.removeItem("hotelCart");
	localStorage.removeItem("roomsForStorage");
	localStorage.removeItem("servicesForStorage");
	$('#cartModal').modal('toggle');
	setTimeout(function () {
		showCart();
	}, 800);
	
});
$("#removeFlight").click(function(){
	//TODO
	
});
	$('#cartModal').modal('toggle');

	$('#cartModal').once('hidden.bs.modal', function() {
		clearModal();
	});

	let hotelRes = JSON.parse(localStorage.getItem("hotelCart"));
	let finalnaCena = 0;
	let roomsForStorage = JSON.parse(localStorage.getItem("roomsForStorage"));
	
	let hotelReservation = {};

	if (hotelRes != null) {
		hotelReservation.dateOfArrival = hotelRes.dateOfArrival;
		hotelReservation.dateOfDeparture = hotelRes.dateOfDeparture;
		hotelReservation.id = hotelRes.hotelId;
		hotelReservation.rooms = [];
		hotelReservation.services = [];
		$.each(roomsForStorage, function(a, b) {
			$('#roomCardsCart').append(
					"<div class=\"card\">" + "	<div class=\"card-body\">"
							+ "		<h5 class=\"card-title\">Soba: "
							+ hotelRes.roomNumbers[a] + ", "
							+ hotelRes.dateOfArrival + " - "
							+ hotelRes.dateOfDeparture + "</h5>"
							+ "		<p class=\"card-text\">Broj kreveta: "
							+ b.size + ", Sprat: " + b.floorNumber + "</p>"
							+ "		<p class=\"card-text\">Cena nocenja: "
							+ b.cena + ", Ukupna cena: " + b.ukupnaCena
							+ "</p>" + "	</div>" + "</div><br />");
			finalnaCena += parseInt(b.ukupnaCena);
			let temp = {};
			temp.roomNumber = hotelRes.roomNumbers[a];
			hotelReservation.rooms.push(temp);
		});

		let servicesForStorage = JSON.parse(localStorage
				.getItem("servicesForStorage"));

		$.each(servicesForStorage, function(a, b) {
			$('#servicesCart').append(
					"<div class=\"card\">" + "	<div class=\"card-body\">"
							+ "		<h5 class=\"card-title\">Soba: " + b.name
							+ "</h5>" + "		<p class=\"card-text\">Cena: "
							+ b.price + "</p>" + "	</div>" + "</div><br />");
			finalnaCena += parseInt(b.price);
			let temp = {};
			temp.id = hotelRes.services[a];
			hotelReservation.services.push(temp);
		});
		
		localStorage.setItem("hotelReservation", JSON.stringify(hotelReservation));
	}
	// ---AUTO----------
	let car = localStorage.getItem("carReservation");
	if (car != null) {
		car = JSON.parse(car);
		$
				.ajax({
					async : false,
					success : function(data) {
						if (data == false) {
							toastr.error('Auto je vec rezervisan :(')
							localStorage.setItem(null);
							return;

						}
						let d = {};
						d = {};
						d.id = car.id;
						d.startDate = car.startDate;
						d.endDate = car.endDate;

						$
								.ajax({

									url : 'rentacar/getcar',
									type : 'post',
									contentType : 'application/json',
									data : JSON.stringify(d),
									async : false,
									success : function(data) {
										$('#carCart')
												.append(
														"<div class=\"card\">"
																+ "	<div class=\"card-body\">"
																+ "		<h5 class=\"card-title\">Auto: "
																+ data.name
																+ ", "
																+ car.startDate
																+ " - "
																+ car.endDate
																+ "</h5>"
																+ "		<p class=\"card-text\">Marka i model: "
																+ data.brand
																+ ' '
																+ data.model
																+ ", Menjac: "
																+ data.transmission
																+ "</p>"
																+ "		<p class=\"card-text\">Vrata: "
																+ data.doors
																+ ", Sedišta: "
																+ data.seats
																+ "</p>"
																+ "		<p class=\"card-text\">Godište: "
																+ data.year
																+ ", Tip: "
																+ data.type
																+ "</p>"
																+ "		<p class=\"card-text\">Dnevna cena: "
																+ data.price
																+ ", Ukupna cena: "
																+ data.totalPrice
																+ "</p>"
																+ "	</div>"
																+ "</div><br />");
										finalnaCena += data.totalPrice;
										car.totalPrice = data.totalPrice;
										localStorage.setItem("carReservation",JSON
												.stringify(car));
									}

								});

					},
					url : 'rentacar/checkiffree',
					type : 'post',
					contentType : "application/json; charset=utf-8",

					data : JSON.stringify(car)
				});

	}
	// ---------------------------

	if (finalnaCena != 0)
		$('#finalPriceCart').append(
				"<h4>Ukupna cena: " + finalnaCena + " €</h4><br/>");

	$('#createReservationButton').once("click", function() {
		//if (hotelRes != null) 
		{
			let r = JSON.parse(localStorage.getItem("hotelReservation"));
			console.log(r);
			let d = {};
			let c = JSON.parse(localStorage.getItem("carReservation"));
			d.hotelReservation = r;
			console.log(c);
			
			if(c!=null)
			$.ajax({

				url : 'rentacar/getcar',
				type : 'post',
				contentType : 'application/json',
				data : JSON.stringify(c),
				async : false,
				success : function(data) {
					c.car=data;
					c.id=null;
					d.carReservation = c;
					c.startDestination= JSON.parse(localStorage.getItem("carStart"));
					c.endDestination= JSON.parse(localStorage.getItem("carEnd"));
					d.carReservation.startDate=convertDate(c.startDate);

					d.carReservation.endDate=convertDate(c.endDate);
				}
			});
			$.ajax({
			//	url : '/hotel/addhotelreservation',
				url : '/reservate/add',
				type : 'post',
				contentType : 'application/json',
				data : JSON.stringify(d),
				success : function() {
					alert('Rezervacija uspesno dodata!');
					$('#cartModal').modal('toggle');
					clearModal();
					localStorage.removeItem("hotelCart");
					localStorage.removeItem("roomsForStorage");
					localStorage.removeItem("servicesForStorage");
					localStorage.removeItem("carReservation");
					localStorage.removeItem("hotelReservation");
				},
				statusCode : {
					403 : function(data) {
						var string = data.responseText;
						//nema else-if jer moze vise gresaka da bude
						if(string.includes("hotel")){
							
							//TODO
						}
						if(string.includes("flight")){
							
							//TODO
						
						}
						if(string.includes("car")){
							//localStorage.removeItem("carReservation");
							toastr.error('Auto je vec rezervisan');
							$("#removeCar").click();
						}else{
							toastr.error('Ne mozete poslati prazanu rezervaciju');
						}
						
					}
				}
			});
		}
	});
}

function clearModal() {
	$('#roomCardsCart').empty();
	$('#carCart').empty();
	$('#servicesCart').empty();
	$('#finalPriceCart').empty();
	location.hash = "";
}

function convertDate(date){
	let a=date.split('/');
	return a[2]+'-'+a[1]+'-'+a[0];
	
}