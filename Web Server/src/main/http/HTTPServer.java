package main.http;

import java.io.IOException;
import java.net.ServerSocket;

import main.io.File;
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
				try {
					HTTPRequest request = client.readRequest();
					String path = request.getPath();
					if (!(path.startsWith("/") || path.startsWith("\\")) || path.contains("..")) {
						client.writeResponse(new HTTPResponse("HTTP/1.1 403 Forbidden",
								FileUtil.getFile("403.html", false, true).getContents()));
					} else {
						try {
							File file = FileUtil.getFile(path, true, false);
							client.writeResponse(new HTTPResponse("HTTP/1.1 300 OK", file.getContents()));
						} catch (NullPointerException e) {
							client.writeResponse(new HTTPResponse("HTTP/1.1 404 Not Found",
									FileUtil.getFile("404.html", false, true).getContents()));
						}
					}
				} catch (Exception e) {
					client.writeResponse(new HTTPResponse("HTTP/1.1 400 Bad Request",
							FileUtil.getFile("400.html", false, true).getContents()));
				}
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
