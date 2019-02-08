package isa.projekat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isa.projekat.model.Car;
import isa.projekat.model.CarReservation;
import isa.projekat.model.Destination;
import isa.projekat.model.Hotel;
import isa.projekat.model.HotelAdditionalService;
import isa.projekat.model.HotelReservation;
import isa.projekat.model.HotelRoom;
import isa.projekat.model.HotelRoomPrice;
import isa.projekat.model.Reservation;
import isa.projekat.model.User;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.HotelAdditionalServiceRepository;
import isa.projekat.repository.HotelRepository;
import isa.projekat.repository.HotelReservationRepository;
import isa.projekat.repository.HotelRoomPriceRepository;
import isa.projekat.repository.HotelRoomRepository;
import isa.projekat.repository.ReservationRepository;
import isa.projekat.security.TokenUtils;

@Service
public class ReservationService {

	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private RentACarService rentACarService;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private DestinationRepository destinationRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HotelRoomRepository hotelRoomRepository;
	@Autowired
	private HotelAdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private HotelRoomPriceRepository roomPriceRepository;
	@Autowired
	private HotelReservationRepository hotelReservationRepository;
	@Autowired
	private CarReservationRepository carReservationRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String reserve(HttpServletRequest request, Reservation reservation) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return "Unautorized";
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return "Unautorized";
		reservation.setUser(user);
		String ret = "OK";
		// dodati proveru i za let, ako bude uradjen
		if (reservation.getCarReservation() == null && reservation.getHotelReservation() == null)
			ret = "BAD REQUEST";
		if (reservation.getCarReservation() != null) {
			reservation.getCarReservation().setUser(user);
			Map<String, Object> params = new HashMap<>();
			params.put("id", reservation.getCarReservation().getCar().getId().toString());
			String date = null;
			Date d = reservation.getCarReservation().getStartDate();
			date = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
			params.put("startDate", date);

			long difference = Math.abs(reservation.getCarReservation().getEndDate().getTime() - reservation.getCarReservation().getStartDate().getTime());
			long differenceDates = difference / (24 * 60 * 60 * 1000);
			Car c = carRepository.getOne(reservation.getCarReservation().getCar().getId());
			reservation.getCarReservation().setCar(c);
			reservation.getCarReservation().setPrice(c.getPrice() * (1 + differenceDates));

			if (!rentACarService.checkCar(params)) {
				ret = "car";
			}
			// TODO
			// URADITI SVE STO TREBA ZA HOTEL I LET
			// AKO JE VEC REZERVISANO, kontatenirati na ret "hotel"/"flight"
			// PODESITI I ONE PARAMETRE KOJI TREBA DA SE PODESE cena, user i slicno
			// JA SAM URADIO ZA CAR RES.
			// metode koje vam u servisu proveravaju da li je vec rezervisano
			// oznacite sa @Transactional(value=TxType.REQUIRED) (prikljucuje se postojecoj
			// ako je ima?)
			// ili sa onim da se mora da se prikljuci postojecoj (ako je ne pozivate sa
			// fronta ili tako nsto)
			// ako u ovoj klasi pravite nove metode koje pozivate iz ove,
			// stavite da MORA da postoji transakcija kojoj se ta prikljucuje
		}

