package main.io;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class File {

	private String filePath;

	public File(String filePath) {
		this.filePath = filePath;
	}

	public String[] getContents() {
		try {
	        FileReader fileReader = new FileReader(filePath);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        List<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null)
	            lines.add(line);
	        bufferedReader.close();
	        return lines.toArray(new String[lines.size()]);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
