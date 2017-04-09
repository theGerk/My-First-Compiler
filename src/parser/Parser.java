/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import SymbolTable.Scope;
import SymbolTable.Scope.IdentifierKind;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

/**
 * Parser for program, parses from a stream
 *
 * @author Benji
 */
public class Parser {

	/**
	 * print error
	 *
	 * @param err error message
	 */
	public void error(String err) {
		System.out.println(err);
		System.exit(1);
	}

	/**
	 * next Token
	 */
	private Token lookAhead;
	/**
	 * Scanner to read
	 */
	private Scanner scanner;

	/**
	 * sets up parser class from file
	 *
	 * @param filename
	 */
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

	/**
	 * Matches against an expected token, throws error if token is not correct,
	 * and eats it and moves on if token is correct.
	 *
	 * @param expected
	 */
	public String match(TokenType expected) {
		String output = lookAhead.getLexeme();
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
		return output;
	}

	public Scope currentScope = new Scope();

	/**
	 * Eats a program
	 */
	public void program() {
		match(TokenType.PROGRAM);
		match(TokenType.ID);	//TODO maybe add symbol table reference
		match(TokenType.SEMICOLON);
		declarations();
		subprogramDeclarations();
		compoundStatement();
		match(TokenType.PERIOD);
	}

	/**
	 * Eats an identifier list. Adds ID names to current scope's buffer
	 */
	public void identifierList() {
		if (!currentScope.addId(match(TokenType.ID))) {
			error("invalid ID");
		}
		if (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			identifierList();	//recursion
		}
	}

	/**
	 * Eats declarations
	 */
	public void declarations() {
		if (lookAhead.equals(TokenType.VAR)) {
			match(TokenType.VAR);
			identifierList();
			match(TokenType.COLON);
			type();
			match(TokenType.SEMICOLON);
			try {
				currentScope.flushBuffer();
			} catch (Exception ex) {
				error(ex.getMessage());
			}
			declarations();		//recursion;
		}
	}

	/**
	 * eats a type
	 */
	public void type() {
		if (lookAhead.equals(TokenType.ARRAY)) {
			currentScope.set(IdentifierKind.ARR);
			match(TokenType.ARRAY);
			match(TokenType.LEFTSQUAREBRACKET);
			match(TokenType.NUM);
			match(TokenType.COLON);
			match(TokenType.NUM);
			match(TokenType.RIGHTSQUAREBRACKET);
			match(TokenType.OF);
			standardType();
		} else {
			currentScope.set(IdentifierKind.VAR);
			standardType();
		}
	}

	/**
	 * eats a standard type
	 */
	public void standardType() {
		if (lookAhead.equals(TokenType.INTEGER)) {
			match(TokenType.INTEGER);
			currentScope.set(TokenType.INTEGER);
		} else {
			match(TokenType.REAL);
			currentScope.set(TokenType.REAL);
		}
	}

	/**
	 * eats a subprogram declarations
	 */
	public void subprogramDeclarations() {
		if (lookAhead.equals(TokenType.FUNCTION) || lookAhead.equals(TokenType.PROCEDURE)) {
			subprogramDeclaration();
			if (lookAhead.equals(TokenType.SEMICOLON)) {
				match(TokenType.SEMICOLON);
			} else {
				subprogramDeclarations();	//recursion
			}
		}
	}

	/**
	 * eats a subprogram declaration
	 */
	public void subprogramDeclaration() {
		subprogramHead();
		declarations();
		subprogramDeclarations();
		compoundStatement();
	}

	/**
	 * eats a subprogram head
	 */
	public void subprogramHead() {
		currentScope.set(IdentifierKind.FUNC);
		if (lookAhead.equals(TokenType.FUNCTION)) {
			match(TokenType.FUNCTION);
			if (!currentScope.addId(match(TokenType.ID))) {
				error("invalid ID");
			}
			Scope lower = currentScope = new Scope(currentScope);
			arguments();
			match(TokenType.COLON);
			currentScope = lower.getParent();
			standardType();
			try {
				currentScope.flushBuffer();
			} catch (Exception ex) {
				error(ex.getMessage());
			}
			currentScope = lower;
			match(TokenType.SEMICOLON);
		} else {
			match(TokenType.PROCEDURE);
			if (!currentScope.addId(match(TokenType.ID))) {
				error("invalid ID");
			}
			currentScope = new Scope(currentScope);
			try {
				currentScope.getParent().flushBuffer();
			} catch (Exception ex) {
				error(ex.getMessage());
			}
			arguments();
			match(TokenType.SEMICOLON);
		}
	}

