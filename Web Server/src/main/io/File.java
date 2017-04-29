package main.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import main.util.js.JSClientInfo;
import main.util.js.JSCode;

public class File {

	private String filePath;

	public File(String filePath) {
		this.filePath = filePath;
	}

	public String[] read() {
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> lines = new ArrayList<String>();
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
				lines.add(line);
			bufferedReader.close();
			return lines.toArray(new String[lines.size()]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] readAndEval(String[][] params, JSClientInfo info) {
		String[] text = read();
		return JSCode.evalServerCode(text, params, info);
	}

	public void write(String[] text) {
		try {
			PrintWriter pw = new PrintWriter(filePath);
			for (String str : text)
				pw.write(str + "\n");
			pw.close();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public String getPath() {
		return filePath;
	}

}
