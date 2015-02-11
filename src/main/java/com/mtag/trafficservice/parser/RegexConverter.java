package com.mtag.trafficservice.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexConverter {

	private String regex;
	private String text;
	private String replaceRegexStrings[];

	private RegexConverter(String regex, String text, String... replaceStrings) {
		this.regex = regex;
		this.text = text;
		this.replaceRegexStrings = replaceStrings;
	}

	public static RegexConverter build(String regex, String text,
			String... replaceStrings) {
		if (regex == null)
			throw new NullPointerException("Regex is null");
		if (text == null)
			throw new NullPointerException("Text is null");

		return new RegexConverter(regex, text, replaceStrings);
	}

	public Integer getInteger() {
		String result = getString().trim();
		if (!result.isEmpty() && result.matches("\\d*")) {
			return Integer.valueOf(result);
		}
		return 0;
	}

	public String getString() {
		Matcher result = result();
		if (result.find()) {
			String resultString = result.group();

			if (replaceRegexStrings != null && replaceRegexStrings.length > 0) {
				for (String replace : replaceRegexStrings) {
					resultString = resultString.replaceAll(replace, "");
				}
				return resultString;
			}
		}
		return "";
	}

	private Matcher result() {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		return m;
	}
}
