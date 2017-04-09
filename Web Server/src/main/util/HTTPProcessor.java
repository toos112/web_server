package main.util;

import main.http.HTTPClient;
import main.http.HTTPRequest;
import main.http.HTTPResponse;
import main.io.File;

public class HTTPProcessor {
	
	private HTTPProcessor() {
		
	}
	
	public static void err(HTTPClient client, String message, String filePath) {
		client.writeResponse(new HTTPResponse("HTTP/1.1 " + message,
			FileUtil.getFile(filePath, false, true).getContents()));
	}
	
	public static void process(HTTPClient client) {
		try {
			HTTPRequest request = client.readRequest();
			String path = request.getPath();
			if (!(path.startsWith("/") || path.startsWith("\\")) || path.contains("..")) {
				err(client, "403 Forbidden", "403.html");
			} else {
				try {
					File file = FileUtil.getFile(path, true, false);
					client.writeResponse(new HTTPResponse("HTTP/1.1 300 OK", file.getContents()));
				} catch (NullPointerException e) {
					err(client, "404 Not Found", "404.html");
				}
			}
		} catch (Exception e) {
			err(client, "400 Bad Request", "400.html");
		}
	}

}
