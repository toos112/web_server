package main.util.js;

public class JSWebSocketMirror {

	private JSWebSocketMirror() {

	}

	public static void callEvent(JSWebSocket socket, String event, Object[] params) {
		if (event.equals("receive")) {
			if (socket.onReceive != null)
				JSCode.call(socket.onReceive, params);
		}
	}

}
