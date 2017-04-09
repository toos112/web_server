package main;

import main.io.File;

public class Server {

	public static void main(String[] args) {
		File test = new File("E:\\gekke afkortingen.txt");
		System.out.println(String.join("\n", test.getContents()));
	}

}
