package main.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	
	private static String[] indexFiles = new String[] { "index.html" };
	
	private FileUtil() {
		
	}

	public static final main.io.File getFile(String path, boolean checkIndex) {
		path = "E:/Gidion web server/web server/html-code" + (path.startsWith("/") ? "/root" : "/" + path);
		if (path.startsWith("/") || path.startsWith("\\"))
			path = "root" + path;

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
		} else {
			try {
				file.createNewFile();
				return new main.io.File(path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
