package main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String INDEX_CHAR = "%%";

	private StringUtil() {

	}

	public static final String[] getBetween(String start, String end, String str) {
		List<String> temp = new ArrayList<String>();
		Pattern pattern = Pattern.compile(Pattern.quote(start) + "(.*?)" + Pattern.quote(end));
		Matcher matcher = pattern.matcher(str);
		while (matcher.find())
			temp.add(matcher.group(1));

		String[] result = new String[temp.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = temp.get(i);
		return result;
	}

	public static final String setIndexes(String[] indexes, String str) {
		for (int i = 0; i < indexes.length; i++)
			str = str.replace(indexes[i], INDEX_CHAR + i);
		return str;
	}

	public static final String renIndexes(String[] data, String str) {
		for (int i = 0; i < data.length; i++)
			str = str.replace(INDEX_CHAR + i, data[i]);
		return str;
	}
	
	public static final String[][] toParams(String params) {
		List<String[]> resultList = new ArrayList<String[]>();
		String[] temp = params.split("&");
		if (temp[0] == "") temp = new String[0];
		for (int i = 0; i < temp.length; i++)
			resultList.add(temp[i].split("="));
		
		String[][] result = new String[resultList.size()][];
		for (int i = 0; i < result.length; i++)
			result[i] = resultList.get(i);
		return result;
	}
	
	public static final int getLength(String[] sa) {
		int result = (sa.length - 1) * 2;
		for (String s : sa)
			result += s.length();
		return result;
	}

}
