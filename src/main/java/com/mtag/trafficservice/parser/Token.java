/*
 * The MIT License
 *
 * Copyright 2015 cwahlmann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mtag.trafficservice.parser;

/**
 *
 * @author cwahlmann
 */
public enum Token {
	TRAFFIC_JAM(TokenType.TYPE_SBST, "staus?"), JAMMED(TokenType.TYPE_ADJ,
			"gestaut"), SLOW_MOVING(TokenType.TYPE_ADJ, "stockend(er)?"), ROADWORKS(
			TokenType.TYPE_SBST, "baustellen?", "straßenarbeiten"), CLOSURE(
			TokenType.TYPE_SBST, "sperrung(en)?", "vollsperrung(en)?"), CLOSED(
			TokenType.TYPE_ADJ, "gesperrt"), PARTIAL_CLOSURE(
			TokenType.TYPE_SBST, "teilsperrung(en)?"), PARTIAL(
			TokenType.TYPE_ADJ, "teils", "teil"), DIRECTION_TO(
			TokenType.TYPE_DIRECTION_TO, "richtung"), DIRECTION_FROM(
			TokenType.TYPE_DIRECTION_FROM, "von"), DANGER(TokenType.TYPE_SBST,
			"gefahr(en)?"), AUTOBAHN(TokenType.TYPE_STREET, "a\\d+"), BUNDESSTR(
			TokenType.TYPE_STREET, "b\\d+"), COUNTRY(TokenType.TYPE_COUNTRY,
			Parser.countries.values().toArray(new String[1])), OTHER(
			TokenType.TYPE_OTHER);

	public static enum TokenType {
		TYPE_SBST, TYPE_ADJ, TYPE_DIRECTION_TO, TYPE_DIRECTION_FROM, TYPE_COUNTRY, TYPE_STREET, TYPE_OTHER
	};

	private final TokenType type;
	private final String[] keys;

	public final boolean matches(String key) {
		for (String k : keys) {
			if (key.toLowerCase().matches(k.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public TokenType getType() {
		return type;
	}

	Token(TokenType type, String... keys) {
		this.type = type;
		this.keys = keys;
	}

}