	/**
	 * eats an arguments
	 */
	public void arguments() {
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			parameterList();
			match(TokenType.RIGHTPARANTHESIS);
		}
	}

	/**
	 * eats a parameter list
	 */
	public void parameterList() {
		identifierList();
		match(TokenType.COLON);
		if (lookAhead.equals(TokenType.SEMICOLON)) {
			match(TokenType.SEMICOLON);
			parameterList();
		}
	}

	/**
	 * eats a compound statement
	 */
	public void compoundStatement() {
		match(TokenType.BEGIN);
		optionalStatements();
		match(TokenType.END);
	}

	/**
	 * eats an optional statement
	 */
	public void optionalStatements() {
		if (lookAhead.equals(TokenType.ID) || lookAhead.equals(TokenType.BEGIN) || lookAhead.equals(TokenType.IF) || lookAhead.equals(TokenType.WHILE)) {
			statementList();
		}
	}

	/**
	 * eats a statement list
	 */
	public void statementList() {
		statement();
		if (lookAhead.equals(TokenType.SEMICOLON)) {
			match(TokenType.SEMICOLON);
			statementList();
		}
	}

	/**
	 * eats a statement
	 */
	public void statement() {
		if (lookAhead.equals(TokenType.ID)) {
			//could also be prodedure statement
			variable();
			match(TokenType.COLONEQUALS);
			expression();
		} else if (lookAhead.equals(TokenType.BEGIN)) {
			compoundStatement();
		} else if (lookAhead.equals(TokenType.IF)) {
			match(TokenType.IF);
			expression();
			match(TokenType.THEN);
			statement();
			match(TokenType.ELSE);
			statement();
		} else /*if (lookAhead.equals(TokenType.WHILE))*/ {
			match(TokenType.WHILE);
			expression();
			match(TokenType.DO);
			statement();
		}
		//also need read and write
	}

	/**
	 * eats a variable
	 */
	public void variable() {
		match(TokenType.ID);
		if (lookAhead.equals(TokenType.LEFTSQUAREBRACKET)) {
			match(TokenType.LEFTSQUAREBRACKET);
			expression();
			match(TokenType.RIGHTSQUAREBRACKET);
		}
	}

	/**
	 * eats a procedure statement
	 */
	public void procedureStatement() {
		match(TokenType.ID);
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			expressionList();
			match(TokenType.RIGHTPARANTHESIS);
		}
	}

	/**
	 * eats an expression list
	 */
	public void expressionList() {
		expression();
		if (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			expressionList();
		}
	}

	/**
	 * eats an expression
	 */
	public void expression() {
		simpleExpression();
		if (relop()) {
			simpleExpression();
		}
	}

	/**
	 * eats a simple expression
	 */
	public void simpleExpression() {
		if (lookAhead.equals(TokenType.PLUS) || lookAhead.equals(TokenType.MINUS)) {
			sign();
		}
		term();
		simplePart();
	}

	/**
	 * eats a simple part
	 */
	public void simplePart() {
		if (addop()) {
			match(lookAhead.getType());
			term();
			simplePart();
		}
	}

	/**
	 * eats a term
	 */
	public void term() {
		factor();
		termPart();
	}

	/**
	 * eats a term part
	 */
	public void termPart() {
		if (mulop()) {
			match(lookAhead.getType());
			factor();
			termPart();
		}
	}

	/**
	 * eats a factor
	 */
	public void factor() {
		if (lookAhead.equals(TokenType.ID)) {
			match(TokenType.ID);
			if (lookAhead.equals(TokenType.LEFTSQUAREBRACKET)) {
				match(TokenType.LEFTSQUAREBRACKET);
				expression();
				match(TokenType.RIGHTSQUAREBRACKET);
			} else if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
				match(TokenType.LEFTPARANTHESIS);
				expressionList();
				match(TokenType.RIGHTPARANTHESIS);
			}
		} else if (lookAhead.equals(TokenType.NUM)) {
			match(TokenType.NUM);
		} else if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			expression();
			match(TokenType.RIGHTPARANTHESIS);
		} else {
			match(TokenType.NOT);
			factor();
		}
	}

	/**
	 * eats a sign
	 */
	public void sign() {
		if (lookAhead.equals(TokenType.PLUS) || lookAhead.equals(TokenType.MINUS)) {
			match(lookAhead.getType());
		}
	}

	/**
	 * checks if the next token is a relop: =, <>, <, <=, >=, >
	 *
	 * @return if the next token is a relop
	 */
	public boolean relop() {
		return lookAhead.equals(TokenType.EQUALS)
				|| lookAhead.equals(TokenType.DIAMOND)
				|| lookAhead.equals(TokenType.LESSTHAN)
				|| lookAhead.equals(TokenType.LESSTHANEQUALS)
				|| lookAhead.equals(TokenType.GREATERTHANEQUALS)
				|| lookAhead.equals(TokenType.GREATERTHAN);
	}

	/**
	 * checks if the next token is a mulop: *, /, DIV, MOD, AND
	 *
	 * @return if the next token is a mulop
	 */
	public boolean mulop() {
		return lookAhead.equals(TokenType.ASTERISK)
				|| lookAhead.equals(TokenType.FORWARDSLASH)
				|| lookAhead.equals(TokenType.DIV)
				|| lookAhead.equals(TokenType.MOD)
				|| lookAhead.equals(TokenType.AND);
	}

	/**
	 * checks if the next token is an addop: +, -, OR
	 *
	 * @return if the next token is an addop
	 */
	public boolean addop() {
		return lookAhead.equals(TokenType.PLUS)
				|| lookAhead.equals(TokenType.MINUS)
				|| lookAhead.equals(TokenType.OR);
	}
}
