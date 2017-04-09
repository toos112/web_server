package main.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {
	
	private String filePath;
	
	public File(String filePath) {
		this.filePath = filePath;
	}
	
	public String[] getContents() {
		Path path = FileSystems.getDefault().getPath(filePath);
		try {
			return (String[]) Files.lines(path).toArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
