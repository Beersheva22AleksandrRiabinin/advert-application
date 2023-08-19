package telran.advert;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.advert.exceptions.NotFoundException;
import telran.advert.model.Advert;
import telran.advert.service.AdvertService;
import telran.advert.service.AdvertServiceImpl;

@SpringBootTest
class ServiceTest {
	
//	@Autowired //otherwise test works with initial state of real maps
	AdvertServiceImpl service;
	
	Advert advert1;
	Advert advert2;
	@BeforeEach
	void setUp() {
		
		service = new AdvertServiceImpl(); //
		
		advert1 = new Advert();
		advert1.id = 0;
		advert1.category = "vehicle";
		advert1.price = 123;
		advert1.name = "name1";
		advert1.details = "details";
		
		advert2 = new Advert();
		advert2.id = 0;
		advert2.category = "vehicle";
		advert2.price = 123;
		advert2.name = "name2";
		advert2.details = "details";
	}
	@Test
	void addTest() {
		String res = service.addAdvert(advert1);
		assertTrue(res.contains("has been added"));
	}
	@Test
	void updateTest() {
		String str = service.addAdvert(advert1);
		int id = getId(str);
		advert1.id = id;
		advert1.name = "qq";
		String expected = String.format("advert with id %d has been updated", id);
		assertEquals(expected, service.updateAdvert(advert1));
		
		advert1.id = 123;
		String exp = (String.format("advert with id %d doesn't exist", id));
		assertThrowsExactly(NotFoundException.class, () -> service.updateAdvert(advert1), exp);
	}
	private int getId(String str) {
		int id = 0;
		for(String string: str.split(" ")) {
			try {
				id = Integer.parseInt(string);
			} catch (NumberFormatException e) {
				//
			}
		}
		return id;
	}
	@Test
	void deleteTest() {
		String str = service.addAdvert(advert1);
		int id = getId(str);
		String expected = String.format("advert with id %d has been deleted", id);
		assertEquals(expected, service.deleteAdvert(id));
		
		String exp = (String.format("advert with id %d doesn't exist", id));
		assertThrowsExactly(NotFoundException.class, () -> service.deleteAdvert(id), exp);
	}
	@Test
	void getAllTest() {
		service.addAdvert(advert1);
		service.addAdvert(advert2);
		assertEquals(2, service.getAll().size());
	}
	@Test
	void getByCategoryTest() {
		service.addAdvert(advert1);
		service.addAdvert(advert2);
		assertEquals(2, service.getByCategory(advert1.category).size());
		assertEquals(0, service.getByCategory("qq").size());
	}
	@Test
	void getByPriceTest() {
		service.addAdvert(advert1);
		service.addAdvert(advert2);
		assertEquals(2, service.getByMaxPrice(advert1.price).size());
		assertEquals(0, service.getByMaxPrice(--advert1.price).size());
	}

}
