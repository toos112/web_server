package main.websocket;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import main.http.HTTPClient;
import main.http.HTTPRequest;
import main.http.HTTPResponse;

public class WebSocket {
	
	private static List<String> protocols = new ArrayList<String>();

	private WebSocket() {

	}

	public static String handshake(HTTPClient client, HTTPRequest request) {
		String key = request.getValue("Sec-WebSocket-Key");
		try {
			key = DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-1")
					.digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HTTPResponse response = new HTTPResponse("HTTP/1.1 101 Switching Protocols", new String[] { "" });
		String protocol = "";
		for (String p : protocols) {
			if (request.valueContains("Sec-WebSocket-Protocol", p)) {
				protocol = p;
				break;
			}
		}
		response.addHeader("Sec-WebSocket-Protocol", protocol);
		response.addHeader("Sec-WebSocket-Accept", key);
		response.addHeader("Upgrade", "websocket");
		response.addHeader("Connection", "Upgrade");
		client.writeResponse(response);
		return protocol;
	}
	
	public static void addProtocol(String name) {
		if (!protocols.contains(name))
			protocols.add(name);
	}

}
