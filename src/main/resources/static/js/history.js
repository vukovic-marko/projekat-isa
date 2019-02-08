function showHistory(){
	 $('#items').empty();
	 $('#items').load("userparts.html #history",function(){
		carHist();
		hotelHist();
		// +letovi, koje nemamo
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
							/*
							 * for(let j=0;j<data[i].rooms.length;j++) html+='<div>'+data[i].rooms[j].roomNumber+'</div>';
							 */
						// html+='</td>';
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
								 let $this=$(this);
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
								 let $this=$(this);
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


function showReservations(){
	 $('#items').empty();
	 $('#items').load("userparts.html #reservations",function(){
		loadReservedHotels();
		 $.ajax({
			 type:'get',
			 url:'reservate/currentcars/',
			 success:function(d){
				 if(d==null)
					 return;
				 for(let i=0;i<d.length;i++){
					 let html='<tr>';
					 html+='<td>'+d[i].car.company.name+'</td>';
					 html+='<td>'+d[i].car.name+'</td>';
					 html+='<td>'+d[i].startDate+'</td>';
					 html+='<td>'+d[i].endDate+'</td>';
					 let date=new Date();
					 date.setHours(0,0,0,0)
					 date.setDate(date.getDate()-2);
					 let sd=d[i].startDate.split('-');
					 let date2=new Date(sd[0],sd[1],sd[2]);
					 date2.setMonth(date2.getMonth()-1);
					 if(date<date2){
						 html += '<td><button class="btn resend btn-danger close" id="'+d[i].id+'">×</button></td>';
					 }else
						 html+='<td></td>';
					 
					 html+='</tr>';
					 $('#cars tbody').append(html);
					 }
					
					 $('.resend').click(function(){
						 let $this=$(this);
						 $.ajax({
							 type:'delete',
							 url:'reservate/deletecar/'+$this.attr('id'),
							 success:function(da){
								 if(da==true){
									 $this.parent().parent().remove();
									 toastr.info('Otkazano!')
								 }else{
									 toastr.error('Ne mozete otkazati ovu rezervaciju :(')
								 }
							 }
				
						 
					 });
					 });
				 
				 
		
			 }
		 });
		  
	 
	 });


}



function loadReservedHotels(){
	
		 $.ajax({
			 type:'get',
			 url:'reservate/currenthotels/',
			 success:function(d){
				 if(d==null)
					 return;
				 for(let i=0;i<d.length;i++){
					 let html='<tr>';
					 let hotel;
						
					 $.ajax({
						 success: function(da){
							 hotel=da;
						 },async:false,
						 url:'review/hotelreview/gethotelbyroom/'+d[i].rooms[0].id,
						 type:'get'
					 });
					 
					 html+='<td>'+hotel.name+'</td>';
					 html+='<td>';
					 for(let j=0;j<d[i].rooms.length;j++){
						 html+='<div>'+d[i].rooms[j].roomNumber+'</div>';
					 }
					 html+='</td>';
					 html+='<td>'+d[i].dateOfArrival+'</td>';
					 html+='<td>'+d[i].dateOfDeparture+'</td>';
					 let date=new Date();
					 date.setHours(0,0,0,0)
					 date.setDate(date.getDate()-2);
					 let sd=d[i].dateOfArrival.split('-');
					 let date2=new Date(sd[0],sd[1],sd[2]);
					 date2.setMonth(date2.getMonth()-1);
					 if(date<date2){
						 html += '<td><button class="btn resend btn-danger close" id="ho'+d[i].id+'">×</button></td>';
					 }else
						 html+='<td></td>';
					 
					 html+='</tr>';
					 $('#hotels tbody').append(html);
					 }
					
					 $('.resend').click(function(){
						 let $this=$(this);
						 $.ajax({
							 type:'delete',
							 url:'reservate/deletehotel/'+$this.attr('id').substring(2),
							 success:function(da){
								 if(da==true){
									 $this.parent().parent().remove();
									 toastr.info('Otkazano!')
								 }else{
									 toastr.error('Ne mozete otkazati ovu rezervaciju :(')
								 }
							 }
				
						 
					 });
					 });
				 
				 
		
			 }
		 });
		  


}