		if (reservation.getHotelReservation() != null) {
			System.out.println("Rez nije null <-");
			HotelReservation hRes = reservation.getHotelReservation();
			System.out.println(reservation.getHotelReservation().getRooms().size());
			List<String> rooms = new ArrayList<String>();

			for (Iterator<HotelRoom> it = hRes.getRooms().iterator(); it.hasNext();) {
				HotelRoom f = it.next();
				rooms.add(f.getRoomNumber());
			}

			System.out.println(rooms.size());

			Date dateOfArrival = hRes.getDateOfArrival();
			Date dateOfDeparture = hRes.getDateOfDeparture();

			Boolean error = false;

			for (String roomNumber : rooms) {
				if (hotelRoomRepository.findRooms(dateOfArrival, dateOfDeparture, roomNumber, hRes.getId()).size() != 0) {
					error = true;
					ret += "hotel";
				}
			}
			if (!error) {
				Set<HotelAdditionalService> additionalServices = new HashSet<HotelAdditionalService>();
				Double price = 0.0;

				for (Iterator<HotelAdditionalService> it = hRes.getServices().iterator(); it.hasNext();) {
					HotelAdditionalService f = it.next();
					HotelAdditionalService aS = additionalServiceRepository.findOne(f.getId());
					if (aS == null)
						ret += "hotel";
					else {
						additionalServices.add(aS);
						price += aS.getPrice();
					}
				}

				Set<HotelRoom> r = new HashSet<HotelRoom>();
				Hotel h = hotelRepository.findOne(hRes.getId());
				HotelReservation res = new HotelReservation();
				res.setDateOfArrival(dateOfArrival);
				res.setDateOfDeparture(dateOfDeparture);
				res.setNumberOfGuests(-1);

				res.setUser(user);
				res.setServices(additionalServices);
				for (String roomNumber : rooms) {
					HotelRoom rr = hotelRoomRepository.findByRoomNumberAndHotel(roomNumber, h);
					HotelRoomPrice rp = roomPriceRepository.findPriceForRoomOnDate(dateOfArrival, rr);

					long diff = dateOfDeparture.getTime() - dateOfArrival.getTime();

					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

					price += diffDays * rp.getPrice();

					rr.getRoomReservations().add(res);
					r.add(rr);
				}

				res.setPrice(price);
				res.setRooms(r);
				reservation.setHotelReservation(res);
				reservation.getHotelReservation().setId(null);
				hotelReservationRepository.save(reservation.getHotelReservation());

				hotelRoomRepository.save(r);
			}
		}

		if (ret.equals("OK")) {
			reservationRepository.save(reservation);

		}
		return ret;

		// return null;
	}

	public Destination getDestination(String id) {
		return destinationRepository.findOne(Long.parseLong(id));
	}

	public Set<CarReservation> getMyCarHistoty(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return null;
		Set<CarReservation> ret = new HashSet<>();
		java.util.Date date = new java.util.Date();
		java.sql.Date d = new java.sql.Date(date.getTime());
		ret = carReservationRepository.findUserHistory(user, d);
		return ret;
	}

	public Set<HotelReservation> getMyHotelHistoty(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return null;
		Set<HotelReservation> ret = new HashSet<>();
		java.util.Date date = new java.util.Date();
		java.sql.Date d = new java.sql.Date(date.getTime());
		ret = hotelReservationRepository.findUserHistory(user, d);
		return ret;
	}

	public Set<CarReservation> currentReservations(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return null;
		Date d = new Date();
		Date date = new java.sql.Date(d.getTime());
		return carReservationRepository.findCurrent(user, date);
	}

	public boolean cancelCar(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return false;
		CarReservation c = carReservationRepository.findOne(Long.parseLong(id));
		if (c.getUser().getId() != user.getId())
			return false;
		Date d = new Date();
		d = new Date(d.getYear(), d.getMonth(), d.getDate());

		if (c.getStartDate().getTime() - d.getTime() < 2 * 24 * 60 * 60 * 1000) {
			return false;
		}
		Reservation res = reservationRepository.findByCarReservation(c);
		res.setCarReservation(null);
		reservationRepository.save(res);
		carReservationRepository.delete(c);
		return true;
	}

	public Set<HotelReservation> currentHotels(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return null;
		Date d = new Date();
		Date date = new java.sql.Date(d.getTime());
		return hotelReservationRepository.findCurrent(user, date);
	}

	public boolean cancelHotel(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (user == null)
			return false;
		HotelReservation c = hotelReservationRepository.findOne(Long.parseLong(id));
		if (c.getUser().getId() != user.getId())
			return false;
		Date d = new Date();
		d = new Date(d.getYear(), d.getMonth(), d.getDate());

		if (c.getDateOfArrival().getTime() - d.getTime() < 2 * 24 * 60 * 60 * 1000) {
			return false;
		}
		Reservation res = reservationRepository.findByHotelReservation(c);
		res.setHotelReservation(null);
		reservationRepository.save(res);
		hotelReservationRepository.delete(c);
		return true;
	}
}
