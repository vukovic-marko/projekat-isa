//za mape sam uzeo yandex
//odustao sam od njegovog geocode servise jer nece da nadje ulice u Budjanovcima!!! :@
//tako da se za geokodiranje koristi here
//a za prikaz mape yandex, jer me je mrzelo da menjam
//a i sad sam video da here KIM prikazuje isprekidanom linijom
// nista od here mapa, samo yandex <3
function getToken() {
    return localStorage.getItem('jwtToken');
}
function refreshToken() {
    $.ajax({
        url: '/user/refresh',
        type: 'post',
        success: function (data) {
            localStorage.setItem('jwtToken', data.accessToken);
        }
    });
}

$(document).ajaxSend(function (event, jqxhr, settings) {
    var token = getToken();
    if (settings.url.includes('https'))
        return;
    if (token != null)
        jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);

});

$(document).ready(

    function () {
        setInterval(refreshToken, 60000); // svaki min
        // LOGOUT
        $('#logout').click(function () {
            localStorage.setItem('jwtToken', null);
            window.location.href = '/';
        });

        $('#rent').click(function () {
            location.hash = 'rentacar';
        });

	    $("#profile").click(function (event) {

	    	location.hash = "profile";
		});

		$("#air").click(function (event) {

			location.hash = "airlineSearch";
		});

        $('#hotel').click(function() {
	    	location.hash = 'hotel';
	    });

        $('#cart').click(function() {
        	location.hash = 'cart';
        })

        $(window).on('hashchange', function () {

			if(!location.hash.includes("profile")) {

				shownTab = false;
			}


            // alert('Changed');
            if (location.hash === '#rentacar') {
                showRentACar();
            } else if (location.hash.includes("racservice")) {
                let t = location.hash.split('=');
                showService(t[1]);
	        } else if(location.hash.includes("profile")) {


				if (location.hash.includes("friends")) {

					console.log('friends');
					indexOfActiveTab = 1;
				}  else if (location.hash.includes("requestsSent")) {

					indexOfActiveTab = 2;
				} else if (location.hash.includes("requestsReceived")) {

					indexOfActiveTab = 3;
				} else if (location.hash.includes("addFriend")) {

					indexOfActiveTab = 4;
				} else {

					indexOfActiveTab = 0;
					$("#tabs").tabs("enable", indexOfActiveTab);
				}

				if(!shownTab) {

					showProfileInfo();
					shownTab = true;
				}
			} else if(location.hash === "#airlineSearch") {

				showAirlineSearch();
            }
            else if (location.hash.includes("hotel")) {
	        	showHotel();
            } else if (location.hash.includes("cart")) {
            	showCart();
            }
        });


        $(window).trigger('hashchange');

	// Provjera telefona
	$.validator.methods.phoneCheck = function(value, element) {
		return this.optional(element)
			|| /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
				.test(value);
	}

	//
});

var indexOfActiveTab = 0;
var shownTab = false;
var validatorProfileInfo = null;

function showAirlineSearch() {
	$("#items").load("/registereduser/profile")
}

