package main.util.js;

import java.util.function.Function;

import javax.script.ScriptEngine;

public class ServerJSUtil extends JSUtil {

	public ServerJSUtil(ScriptEngine engine) {
		super(engine);
		isServer = true;
	}

	public final void event(String event, Function<Object, Object> func) {
		ServerScriptManager.instance.registerEvent(event, func);
	}

	public final JSWSManager getWSMan() {
		return JSWSManager.instance;
	}

}
