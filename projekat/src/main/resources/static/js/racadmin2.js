//jos jedan fajl za rent a car admina xD
//
/**
 * ovde su f-je za: rad sa vozilima
 */

function myCarsRACAdmin() {

	$
			.ajax({
				url : 'racadmin/cars/',
				type : 'get',
				success : function(cars) {
					$('#items').empty();

					html = '<div row row-eq-height bakground >';
					html += '<button class="btn btn-primary" id="newCar">Novi auto</button>';
					html += '<div id="cars"  class="container" ></div>';
					$('#items').append(html);
					$('#newCar')
							.click(
									function() {
										let c = {};
										$.ajax({
											url : 'racadmin/company/',
											type : 'get',
											async : false,
											success : function(company) {
												c = company;
											}

										});
										if (c == "") {
											alert('Ne možete dodati auto nepostojećoj kompaniji!');
											return;

										}
										showModalForm('add');
									});
					$('#cars').load("rent/parts.html #carsTable", function() {
						if (cars != null)
							for (let i = 0; i < cars.length; i++) {
								$('#carsTable').append(createCarTR(cars[i]));
							}
						deleteCarListeners();
					});

				}

			});

}

function createCarTR(value) {
	html = '<tr class="car-row" id="' + value.id + '">';
	html += '<td class="name">' + value.name + '</td>';
	html += '<td class="brand">' + value.brand + '</td>';
	html += '<td class="model">' + value.model + '</td>';
	html += '<td class="seats">' + value.seats + '</td>';
	html += '<td class="year">' + value.year + '</td>';
	html += '<td class="type">' + value.type + '</td>';
	html += '<td class="doors">' + value.doors + '</td>';
	html += '<td class="transmission">' + value.transmission + '</td>';
	html += '<td class="price">' + value.price + '</td>';
	html += '<td><button class="btn rembtn btn-danger close">×</button></td>';
	html += '</tr>';
	return html;
}

function showModalForm(flag, id, cb) {
	$('body')
			.append(
					'<div id="modalcont"></div><button id="hbtn" hidden data-toggle="modal" href="#modalCar"></button>');
	$('#modalcont').load(
			"rent/parts.html #modalCar",
			function() {
				$('#newcarform').validate({
					rules : {

						modalmodel : {
							required : true
						},
						modalname : {
							required : true
						},
						modalbrand : {
							required : true
						},
						modalprice : {
							required : true
						}

					},
					messages : {
						modalname : {
							required : "Ime nije uneto",
						},

						modalmodel : {
							required : "Model nije unet",
						},
						modalbrand : {
							required : "Marka nije uneta"
						},

						modalprice : {
							required : "Cena nije uneta"
						}
					}
				});
				$('#modalCar').on('hidden.bs.modal', function(e) {
					$('#modalcont').remove();
					$('#hbtn').remove();
				});

				$('#modalCar').on('shown.bs.modal', function(e) {
					if (cb != undefined)
						cb();
				});
				if (flag == 'edit') {
					$('#modal-title').html('Izmeni auto');
					$('#addbo').html('Izmeni');

				}

				$('#hbtn').trigger("click");
				$('#addbo').click(
						function() {

							if ($('#newcarform').valid()) {
								var d = {};
								d.name = $('#modalname').val();
								d.brand = $('#modalbrand').val();
								d.model = $('#modalmodel').val();
								d.price = $('#modalprice').val();
								d.doors = $('#modaldoors').val();
								d.year = $('#modalyear').val();
								d.type = $('#modaltype').val();
								d.seats = $('#modalseats').val();
								d.transmission = $('#modaltransmission').val();

								if (flag == 'add') {
									$.ajax({
										url : '/racadmin/car',
										type : 'post',
										contentType : 'application/json',
										data : JSON.stringify(d),

										success : function(data) {
											if (data != null) {

												$('#btn-error2').show().html(
														'Uspesno!').fadeOut(
														5000);
												d.id = data;
												let html = createCarTR(d);
												$('#carsTable tbody').append(
														html);
												deleteCarListeners();
											} else {
												$('#btn-error2').show().html(
														'Doslo je do greske!')
														.fadeOut(5000);
											}
										}

									});
								} else {
									d.id = id;
									$.ajax({
										url : '/racadmin/car',
										type : 'put',
										contentType : 'application/json',
										data : JSON.stringify(d),

										success : function(data) {
											if (data) {

												$('#btn-error2').show().html(
														'Uspesno!').fadeOut(
														5000);
												$('#' + id).replaceWith(
														createCarTR(d));
												deleteListeners();
											} else {
												$('#btn-error2').show().html(
														'Doslo je do greske!')
														.fadeOut(5000);
											}
										}

									});
								}
							}

						});

			});

}

function deleteCarListeners() {
	$('.car-row').unbind('click');
	$('.car-row').click(function() {
		$this = this;
		let cb = function() {

			$('#modalname').val($($this).find('.name').text());
			$('#modalbrand').val($($this).find('.brand').text());
			$('#modalmodel').val($($this).find('.model').text());
			$('#modalprice').val($($this).find('.price').text());
			$('#modaldoors').val($($this).find('.doors').text());
			$('#modalyear').val($($this).find('.year').text());
			$('#modaltype').val($($this).find('.type').text());
			$('#modalseats').val($($this).find('.seats').text());
			$('#modaltransmission').val($($this).find('.transmission').text());

		}
		showModalForm('edit', $(this).attr('id'), cb);

	});

	$('.rembtn').unbind('click');
	$('.rembtn').click(function(event) {
		event.stopPropagation();
		let row = $(this).parent().parent();
		$.ajax({

			url : 'racadmin/car/' + $(this).parent().parent().attr('id'),
			type : 'delete',
			success : function(data) {
				if (data == true)
					row.remove();
			}
		});

	});
}
