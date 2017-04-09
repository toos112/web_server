package main.http;

import java.util.Hashtable;

public class HTTPResponse {

	private Hashtable<String, String> header = new Hashtable<String, String>();
	private String body;
	private String status;
	
	public HTTPResponse(String status, String body) {
		this.status = status;
		this.body = body;
	}
	
	String getText() {
		return status + "\n" + header;
	}
	
	void addHeader(String key, String value) {
		header.put(key, value);
	}
}
