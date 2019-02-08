function showHistory(){
	 $('#items').empty();
	 $('#items').load("userparts.html #history",function(){
		 $.ajax({
			 type:'get',
			 url:'reservate/mycarhistory',
			 success:function(data){
				 
				 for(let i=0;i<data.length;i++){
					 let html='<tr><td> '+data[i].car.company.name+' </td> ';
					 html+='<td>'+data[i].car.name+'</td>';
					 $.ajax({
						 type:'get',
						 url:'review/carreview/'+data[i].id,
						 success:function(d){
							 if(d.companyReview!=null)
								 html+='<td>'+d.companyReview+' / 5</td>';
							 else
								 html+='<td><input style="width:50px" type="number" min="1" max="5"></input>'+
								 '/ 5<button id="company'+data[i].id+'">Oceni</button></td>';
							 
							 if(d.carReview!=null)
								 html+='<td>'+d.carReview+' / 5</td>';
							 else
								 html+='<td><input style="width:50px" type="number" min="1" max="5"></input>'+
								 '/ 5<button id="car'+data[i].id+'">Oceni</button></td>';
							 html+='</tr>'; 
							 $('#cars tbody').append(html);
							 $('#car'+data[i].id).click(function(){
								 if(parseInt($(this).prev().val())<1 || parseInt($(this).prev().val())>5)
									 return;
								 let rev={};
								 rev.a=parseInt($(this).prev().val());
								 
								 $.ajax({
									 url:'review/carreview/'+data[i].id,
									 type:'post',
									contentType : 'application/json',
									 data:JSON.stringify(rev),
									 success:function(){
										 $('#car'+data[i].id).parent().html(data[i].id+' /5');
									 }
								 });
								 
							 });
							 $('#company'+data[i].id).click(function(){
								 if(parseInt($(this).prev().val())<1 || parseInt($(this).prev().val())>5)
									 return;
								 let rev={};
								 rev.a=parseInt($(this).prev().val());
								 
								 $.ajax({
									 url:'review/carreview/company/'+data[i].id,
									 type:'post',
									contentType : 'application/json',
									 data:JSON.stringify(rev),
									 success:function(){
										 $('#company'+data[i].id).parent().html(data[i].id+' /5');
									 }
								 });
								 
							 });
							 
						 }
					 });
				 			 
				 }
			 }
		 });
		 
	 });
	
	
	
}