package main.http;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

import main.util.HTTPProcessor;

public class HTTPSServer {

	private ServerSocket server;
	private boolean running = false;

	public HTTPSServer(int port) throws IOException {
		ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
		server = ssf.createServerSocket(80);
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
						HTTPProcessor.process(client);
						client.close();
					}
				}).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
