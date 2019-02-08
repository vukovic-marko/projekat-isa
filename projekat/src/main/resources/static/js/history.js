function showHistory(){
	 $('#items').empty();
	 $('#items').load("userparts.html #history",function(){
		carHist();
		hotelHist();
	 });
	
	
	
}

function carHist(){
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
									 $('#car'+data[i].id).parent().html(rev.a+' /5');
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
									 $('#company'+data[i].id).parent().html(rev.a+' /5');
								 }
							 });
							 
						 });
						 
					 }
				 });
			 			 
			 }
		 }
	 });

}


function hotelHist(){
	 $.ajax({
		 type:'get',
		 async:false,
		 url:'reservate/myhotelhistory',
		 success:function(data){
			
			 
			 for(let i=0;i<data.length;i++){
				 let hotel;
			
				 $.ajax({
					 success: function(da){
						 hotel=da;
					 },async:false,
					 url:'review/hotelreview/gethotelbyroom/'+data[i].rooms[0].id,
					 type:'get'
				 });
				 
				 
				 
				 let html='<tr><td> '+hotel.name+' </td> ';
				 
				 
			 
					 $.ajax({
						 type:'get',
						 url:'review/hotelreview/'+data[i].id,
						 async:false,
						 success:function(d){
							 if(d.hotelReview!=null)
								 html+='<td>'+d.hotelReview+' / 5</td>';
							 else
								 html+='<td><input style="width:50px" type="number" min="1" max="5"></input>'+
								 '/ 5<button id="hotel'+data[i].id+'">Oceni</button></td>';
							// html+='<td>';
							/* for(let j=0;j<data[i].rooms.length;j++)
								 html+='<div>'+data[i].rooms[j].roomNumber+'</div>';*/
						//	html+='</td>';
							html+='<td>';
							 for(let j=0;j<data[i].rooms.length;j++){
								html+=data[i].rooms[j].roomNumber+' :   ';
								let flag=true;
								if(d.roomReviews!=null)
								 for(let k=0;k<d.roomReviews.length;k++){
									 if(d.roomReviews[k].hotelRoom.id==data[i].rooms[j].id){
										 html+='<span>'+d.roomReviews[k].review+' / 5</span>';
										 flag=false;
										 break;
									 }
									 
									}
								 
								 if(flag){
									 html+='<span><input style="width:50px" type="number" min="1" max="5"></input>'+
									 '/ 5<button class="room" id="'+data[i].rooms[j].id+'_'+data[i].id+'">Oceni</button></span>';
								 }
								 html+="<br/>";
								 
							 }
										 

							 html+='</td></tr>';
							 $('#hotels tbody').append(html);
							 
							 
							 $('.room').click(function(){
								 if(parseInt($(this).prev().val())<1 || parseInt($(this).prev().val())>5)
									 return;
								 let rev={};
								 rev.a=parseInt($(this).prev().val());
								 let ids=$(this).attr('id').split('_');
								 $this=$(this);
								 $.ajax({
									 url:'review/roomreview/'+ids[1]+'/'+ids[0],
									 type:'post',
									contentType : 'application/json',
									 data:JSON.stringify(rev),
									 success:function(){
										 $this.parent().html(rev.a+' /5');
									 }
								 });
								 
							 });
							 $('#hotel'+data[i].id).click(function(){
								 if(parseInt($(this).prev().val())<1 || parseInt($(this).prev().val())>5)
									 return;
								 let rev={};
								 rev.a=parseInt($(this).prev().val());
								 $this=$(this);
								 $.ajax({
									 url:'review/hotel/'+data[i].id,
									 type:'post',
									contentType : 'application/json',
									 data:JSON.stringify(rev),
									 success:function(){
										 $this.parent().html(rev.a+' /5');
									 }
								 });
								 
							 });
							 
						 }
					 });
				 
				 
				 
			 			 
			 }
		 }
	 });

}
