package answer.king.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Newly added class to handle line item logic and this class can be persisted
 * @author Pushkar
 *
 */
@Entity
@Table(name = "T_LINE_ITEM" )
public class LineItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lineItemId;
	
	@NotNull
	@OneToOne(cascade = { CascadeType.ALL, CascadeType.PERSIST })	
	private Item item;
	
	@Column(name = "QUANTITY" )
	private Long quantity;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;
	
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	
}
