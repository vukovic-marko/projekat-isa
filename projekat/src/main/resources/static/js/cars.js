function showCars(data) {
	$('#cars').empty();
	for (let i = 0; i < data.length; ++i) {
		let html = createCar(data[i]);
		$('#cars').append(html)
	}
	$('.rsv-car').click(function(){
		let id=$(this).attr('id');
		let carReservation={};
		carReservation.startDate=$('#startdate').val();
		carReservation.endDate=$('#enddate').val();
		carReservation.startDestionation=$('#startlocation').val();
		carReservation.endDestination=$('#endlocation').val();
		$.ajax({
			success:function(data){
				if(data!=null)
					localStorage.setItem("carStart",JSON.stringify(data));
			},
			url:'/reservate/dest/'+carReservation.startDestionation,
			type:'get',

		});		
		$.ajax({
			success:function(data){
				if(data!=null)
					localStorage.setItem("carEnd",JSON.stringify(data));
			},
			url:'/reservate/dest/'+carReservation.endDestination,
			type:'get',

		});
		
		carReservation.id=id;
		
		$.ajax({
			success:function(data){
				if(data==false){
					toastr.error('Auto je vec rezervisan!')
					return;
				}
				localStorage.setItem('carReservation', JSON.stringify(carReservation));
				toastr.info('Dodato u rezervacije!')
			},
			url:'rentacar/checkiffree',
			type:'post',
			contentType: "application/json; charset=utf-8",
      
			data:JSON.stringify(carReservation)
		});
	});

}

function createCar(d) {
	$.ajax({
		async:false,
		type:'get',
		url:'/rentacar/average/'+d.id,
		success:function(data){
			if(data==0)
				d.average='-';
			else
				d.average=data;
			
		}
	})
	
	let html='<div class="col-md-4 text-center">'+
    '<div class="item card border border-dark">'+
    '<div class="card-header">'+
        '<div "class="row card-title rac-name">' +
            '<div class=" col-md-6 text-left"><h3>' + d.name + '</h3></div>'+
            '<div class=" col-md-6 text-right"><h3>Ocena: ' + d.average+ '</h3></div>'+
        '</div>'+
    '</div>'+
    '<div class=" card-body">'+
        '<div class="row">'+
            '<div class=" col-md-12 "><h3>' + d.brand+'   '+d.model + '</h3></div>'+
            '<div class=" col-md-6 text-left"><h4>Vrata: ' + d.doors+'</h4></div>'+
            '<div class=" col-md-6 text-right"><h4>Sedišta: ' + d.seats+'</h4></div>'+
            '<div class=" col-md-6 text-left"><h4>' + d.year+'. god</h4></div>'+
            '<div class=" col-md-6 text-right"><h4>Tip: '+d.type +'</h4></div>'+
        '</div>' +    
    '</div>' +
    '<div class="card-footer">'+
        '<div class="row">'+
        '<div class=" col-md-6"><h3>' + d.totalPrice + '</h3></div>'+
        '<div class=" col-md-6"><h3><button class="btn rsv-car btn-primary" id="'+d.id+'">Rezervisi</button></h3></div>'+
        '</div>'+
    '</div>'+
'</div>' +
'</div>';

return html;
	
	
}