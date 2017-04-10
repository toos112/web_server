package main.util.js;

import main.util.js.event.JSWebSocketEvent;

public class JSWebSocketMirror {

	private JSWebSocketMirror() {

	}

	public static void callEvent(JSWebSocket socket, String event, JSWebSocketEvent param) {
		if (event.equals("receive")) {
			if (socket.onReceive != null)
				JSCode.call(socket.onReceive, param);
		} else if (event.equals("close")) {
			if (socket.onClose != null)
				JSCode.call(socket.onClose, param);
		}
	}

}
