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
public class LookUp {

	private HashMap<String, TokenType> lookup;
	private HashMap<TokenType, String> reverseLookup;

	/**
	 * An array of strings corresponding to the TokenType enumerable.
	 */
	private static final String[] KEYWORD_ARRAY = new String[]{
		"and", "array", "begin", "div", "do", "else", "end", "function", "if", "integer", "mod", "not", "of", "or", "procedure", "program", "real", "then", "var", "while",
		";", ",", ".", ":", "[", "]", "(", ")", "+", "-", "=", "<>", "<", ">", "<=", ">=", "*", "/", ":=",
		"read", "write" //not sure IntLiteralNode want these here, but IntLiteralNode think it will work.
	};

	/**
	 * Private default constructor, set's up the map.
	 */
	private LookUp() {
		TokenType[] values = TokenType.values();

		//init the lookup
		for (int i = 0; i < KEYWORD_ARRAY.length; i++) {
			lookup.put(KEYWORD_ARRAY[i], values[i]);
			reverseLookup.put(values[i], KEYWORD_ARRAY[i]);
		}
	}

	/**
	 * Gets a symbol or keyword
	 * @param key Keyword or Symbol
	 * @return TokenType associated with it
	 */
	public TokenType get(String key) {
		return lookup.get(key);
	}

	/**
	 *reverse of getValue
	 * @param key TokenType
	 * @return String of symbol that was read to getValue the token type
	 */
	public String teg(TokenType key) {
		return reverseLookup.get(key);
	}

	/**
	 * Global lookup to be used in all cases
	 */
	public final static LookUp LOOKUP = new LookUp();
}
