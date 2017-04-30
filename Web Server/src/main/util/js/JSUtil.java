package main.util.js;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
				String[] file = FileUtil.getFile(path, false, false, false).read();
				engine.eval(String.join("\n", file));
				included.add(path);
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public final String readf(String path) {
		try {
			String[] file = FileUtil.getFile(path, false, false, false).read();
			return String.join("\n", file);
		} catch (NullPointerException e) {
			return "";
		}
	}

	public final void writef(String path, String text) {
		String[] stra = text.split("\n");
		FileUtil.getFile(path, false, false, true).write(stra);
	}

	public final boolean svr() {
		return isServer;
	}
	
	public final long getTime() {
		return System.currentTimeMillis();
	}
	
	public final void delay(long time, final Function<Object, Object> func) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				JSCode.call(func, null);
			}
		}).start();
	}
	
	public final void loop(long time, final Function<Object, Object> func) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					JSCode.call(func, null);
				}
			}
		}).start();
	}

}
