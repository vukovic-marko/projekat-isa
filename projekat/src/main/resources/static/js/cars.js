function showCars(data) {
	$('#cars').empty();
	for (let i = 0; i < data.length; ++i) {
		let html = createCar(data[i]);
		$('#cars').append(html)
	}
	

}

function createCar(d) {

	let html='<div class="col-md-4 text-center">'+
    '<div class="item card border border-dark">'+
    '<div class="card-header">'+
        '<div "class="row card-title rac-name">' +
            '<div class=" col-md-6 text-left"><h3>' + d.name + '</h3></div>'+
            '<div class=" col-md-6 text-right"><h3>' +  '</h3></div>'+
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