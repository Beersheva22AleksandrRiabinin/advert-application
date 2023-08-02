package telran.advert.service;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.advert.exceptions.NotFoundException;
import telran.advert.model.Advert;

@Service
@Slf4j
public class AdvertServiceImpl implements AdvertService{
	
	private static final int MIN_ID = 100000;
	private static final int MAX_ID = 1000000;
	
	Map<Integer, Advert> adverts = new HashMap<>();
	Map<String, Set<Advert>> advertsByCategory = new HashMap<>();
	TreeMap<Integer, Set<Advert>> advertsByPrice = new TreeMap<>();

	@Override
	public Advert addAdvert(Advert advert) {
		int id = 0;
		do {
			id = (int) (Math.random() * (MAX_ID - MIN_ID) + MIN_ID);
		} while (adverts.containsKey(id));
		advert.id = id;
		
		adverts.put(id, advert);
		advertsByCategory.computeIfAbsent(advert.category, c -> new HashSet<>()).add(advert);
		advertsByPrice.computeIfAbsent(advert.price, p -> new HashSet<>()).add(advert);
		
		log.debug("advert {} id {} has been added", advert.name, advert.id);
		return advert;
	}

	@Override
	public Advert updateAdvert(Advert advert) {
		
		if (!adverts.containsKey(advert.id)) {
			throw new NotFoundException(String.format("advert with id %s doesn't exist", advert.id));
		}
		
		adverts.replace(advert.id, advert);
		
		Set<Advert> setByCategory = advertsByCategory.get(advert.category);
		setByCategory.removeIf(a -> a.id == advert.id);
		setByCategory.add(advert);
		
		Set<Advert> setByPrice = advertsByPrice.get(advert.price);
		setByPrice.removeIf(a -> a.id == advert.id);
		setByPrice.add(advert);
			
		return advert;
	}
	
}
