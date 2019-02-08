package isa.projekat.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import isa.projekat.model.User;
import isa.projekat.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private UserService userService;
	
	
	
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
	public void testActivation() {
		User u=new User();
		u.setUsername("user");
		when(userRepository.findOneByUsername("user")).thenReturn(u);
		userService.activate(u.getUsername());
		assertTrue(u.getActivated());
	}
	
	@Test
	public void testPassChange() {
		User u=new User();
		u.setUsername("user");
		u.setPassword("pass");
		when(userRepository.findOneByUsername("user")).thenReturn(u);
		when(passwordEncoder.encode("NEKALOZINKA")).thenReturn("sifra");
		userService.changePassword("NEKALOZINKA", u);
		assertTrue(u.getPassword().equals("sifra"));
	}
	
	@Test
	public void testCheckEmail() {

		when(userRepository.findOneByEmail(anyString())).thenReturn(null);
		assertTrue(userService.checkEmail("email"));
	}
	
	


	
}
