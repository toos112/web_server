package main.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	
	private static String[] indexFiles = new String[] { "index.html" };
	
	
	private FileUtil() {
		
	}

	public static final main.io.File getFile(String path, boolean root, boolean checkIndex, boolean create) {
		if (root) {
			if (path.startsWith("/")) path = "root" + path;
			else path = "root/" + path;
		}

		File file = new File(path);
		if (file.isDirectory() && checkIndex) {
			for (String fileName : indexFiles) {
				String ipath = path + "/" + fileName;
				File ifile = new File(ipath);
				if (ifile.exists() && ifile.isFile()) {
					return new main.io.File(ipath);
				}
			}
			return null;
		} else if (file.isFile()) {
			return new main.io.File(path);
		} else if (create) {
			try {
				file.createNewFile();
				return new main.io.File(path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else return null;
	}
	
	public static final String getMimeType(String file) {
		if (file.endsWith(".css"))
			return "text/css";
		else if (file.endsWith(".html"))
			return "text/html";
		else if (file.endsWith(".js"))
			return "text/javascript";
		else return "text/plain";
	}

}
