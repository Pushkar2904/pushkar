package answer.king.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class can be persisted now. Added JPA annotations accordingly
 * @author Pushkar
 *
 */
@Entity
@Table(name = "T_RECEIPT" )
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long receiptId;
	

	@Column
	private BigDecimal payment;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Order order;
	
	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	
	/**
	 * The price will change based on quantity, so did update the API
	 * @return change
	 */
	public BigDecimal getChange() {
		
		BigDecimal totalOrderPrice = BigDecimal.valueOf(0);
		
		for(LineItem lineItem : order.getLineItems()) {
			totalOrderPrice = totalOrderPrice.add(lineItem.getItem().getPrice().multiply(BigDecimal.valueOf(lineItem.getQuantity())));
		}			

		return payment.subtract(totalOrderPrice);
	}
}
