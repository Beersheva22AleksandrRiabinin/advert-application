package telran.advert.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

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
	public String addAdvert(Advert advert) {
		int id = 0;
		do {
			id = (int) (Math.random() * (MAX_ID - MIN_ID) + MIN_ID);
		} while (adverts.containsKey(id));
		advert.id = id;
		
		adverts.put(id, advert);
		advertsByCategory.computeIfAbsent(advert.category, c -> new HashSet<>()).add(advert);
		advertsByPrice.computeIfAbsent(advert.price, p -> new HashSet<>()).add(advert);
		String res = String.format("advert %s with id %s has been added", advert.name, advert.id);
		return res;
	}

	@Override
	public String updateAdvert(Advert advert) {
		
		if (!adverts.containsKey(advert.id)) {
			throw new NotFoundException(String.format("advert with id %s doesn't exist", advert.id));
		}
		
		
		Set<Advert> setByCategory = advertsByCategory.get(advert.category);
		setByCategory.removeIf(a -> a.id == advert.id);
		setByCategory.add(advert);
		
		Advert oldAdvert = adverts.get(advert.id);
		int oldPrice = oldAdvert.price;
		Set<Advert> setByOldPrice = advertsByPrice.get(oldPrice);
		setByOldPrice.removeIf(a -> a.id == advert.id);
		int newPrice = advert.price;
		advertsByPrice.computeIfAbsent(newPrice, p -> new HashSet<>()).add(advert);
		
		adverts.replace(advert.id, advert);
		
		String res = String.format("advert with id %s has been updated", advert.id);
		log.debug(res);
			
		return res;
	}

	@Override
	public List<Advert> getAll() {
		List<Advert> res = adverts.values().stream().toList();
		log.trace("service returns {} adverts", res.size());
		return res;
	}

	@Override
	public String deleteAdvert(int id) {	
		Advert advRemoved = adverts.remove(id);
		if (advRemoved == null) {
			throw new NotFoundException(String.format("advert with id %s doesn't exist", id));
		}

		advertsByCategory.get(advRemoved.category).remove(advRemoved);
		advertsByPrice.get(advRemoved.price).remove(advRemoved);
		
		String res = String.format("advert with id %d has been deleted", id);
		log.debug(res);
		return res;
	}

	@Override
	public List<Advert> getByCategory(String category) {
		List<Advert> res = new ArrayList<>();
		Set<Advert> set =  advertsByCategory.get(category);
		if (set != null) {
			res = set.stream().toList();
		}
		log.debug("service returned {} adverts with category {}", res.size(), category);
		return res;
	}

	@Override
	public List<Advert> getByMaxPrice(int price) {
		List<Advert> res = new ArrayList<>();
		Set<Advert> set = advertsByPrice.subMap(0, false, price, true).values()
				.stream().flatMap(Set::stream).collect(Collectors.toSet());
		res = set.stream().toList();
		log.debug("service returned {} adverts with prise less than {}", res.size(), price);
		return res;
	}

	@Override
	public void save() {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			output.writeObject(getAll());
			log.info("saved");
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void restore() {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			List<Advert> allAdverts = (List<Advert>) input.readObject();
			allAdverts.forEach(this::addAdvert);
			log.info("restored {} adverts", allAdverts.size());
		} catch (FileNotFoundException e) {
			//
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
		
	}
	
}
