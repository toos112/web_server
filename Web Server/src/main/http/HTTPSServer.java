package main.http;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import main.util.HTTPProcessor;

public class HTTPSServer implements Runnable {

	private SSLServerSocket server;
	private boolean running = false;

	static {
		System.setProperty("javax.net.ssl.keyStore", "https/keystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "kippendans666");
	}

	public HTTPSServer(int port) throws IOException {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		server = (SSLServerSocket) ssf.createServerSocket(port);
	}

	public void stop() {
		running = false;
	}

	public void start() {
		running = true;
		while (running) {
			try {
				SSLSocket socket = (SSLSocket) server.accept();
				HTTPClient client = new HTTPClient(socket);
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

	@Override
	public void run() {
		start();
	}
}
