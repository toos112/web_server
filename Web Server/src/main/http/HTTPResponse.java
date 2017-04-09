package main.http;

import java.util.Hashtable;

public class HTTPResponse {

	private Hashtable<String, String> header = new Hashtable<String, String>();
	private String body;
	private String status;
	
	public HTTPResponse(String status) {
		
	}
	
	String getText() {
		return "";
	}
	
	void addHeader(String key, String value) {
		
	}
	
	void setBody(String body) {
		
	}

}
