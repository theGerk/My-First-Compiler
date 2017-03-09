/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 *
 * @author Benji
 */
public class Token {

	private TokenType type;
	private String input;

	public Token(String key) {
		input = key;
		type = LookUp.lookUp.get(key);
		if (type == null) {
			type = TokenType.ID;
		}
	}

	public Token(int key, String stringForm) {
		type = TokenType.NUM;
		input = stringForm;
	}

	public String getLexeme() {
		return input;
	}

	@Override
	public String toString() {
		return "Token: " + this.input;
	}

	public TokenType getType() {
		return type;
	}

	public boolean equals(TokenType compareAgainst) {
		return compareAgainst == getType();
	}
}
