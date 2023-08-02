package telran.advert.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public class Advert {
	
	public int id;
	public String category;
	public int price;
	public String name;
	public String details;

}