function showProfileInfo() {

	$("#items").
	load("/registereduser/profile", function() {

			$("#tabs").hide();

			$("#tabs").tabs({
				active: indexOfActiveTab,
				collapsible: true,
				show: 400
			});

			$("#tabs").on("tabsbeforeload", function (event, ui) {

				//selektovani tab
				var indeks = $(this).tabs("option", "active");
				var myjqxhr = ui.jqXHR;

				if (indeks == 0) {

					location.hash = 'profile';
				} else if (indeks == 1) {

					location.hash = 'profile#friends';
				}  else if (indeks == 2) {

					location.hash = 'profile#requestsSent';
				} else if (indeks == 3) {

					location.hash = 'profile#requestsReceived';
				} else if (indeks == 4) {

					location.hash = 'profile#addFriend';
				}
			});

		$("#tabs").on("tabsload", function (event, ui) {

			var indeks = $(this).tabs("option", "active");

			if (indeks == 0) {

				$("#username").prop("disabled", true);
				$("#userInfoFieldset").prop("disabled", true);

				validatorProfileInfo = $('#profileInfoForm').validate({
					rules: {
						username: {
							required: true,
							remote: "/registereduser/profile/validity/checkusername/"
						},
						email: {
							required: true,
							email: true,
							remote: "/registereduser/profile/validity/checkemail/"
						},
						firstName: {
							required: true
						},
						lastName: {
							required: true
						},
						phone: {
							required: true,
							phoneCheck: true
						},
						city: {
							required: true
						}

					},
					messages: {
						username: {
							required: "Ime nije uneto",
							remote: "Korisničko ime se ne može mjenjati"
						},
						firstName: {
							required: "Ime nije uneto",
							letters: "Dozvoljena su samo slova srpske latinice i razmaci"
						},
						lastName: {
							required: "Prezime nije uneto",
							letters: "Dozvoljena su samo slova srpske latinice i razmaci"
						},
						email: {
							required: "Email adresa nije uneta",
							email: "Neispravan format adrese",
							remote: "Email se ne može mjenjati"
						},
						phone: {
							required: "Telefon nije unet",
							phoneCheck: "Neispravan format (123/456-78-99)"
						},
						city: {
							required: "Grad nije unet"
						}
					}
				});

				// Profile info dugmad
				$(document).on("click", "#edit", function (event) {

					$("#cityFieldset").prop("disabled", false);
					$("#userInfoFieldset").prop("disabled", false);
					$("#btnEdit").hide();
					$("#btnsEditing").show();
				});

				$(document).on("click", "#cancel", function (event) {

					$("#reset").trigger("click");

					$("#username").prop("disabled", true);
					$("#userInfoFieldset").prop("disabled", true);
					$("#btnsEditing").hide();
					$("#btnEdit").show();
				});

				$(document).on("click", "#reset", function (event) {

					$("#profileInfoForm").trigger("reset");

					validatorProfileInfo.resetForm();
				});

				$(document).on("click", "#submit", function (event) {

					if ($("#profileInfoForm").valid()) {

						var myform = $('#profileInfoForm');

						// Find disabled inputs, and remove the "disabled" attribute
						var disabled = myform.find(':input:disabled').removeAttr('disabled');

						// serialize the form
						var serialized = myform.serialize();

						// re-disabled the set of inputs that you previously enabled
						disabled.attr('disabled','disabled');


						$.ajax({
							url: "/api/registeredusers/edit",
							data: serialized,
							method: 'PATCH'
						}).done(function (event, data, jqxhr) {

							console.log("Uspjesno je poslan patch");

							$("#cancel").trigger("click");

							$("#tabs").tabs("load", 0);

						})
					} else {

						console.log("Nije poslan");
					}
				});
			}
			else if (indeks == 1) {

				$("button[id^='friendDelete_']").one("click", function(event) {

					event.preventDefault();
					$(this).hide();

					var context = $(this);

					var payload = {};
					payload.idFriend = ((context[0].id).split('_'))[1];

					payload = JSON.stringify(payload);
					$.ajax({
						url: '/api/registeredusers/friends/delete',
						method: 'DELETE',
						data: payload,
						contentType: 'application/json',
						headers: {Authorization : 'Bearer ' + getToken()}

					}).done(function () {

						var $par = context.parent(".card-body");
						$par.addClass("bg-danger");
						$par.delay(1000).hide('fade', 500, function() {

							$(this).closest('div .col-md-3').remove();
						});
					});
				});
			}
			else if (indeks == 2) {

				$("button[id^='reqSent_']").one("click", function(event) {

					event.preventDefault();
					$(this).hide();

					var context = $(this);

					var payload = {};
					payload.idReceiver = ((context[0].id).split('_'))[1];

					payload = JSON.stringify(payload);
					$.ajax({
						url: '/api/registeredusers/requests/cancelRequest',
						method: 'DELETE',
						data: payload,
						contentType: 'application/json',
						headers: {Authorization : 'Bearer ' + getToken()}

					}).done(function () {

						var $par = context.parent(".card-body");
						$par.addClass("bg-danger");
						$par.delay(1000).hide('fade', 500, function() {

							$(this).closest('div .col-md-3').remove();
						});
					});
    });
			}
			else if (indeks == 3) {

				$("button[id^='reqReceived_']").one("click", function(event) {

					event.preventDefault();
					$(this).hide();

					var context = $(this);

					var payload = {};
					payload.idRequester = ((context[0].id).split('_'))[1];

					payload = JSON.stringify(payload);
					$.ajax({
						url: '/api/registeredusers/requests/acceptRequest',
						method: 'PATCH',
						data: payload,
						contentType: 'application/json',
						headers: {Authorization : 'Bearer ' + getToken()}

					}).done(function () {

						var $par = context.parent(".card-body");
						$par.addClass("bg-success");
						$par.delay(1000).hide('fade', 500, function() {

							$(this).closest('div .col-md-3').remove();
						});
					});
				});
			}
			else if (indeks == 4) {

				$("#loadingAlert").hide();
				$("#loadedAlert").hide();
				$("#friendAddedAlert").hide();
				$("#emptyAlert").show();

				$("#submitFriends").off('click');

				$("#submitFriends").on("click", function (event) {

					event.preventDefault();

					//
					var friendQuery = $("#findFriendsForm").serialize();

					$("#emptyAlert").hide("fade");
					$("#loadedAlert").hide("fade");
					$("#loadingAlert").show('fade', 500);
					// Dodati handler za klik za svaku od dugmadi

					$("#resultsUsers").load("/registereduser/profile/friends/add/results", friendQuery, function () {

						if($("#tmpUserList").children().length == 0) {

							$("#emptyAlert").show("fade", 1000);
						} else {

							$("#loadedAlert").show("fade", 1000);
						}

						$("#loadingAlert").hide("fade");

						$("button[id^='add_']").one("click", function (event) {

							event.preventDefault();

							var context = $(this);
							context.hide();

							var $target = event.target;
							var idButton = ($target.id).split("_");

							var vid =  parseInt(idButton[1]);
							var payload = {id:  vid};


							payload = JSON.stringify(payload);
							// Salje se zahtjev useru sa id
							$.ajax({

								url: "/api/registeredusers/friends/add",
								method: 'POST',
								data: payload,
								contentType: 'application/json',
								success: function(data, textStatus, jqxhr) {
									// console.log("=========================\nSUCCESS ADD FRIEND");
									// console.log("=========================");

									$("#loadedAlert")
										.hide("fade", 50).delay(2100).show("fade", 100);

									$("#friendAddedAlert")
										.show("fade", 2000)
										.hide("fade", 100);

								},
								error: function(data, textStatus, jqxhr) {
									// console.log("=========================\nFAIL ADD FRIEND");
									// console.log("=========================");
								}
							}).done(function(event) {

								// Iskljucuje se dugme
								context.off("click");
								context.hide();

								var $b = context.parent(".card-body");
								$b.addClass("bg-success");
							});
						});

					});
				});
			}
		});

		// Refresh taba
		$(document).on("click", "#buttonRefresh", function (event) {

			console.log('button clicked');

			$("#tabs").tabs("disable");

			//
			var loadedTabs = 0;
			$("#tabs").on("tabsload", function (event) {

				loadedTabs++;
				if (loadedTabs == 4) {

					$(this).tabs("enable");
					//$(this).off("tabsload");
				}
			});

			for (var i = 0; i < 4; i++) {

				$("#tabs").tabs("load", i);
			}
		});

		$("#tabs").tabs("load", indexOfActiveTab).show("fade", {}, 400, function() {});
	});
}

