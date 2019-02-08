package isa.projekat.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import isa.projekat.model.BranchOffice;
import isa.projekat.model.Car;
import isa.projekat.model.Destination;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
import isa.projekat.repository.CarRepository;
import isa.projekat.repository.DestinationRepository;
import isa.projekat.repository.RentACarCompanyRepository;
import isa.projekat.repository.UserRepository;
import isa.projekat.security.TokenUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentACarAdminServiceTest {

	//spy da bih izmenio ponasaljem metode koja izvlaci korisnika iz tokena
	@InjectMocks
	@Spy
	private RentACarAdminService rentACarAdminService;
	
	@Mock
	private CarRepository carRepository;
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private TokenUtils tokenUtils;
	
	@Mock
	private RentACarCompanyRepository rentACarCompanyRepository;
	@Mock
	private DestinationRepository destinationRepository;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void nullParameters() {
		User u=new User();
		Mockito.doReturn(u).when(rentACarAdminService).getUserFromRequestToken(any());
	
		when(userRepository.save(any(User.class))).thenReturn(u);
		assertFalse(rentACarAdminService.edit(null, u));
		
	}
	
	@Test
	public void getCarsTest() {
		when(rentACarAdminService.getUserFromRequestToken(any())).thenReturn(null);
		assertNull(rentACarAdminService.getCars(null));
	}
	

	@Test
	public void checkCarTest() {
		User u=new User();
		Mockito.doReturn(u).when(rentACarAdminService).getUserFromRequestToken(any());
		when(tokenUtils.getToken(any())).thenReturn("asd");
		Car c=new Car();
		when(carRepository.findOne(anyLong())).thenReturn(c);
		when(carRepository.checkIfReserved(any(), any())).thenReturn(null);
		assertTrue(rentACarAdminService.checkCar(null, "44"));
	}
	
	
	@Test
	public void addBranchTest() {
		User u=new User();
		Mockito.doReturn(u).when(rentACarAdminService).getUserFromRequestToken(any());
		RentACarCompany c=new RentACarCompany();
		c.setBranchOffices(new HashSet<>());
		when(rentACarCompanyRepository.findOneByAdmin(any())).thenReturn(c);
	
		BranchOffice vo= new BranchOffice();	
		Destination d=new Destination();
		d.setCity("");
		d.setCountry("");
		vo.setLocation(d);
		when(destinationRepository.findByCountryAndCity(any(),any())).thenReturn(d);
		rentACarAdminService.addBranch(null,vo);
		assertTrue(c.getBranchOffices().size()!=0);
	}
	

	
	
	
}
