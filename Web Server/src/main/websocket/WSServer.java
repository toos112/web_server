package main.websocket;

import main.util.js.JSWebSocket;
import main.util.js.JSWebSocketMirror;
import main.util.js.ServerScriptManager;
import main.util.js.event.JSWebSocketEvent;
import main.util.js.event.JSWebSocketMessageEvent;

public class WSServer {

	private WSClient client;
	private boolean running = false;
	private String protocol;
	private JSWebSocket jsWebSocket;

	public WSServer(WSClient client, String protocol) {
		this.client = client;
		this.protocol = protocol;
	}

	public void setJSWebSocket(JSWebSocket jsWebSocket) {
		this.jsWebSocket = jsWebSocket;
	}

	private void processText(String text) {
		if (jsWebSocket != null) {
			JSWebSocketMirror.callEvent(jsWebSocket, "receive", new JSWebSocketMessageEvent(jsWebSocket, text));
			ServerScriptManager.instance.triggerEvent("ws.receive", new JSWebSocketMessageEvent(jsWebSocket, text));
		}
	}

	private void processBytes(byte[] bytes) {
		processText(new String(bytes));
	}

	private void processControl(WSRequest request) {
		if (request.getOpcode() == 0x8) {
			JSWebSocketMirror.callEvent(jsWebSocket, "close", new JSWebSocketEvent(jsWebSocket));
			ServerScriptManager.instance.triggerEvent("ws.close", new JSWebSocketEvent(jsWebSocket));
			running = false;
		} else if (request.getOpcode() == 0x9) {
			WSResponse response = new WSResponse(0xA);
			response.setPayload(request.getPayloadBytes());
			client.writeResponse(response);
		}

	}

	private void received(WSRequest request) {
		if (!request.isFinished()) {
			if (request.getOpcode() == 1) {
				String text = request.getPayloadString();
				while (true) {
					WSRequest request2 = client.readRequest();
					if (request2 != null) {
						if (request2.getOpcode() == 0) {
							text += request2.getPayloadString();
							if (request2.isFinished())
								break;
						} else
							received(request2);
					}
				}
				processText(text);
			} else if (request.getOpcode() == 2) {
				byte[] bytes = request.getPayloadBytes();
				while (true) {
					WSRequest request2 = client.readRequest();
					if (request2 != null) {
						if (request2.getOpcode() == 0) {
							byte[] addedBytes = request2.getPayloadBytes();
							byte[] newBytes = new byte[bytes.length + addedBytes.length];
							for (int i = 0; i < bytes.length; i++)
								newBytes[i] = bytes[i];
							for (int i = 0; i < addedBytes.length; i++)
								newBytes[bytes.length + i] = addedBytes[i];
							bytes = newBytes;
							if (request2.isFinished())
								break;
						} else
							received(request2);
					}
				}
				processBytes(bytes);
			}
		} else {
			if (request.getOpcode() == 1)
				processText(request.getPayloadString());
			else if (request.getOpcode() == 2)
				processBytes(request.getPayloadBytes());
			else
				processControl(request);
		}
	}

	public void send(String text) {
		WSResponse response = new WSResponse(0x1);
		response.setPayload(text.getBytes());
		client.writeResponse(response);
	}

	public void start() {
		running = true;
		while (running) {
			WSRequest request = client.readRequest();
			if (request != null)
				received(request);
		}
	}

	public WSClient getClient() {
		return client;
	}

	public String getProtocol() {
		return protocol;
	}

}
