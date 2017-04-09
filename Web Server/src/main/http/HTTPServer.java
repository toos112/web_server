package main.http;

import java.io.IOException;
import java.net.ServerSocket;

import main.util.FileUtil;

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
				HTTPRequest request = client.readRequest();
				client.writeResponse(new HTTPResponse("HTTP/1.1 300 OK", FileUtil.getFile(request.getPath(), true).getContents()));
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
