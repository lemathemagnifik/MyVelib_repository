package CLUI;

public class SyntaxErrorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2866030785069621851L;

	public SyntaxErrorException() {
		super();
	}
	
	public SyntaxErrorException(String message) {
		super(message);
	}
}

