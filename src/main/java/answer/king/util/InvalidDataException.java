package answer.king.util;

public class InvalidDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InvalidDataException() {
		
	}

	public InvalidDataException(String message) {
		super(message);
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
