/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.util.HashMap;

/**
 * maps from a string representation of token to TokenType To use simply use the
 * public lookup inside of it.
 *
 * @author Benji
 */
@SuppressWarnings("serial")
public class LookUp extends HashMap<String, TokenType> {

	/**
	 * An array of strings corresponding to the TokenType enumerable.
	 */
	private static final String[] KEYWORD_ARRAY = new String[]{
		"and", "array", "begin", "div", "do", "else", "end", "function", "if", "integer", "mod", "not", "of", "or", "procedure", "program", "real", "then", "var", "while",
		";", ",", ".", ":", "[", "]", "(", ")", "+", "-", "=", "<>", "<", ">", "<=", ">=", "*", "/", ":=",
		"read", "write" //not sure I want these here, but I think it will work.
	};

	/**
	 * Private default constructor, set's up the map.
	 */
	private LookUp() {

		//init the lookup
		for (int i = 0; i < KEYWORD_ARRAY.length; i++) {
			put(KEYWORD_ARRAY[i], TokenType.values()[i]);
		}
	}

	/**
	 * Global lookup to be used in all cases
	 */
	public final static LookUp LOOKUP = new LookUp();
}
