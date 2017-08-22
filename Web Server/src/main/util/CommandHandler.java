package main.util;

import java.util.Scanner;

import main.util.js.ServerScriptManager;
import main.util.js.event.JSCommandEvent;

public class CommandHandler implements Runnable {
	
	private Scanner scanner = new Scanner(System.in);

	@Override
	public void run() {
		while (true) {
			String line = scanner.nextLine();
			ServerScriptManager.instance.triggerEvent("cmd", new JSCommandEvent(line));
		}
	}

}
