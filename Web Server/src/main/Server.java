package main;

import java.io.IOException;

import main.http.HTTPSServer;
import main.http.HTTPServer;
import main.util.CommandHandler;
import main.util.js.ServerScriptManager;

public class Server {

	public static void main(String[] args) {
		try {
			int httpPort = Integer.parseInt(args[0]);
			HTTPServer http = new HTTPServer(httpPort, args.length >= 2);
			new Thread(ServerScriptManager.instance).run();
			new Thread(http).start();
			if (args.length >= 2) {
				int httpsPort = Integer.parseInt(args[1]);
				HTTPSServer https = new HTTPSServer(httpsPort);
				new Thread(https).start();
			}
			System.out.println("Created server!");
			new Thread(new CommandHandler()).run();
		} catch (IOException e) {
			System.err.println("Failed to create server!");
			e.printStackTrace();
		}
	}

}