function showRentACar() {

    $
        .ajax({
            url: 'rentacar/',
            type: 'get',
            success: function (data) {
                $('#items').empty();
                $('#items')
                    .append(
                        '<div class="row" id="search"></div><hr><div class="row" id="row"></div>');
                $('#search')
                    .load(
                        'rent/parts.html #searchRACdiv > ',
                        function () {
                            $.ajax({
                                url: 'rentacar/destinations',
                                type: 'get',
                                success: function (data) {
                                    for (let i = 0; i < data.length; ++i) {
                                        let html = '<option value="' + data[i].id
                                            + '">';
                                        html += data[i].city + ', '
                                            + data[i].country;
                                        html += '</option>';
                                        $('#location').append(html);
                                    }

                                }

                            });

                        	let t = new Date().toISOString().split("T")[0];
                            t = t.split('-');
                            t = t[2] + '/' + t[1] + '/' + t[0];
                        	$("#enddate").datepicker();
                            $("#enddate").datepicker("option", "dateFormat",
                                "dd/mm/yy");
                            $("#enddate").datepicker("option", "minDate", t);
                            $("#startdate").datepicker();
                            $("#startdate").datepicker("option", "dateFormat",
                                "dd/mm/yy");
                            $("#startdate").datepicker("option", "minDate", t);



                            $.validator.addMethod('rq', function (
                                value, element, param) {
                                if (value == "")
                                    return false;

                                return true;

                            }, 'poruka xD');

                            $('#searchForm')
                                .validate(
                                    {
                                        rules: {
                                            startdate: {
                                                rq: true
                                            },
                                        },
                                        messages: {
                                            startdate: "Datum preuzimanja nije unet",
                                        }
                                    });

                            $('#searchBtn').click(function () {
                                if ($('#searchForm').valid()) {
                                    let d = {};
                                    d.startDate = $("#startdate").val();
                                    d.endDate = $("#enddate").val();
                                    d.name = $("#name").val();
                                    d.location = $("#location").val();

                                    $.ajax({

                                        url: 'rentacar/findcompanies',
                                        type: 'post',
                                        contentType: 'application/json',
                                        data: JSON.stringify(d),
                                        success: function (data) {
                                        	  $('#row').empty();
                                        	showRentACarCompanies(data);
                                        }

                                    });
                                }

                            });


                        });

                showRentACarCompanies(data);

            }
        });

}

