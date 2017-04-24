package main.util.js;

import main.http.HTTPClient;

public class JSClientInfo {
	
	private String[] headers;
	private HTTPClient client;
	
	public JSClientInfo(HTTPClient client) {
		this.client = client;
	}
	
	public final void setHeaders(String[] headers) {
		this.headers = headers;
	}
	
	public final String getHeaders() {
		return String.join("\n", headers);
	}
	
	public final String getInetAddress() {
		return client.getSocket().getInetAddress().toString();
	}

}
