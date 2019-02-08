package isa.projekat.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import isa.projekat.model.CarReview;
import isa.projekat.repository.CarReservationRepository;
import isa.projekat.repository.CarReviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarReviewServiceTest {

	
	@Mock
	private CarReviewRepository carReviewRepository;
	@Mock
	private CarReservationRepository carReservationRepository;

	@InjectMocks
	private CarReviewService CarReviewService;
	
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
	public void setCarReviewTest() {
		when(carReviewRepository.findByReservationId((long)45)).thenReturn(null);
		when(carReviewRepository.save(any(CarReview.class))).thenReturn(new CarReview());
		assertTrue(CarReviewService.setCarReview("45",1));
	}
	
	@Test
	public void getCarReviewTest() {
		CarReview c=new CarReview();
		when(carReviewRepository.findByReservationId(anyLong())).thenReturn(c);
		assertNotNull(CarReviewService.getReview("12"));
	}
	
	@Test
	public void setCarCompanyTest() {
		when(carReviewRepository.findByReservationId(anyLong())).thenReturn(null);
		when(carReservationRepository.findOne(anyLong())).thenReturn(null);
		when(carReviewRepository.save(any(CarReview.class))).thenReturn(new CarReview());
		assertTrue(CarReviewService.setCarCompanyReview("4",4));
	}
	
}