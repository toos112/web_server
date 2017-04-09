package main.http;

import java.util.HashMap;

public class HTTPRequest {

	private HashMap<String, String> header = new HashMap<String, String>();
	private String status;

	public HTTPRequest(String[] lines) throws Exception {
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (i == 0) {
				if (line.length() < 2) throw new Exception();
				status = line;
				continue;
			}
			if (line.equals(""))
				continue;
			String[] pair = line.split(": ");
			if (pair.length != 2) throw new Exception();
			header.put(pair[0], pair[1]);
		}
	}

	public String getValue(String key) {
		return header.get(key);
	}

	public String getStatus() {
		return status;
	}
	
	public String getPath() {
		String[] statusArr = status.split(" ");
		return statusArr[1];
	}
	
	public String getMethod() {
		String[] statusArr = status.split(" ");
		return statusArr[0];
	}
}
