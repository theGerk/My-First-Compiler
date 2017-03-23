/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.util.HashMap;

/**
 *
 * @author Benji
 */
public class LookUp extends HashMap<String, TokenType> {

	private static String[] keywordArray = new String[]{
		"and", "array", "begin", "div", "do", "else", "end", "function", "if", "integer", "mod", "not", "of", "or", "procedure", "program", "real", "then", "var", "while",
		";", ",", ".", ":", "[", "]", "(", ")", "+", "-", "=", "<>", "<", ">", "<=", ">=", "*", "/", ":="
	};

	private LookUp() {

		//init the lookup
		for (int i = 0; i < keywordArray.length; i++) {
			put(keywordArray[i], TokenType.values()[i]);
		}
	}

	public static LookUp lookUp = new LookUp();
}