function showRentACarCompanies(data){
	for (let i = 0; i < data.length; i++) {
        let c = createRAC(data[i]);
        $('#row').append(c.html);
        showOnMap(c, data[i]);
    }
	 $('.shcars').click(function () {
         let id = $(this).attr('id');
         location.hash = 'racservice=' + id;
     });
}

function showOnMap(c, data) {
    ymaps
        .ready(function () {

            var map = new ymaps.Map(c.id, {
                center: [c.pos.Latitude, c.pos.Longitude],
                zoom: 10
            });
            let myPlacemark = new ymaps.Placemark([c.pos.Latitude,
            c.pos.Longitude], {
                    hintContent: c.name,
                    balloonContent: c.adr
                });
            map.geoObjects.add(myPlacemark);
            for (let j = 0; j < data.branchOffices.length; j++) {
                let bo = data.branchOffices[j];
                let adr = bo.address + ' ' + bo.location.city + ' '
                    + bo.location.country;
                $
                    .ajax({
                        url: 'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='
                            + adr.replace(/ /g, '+'),
                        // url:
                        // 'https://geocode-maps.yandex.ru/1.x/?apikey=68f27d0d-4416-406a-8570-ec7b43f271a6&format=json&geocode='+adr.replace(/
                        // /g,'+')+'&lang=en-US',
                        type: 'get',
                        async: false,
                        success: function (mi) {
                            let pos = mi.Response.View[0].Result[0].Location.DisplayPosition;
                            let pm = new ymaps.Placemark([
                                pos.Latitude, pos.Longitude], {
                                    hintContent: adr,
                                    balloonContent: adr
                                });
                            map.geoObjects.add(pm);
                        }

                    });
            }
        });
}

function createRAC(data) {
    let adr = data.address + ' ' + data.location.city + ' '
        + data.location.country;
    let html = '<div class="col-md-4 text-center">';
    html += '<div class="rac-company card border border-dark">';
    html += '<div class="card-header"><h3 "class=" card-title rac-name">'
        + data.name + '</h3>' + '</div>';
    html += '<div class="card-body">';
    html += '<h3 "class="rac-address">' + adr + '</h3>';
    html += '<textarea readonly style="width: -webkit-fill-available;" class="rac-description"> '
        + data.description + '</textarea>';
    html += '</div>';

    html += '<div  class="card-footer"><div class="map" id="map' + data.id
        + '" style="height: 250px"></div>';
    html += '<button class="btn shcars btn-primary" id="' + data.id
        + '">Automobili</button>'
    html += '	</div></div></div>';

    let ret = {};
    ret.html = html;
    ret.id = 'map' + data.id;
    ret.name = data.name;
    ret.adr = adr;
    $
        .ajax({
            url: 'https://geocoder.api.here.com/6.2/geocode.json?app_id=aJx1PxrXFwpMDT0M30rJ&app_code=am23BxvdgXkXf2c15NUZgw&searchtext='
                + adr.replace(/ /g, '+'),
            type: 'get',
            async: false,
            success: function (mi) {
                ret.pos = mi.Response.View[0].Result[0].Location.DisplayPosition;
            }

        });
    return ret;
}

