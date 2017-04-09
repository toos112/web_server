package main;

import java.io.IOException;

import main.http.HTTPServer;

public class Server {

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		try {
			HTTPServer server = new HTTPServer(port);
			System.out.println("Created server on port: " + port);
			server.start();
		} catch (IOException e) {
			System.err.println("Failed to start server on port: " + port);
			e.printStackTrace();
		}
	}

}
