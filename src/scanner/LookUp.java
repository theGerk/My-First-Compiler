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
public class LookUp {

	public static HashMap<String, TokenType> lookUp;
	private static String[] keywordArray = new String[]{
		"and", "array", "begin", "div", "do", "else", "end", "function", "if", "integer", "mod", "not", "of", "or", "procedure", "program", "real", "then", "var", "while",
		";", ",", ".", ":", "[", "]", "(", ")", "+", "-", "=", "<>", "<", ">", "<=", ">=", "*", "/", ":="
	//ID and NUM left empty because we don't know what to do
	};

	public static void initilize() {
		lookUp = new HashMap<>();

		//init the lookup
		for (int i = 0; i < keywordArray.length; i++) {
			lookUp.put(keywordArray[i], TokenType.values()[i]);
		}
	}
}
