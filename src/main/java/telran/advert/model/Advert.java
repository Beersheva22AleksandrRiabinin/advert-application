package telran.advert.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public class Advert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	@NotEmpty(message = "should not be empty") //wrong field name
	@Pattern(regexp = "[a-zA-z\s,]+", message = "category value mismatches pattern") //post
	public String category;
	@Positive(message = "price should be positive")
//	@Max(value = Integer.MAX_VALUE, message = "too expensive")
	public int price;
	@NotEmpty(message = "should not be empty") //wrong field name
	public String name;
	@NotEmpty(message = "should not be empty") //wrong field name
	public String details;

}
