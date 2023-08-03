package telran.advert;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
	
	@Autowired
	AdvertService service;
	
	Advert advert;
	@BeforeEach
	void setUp() {	
		advert = new Advert();
		advert.id = 0;
		advert.category = "vehicle";
		advert.price = 123;
		advert.name = "name";
		advert.details = "details";
	}
	@Test
	void addTest() {
		String res = service.addAdvert(advert);
		assertTrue(res.contains("has been added"));
	}
	@Test
	void updateTest() {
		String str = service.addAdvert(advert);
		int id = getId(str);
		advert.id = id;
		advert.name = "qq";
		String expected = String.format("advert with id %s has been updated", id);
		assertEquals(expected, service.updateAdvert(advert));
		
		advert.id = 123;
		String exp = (String.format("advert with id %s doesn't exist", id));
		assertThrowsExactly(NotFoundException.class, () -> service.updateAdvert(advert), exp);
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
		String str = service.addAdvert(advert);
		int id = getId(str);
		String expected = String.format("advert with id %d has been deleted", id);
		assertEquals(expected, service.deleteAdvert(id));
		
		int id2 = 123;
		String exp = (String.format("advert with id %s doesn't exist", id));
		assertThrowsExactly(NotFoundException.class, () -> service.deleteAdvert(id2), exp);
	}

}
