package main.http;

import java.util.List;

import main.io.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HTTPServer {

	private ServerSocket server;
	private boolean running = false;

	public HTTPServer(int port) throws IOException {
		server = new ServerSocket(port);
	}

	public void stop() {
		running = false;
	}

	public void start() {
		running = true;
		while (running) {
			try {
				Socket clientSocket = server.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				List<String> requestList = new ArrayList<String>();
				while (true) {
					String line = reader.readLine();
					if (line == null) break;
					if (line.equals("")) break;
					requestList.add(line);
				}
				String[] requestText = new String[requestList.size()];
				requestText = requestList.toArray(requestText);
				HTTPRequest request = new HTTPRequest(requestText);
				HTTPResponse response = new HTTPResponse("HTTP/1.1 300 OK", new File("E:\\gekke afkortingen.txt").getContents());
				String[] responseText = response.getText();
				for (String str : responseText)
					writer.write(str + "\n");
				writer.flush();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
