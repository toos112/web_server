package main.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import main.http.HTTPClient;
import main.http.HTTPRequest;
import main.http.HTTPResponse;

public class WebSocket {
	
	Socket clientSocket;
	
	public String readMessage() {
		try {
			int type = clientSocket.getInputStream().read();
			if (type != 129) return null;
			int length = 0;
			int lengthBits = clientSocket.getInputStream().read() - 128;
			if (lengthBits > 125) {
				if (lengthBits == 126) {
					for (int i = 0; i < 2; i++) {
						length *= 256;
						length += clientSocket.getInputStream().read();
					}
				} else if (lengthBits == 127) {
					for (int i = 0; i < 4; i++) {
						length *= 256;
						length += clientSocket.getInputStream().read();
					}
				} else return null;
			} else length = lengthBits;
			byte[] key = new byte[4];
			for (int i = 0; i < 4; i++)
				key[i] = (byte) clientSocket.getInputStream().read();
			byte[] str = new byte[length];
			for (int i = 0; i < length; i++)
				str[i] = (byte) (clientSocket.getInputStream().read() ^ key[i & 0x3]);
			return new String(str);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public WebSocket(HTTPClient client, HTTPRequest request) {
		String key = request.getValue("Sec-WebSocket-Key");
		try {
			key = DatatypeConverter
			.printBase64Binary(
			        MessageDigest
			        .getInstance("SHA-1")
			        .digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
			                .getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HTTPResponse response = new HTTPResponse("HTTP/1.1 101 Switching Protocols", new String[] { "" });
		response.addHeader("Sec-WebSocket-Protocol", "chat");
		response.addHeader("Sec-WebSocket-Accept", key);
		response.addHeader("Upgrade", "websocket");
		response.addHeader("Connection", "Upgrade");
		client.writeResponse(response);
		
		clientSocket = client.getSocket();
		
		while (true) {
			String test = readMessage();
			if (test != null) System.out.println(test);
		}
	}

}
