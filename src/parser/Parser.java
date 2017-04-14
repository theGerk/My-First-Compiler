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
		try {
			ProgramNode output;
			match(TokenType.PROGRAM);
			output = new ProgramNode(match(TokenType.ID));	//TODO maybe add symbol table reference
			match(TokenType.SEMICOLON);
			output.setVariables(declarations());
			output.setFunctions(subprogramDeclarations());
			output.setMain(compoundStatement());
			match(TokenType.PERIOD);
			return output;
		} catch (Exception ex) {
			error(ex.getMessage());
			return null;
		}
	}

	/**
	 * Adds list of IDs to symbol table
	 *
	 * @return list of identifier strings
	 */
	public ArrayList<String> identifierList() throws Exception {
		ArrayList<String> output = new ArrayList<>();
		String id = match(TokenType.ID);
		currentScope.put(id);
		output.add(id);
		while (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			id = match(TokenType.ID);
			currentScope.put(id);
			output.add(id);
		}
		return output;
	}

	/**
	 * Adds declared variables to currentScope.
	 *
	 * @return Declarations node
	 */
	public DeclarationsNode declarations() throws Exception {
		DeclarationsNode output = new DeclarationsNode();
		while (lookAhead.equals(TokenType.VAR)) {
			match(TokenType.VAR);
			ArrayList<String> identifierList = identifierList();
			match(TokenType.COLON);
			Pair<TokenType, IdentifierKind> info = type();
			for (String identifier : identifierList) {
				currentScope.set(identifier, info.getKey());
				currentScope.set(identifier, info.getValue());
				output.addVariable(new VariableNode(identifier, currentScope.getType(identifier)));
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
	public SubProgramDeclarationsNode subprogramDeclarations() throws Exception {
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
	public SubProgramNode subprogramDeclaration() throws Exception {
		SubProgramNode output = subprogramHead(); //enters child scope in here
		output.variables = declarations();
		output.subFunctions = subprogramDeclarations();
		output.instructions = compoundStatement();
		currentScope = currentScope.getParent();
		return output;
	}

	/**
	 * parses a function or procedure signature, creates new scope for it,
	 * enters scope, and adds itself to parent scope.
	 *
	 * @return node with information about the function
	 */
	public SubProgramNode subprogramHead() throws Exception {
		SubProgramNode output;
		if (lookAhead.equals(TokenType.FUNCTION)) {
			//set up function
			match(TokenType.FUNCTION);

			//add name to symbol table
			output = new SubProgramNode(match(TokenType.ID));
			currentScope.put(output.name);
			currentScope.set(output.name, IdentifierKind.FUNC);
			currentScope = new Scope(currentScope);	//creates new scope and enters

			//set arguments in syntax tree
			output.arguments = arguments();

			//set arguments in symbol table
			for (VariableNode var : output.arguments.getVars()) {
				currentScope.getParent().addArg(output.name, var.getName(), var.getType());
			}

			match(TokenType.COLON);

			//set type in symbol table
			currentScope.getParent().set(output.name, standardType());
			match(TokenType.SEMICOLON);
		} else {

			match(TokenType.PROCEDURE);

			//add name to symbol table
			output = new SubProgramNode(match(TokenType.ID));
			currentScope.put(output.name);
			//add kind to symbol table
			currentScope.set(output.name, IdentifierKind.FUNC);

			currentScope = new Scope(currentScope);
			output.arguments = arguments();

			//add arguments to symbol table
			for (VariableNode var : output.arguments.getVars()) {
				currentScope.getParent().addArg(output.name, var.getName(), var.getType());
			}

			match(TokenType.SEMICOLON);
		}
		return output;
	}

	/**
	 * parses arguments for procedure or function
	 *
	 * @return
	 */
	public DeclarationsNode arguments() throws Exception {
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			DeclarationsNode output = parameterList();
			match(TokenType.RIGHTPARANTHESIS);
			return output;
		}
		return new DeclarationsNode();
	}

	/**
	 * parses parameters for procedure or function
	 *
	 * @return parameter list
	 */
	public DeclarationsNode parameterList() throws Exception {		//TODO check to make sure the grammer is right here
		DeclarationsNode output = new DeclarationsNode();
		while (true) {
			ArrayList<String> ids = identifierList();
			match(TokenType.COLON);
			Pair<TokenType, IdentifierKind> info = type();
			for (String id : ids) {
				output.addVariable(new VariableNode(id, info.getKey()));
				currentScope.set(id, info.getKey());
				currentScope.set(id, info.getValue());
			}
			if (!lookAhead.equals(TokenType.SEMICOLON)) {
				return output;
			}
			match(TokenType.SEMICOLON);
		}
	}

	/**
	 * eats a compound statement
	 */
	public CompoundStatementNode compoundStatement() throws Exception {
		match(TokenType.BEGIN);
		CompoundStatementNode output = optionalStatements();
		match(TokenType.END);
		return output;
	}

	/**
	 * eats an optional statements
	 */
	public CompoundStatementNode optionalStatements() throws Exception {
		if (lookAhead.equals(TokenType.ID) || lookAhead.equals(TokenType.BEGIN) || lookAhead.equals(TokenType.IF) || lookAhead.equals(TokenType.WHILE)) {	//TODO double check grammer here
			return statementList();
		}
		return new CompoundStatementNode();
	}

	/**
	 * eats a statement list
	 */
	public CompoundStatementNode statementList() throws Exception {
		CompoundStatementNode output = new CompoundStatementNode();
		output.addStatement(statement());
		while (lookAhead.equals(TokenType.SEMICOLON)) {
			match(TokenType.SEMICOLON);
			output.addStatement(statement());
		}
		return output;
	}

	/**
	 * eats a statement
	 */
	public StatementNode statement() throws Exception {
		switch (lookAhead.getType()) {
			case ID:
				switch (currentScope.getKind(lookAhead.getLexeme())) {
					case VAR:
					case ARR:
						VariableNode var = variable();
						match(TokenType.ASSIGNOP);
						ExpressionNode expr = expression();
						return new AssignmentStatementNode(var, expr);
					case FUNC:
						return procedureStatement();
					default:
						throw new Exception("You done goofed up");
				}
			case BEGIN:
				return compoundStatement();
			case IF:
				match(TokenType.IF);
				ExpressionNode condition = expression();
				match(TokenType.THEN);
				StatementNode onTrue = statement();
				match(TokenType.ELSE);
				StatementNode onFalse = statement();
				return new IfStatementNode(condition, onTrue, onFalse);
			case WHILE:
				match(TokenType.WHILE);
				condition = expression();
				match(TokenType.DO);
				StatementNode execute = statement();
				return new WhileLoopNode(condition, execute);
			case READ:
				match(TokenType.READ);
				match(TokenType.LEFTPARANTHESIS);
				VariableNode variable = variable(); //breaks with tradition of following Stienmetz code, but will allow us to read directly into an index in an array.	//TODO get permision
				match(TokenType.RIGHTPARANTHESIS);
				return new AssignmentStatementNode(variable, new ConsoleReadNode(variable.getType()));
			case WRITE:
				match(TokenType.WRITE);
				match(TokenType.LEFTPARANTHESIS);
				ExpressionNode expression = expression();
				match(TokenType.RIGHTPARANTHESIS);
				return new ConsoleWriteNode(expression);
			default:
				throw new Exception("You done goofed up");
		}
	}

	/**
	 * parses a variable, possibly one in an array
	 */
	public VariableNode variable() throws Exception {
		VariableNode output;
		String id = match(TokenType.ID);
		switch (currentScope.getKind(id)) {
			case VAR:
				output = new VariableNode(id, currentScope.getType(id));
				break;
			case ARR:
				match(TokenType.LEFTSQUAREBRACKET);
				output = new ArrayVarNode(id, expression(), currentScope.getType(id));
				match(TokenType.RIGHTSQUAREBRACKET);
				break;
			default:
				throw new Exception("Invalid id");
		}
		return output;
	}

	/**
	 * eats a procedure statement
	 */
	public ProcedureStatementNode procedureStatement() throws Exception {
		ArrayList<ExpressionNode> parameters = new ArrayList<>();
		String id = match(TokenType.ID);
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			parameters = expressionList();
			match(TokenType.RIGHTPARANTHESIS);
		}
		return new ProcedureStatementNode(id, parameters, currentScope);
	}

	/**
	 * eats an expression list
	 */
	public ArrayList<ExpressionNode> expressionList() throws Exception {
		ArrayList<ExpressionNode> output = new ArrayList<>();
		output.add(expression());
		while (lookAhead.equals(TokenType.COMMA)) {
			match(TokenType.COMMA);
			output.add(expression());
		}
		return output;
	}

	/**
	 * eats an expression
	 */
	public ExpressionNode expression() throws Exception {
		ExpressionNode firstPart = simpleExpression();
		if (relop()) {
			TokenType operator = lookAhead.getType();
			match(operator);
			ExpressionNode secondPart = simpleExpression();
			return new BinaryOperationNode(firstPart, operator, secondPart);
		} else {
			return firstPart;
		}
	}

	/**
	 * eats a simple expression
	 */
	public ExpressionNode simpleExpression() throws Exception {
		ExpressionNode output;
		if (lookAhead.equals(TokenType.PLUS) || lookAhead.equals(TokenType.MINUS)) {
			TokenType sign = sign();
			output = term();
			output = new UnaryOperationNode(sign, output);
		} else {
			output = term();
		}
		Pair<TokenType, ExpressionNode> simplePart = simplePart();
		if (simplePart != null) {
			output = new BinaryOperationNode(output, simplePart.getKey(), simplePart.getValue());
		}
		return output;
	}

	/**
	 * May return null, need to check for that
	 */
	public Pair<TokenType, ExpressionNode> simplePart() throws Exception {
		if (addop()) {
			TokenType type = lookAhead.getType();
			match(type);
			ExpressionNode expr = term();
			Pair<TokenType, ExpressionNode> tokenTypeExpressionNodePair = simplePart();
			if (tokenTypeExpressionNodePair != null) {
				expr = new BinaryOperationNode(expr, tokenTypeExpressionNodePair.getKey(), tokenTypeExpressionNodePair.getValue());
			}
			return new Pair<>(type, expr);
		} else {
			return null;
		}
	}

	/**
	 * eats a term
	 */
	public ExpressionNode term() throws Exception {
		ExpressionNode output = factor();
		Pair<TokenType, ExpressionNode> tokenTypeExpressionNodePair = termPart();
		if (tokenTypeExpressionNodePair != null) {
			output = new BinaryOperationNode(output, tokenTypeExpressionNodePair.getKey(), tokenTypeExpressionNodePair.getValue());
		}
		return output;
	}

	/**
	 * May return null, need to check for that
	 */
	public Pair<TokenType, ExpressionNode> termPart() throws Exception {
		if (mulop()) {
			TokenType type = lookAhead.getType();
			match(type);
			ExpressionNode expr = factor();
			Pair<TokenType, ExpressionNode> tokenTypeExpressionNodePair = termPart();
			if(tokenTypeExpressionNodePair!=null)
				expr = new BinaryOperationNode(expr, tokenTypeExpressionNodePair.getKey(), tokenTypeExpressionNodePair.getValue());
			return new Pair<>(type,expr);
		} else {
			return null;
		}
	}

	/**
	 * returns a factor
	 */
	public ExpressionNode factor() throws Exception {
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
				throw new Exception("invalid factor");
		}
	}

	/**
	 * eats a sign
	 */
	public TokenType sign() throws Exception {
		if (lookAhead.equals(TokenType.PLUS) || lookAhead.equals(TokenType.MINUS)) {
			TokenType type = lookAhead.getType();
			match(type);
			return type;
		} else {
			throw new Exception("you done fucked boi");
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
