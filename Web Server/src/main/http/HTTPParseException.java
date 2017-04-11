package main.http;

public class HTTPParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public HTTPParseException() {
	}
	
	public HTTPParseException(String message) {
		super(message);
	}

}
