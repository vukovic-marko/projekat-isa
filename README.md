-------
TIM 54:
-------

Predmet: Internet softverske arhitekture
Godina: 2018/2019

Student A: Milorad Savčić RA 112/2015
Student B: Marko Vuković RA 200/2015
Student C: Stevan Rašković RA 113/2015

Back-end: Spring Boot
Front-end: JQuery

Za pokretanje aplikacije u lokalnom okruženju bilo bi potrebno izmeniti
sadržaj src/main/resources/application.properties fajla:

	spring.datasource.url = jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7278120?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true
	spring.datasource.username =sql7278120
	spring.datasource.password =wsR7Tl7ZZ6
	
	// ...
	
	hostname=projekat-isa.herokuapp.com 	
	
u sledeće:

	spring.datasource.url = jdbc:mysql://localhost:3306/projekat?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true
	spring.datasource.username =root
	spring.datasource.password =root
	
	// ...
	
	hostname=localhost:8090
	
Ovakav način pokretanja zahteva u lokalnom okruženju instaliranu i kreiranu mysql 
bazu podataka pod nazivom projekat, na portu 3306. Ove podatke moguće je menjati
i prilagođavati izmenom prethodno navedenih linija.


Pokretanje aplikacije u Eclipse razvojnom okruženju vrši se kloniranjem
github repozitorijuma i odabiranjem opcije Import Maven project into Eclipse.
Nakon završetka operacije, potrebno je nad projektom odabrati opciju 
Run As -> Spring Boot App. Ukoliko su izvršene prethodno navedene promene, 
aplikacija će uspešno biti pokrenuta na portu 8090, i može joj se pristupiti
posećivanjem linka http://localhost:8090
