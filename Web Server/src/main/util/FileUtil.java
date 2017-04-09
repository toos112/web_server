package main.util;

import java.io.File;

public class FileUtil {
	
	private static String[] indexFiles = new String[] { "index.html" };
	
	private FileUtil() {
		
	}

	public static final main.io.File getFile(String path, boolean checkIndex) {
		if (path.startsWith("/") || path.startsWith("\\"))
			path = "root" + path;

		if (new File(path).isDirectory() && checkIndex) {
			for (String fileName : indexFiles) {
				String ipath = path + "/" + fileName;
				if (new File(ipath).exists() && new File(ipath).isFile()) {
					return new main.io.File(ipath);
				}
			}
		} else if (new File(path).isFile())
			return new main.io.File(path);

		return null;
	}

}
