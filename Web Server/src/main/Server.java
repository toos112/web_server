package main;

import java.io.IOException;

import main.http.HTTPSServer;
import main.http.HTTPServer;
import main.util.js.ServerScriptManager;

public class Server {

	public static void main(String[] args) {
		int httpPort = Integer.parseInt(args[0]);
		int httpsPort = Integer.parseInt(args[1]);

		try {
			HTTPServer http = new HTTPServer(httpPort);
			HTTPSServer https = new HTTPSServer(httpsPort);
			new Thread(ServerScriptManager.instance).run();
			System.out.println("Created server!");
			new Thread(http).start();
			new Thread(https).start();
		} catch (IOException e) {
			System.err.println("Failed to create server!");
			e.printStackTrace();
		}
	}

}
