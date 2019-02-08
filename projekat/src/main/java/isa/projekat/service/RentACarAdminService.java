package isa.projekat.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.projekat.model.BranchOffice;
import isa.projekat.model.Car;
import isa.projekat.model.CarReservation;
import isa.projekat.model.Destination;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
import isa.projekat.repository.BranchesRepository;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.RentACarCompanyRepository;
import isa.projekat.repository.UserRepository;
import isa.projekat.security.TokenUtils;


@Service
public class RentACarAdminService {
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
	private CarReservationRepository carReservationRepository;
	
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
		if (c.getLocation().getCity()== null || c.getLocation().getCity().equals(""))
			return false;
		if (c.getLocation().getCountry()== null || c.getLocation().getCountry().equals(""))
			return false;
		if (c.getName() == null || c.getName().equals(""))
			return false;
		if (c.getAddress() == null || c.getAddress().equals(""))
			return false;
		RentACarCompany com=rentACarCompanyRepository.findOneByAdmin(user);
		if(com==null)
			com=new RentACarCompany();
		com.setName(c.getName());
		com.setAddress(c.getAddress());
		com.setDescription(c.getDescription()!=null?c.getDescription():"");
		com.setLocation(c.getLocation());
		Destination d=destinationRepository.findByCountryAndCity(c.getLocation().getCountry(), c.getLocation().getCity());
		if(d!=null)
			com.setLocation(d);
		com.setAdmin(user);
		rentACarCompanyRepository.save(com);
		return true;
	}

	public Long addBranch(HttpServletRequest request,@Valid BranchOffice bo) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany c=rentACarCompanyRepository.findOneByAdmin(user);
		if(c==null)
			return null;
		c.getBranchOffices().add(bo);
		Destination d=destinationRepository.findByCountryAndCity(bo.getLocation().getCountry(), bo.getLocation().getCity());
		if(d!=null)
			bo.setLocation(d);
		rentACarCompanyRepository.save(c);
		return bo.getId();
	}

	public boolean delete(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		BranchOffice bo=branchRepository.findOne(Long.parseLong(id));
		if(bo==null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany c=rentACarCompanyRepository.findOneByAdmin(user);
		c.getBranchOffices().remove(bo);
		rentACarCompanyRepository.save(c);
		return true;
	}

	public boolean editBranch(HttpServletRequest request, @Valid BranchOffice bo) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		BranchOffice bo2=branchRepository.findOne((bo.getId()));
		if(bo2==null)
			return false;
		bo2.setAddress(bo.getAddress());
		Destination d=destinationRepository.findByCountryAndCity(bo.getLocation().getCountry(),	bo.getLocation().getCity());
		if(d==null) {
			d=new Destination();
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
		
		RentACarCompany c=rentACarCompanyRepository.findOneByAdmin(user);
		if(c==null)
			return null;
		return c.getCars();
	}

	public Long addCar(HttpServletRequest request,@Valid Car c) {
		
		User user=getUserFromRequestToken(request);
		if(user==null)
			return null;
		RentACarCompany comp=rentACarCompanyRepository.findOneByAdmin(user);
		if(comp==null)
			return null;
		comp.getCars().add(c);
		c.setCompany(comp);
		rentACarCompanyRepository.save(comp);
		return c.getId();
	}
	
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public boolean editCar(HttpServletRequest request, Car c) {
		if(!checkCar(request, c.getId().toString()))
			return false;
	String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		Car c2=carRepository.findOne(c.getId());
		if(c2==null)
			return false;
		c.setId(c2.getId());
		carRepository.save(c);
		return true;
	}
	@Transactional(value=TxType.REQUIRES_NEW)
	public boolean deleteCar(HttpServletRequest request, String id) {
		if(!checkCar(request, id))
			return false;
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		Car c=carRepository.findOne(Long.parseLong(id));
		if(c==null)
			return false;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		RentACarCompany co=rentACarCompanyRepository.findOneByAdmin(user);
		co.getCars().remove(c);
		rentACarCompanyRepository.save(co);
		return true;
	}
	
	@Transactional(value=TxType.REQUIRED)
	public boolean checkCar(HttpServletRequest request, String id) {
		String token = tokenUtils.getToken(request);
		if (token == null)
			return false;
		Car car=carRepository.findOne(Long.parseLong(id));
		if(car==null)
			return false;
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		Car car2=carRepository.checkIfReserved(car, d);
		if(car2!=null)
			return false;
		return true;
	}

	
	public Map<String,String> getReport(HttpServletRequest request, Map<String, String> params) {
		User u=getUserFromRequestToken(request);
		if(u==null)
			return null;
		Map<String,String> ret=new LinkedHashMap<>();
		String dateStart = params.get("startDate");
		if(dateStart==null)
			return null;
		String[] parts = dateStart.split("/");
		java.sql.Date dstart = new java.sql.Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		dateStart = params.get("endDate");
		if(dateStart==null)
			return null;
		parts = dateStart.split("/");
		java.sql.Date dend = new java.sql.Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		if(dend.getTime()<dstart.getTime())
			return null;
		String temp=params.get("type");
		List<CarReservation> crs=carReservationRepository.getReport(dstart, dend);
		if(temp.equals("Dnevni"))
			ret= getDailyReport(dstart,dend,crs,ret,24*60*60*1000);
		else if(temp.equals("Nedeljni"))
			ret= getDailyReport(dstart,dend,crs,ret,7*24*60*60*1000);
		else
			ret=getMonthly(dstart,dend,crs,ret);
		return ret;
	}
	
	private Map<String, String> getMonthly(Date dstart, Date dend, List<CarReservation> crs, Map<String, String> ret) {
		Calendar cal=Calendar.getInstance();
		Integer j=1;
		
		while(true) {
			Integer sum=0;
			cal.set(dstart.getYear()+1900, dstart.getMonth(), 1);
			cal.add(Calendar.MONTH,1);
			long end=cal.getTimeInMillis();
			boolean flag=false;
			if(end>dend.getTime())
				{end=dend.getTime();flag=true;}
			for(CarReservation cr:crs) {
				if(cr.getStartDate().getTime()<end && cr.getStartDate().getTime()>=dstart.getTime())
					sum++;
			}
			ret.put(j.toString(), sum.toString());
			dstart.setTime(end);
			j++;
			if(flag)
				break;
		}
		return ret;
	}

	private Map<String, String> getDailyReport(Date dstart, Date dend, List<CarReservation> crs, Map<String, String> ret,long length) {
		
		Integer j=1;
		while(true) {
			Integer sum=0;
			long end=dstart.getTime()+length;
			boolean flag=false;
			if(end>dend.getTime())
				{end=dend.getTime();flag=true;}
			for(CarReservation cr:crs) {
				if(cr.getStartDate().getTime()<end && cr.getStartDate().getTime()>=dstart.getTime())
					sum++;

			}
			ret.put(j.toString(), sum.toString());
			dstart.setTime(dstart.getTime()+length);
			j++;
			if(flag)
				break;
			
		}
		return ret;
	}

	private User getUserFromRequestToken(HttpServletRequest request) {
		//TODO  izbaci ovaj kod iz ostalih metoda, premesti ovdee
		String token = tokenUtils.getToken(request);
		if (token == null)
			return null;
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
		return user;
	}

	public Float getProfit(HttpServletRequest request, Map<String, String> params) {
		User u=getUserFromRequestToken(request);
		if(u==null)
			return null;
		float ret=(float) 0;
		String dateStart = params.get("startDate");
		if(dateStart==null)
			return null;
		String[] parts = dateStart.split("/");
		java.sql.Date dstart = new java.sql.Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		dateStart = params.get("endDate");
		if(dateStart==null)
			return null;
		parts = dateStart.split("/");
		java.sql.Date dend = new java.sql.Date(Integer.parseInt(parts[2]) - 1900, Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));
		if(dend.getTime()<dstart.getTime())
			return null;
		RentACarCompany company=rentACarCompanyRepository.findOneByAdmin(u);
		if(company==null)
			return null;
		List<CarReservation> crs=carReservationRepository.getReservationsByCompany(company,dstart,dend);
		for(CarReservation cr:crs) {
		
			ret+=cr.getPrice();
		}
		return ret;
	}

}
