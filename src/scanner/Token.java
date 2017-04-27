/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 * Represents a Token read by the scanner.
 *
 * @author Benji
 */
public class Token {

	/**
	 * Type of the Token
	 */
	private TokenType type;
	/**
	 * string given to create the token
	 */
	private final String input;
	/**
	 * Token's line in input file
	 */
	public final int line;
	/**
	 * Token's column in input file
	 */
	public final int column;

	/**
	 * Determines token's type based off it's string
	 *
	 * @param key String of the token
	 * @param line line on which the token is found
	 * @param col column on which token is found
	 */
	public Token(String key, int line, int col) {
		input = key;
		type = LookUp.LOOKUP.get(key);
		if (type == null) {
			type = TokenType.ID;
		}
		this.line = line;
		this.column = col;
	}

	/**
	 * Sets an int literal token, by passing in a number and the string version
	 * that was read.
	 *
	 * @param key Could be any number, supposed to be the number that was read
	 * @param stringForm String version of the number
	 * @param line line on which token is found
	 * @param col column on which token is found
	 */
	public Token(int key, String stringForm, int line, int col) {
		type = TokenType.INT_LITERAL;
		input = stringForm;
		this.line = line;
		this.column = col;

	}

	/**
	 * Sets a real literal token, by passing in a number and the string version
	 * that was read.
	 *
	 * @param key Could be any number, supposed to be the number that was read
	 * @param stringForm String version of the number
	 * @param line line on which token is found
	 * @param col column on which token is found
	 */
	public Token(float key, String stringForm, int line, int col) {
		type = TokenType.REAL_LITERAL;
		input = stringForm;
		this.line = line;
		this.column = col;

	}

	/**
	 * Gets the string that created the Token
	 *
	 * @return string form of token
	 */
	public String getLexeme() {
		return input;
	}

	/**
	 * Gets a formated version of the token
	 *
	 * @return human readable string representing a token
	 */
	@Override
	public String toString() {
		return "Token: " + this.input;
	}

	/**
	 * Gets the type of token that it is
	 *
	 * @return it's TokenType
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * overload of equals for comparing another token type
	 *
	 * @param compareAgainst TokenType to compare against
	 * @return if they have the same token type
	 */
	public boolean equals(TokenType compareAgainst) {
		return compareAgainst == getType();
	}

	/**
	 * overload of equals for comparing tokens
	 *
	 * @param compareAgainst
	 * @return if they are the same token
	 */
	public boolean equals(Token compareAgainst) {
		return getType() == compareAgainst.getType() && getLexeme() == compareAgainst.getLexeme();
	}
}
