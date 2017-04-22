package main.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {

	private HashMap<String, String> header = new HashMap<String, String>();
	private String status;

	public HTTPRequest(String[] lines) throws HTTPParseException {
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (i == 0) {
				if (line.length() < 2) throw new HTTPParseException();
				status = line;
				continue;
			}
			if (line.equals(""))
				continue;
			String[] pair = line.split(": ");
			if (pair.length != 2) throw new HTTPParseException();
			header.put(pair[0], pair[1]);
		}
	}
	
	public boolean valueContains(String key, String value) {
		return Arrays.asList(header.get(key).split(", ")).contains(value);
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

	public String[] getHeaders() {
		String[] result = new String[header.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : header.entrySet())
			result[i++] = entry.getKey() + ": " + entry.getValue();
		return null;
	}
}
