package main.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {

	private HashMap<String, String> header = new HashMap<String, String>();
	private String[] body;
	private String status;

	public HTTPResponse(String status, String[] body) {
		this.status = status;
		this.body = body;
	}

	public String[] getText() {
		String headers = "";
		for (Map.Entry<String, String> entry : header.entrySet())
			headers += entry.getKey() + ": " + entry.getValue() + "\r\n";
		return (status + "\r\n" + headers + "\r\n" + String.join("\r\n", body)).split("\r\n");
	}

	public void addHeader(String key, String value) {
		header.put(key, value);
	}
}
