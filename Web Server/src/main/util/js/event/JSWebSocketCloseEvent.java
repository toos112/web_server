package main.util.js.event;

import main.util.js.JSWebSocket;

public class JSWebSocketCloseEvent extends JSWebSocketEvent {
	
	private boolean timeout;

	public JSWebSocketCloseEvent(JSWebSocket ws, boolean timeout) {
		super(ws);
		this.timeout = timeout;
	}
	
	public boolean getTimeout() {
		return timeout;
	}

}
