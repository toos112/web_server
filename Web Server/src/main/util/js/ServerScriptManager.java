package main.util.js;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.CompiledScript;

import main.util.FileUtil;
import main.util.js.event.JSEvent;

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
		try {
			CompiledScript script = ((Compilable) engine).compile(String.join("\n", FileUtil.getFile(file, false, false, false).read()));
			script.eval();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public void registerEvent(String event, Function<Object, Object> func) {
		if (!eventMap.containsKey(event))
			eventMap.put(event, new ArrayList<Function<Object, Object>>());
		eventMap.get(event).add(func);
	}

	public void triggerEvent(String event, JSEvent params) {
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
