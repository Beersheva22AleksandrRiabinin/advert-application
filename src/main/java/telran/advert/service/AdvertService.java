package telran.advert.service;

import java.util.*;

import telran.advert.model.Advert;

public interface AdvertService {
	
	String addAdvert(Advert advert);
	String updateAdvert(Advert advert);
	List<Advert> getAll();
	String deleteAdvert(int id);
	List<Advert> getByCategory(String category);

}
