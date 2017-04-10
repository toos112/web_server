package main.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HTTPClient {

	private BufferedReader reader;
	private BufferedWriter writer;
	private Socket socket;

	public HTTPClient(Socket socket) throws IOException {
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] readLines() {
		List<String> requestList = new ArrayList<String>();
		while (true) {
			String line = readLine();
			if (line == null) return null;
			if (line.equals("")) break;
			requestList.add(line);
		}
		String[] requestText = new String[requestList.size()];
		requestText = requestList.toArray(requestText);
		return requestText;
	}
	
	public void writeLine(String line) {
		try {
			writer.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLines(String[] lines) {
		for (String line : lines)
			writeLine(line);
	}
	
	public void flush() {
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HTTPRequest readRequest() throws HTTPParseException {
		return new HTTPRequest(readLines());
	}
	
	public void writeResponse(HTTPResponse response) {
		writeLines(response.getText());
		writeLine("");
		flush();
	}
	
	public Socket getSocket() {
		return socket;
	}

}