function showService(id) {
    let d = {};
    $('#items').empty();
    $('#items').append(
            '<div id="lol">'
            + '<a data-toggle="collapse" href="#asd"><h2>Pretraga</h2></a>'
            + '<div class="row aria-expanded= collapse show" id="asd"></div>'
            +

            '</div>	<div id="cars" class="row row-eq-height"></div>');

    $('#asd').load(
            "rent/parts.html #search",
            function () {

                search
                $.validator.addMethod('ge', function (value, element,
                    param) {
                    if ($(param).val() == "")
                        return true;
                    if (this.optional(element) && value == "")
                        return true;
                    return parseFloat(value) >= parseFloat($(param)
                        .val());
                }, 'poruka xD');
                $.validator.addMethod('le', function (value, element,
                    param) {
                    if ($(param).val() == "")
                        return true;
                    if (this.optional(element) && value == "")
                        return true;
                    return parseFloat(value) <= parseFloat($(param)
                        .val());
                }, 'poruka xD');
                $.validator.addMethod('rq', function (value, element,
                    param) {
                    if (value == "")
                        return false;

                    return true;

                }, 'poruka xD');
                $('#searchForm')
                    .validate(
                        {
                            rules: {
                                enddate: {
                                    rq: true
                                },
                                startdate: {
                                    rq: true
                                },
                                passengers: "required",
                                maxprice: {
                                    ge: "#minprice"
                                },
                                minprice: {
                                    le: "#maxprice"
                                }
                            },
                            messages: {
                                enddate: "Datum vraćanja nije unet",
                                startdate: "Datum preuzimanja nije unet",
                                maxprice: {
                                    ge: "Maksimalna cena mora biti veca ili jednaka minimalnoj"
                                },
                                minprice: {
                                    le: " Minimalna cena mora biti manja ili jednaka maksimalnoj"
                                },
                                passengers: "Unesite broj putnika"
                            }
                        });

                let t = new Date().toISOString().split("T")[0];
                t = t.split('-');
                t = t[2] + '/' + t[1] + '/' + t[0];
                $("#enddate").datepicker();
                $("#enddate").datepicker("option", "dateFormat",
                    "dd/mm/yy");
                $("#enddate").datepicker("option", "minDate", t);
                $("#startdate").datepicker();
                $("#startdate").datepicker("option", "dateFormat",
                    "dd/mm/yy");
                $("#startdate").datepicker("option", "minDate", t);

                $('#searchBtn').click(function () {
                    if ($('#searchForm').valid()) {
                        let d = {};
                        d = {};
                        d.id = id;
                        d.startDate = $("#startdate").val();
                        d.endDate = $("#enddate").val();
                        d.type = $("#cartype").val();
                        d.minprice = $("#minprice").val();
                        d.maxprice = $("#maxprice").val();
                        d.passengers = $("#passengers").val();

                        $.ajax({

                            url: 'rentacar/freecars',
                            type: 'post',
                            contentType: 'application/json',
                            data: JSON.stringify(d),
                            success: function (data) {
                                showCars(data);
							}
                        });
                    }

                });

                $.ajax({
                    url: 'rentacar/' + id + '/destinations',
                    type: 'get',
                    success: function (data) {
                        for (let i = 0; i < data.length; ++i) {
                            let html = '<option value="' + data[i].id
                                + '">';
                            html += data[i].city + ', '
                                + data[i].country;
                            html += '</option>';
                            $('.dest').append(html);
                        }

                    }
				});
            });

	}

	/*
	 * $.ajax({ url:'/rentacar/cars/'+id, type:'get', data:d, sucess:function(){
	 *
	 *  }
	 *
	 *
	 * });
	 */

