package main.util.js.event;

import main.util.js.JSWebSocket;

public class JSWebSocketMessageEvent extends JSWebSocketEvent {
	
	private String message;

	public JSWebSocketMessageEvent(JSWebSocket ws, String message) {
		super(ws);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
