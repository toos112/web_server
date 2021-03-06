package main.util.js;

import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import main.io.File;
import main.util.StringUtil;

public class JSCode {

	private JSCode() {

	}

	public static final String[] evalServerCode(String[] stra, String[][] params, JSClientInfo info) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		String qsJSON = "{";
		for (String[] pa : params) {
			if (pa.length == 2)
				qsJSON += "\"" + pa[0] + "\":\"" + pa[1] + "\",";
		}
		if (qsJSON.endsWith(","))
			qsJSON = qsJSON.substring(0, qsJSON.length() - 1);
		qsJSON += "}";

		engine.put("_qs", qsJSON);
		engine.put("_", new JSUtil(engine));
		engine.put("_ci", info);

		String result = String.join("%n%", stra);
		String[] code = StringUtil.getBetween("(js:", ":js)", result);
		String[] tempCode = new String[code.length];

		for (int i = 0; i < tempCode.length; i++)
			tempCode[i] = ("(js:" + code[i] + ":js)");
		for (int i = 0; i < code.length; i++)
			code[i] = code[i].replace("%n%", "\n");
		result = StringUtil.setIndexes(tempCode, result);

		String[] data = new String[code.length];
		for (int i = 0; i < code.length; i++)
			data[i] = eval(engine, code[i]);

		result = StringUtil.renIndexes(data, result);
		return result.split("%n%");
	}

	public static final String eval(ScriptEngine engine, String code) {
		try {
			Object val = engine.eval(code);
			if (val == null)
				return "";
			return val.toString();
		} catch (ScriptException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static final String eval(ScriptEngine engine, String[] code) {
		return eval(engine, String.join("\n", code));
	}

	public static final String eval(ScriptEngine engine, File file) {
		try {
			Object val = engine.eval(String.join("\n", file.read()));
			if (val == null)
				return "";
			return val.toString();
		} catch (ScriptException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static final void call(Function<Object, Object> func, Object param) {
		func.apply(param);
	}
}
