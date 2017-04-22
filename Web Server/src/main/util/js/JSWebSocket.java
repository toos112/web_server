package main.util.js;

import java.util.function.Function;

import main.websocket.WSServer;

public class JSWebSocket {

	private WSServer server;
	Function<Object, Object> onReceive = null;
	Function<Object, Object> onClose = null;

	public JSWebSocket(WSServer server) {
		this.server = server;
	}

	public final String getProtocol() {
		return server.getProtocol();
	}
	
	public final String getAddress() {
		return server.getClient().getSocket().getInetAddress().toString();
	}

	public final void out(String text) {
		server.send(text);
	}

	public final void event(String event, Function<Object, Object> handler) {
		if (event.equals("receive"))
			onReceive = handler;
		else if (event.equals("close"))
			onClose = handler;
	}

}
