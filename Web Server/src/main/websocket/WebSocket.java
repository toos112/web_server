package main.websocket;

import java.util.Base64;

import main.http.HTTPClient;
import main.http.HTTPRequest;
import main.http.HTTPResponse;

public class WebSocket {

	private static String SHA1(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++)
			result += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
		return result;
	}

	public WebSocket(HTTPClient client, HTTPRequest request) {
		String key = request.getValue("Sec-WebSocket-Key");
		key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		key = SHA1(key.getBytes());
		key = new String(Base64.getEncoder().encode(key.getBytes()));
		HTTPResponse response = new HTTPResponse("HTTP/1.1 101 Switching Protocols", new String[] { "" });
		response.addHeader("sSec-WebSocket-Accept", key);
		client.writeResponse(response);
		
		while (true)
			System.out.println(client.readLine());
	}

}
