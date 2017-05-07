package main;

import java.io.IOException;

import main.http.HTTPSServer;
import main.http.HTTPServer;
import main.util.js.ServerScriptManager;

public class Server {

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		try {
			HTTPServer http = new HTTPServer(port);
			HTTPSServer https = new HTTPSServer(port);
			new Thread(ServerScriptManager.instance).run();
			System.out.println("Created server on port: " + port);
			http.start();
			https.start();
		} catch (IOException e) {
			System.err.println("Failed to start server on port: " + port);
			e.printStackTrace();
		}
	}

}
