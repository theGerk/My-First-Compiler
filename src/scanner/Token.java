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
	 * Determines token's type based off it's string
	 *
	 * @param key String of the token
	 */
	public Token(String key) {
		input = key;
		type = LookUp.lookUp.get(key);
		if (type == null) {
			type = TokenType.ID;
		}
	}

	/**
	 * Sets a number token, by passing in a number and the string version that
	 * was read.
	 *
	 * @param key Could be any number, supposed to be the number that was read
	 * @param stringForm String version of the number
	 */
	public Token(int key, String stringForm) {
		type = TokenType.NUM;
		input = stringForm;
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
