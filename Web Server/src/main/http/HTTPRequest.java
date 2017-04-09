package main.http;

import java.util.Hashtable;

public class HTTPRequest {

	private Hashtable<String, String> header = new Hashtable<String, String>();

	public HTTPRequest(String str) {
		String[] lines = str.split("\r\n");
		for (int i = 0; i < lines.length; i++) {
			if (i == 0) continue;
			String line = lines[i];
			if (line.equals("")) continue;
			String[] pair = line.split(": ");
			header.put(pair[0], pair[1]);
		}
	}
	
	public String getValue(String key) {
		return header.get(key);
	}

}
