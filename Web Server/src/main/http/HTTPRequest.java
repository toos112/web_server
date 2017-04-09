package main.http;

import java.util.HashMap;

public class HTTPRequest {

	private HashMap<String, String> header = new HashMap<String, String>();
	private String status;

	public HTTPRequest(String str) {
		String[] lines = str.split("\r\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (i == 0) {
				status = line;
				continue;
			}
			if (line.equals(""))
				continue;
			String[] pair = line.split(": ");
			header.put(pair[0], pair[1]);
		}
	}

	public String getValue(String key) {
		return header.get(key);
	}
	
	public String getStatus() {
		return status;
	}

}
