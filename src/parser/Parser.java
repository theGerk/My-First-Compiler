/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import static sun.management.Agent.error;

/**
 *
 * @author Benji
 */
public class Parser {

	private Token lookAhead;
	private Scanner scanner;

	public Parser(String filename) {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(filename);
		} catch (Exception e) {
			error("not a file");
		}

		InputStreamReader isr = new InputStreamReader(fis);
		scanner = new Scanner(isr);

		try {
			lookAhead = scanner.nextToken();
		} catch (IOException ex) {
			error("Could not get next token");
		}
	}

	public void match(TokenType expected) {
		if (this.lookAhead.equals(expected)) {
			try {
				this.lookAhead = scanner.nextToken();
				if (this.lookAhead == null) {
					this.lookAhead = new Token("");//not sure if this is valid
				}
			} catch (IOException ex) {
				error("cannot get next token");
			}
		} else {
			error("invalid match. expected " + expected + " recived " + this.lookAhead.getType());
		}
	}

	public void program() {
		match(TokenType.PROGRAM);
		match(TokenType.ID);
		declarations();
		subprogramDeclarations();
		compoundStatement();
		match(TokenType.PERIOD);
	}

	public void identifierList() {
		match(TokenType.ID);
		if (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			identifierList();	//recursion
		}
	}

	public void declarations() {
		if (lookAhead.equals(TokenType.VAR)) {
			identifierList();
			match(TokenType.COLON);
			type();
			match(TokenType.SEMICOLON);
			declarations();		//recursion;
		}
	}

	public void type() {
		if (lookAhead.equals(TokenType.ARRAY)) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTSQUAREBRACKET);
			match(TokenType.NUM);
			match(TokenType.COLON);
			match(TokenType.RIGHTSQUAREBRACKET);
			match(TokenType.OF);
			standardType();
		}
	}

	public void standardType() {
		if (lookAhead.equals(TokenType.INTEGER)) {
			match(TokenType.INTEGER);
		} else {
			match(TokenType.REAL);
		}
	}

	public void subprogramDeclarations() {

	}
}
