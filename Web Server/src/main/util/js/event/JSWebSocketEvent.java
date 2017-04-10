package main.util.js.event;

import main.util.js.JSWebSocket;

public class JSWebSocketEvent extends JSEvent {

	private JSWebSocket ws;

	public JSWebSocketEvent(JSWebSocket ws) {
		this.ws = ws;
	}
	
	public JSWebSocket getWS() {
		return ws;
	}

}
