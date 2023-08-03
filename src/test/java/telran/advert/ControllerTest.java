package telran.advert;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import telran.advert.controller.AdvertController;
import telran.advert.model.Advert;
import telran.advert.service.AdvertService;
import telran.advert.service.AdvertServiceImpl;

//@Service
class MockService implements AdvertService {

	@Override
	public String addAdvert(Advert advert) {
		
		return "addtest";
	}

	@Override
	public String updateAdvert(Advert advert) {
		
		return "updatetest";
	}

	@Override
	public List<Advert> getAll() {
		
		return new ArrayList<Advert>();
	}

	@Override
	public String deleteAdvert(int id) {
		
		return "deletetest";
	}

	@Override
	public List<Advert> getByCategory(String category) {

		return new ArrayList<Advert>();
	}

	@Override
	public List<Advert> getByMaxPrice(int price) {
		
		return new ArrayList<Advert>();
	}

	@Override
	public void save() {
		//	
	}

	@Override
	public void restore() {
		// 	
	}
	
}


@WebMvcTest({ AdvertController.class, MockService.class })
class ControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	Advert advert;
	String baseUrl = "http://localhost:8080/adverts";
//	String addUrl = String.format("%s/add", baseUrl);
//	String updateUrl = String.format("%s/update", baseUrl);

	@BeforeEach
	void setUp() {
		advert = new Advert();
//		advert.id = 0;
//		advert.category = "vehicle";
//		advert.price = 123;
//		advert.name = "test";
//		advert.details = "test";
	}

	@Test
	void mockMvcExists() {
		assertNotNull(mockMvc);
	}
	
	@Test
	void addTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		String response = mockMvc.perform(post(baseUrl)
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals("addtest", response);
	}
//	private ResultActions getRequestBase(String messageJson) throws Exception {
//		return mockMvc.perform(post(baseUrl)
//				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
//				.andDo(print());
//	}
	@Test
	void updateTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		String response = mockMvc.perform(put(baseUrl)
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals("updatetest", response);
	}
	@Test
	void getAllTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		int response = mockMvc.perform(get(baseUrl)
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentLength();
		assertEquals(0, response);
	}
	@Test
	void deleteTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		String response = mockMvc.perform(delete(baseUrl + "/123123")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals("deletetest", response);
	}	
	@Test
	void wrongPathTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		String response = mockMvc.perform(put(baseUrl + "/qwe")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("wrong path")); //
	}
	@Test
	void categoryTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		int response = mockMvc.perform(get(baseUrl + "/category/doesntmatter")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentLength();
		assertEquals(0, response);
	}
	@Test
	void priceTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		int response = mockMvc.perform(get(baseUrl + "/price/12345")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentLength();
		assertEquals(0, response);
	}
	@Test
	void notFoundTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		int response = mockMvc.perform(get(baseUrl + "/")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isNotFound()) // <- here is our test, expected status
				.andReturn().getResponse().getContentLength();
		assertEquals(0, response);
	}
//	@Test
//	void categoryValidationTest() throws Exception {
//		String messageJson = mapper.writeValueAsString(advert);
//		String response = mockMvc.perform(get(baseUrl + "category/67890")
//				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
//				.andDo(print())
//				.andExpect(status().isNotFound())
//				.andReturn().getResponse().getContentAsString();
//		System.out.println(response);
//		assertTrue(response.contains("category value mismatches pattern"));
//	}
//	@Test
	void wrongArgumentTypeTest() throws Exception {
		String messageJson = mapper.writeValueAsString(advert);
		String response = mockMvc.perform(get(baseUrl + "price/QQ")
				.contentType(MediaType.APPLICATION_JSON).content(messageJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("Failed to convert value"));
	}
}
