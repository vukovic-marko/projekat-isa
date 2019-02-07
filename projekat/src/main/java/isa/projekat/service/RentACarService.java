package isa.projekat.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.projekat.model.BranchOffice;
import isa.projekat.model.Car;
import isa.projekat.model.CarType;
import isa.projekat.model.Destination;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
import isa.projekat.repository.BranchesRepository;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.RentACarCompanyRepository;
import isa.projekat.repository.UserRepository;
import isa.projekat.security.TokenUtils;

@Service
public class RentACarService {
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private RentACarCompanyRepository rentACarCompanyRepository;
	@Autowired
	private DestinationRepository destinationRepository;
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private BranchesRepository branchRepository;

	public boolean edit(HttpServletRequest request, User u) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);

		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (u.getCity() == null || u.getCity().equals(""))
			return false;
		if (u.getPhone() == null || u.getPhone().equals(""))
			return false;
		if (u.getFirstName() == null || u.getFirstName().equals(""))
			return false;
		if (u.getLastName() == null || u.getLastName().equals(""))
			return false;
		user.setCity(u.getCity());
		user.setFirstName(u.getFirstName());
		user.setLastName(u.getLastName());
		user.setPhone(u.getPhone());
		userRepository.save(user);
		return true;
	}

	public RentACarCompany company(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		return rentACarCompanyRepository.findOneByAdmin(user);
	}

	public User getAdmin(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		return user;
	}

	public boolean editCompany(HttpServletRequest request, RentACarCompany c) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		if (c.getLocation().getCity() == null || c.getLocation().getCity().equals(""))
			return false;
		if (c.getLocation().getCountry() == null || c.getLocation().getCountry().equals(""))
			return false;
		if (c.getName() == null || c.getName().equals(""))
			return false;
		if (c.getAddress() == null || c.getAddress().equals(""))
			return false;
		RentACarCompany com = rentACarCompanyRepository.findOneByAdmin(user);
		if (com == null)
			com = new RentACarCompany();
		com.setName(c.getName());
		com.setAddress(c.getAddress());
		com.setDescription(c.getDescription() != null ? c.getDescription() : "");
		com.setLocation(c.getLocation());
		Destination d = destinationRepository.findByCountryAndCity(c.getLocation().getCountry(), c.getLocation().getCity());
		if (d != null)
			com.setLocation(d);
		com.setAdmin(user);
		rentACarCompanyRepository.save(com);
		return true;
	}

	public Long addBranch(HttpServletRequest request, @Valid BranchOffice bo) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany c = rentACarCompanyRepository.findOneByAdmin(user);
		if (c == null)
			return null;
		c.getBranchOffices().add(bo);
		Destination d = destinationRepository.findByCountryAndCity(bo.getLocation().getCountry(), bo.getLocation().getCity());
		if (d != null)
			bo.setLocation(d);
		rentACarCompanyRepository.save(c);
		return bo.getId();
	}

	public boolean delete(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		BranchOffice bo = branchRepository.findOne(Long.parseLong(id));
		if (bo == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany c = rentACarCompanyRepository.findOneByAdmin(user);
		c.getBranchOffices().remove(bo);
		rentACarCompanyRepository.save(c);
		return true;
	}

	public boolean editBranch(HttpServletRequest request, @Valid BranchOffice bo) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		BranchOffice bo2 = branchRepository.findOne((bo.getId()));
		if (bo2 == null)
			return false;
		bo2.setAddress(bo.getAddress());
		Destination d = destinationRepository.findByCountryAndCity(bo.getLocation().getCountry(), bo.getLocation().getCity());
		if (d == null) {
			d = new Destination();
			d.setCity(bo.getLocation().getCity());
			d.setCountry(bo.getLocation().getCountry());
		}
		bo2.setLocation(d);
		branchRepository.save(bo2);
		return true;
	}

	public Set<Car> getCars(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);

		RentACarCompany c = rentACarCompanyRepository.findOneByAdmin(user);
		if (c == null)
			return null;
		return c.getCars();
	}

	public Long addCar(HttpServletRequest request, @Valid Car c) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);

		RentACarCompany comp = rentACarCompanyRepository.findOneByAdmin(user);
		if (comp == null)
			return null;
		comp.getCars().add(c);
		rentACarCompanyRepository.save(comp);
		return c.getId();
	}

	public boolean editCar(HttpServletRequest request, Car c) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		Car c2 = carRepository.findOne(c.getId());
		if (c2 == null)
			return false;
		c.setId(c2.getId());
		carRepository.save(c);
		return true;
	}

	public boolean deleteCar(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		Car c = carRepository.findOne(Long.parseLong(id));
		if (c == null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany co = rentACarCompanyRepository.findOneByAdmin(user);
		co.getCars().remove(c);
		rentACarCompanyRepository.save(co);
		return true;
	}

	public List<RentACarCompany> getAll() {
		return rentACarCompanyRepository.findAll();
	}

	public Set<Destination> getDestinations(String id) {
		Set<Destination> ret = new HashSet<>();
		RentACarCompany c = rentACarCompanyRepository.findOne(Long.parseLong(id));
		if (c == null)
			return ret;
		ret.add(c.getLocation());
		for (BranchOffice bo : c.getBranchOffices())
			ret.add(bo.getLocation());
		return ret;
	}

	public Set<RentACarCompany> getCompanies(Map<String, String> params) {
		Set<RentACarCompany> ret = null;
		String name = params.get("name");
		if (name.equals(""))
			name = null;
		else
			name="%"+name+"%";
		String dateStart = params.get("startDate");
		String[] parts = dateStart.split("/");
		@SuppressWarnings("deprecation")
		Date d = new Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		Destination location = destinationRepository.findOne(Long.parseLong(params.get("location")));
		ret = rentACarCompanyRepository.findByNameLike(name, location, d);
		return ret;
	}

	@SuppressWarnings("deprecation")
	public List<Car> getFreeCars(Map<String, String> params) {
		// TODO Auto-generated method stub
		String dateStart = params.get("startDate");
		String[] parts = dateStart.split("/");
		Date d = new Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		RentACarCompany c = rentACarCompanyRepository.findOne(Long.parseLong(params.get("id")));
		CarType type = CarType.getValue(params.get("type"));
		String endDate = params.get("endDate");
		String[] parts2 = endDate.split("/");
		int seats = Integer.parseInt(params.get("passengers"));
		java.util.Date date1 = null;
		java.util.Date date2 = null;

		SimpleDateFormat dates = new SimpleDateFormat("dd/mm/yyyy");

		try {
			date1 = dates.parse(parts[0] + '/' + parts[1] + '/' + parts[2]);
			date2 = dates.parse(parts2[0] + '/' + parts2[1] + '/' + parts2[2]);
		} catch (Exception e) {

			e.printStackTrace();
			throw new BeanCreationException("exc", e);
		}

		long difference = Math.abs(date1.getTime() - date2.getTime());
		long differenceDates = difference / (24 * 60 * 60 * 1000);

		List<Car> ret = carRepository.findFreeCars(c, d, type, seats);
		for (Car ca : ret)
			ca.setTotalPrice(ca.getPrice() * (1 + differenceDates));
		return ret;
	}

	public List<Destination> getAllDestinations() {
		return destinationRepository.findAllByOrderByCountryAscCityAsc();
	}
	
	@Transactional(value=TxType.REQUIRED)
	public boolean checkCar(Map<String, String> params) {
		String id=params.get("id");
		if(id==null)
			return false;
		Car car=carRepository.findOne(Long.parseLong(id));
		if(car==null)
			return false;
		String dateStart = params.get("startDate");
		if(dateStart==null)
			return false;
		String[] parts = dateStart.split("/");
		java.sql.Date date = new java.sql.Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		Car car2=carRepository.checkIfReserved(car, date);
		if(car2!=null)
			return false;
		return true;
	}
	
	@Transactional(value=TxType.REQUIRED)
	public boolean reserve(HttpServletRequest request, Map<String,String> params) {
		
		return true;
	}
	
	

}
