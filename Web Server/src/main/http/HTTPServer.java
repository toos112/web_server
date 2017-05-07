package main.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HTTPServer implements Runnable {

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
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							HTTPRequest request = client.readRequest();
							HTTPResponse response = new HTTPResponse("HTTP/1.1 301 Moved Permanently", new String[] {});
							String host = request.getValue("Host");
							if (host == null) {
							} else if (host.startsWith("http://")) {
								response.addHeader("Location", "https://" + host.substring(7, host.length()));
							} else if (host.startsWith("ws://")) {
								response.addHeader("Location", "wss://" + host.substring(5, host.length()));
							} else
								response.addHeader("Location", "https://" + host);
							client.writeResponse(response);
							client.close();
						} catch (HTTPParseException e) {
							e.printStackTrace();
						}
					}
				}).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		start();
	}
}
