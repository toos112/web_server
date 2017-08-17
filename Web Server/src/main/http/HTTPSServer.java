package main.http;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import main.io.File;
import main.util.HTTPProcessor;

public class HTTPSServer implements Runnable {

	private SSLServerSocket server;
	private boolean running = false;
	
	private static void updatePassword() {
		File file = new File("https/pass.txt");
		String[] fileContents = file.read();
		if (fileContents != null)
			System.setProperty("javax.net.ssl.keyStorePassword", fileContents[0]);
	}

	static {
		System.setProperty("javax.net.ssl.keyStore", "https/keystore.jks");
		updatePassword();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() { updatePassword(); }
		}, 0, 1000 * 10);
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
