package main.util.js;

import main.websocket.WebSocket;

public class JSWSManager {
	
	public static JSWSManager instance = new JSWSManager();
	
	private JSWSManager() {
		
	}
	
	public final void addProtocol(String name) {
		WebSocket.addProtocol(name);
	}

}
