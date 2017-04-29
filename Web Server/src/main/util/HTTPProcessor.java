package main.util;

import java.io.IOException;

import main.http.HTTPClient;
import main.http.HTTPParseException;
import main.http.HTTPRequest;
import main.http.HTTPResponse;
import main.io.File;
import main.util.js.JSClientInfo;
import main.util.js.JSWebSocket;
import main.util.js.ServerScriptManager;
import main.util.js.event.JSWebSocketEvent;
import main.websocket.WSClient;
import main.websocket.WSServer;
import main.websocket.WebSocket;

public class HTTPProcessor {

	private HTTPProcessor() {

	}

	public static void err(HTTPClient client, String message, String filePath, String[][] params, JSClientInfo info) {
		client.writeResponse(
				new HTTPResponse("HTTP/1.1 " + message, FileUtil.getFile(filePath, false, false, false).readAndEval(params, info)));
	}

	public static void process(HTTPClient client) {
		JSClientInfo info = new JSClientInfo(client);
		try {
			HTTPRequest request = client.readRequest();
			String[] pathArr = request.getPath().split("\\?");
			String path = pathArr[0];
			String[][] params = StringUtil.toParams(pathArr.length == 2 ? pathArr[1] : "");
			if (request.getMethod().equals("GET")) {
				if (!(path.startsWith("/") || path.startsWith("\\")) || path.contains("..")) {
					err(client, "403 Forbidden", "error/403.html", params, info);
				} else {
					if (request.valueContains("Connection", "Upgrade")) {
						if (request.valueContains("Upgrade", "websocket") || request.valueContains("Upgrade", "Websocket")) {
							String protocol = WebSocket.handshake(client, request);
							WSClient wsClient = new WSClient(client.getSocket());
							WSServer wsServer = new WSServer(wsClient, protocol);
							JSWebSocket jsWebSocket = new JSWebSocket(wsServer);
							wsServer.setJSWebSocket(jsWebSocket);
							ServerScriptManager.instance.triggerEvent("ws.new", new JSWebSocketEvent(jsWebSocket));
							wsServer.start();
						}
					} else {
						try {
							File file = FileUtil.getFile(path, true, true, false);
							HTTPResponse response = new HTTPResponse("HTTP/1.1 200 OK", file.readAndEval(params, info));
							response.addHeader("Content-Type", FileUtil.getMimeType(file.getPath()));
							client.writeResponse(response);
						} catch (NullPointerException e) {
							err(client, "404 Not Found", "error/404.html", params, info);
						}
					}
				}
			}
		} catch (HTTPParseException e) {
			err(client, "400 Bad Request", "error/400.html", new String[][] {}, info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
