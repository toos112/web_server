package main.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {

	private HashMap<String, String> header = new HashMap<String, String>();
	private String body;
	private String status;

	public HTTPResponse(String status, String body) {
		this.status = status;
		this.body = body;
	}

	String getText() {
		String headers = "";
		for (Map.Entry<String, String> entry : header.entrySet())
			headers += entry.getKey() + ": " + entry.getValue() + "\n";
		return status + "\n" + headers + "\n" + body;
	}

	void addHeader(String key, String value) {
		header.put(key, value);
	}
}
