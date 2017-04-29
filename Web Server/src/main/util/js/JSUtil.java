package main.util.js;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import main.util.FileUtil;

public class JSUtil {

	private List<String> included;
	private ScriptEngine engine;
	protected boolean isServer;

	public JSUtil(ScriptEngine engine) {
		included = new ArrayList<String>();
		this.engine = engine;
		isServer = false;
	}

	public final void out(String text) {
		System.out.println(text);
	}

	public final void I(String path) {
		try {
			if (!included.contains(path)) {
				engine.eval(String.join("\n", FileUtil.getFile(path, false, false, false).read()));
				included.add(path);
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public final String readf(String path) {
		String[] file = FileUtil.getFile(path, false, false, false).read();
		return String.join("\n", file);
	}

	public final void writef(String path, String text) {
		String[] stra = text.split("\n");
		FileUtil.getFile(path, false, false, true).write(stra);
	}

	public final boolean svr() {
		return isServer;
	}
	
	public final long getTime() {
		return System.currentTimeMillis() / 1000L;
	}

}
