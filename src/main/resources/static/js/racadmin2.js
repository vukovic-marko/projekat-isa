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
					html += '<div id="cars"  class="" ></div>';
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
	let flag=true;
	$.ajax({
		async:false,
		url:"racadmin/checkcar/"+value.id,
		success:function(ret){
			flag=ret;
		}
		
	});
	$.ajax({
		async:false,
		type:'get',
		url:'/rentacar/average/'+value.id,
		success:function(d){
			if(d==0)
				value.average='-';
			else
				value.average=data;
			
		},statusCode:{
			404:function(){
				value.average='-';
			}
		}
	})
	let txt="";
	if(!flag)
		txt="-uneditable";
	html = '<tr class="car-row'+txt+'" id="' + value.id + '">';
	html += '<td class="name">' + value.name + '</td>';
	html += '<td class="brand">' + value.brand + '</td>';
	html += '<td class="model">' + value.model + '</td>';
	html += '<td class="seats">' + value.seats + '</td>';
	html += '<td class="year">' + value.year + '</td>';
	html += '<td class="type">' + value.type + '</td>';
	html += '<td class="doors">' + value.doors + '</td>';
	html += '<td class="transmission">' + value.transmission + '</td>';
	html += '<td class="price">' + value.price + '</td>';
	html += '<td class="price">' + value.average + '</td>';
	if(flag)
		html += '<td><button class="btn rembtn btn-danger close">×</button></td>';
	else
		html += '<td> </td>';
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


function showReports(){
	$('#items').empty();

        
	$('#items').load('rent/parts.html #reportsForm',function(){
		$('#items').append('<div id="moneycont" style="height: 100; width: 100%;"></div>');
			$('#items').append('<div id="chartContainer" style="height: 300px; width: 100%;"></div>');
    
		$.validator.addMethod('ge', function (value, element,
                 param) {
			 
			      if ($(param).val() == "")
                     return true;
                 if (this.optional(element) && value == "")
                     return true;
                 let t=value.split('/');
                 if(t.length!=3)
                	 return false;
                 let t2=$(param).val().split('/');
                 if(t2.length!=3)
                	 return true;
                 let date1=new Date(t[2],t[1],t[0]);
                 let date2=new Date(t2[2],t2[1],t2[0]);
                 return date1>=date2;
             }, 'poruka xD');
             $.validator.addMethod('le', function (value, element,param) {
            		 if ($(param).val() == "")
                         return true;
                     if (this.optional(element) && value == "")
                         return true;
                     let t=value.split('/');
                     if(t.length!=3)
                    	 return false;
                     let t2=$(param).val().split('/');
                     if(t2.length!=3)
                    	 return true;
                     let date1=new Date(t[2],t[1],t[0]);
                     let date2=new Date(t2[2],t2[1],t2[0]);
                     return date1<=date2;
             }, 'poruka xD');
             $.validator.addMethod('rq', function (value, element,
                 param) {
                 if (value == "")
                     return false;

                 return true;

             }, 'poruka xD');
             $('#reportsForm')
                 .validate(
                     {
                         rules: {
                             enddate: {
                                 ge: '#startdate',
                                 rq: true
                             },
                             startdate: {
                                 le: '#enddate',
                                 rq: true
                             }
                             
                         },
                         messages: {
                        	 enddate: {
                                 ge: 'Datum kraja mora biti veći ili jednak datumu početka',
                                 rq: 'Datum kraja je obavezan'
                             },
                             startdate: {
                                 le: 'Datum početka mora biti manji ili jednak datumau kraja',
                                 rq: 'Datum početka je obavezan'
                             }
      
                         }
                     });
             
          
             $("#enddate").datepicker();
             $("#enddate").datepicker("option", "dateFormat",
                 "dd/mm/yy");
             $("#startdate").datepicker();
             $("#startdate").datepicker("option", "dateFormat",
                 "dd/mm/yy");
             
             $('#moneybtn').click(function () {
            	 if( $('#reportsForm').valid()){
            		 let d={};
             		
            		 d.endDate=$('#enddate').val();
            		 d.startDate=$('#startdate').val();
            		 
            		 $.ajax({
         			 	data:JSON.stringify(d),
         				url : 'racadmin/profit',
         				contentType : 'application/json',
         				type : 'post',
         				success : function(data) {
         					$('#moneycont').empty();
         					$('#moneycont').append("<h2>Prihodi za izabrani period su: "+data+" €</h2>");
         				}
            		 });
            	 }
             });
             $('#searchBtn').click(function () {
            	 if( $('#reportsForm').valid()){
            		 let d={};
            		
            		 d.endDate=$('#enddate').val();
            		 d.startDate=$('#startdate').val();
            		 d.type=$('#type').val();
            		 $.ajax({
            			 	data:JSON.stringify(d),
            				url : 'racadmin/report',
            				contentType : 'application/json',
            				type : 'post',
            				success : function(data) {
            					let dps=[];
            					let dps1={};
            					dps1.x=[];
            					dps1.y=[];
            					dps1.type='bar';
            					for (x in data) {
            						dps1.x.push(x);
            						dps1.y.push(data[x]);
            					}
            					dps.push(dps1);
            				
            					let title;
            					if(d.type==="Dnevni")
            						title='Dani';
            					else if(d.type==="Nedeljni")
            						title='Nedelje';
            					else 
            						title="Meseci"
            							
            					  var layout = { xaxis:
                                  {tickformat: ',d',
            						title:title,
                                    fixedrange: true
                                  },
                                  yaxis:
                                  {tickformat: ',d',
                                	  title:'Broj rezervacija',
                                    fixedrange: true
                                  }
            					  	};
            					  
            					
            					Plotly.newPlot('chartContainer', dps, layout, {title: 'Izveštaj'});
            				}
            			});
            		 
            	 }
            	 
             });

		
	});
	
}
