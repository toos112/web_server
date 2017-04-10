package main.util.js;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import main.util.FileUtil;

public class ServerScriptManager implements Runnable {

	public static ServerScriptManager instance = new ServerScriptManager();

	private ScriptEngineManager manager;
	private ScriptEngine engine;

	private static HashMap<String, List<Function<Object, Object>>> eventMap;

	private ServerScriptManager() {
		manager = new ScriptEngineManager();
		engine = manager.getEngineByName("JavaScript");
		engine.put("_", new ServerJSUtil(engine));
		eventMap = new HashMap<String, List<Function<Object, Object>>>();
	}

	public ScriptEngine getEngine() {
		return engine;
	}

	public void eval(String file) {
		JSCode.eval(engine, FileUtil.getFile(file, false));
	}

	public void registerEvent(String event, Function<Object, Object> func) {
		if (!eventMap.containsKey(event))
			eventMap.put(event, new ArrayList<Function<Object, Object>>());
		eventMap.get(event).add(func);
	}

	public void triggerEvent(String event, Object[] params) {
		if (!eventMap.containsKey(event))
			return;
		List<Function<Object, Object>> handlers = eventMap.get(event);
		for (Function<Object, Object> handler : handlers)
			JSCode.call(handler, params);
	}

	@Override
	public void run() {
		eval("scripts/main.js");
	}

}
