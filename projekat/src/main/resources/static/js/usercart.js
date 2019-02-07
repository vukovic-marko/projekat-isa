function showCart() {
	
		$('#cartModal').modal('toggle');		
	
		$('#cartModal').once('hidden.bs.modal', function () {
			clearModal();
		});
		
		let hotelRes = JSON.parse(localStorage.getItem("hotelCart"));
		let finalnaCena = 0;		
		let roomsForStorage = JSON.parse(localStorage.getItem("roomsForStorage"));
		
		$.each(roomsForStorage, function(a, b) {
			$('#roomCardsCart').append(
					"<div class=\"card\">" +
				    "	<div class=\"card-body\">" +
				    "		<h5 class=\"card-title\">Soba: " + hotelRes.roomNumbers[a] + ", " + hotelRes.dateOfArrival + " - " + hotelRes.dateOfDeparture + "</h5>" +
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
				
		$('#createReservationButton').once("click", function() {
			let r = JSON.parse(localStorage.getItem("hotelCart"));
			console.log(r);
			$.ajax({
				url: '/hotel/addhotelreservation',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify(r),
				success: function() {
					alert('Rezervacija uspesno dodata!');
					$('#cartModal').modal('toggle');
					clearModal();
					localStorage.removeItem("hotelCart");
					localStorage.removeItem("roomsForStorage");
					localStorage.removeItem("servicesForStorage");
				}
			});
			
		});
}

function clearModal() {
	$('#roomCardsCart').empty();
	$('#servicesCart').empty();
	$('#finalPriceCart').empty();
	location.hash = "";
}