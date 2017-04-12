/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import symboltable.Scope;
import symboltable.Scope.IdentifierKind;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import syntaxtree.*;

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
	private void error(String err) {
		System.out.println(err);
		System.exit(1);
	}

	private Token lookAhead;		//next token
	private Scanner scanner;
	private Scope currentScope = new Scope();

	/**
	 * sets up parser class from file
	 *
	 * @param filename
	 */
	public Parser(String filename) {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(filename);
		} catch (FileNotFoundException ex) {
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
	 * @return the string that was eaten
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

	/**
	 * Parses a program into a ProgramNode
	 *
	 * @return Program node with all useful information
	 */
	public ProgramNode program() {
		ProgramNode output;
		match(TokenType.PROGRAM);
		output = new ProgramNode(match(TokenType.ID));	//TODO maybe add symbol table reference
		match(TokenType.SEMICOLON);
		output.getVariables(declarations());
		output.setFunctions(subprogramDeclarations());
		output.getMain(compoundStatement());
		match(TokenType.PERIOD);
		return output;
	}

	/**
	 * Adds list of IDs to symbol table
	 *
	 * @return list of identifier strings
	 */
	public ArrayList<String> identifierList() {
		ArrayList<String> output = new ArrayList<>();
		String id = match(TokenType.ID);
		try {
			currentScope.put(id);
			output.add(id);
		} catch (Exception ex) {
			error("used ID");
		}
		while (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			id = match(TokenType.ID);
			try {
				currentScope.put(id);
				output.add(id);
			} catch (Exception ex) {
				error(id + " already in use");
			}
		}
		return output;
	}

	/**
	 * Adds declared variables to currentScope.
	 *
	 * @return Declarations node
	 */
	public DeclarationsNode declarations() {
		DeclarationsNode output = new DeclarationsNode();
		while (lookAhead.equals(TokenType.VAR)) {
			match(TokenType.VAR);
			ArrayList<String> identifierList = identifierList();
			match(TokenType.COLON);
			Pair<TokenType, IdentifierKind> info = type();
			for (String identifier : identifierList) {
				try {
					currentScope.set(identifier, info.getKey());
				} catch (Exception ex) {
					error(identifier + " type already set");
				}
				try {
					currentScope.set(identifier, info.getValue());
				} catch (Exception ex) {
					error(identifier + " info already set");
				}
				output.addVariable(new VariableNode(identifier));
			}
			match(TokenType.SEMICOLON);
		}
		return output;
	}

	/**
	 * sets type (and kind if array) for currentScope buffer.
	 *
	 * @return
	 */
	public Pair<TokenType, IdentifierKind> type() {
		if (lookAhead.equals(TokenType.ARRAY)) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTSQUAREBRACKET);
			match(TokenType.NUM);
			match(TokenType.COLON);
			match(TokenType.NUM);
			match(TokenType.RIGHTSQUAREBRACKET);
			match(TokenType.OF);
			return new Pair<>(standardType(), IdentifierKind.ARR);
		} else {
			return new Pair<>(standardType(), IdentifierKind.VAR);
		}
	}

	/**
	 * parses out a type
	 *
	 * @return Type of something
	 */
	public TokenType standardType() {
		if (lookAhead.equals(TokenType.INTEGER)) {
			match(TokenType.INTEGER);
			return TokenType.INTEGER;
		} else {
			match(TokenType.REAL);
			return TokenType.REAL;
		}
	}

	/**
	 * parses functions and procedures
	 *
	 * @return Node
	 */
	public SubProgramDeclarationsNode subprogramDeclarations() {
		SubProgramDeclarationsNode output = new SubProgramDeclarationsNode();
		while (lookAhead.equals(TokenType.FUNCTION) || lookAhead.equals(TokenType.PROCEDURE)) {
			output.addSubProgramDeclaration(subprogramDeclaration());
			match(TokenType.SEMICOLON);
		}
		return output;
	}

	/**
	 * parses a single function or procedure
	 *
	 * @return Node with function's information
	 */
	public SubProgramNode subprogramDeclaration() {
		SubProgramNode output = subprogramHead(); //enters child scope in here
		output.variables = declarations();
		output.subFunctions = subprogramDeclarations();
		output.instructions = compoundStatement();
		currentScope = currentScope.getParent();
	}

	/**
	 * parses a function or procedure signature, creates new scope for it,
	 * enters scope, and adds itself to parent scope.
	 *
	 * @return node with information about the function
	 */
	public SubProgramNode subprogramHead() {
		SubProgramNode output;
		if (lookAhead.equals(TokenType.FUNCTION)) {
			match(TokenType.FUNCTION);
			output = new SubProgramNode(match(TokenType.ID));
			try {
				currentScope.put(output.name);
			} catch (Exception ex) {
				error(output.name + ": invalid ID");
			}
			try {
				currentScope.set(output.name, IdentifierKind.FUNC);
			} catch (Exception ex) {
				error(output.name + ": already has kind");
			}
			currentScope = new Scope(currentScope);	//creates new scope and enters
			output.arguments = arguments();
			match(TokenType.COLON);
			try {
				currentScope.getParent().set(output.name, standardType());
			} catch (Exception ex) {
				error(output.name + ": already has type set");
			}
			match(TokenType.SEMICOLON);
		} else {
			match(TokenType.PROCEDURE);
			output = new SubProgramNode(match(TokenType.ID));
			try {
				currentScope.put(output.name);
			} catch (Exception ex) {
				error(output.name + ": invalid ID");
			}
			try {
				currentScope.set(output.name, IdentifierKind.FUNC);
			} catch (Exception ex) {
				error(": already has kind set");
			}
			currentScope = new Scope(currentScope);
			output.arguments = arguments();
			match(TokenType.SEMICOLON);
		}
		return output;
	}

	/**
	 * parses arguments for procedure or function
	 */
	public DeclarationsNode arguments() {
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			parameterList();
			match(TokenType.RIGHTPARANTHESIS);
		}
	}

	/**
	 * parses parameters for procedure or function
	 */
	public void parameterList() {
		identifierList();
		match(TokenType.COLON);
		type();
		try {
			currentScope.flushBuffer();				//flushes currentScope buffer
		} catch (Exception ex) {
			error("wouldn't happen, would it?");
		}
		if (lookAhead.equals(TokenType.SEMICOLON)) {
			match(TokenType.SEMICOLON);
			parameterList();	//recursion
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
	 * eats an optional statements
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
		switch (lookAhead.getType()) {
			case ID:
				switch (currentScope.getKind(lookAhead.getLexeme())) {
					case VAR:
					case ARR:
						variable();
						match(TokenType.ASSIGNOP);
						expression();
						break;
					case FUNC:
						procedureStatement();
						break;
					default:
						error("You done goofed");
				}
				break;
			case BEGIN:
				compoundStatement();
				break;
			case IF:
				match(TokenType.IF);
				expression();
				match(TokenType.THEN);
				statement();
				match(TokenType.ELSE);
				statement();
				break;
			case WHILE:
				match(TokenType.WHILE);
				expression();
				match(TokenType.DO);
				statement();
				break;
			case READ:
				match(TokenType.READ);
				match(TokenType.LEFTPARANTHESIS);
				variable();		//breaks with tradition of following Stienmetz code, but will allow us to read directly into an index in an array.

//				if (currentScope.getKind(lookAhead.getLexeme()) == Scope.IdentifierKind.VAR) {
//					match(TokenType.ID);		//TODO check it is a valid type
//				} else {
//					error("can only read variables");
//				}
				match(TokenType.RIGHTPARANTHESIS);
				break;
			case WRITE:
				match(TokenType.WRITE);
				match(TokenType.LEFTPARANTHESIS);
				expression();
				match(TokenType.RIGHTPARANTHESIS);
				break;
			default:
				error("You done goofed");
		}
	}

	/**
	 * parses a variable, possibly one in an array, confirms that the Identifier
	 * is of an array or variable
	 */
	public void variable() {
		switch (currentScope.getKind(match(TokenType.ID))) {
			case VAR:
				break;
			case ARR:
				match(TokenType.LEFTSQUAREBRACKET);
				expression();
				match(TokenType.RIGHTSQUAREBRACKET);
				break;
			default:
				error("Invalid ID");
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
			match(lookAhead.getType());
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
		switch (lookAhead.getType()) {
			case ID:
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
				break;
			case NUM:
				match(TokenType.NUM);
				break;
			case LEFTPARANTHESIS:
				match(TokenType.LEFTPARANTHESIS);
				expression();
				match(TokenType.RIGHTPARANTHESIS);
				break;
			case NOT:
				match(TokenType.NOT);
				factor();
				break;
			default:
				error("invalid factor");
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
