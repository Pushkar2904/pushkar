package answer.king.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Strings;

import answer.king.util.InvalidDataException;

@Entity
@Table(name = "T_ITEM")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID")
	private Long id;

	private String name;

	private BigDecimal price;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	//Added validation to name always be without any numbers and special characters
	public void setName(String name) throws InvalidDataException {
		boolean isValid = !Strings.isNullOrEmpty(name) ? name.chars().allMatch( Character::isAlphabetic) : false;
		if(isValid) {		
			this.name = name;
		}
		else {
			throw new InvalidDataException("The name contains value/s other than characters!!");
		}
	}

	public BigDecimal getPrice() {
		return price;
	}

	//Added validation to price always greater than 0
	public void setPrice(BigDecimal price) throws InvalidDataException {
		if(price.compareTo(BigDecimal.ZERO) > 0) {			
			this.price = price;
		}
		else {
			throw new InvalidDataException("The price contains negative value!!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	
	
	
	
}
