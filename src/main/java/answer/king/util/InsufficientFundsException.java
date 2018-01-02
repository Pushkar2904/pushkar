package answer.king.util;

public class InsufficientFundsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InsufficientFundsException() {
		
	}

	public InsufficientFundsException(String message) {
		super(message);
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
