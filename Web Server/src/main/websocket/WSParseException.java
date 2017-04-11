package main.websocket;

public class WSParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private boolean close;
	
	public WSParseException(boolean close) {
		this.close = close;
	}
	
	public WSParseException(String message) {
		super(message);
	}
	
	public boolean close() {
		return close;
	}

}
