package telran.advert.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.advert.model.Advert;
import telran.advert.service.AdvertService;
import telran.advert.service.AdvertServiceImpl;


@CrossOrigin //CORS ¯\_(ツ)_/¯
@RestController //based on this class will be created servlet
@RequestMapping("adverts")
@RequiredArgsConstructor //final fields
@Slf4j
@Validated //only for controller **
public class AdvertController {
	
	final AdvertServiceImpl service;
	
//	@PostMapping
//	String addAdvert(@RequestBody @Valid Advert advert) {
//		log.debug("controller received advert {}", advert.name);
//		Advert ad = service.addAdvert(advert);
//		int id = ad.id;
//		System.out.println(ad);
//		log.debug("id is {}", id);
//		return String.format("advert with id %s has been added", id);
//	}
	@PostMapping
	Advert addAdvert(@RequestBody @Valid Advert advert) {
		log.debug("controller received advert {}", advert.name);
		Advert adv = service.addAdvert(advert);
		return adv;
	}

}
