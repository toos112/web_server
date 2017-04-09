package main.http;

import java.io.IOException;
import java.net.ServerSocket;

import main.util.HTTPProcessor;

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
				HTTPClient client = new HTTPClient(server.accept());
				HTTPProcessor.process(client);
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
