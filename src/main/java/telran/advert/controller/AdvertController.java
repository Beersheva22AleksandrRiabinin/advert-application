package telran.advert.controller;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.advert.model.Advert;
import telran.advert.service.AdvertService;
import telran.advert.service.AdvertServiceImpl;


@CrossOrigin //CORS
@RestController
@RequestMapping("adverts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdvertController {
	
	final AdvertService service;
	
	@PostMapping
	String addAdvert(@RequestBody @Valid Advert advert) {
		log.debug("controller received an advert {} to add", advert.name);
		String res = service.addAdvert(advert);
		log.debug(res);
		return res;
	}
	
	@PutMapping
	String updateAdvert(@RequestBody @Valid Advert advert) {
		log.debug("controller received an advert {} to update", advert.id);
		return service.updateAdvert(advert);
	}
	
	@GetMapping
	List<Advert> getAllAdverts() {
		log.trace("controller received request for getting all adverts");
		return service.getAll();
	}
	
	@DeleteMapping("/{id}")
	String deleteAdvert(@PathVariable(name = "id") int id) {
		log.debug("controller received request for deleting {}", id);
		return service.deleteAdvert(id);
	}
	
	@GetMapping("category/{category}")
	List<Advert> getAdvertsByCategory(@PathVariable(name = "category") String category) {
		log.debug("controller received request for getting adverts in category {}", category);
		return service.getByCategory(category);
	}
	
	@GetMapping("price/{price}")
	List<Advert> getAdvertsMaxPrice(@PathVariable(name = "price") int price) {
		log.debug("controller received request for getting adverts cheaper than {}", price);
		return service.getByMaxPrice(price);
	}
	
	@PostConstruct
	void init() {
		log.info("registered service {}", service.getClass().getSimpleName());
		service.restore();
	}
	
	@PreDestroy
	void shutdown() {
		service.save();
		log.info("context closed");
	}
	
//	@Configuration
//	@EnableWebMvc
//	public class CorsConfiguration implements WebMvcConfigurer {
//		//CORS
//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/**").allowedMethods("GET", "POST","PUT", "DELETE");
//	    }
//	}

}
