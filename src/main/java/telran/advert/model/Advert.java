package telran.advert.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	@Pattern(regexp = "^(?! )[a-zA-Z\s,]+", message = "category value mismatches pattern")
	public String category;
	@Positive(message = "price should be positive")
	public int price;
	@NotEmpty(message = "should not be empty")
	public String name;
	@NotEmpty(message = "should not be empty")
	public String details;

}
