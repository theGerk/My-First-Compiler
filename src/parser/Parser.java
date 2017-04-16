/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import javafx.util.Pair;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.Scope;
import symboltable.Scope.IdentifierKind;
import syntaxtree.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
			error("Could not getValue next token");
		}
	}

	/**
	 * Matches against an expected token, throws error if token is not correct,
	 * and eats it and moves on if token is correct.
	 *
	 * @param expected
	 *
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
				error("cannot getValue next token");
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
			match(TokenType.PROGRAM);
			String id = match(TokenType.ID);	//TODO maybe add symbol table reference
			match(TokenType.SEMICOLON);
			DeclarationsNode declarations = declarations();
			SubProgramDeclarationsNode subprogramDeclarations = subprogramDeclarations();
			CompoundStatementNode compoundStatement = compoundStatement();
			match(TokenType.PERIOD);
			return new ProgramNode(id, declarations, subprogramDeclarations, compoundStatement);
		} catch (Exception ex) {
			error(ex.getMessage());
			return null;
		}
	}

	/**
	 * Adds list of IDs to symbol table
	 *
	 * @return list of identifier strings
	 * @throws java.lang.Exception error message
	 */
	private ArrayList<String> identifierList() throws Exception {
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
	 * @throws java.lang.Exception error message
	 */
	private DeclarationsNode declarations() throws Exception {
		DeclarationsNode output = new DeclarationsNode();
		while (lookAhead.equals(TokenType.VAR)) {
			match(TokenType.VAR);
			ArrayList<String> identifierList = identifierList();
			match(TokenType.COLON);
			TypeReturn type = type();
			for (String identifier : identifierList) {
				type.set(identifier);
				output.addVariable(identifier);
			}
			match(TokenType.SEMICOLON);
		}
		return output;
	}

	private class TypeReturn {

		public void set(String id) throws Exception {
			currentScope.set(id, kind);
			currentScope.set(id, type);
			if (kind == IdentifierKind.ARR) {
				currentScope.set(id, start, end);
			}
		}

		public TypeReturn(TokenType type) {
			this.type = type;
			kind = IdentifierKind.VAR;
			start = end = 0;
		}

		public TypeReturn(TokenType type, int start, int end) {
			this.type = type;
			this.kind = IdentifierKind.ARR;
			this.start = start;
			this.end = end;
		}

		final TokenType type;
		final IdentifierKind kind;

		//only used in array
		final int start;
		final int end;
	}

	/**
	 * sets type (and kind if array) for currentScope buffer.
	 *
	 * @return
	 */
	private TypeReturn type() {
		if (lookAhead.equals(TokenType.ARRAY)) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTSQUAREBRACKET);
			int start = Integer.parseInt(match(TokenType.INT_LITERAL));
			match(TokenType.COLON);
			int end = Integer.parseInt(match(TokenType.INT_LITERAL));
			match(TokenType.RIGHTSQUAREBRACKET);
			match(TokenType.OF);
			return new TypeReturn(standardType(), start, end);
		} else {
			return new TypeReturn(standardType());
		}
	}

	/**
	 * parses out a type
	 *
	 * @return Type of something
	 */
	private TokenType standardType() {
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
	private SubProgramDeclarationsNode subprogramDeclarations() throws Exception {
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
	private SubProgramNode subprogramDeclaration() throws Exception {
		SubProgramHeadNode head = subprogramHead(); //enters child scope in here
		DeclarationsNode variables = declarations();
		SubProgramDeclarationsNode subFunctions = subprogramDeclarations();
		CompoundStatementNode instructions = compoundStatement();
		currentScope = currentScope.getParent();
		return new SubProgramNode(head, variables, subFunctions, instructions);
	}

	/**
	 * parses a function or procedure signature, creates new scope for it,
	 * enters scope, and adds itself to parent scope.
	 *
	 * @return node with information about the function
	 */
	private SubProgramHeadNode subprogramHead() throws Exception {
		if (lookAhead.equals(TokenType.FUNCTION)) {
			//Match function
			match(TokenType.FUNCTION);

			//match ID
			String name = match(TokenType.ID);
			currentScope.put(name);
			currentScope.set(name, IdentifierKind.FUNC);
			currentScope = new Scope(currentScope, name);	//creates new scope and enters
			currentScope.getParent().set(name, currentScope);

			//match arguments
			ArrayList<String> arguments = arguments();
			for (String var : arguments) {
				currentScope.getParent().addArg(name, var, currentScope.getType(var));
			}

			match(TokenType.COLON);

			//match type
			TokenType type = standardType();
			currentScope.getParent().set(name, type);

			match(TokenType.SEMICOLON);
			return new SubProgramHeadNode(name, arguments);
		} else {

			match(TokenType.PROCEDURE);

			//match id
			String name = match(TokenType.ID);
			currentScope.put(name);
			currentScope.set(name, IdentifierKind.FUNC);
			currentScope = new Scope(currentScope, name);
			currentScope.getParent().set(name, currentScope);

			//match arguments
			ArrayList<String> arguments = arguments();
			for (String var : arguments) {
				currentScope.getParent().addArg(name, var, currentScope.getType(var));
			}

			match(TokenType.SEMICOLON);
			return new SubProgramHeadNode(name, arguments);
		}
	}

	/**
	 * parses arguments for procedure or function
	 *
	 * @return
	 */
	private ArrayList<String> arguments() throws Exception {
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			ArrayList<String> output = parameterList();
			match(TokenType.RIGHTPARANTHESIS);
			return output;
		}
		return new ArrayList<String>();
	}

	/**
	 * parses parameters for procedure or function
	 *
	 * @return parameter list
	 */
	private ArrayList<String> parameterList() throws Exception {
		while (true) {
			ArrayList<String> ids = identifierList();
			match(TokenType.COLON);
			TypeReturn typeInfo = type();
			for (String id : ids) {
				typeInfo.set(id);
			}
			if (!lookAhead.equals(TokenType.SEMICOLON)) {
				return ids;
			}
			match(TokenType.SEMICOLON);
		}
	}

	/**
	 * eats a compound statement
	 */
	private CompoundStatementNode compoundStatement() throws Exception {
		match(TokenType.BEGIN);
		CompoundStatementNode output = optionalStatements();
		match(TokenType.END);
		return output;
	}

	/**
	 * eats an optional statements
	 */
	private CompoundStatementNode optionalStatements() throws Exception {
		if (lookAhead.equals(TokenType.ID) || lookAhead.equals(TokenType.BEGIN) || lookAhead.equals(TokenType.IF) || lookAhead.equals(TokenType.WHILE)) {
			return statementList();
		}
		return new CompoundStatementNode();
	}

	/**
	 * eats a statement list
	 */
	private CompoundStatementNode statementList() throws Exception {
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
	private StatementNode statement() throws Exception {
		switch (lookAhead.getType()) {
			case ID:
				switch (currentScope.getKind(lookAhead.getLexeme())) {
					case VAR:
					case ARR: {
						VariableNode var = variable();
						match(TokenType.ASSIGNOP);
						ExpressionNode expr = expression();
						return new VariableAssignmentStatementNode(var, expr);
					}
					case FUNC: {
						return procedureStatement();
					}
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
				VariableNode variable = variable(); //breaks with tradition of following Stienmetz code, but will allow us to read directly into an index in an array.	//TODO getValue permission
				match(TokenType.RIGHTPARANTHESIS);
				return new VariableAssignmentStatementNode(variable, new ConsoleReadNode(variable.getType()));
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
	private VariableNode variable() throws Exception {
		VariableNode output;
		String id = match(TokenType.ID);
		switch (currentScope.getKind(id)) {
			case VAR:
				output = new VariableNode(id, currentScope);
				break;
			case ARR:
				match(TokenType.LEFTSQUAREBRACKET);
				output = new ArrayVarNode(id, expression(), currentScope);
				match(TokenType.RIGHTSQUAREBRACKET);
				break;
			default:
				throw new Exception("Invalid id");
		}
		return output;
	}

	/**
	 * determines if it is a procedure statement or if it is a function
	 * assignment statement, then returns accordingly
	 */
	private StatementNode procedureStatement() throws Exception {
		ArrayList<ExpressionNode> parameters = new ArrayList<>();
		String id = match(TokenType.ID);
		if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
			match(TokenType.LEFTPARANTHESIS);
			parameters = expressionList();
			match(TokenType.RIGHTPARANTHESIS);
		} else if (lookAhead.equals(TokenType.ASSIGNOP) && currentScope.getType(id) != null) { //TODO document this
			ExpressionNode expression = expression();
			return new FunctionAssignmentStatementNode(id, expression, currentScope);
		}
		return new ProcedureStatementNode(id, parameters, currentScope);
	}

	/**
	 * eats an expression list
	 */
	private ArrayList<ExpressionNode> expressionList() throws Exception {
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
	private ExpressionNode expression() throws Exception {
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
	private ExpressionNode simpleExpression() throws Exception {
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
	private Pair<TokenType, ExpressionNode> simplePart() throws Exception {
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
	private ExpressionNode term() throws Exception {
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
	private Pair<TokenType, ExpressionNode> termPart() throws Exception {
		if (mulop()) {
			TokenType type = lookAhead.getType();
			match(type);
			ExpressionNode expr = factor();
			Pair<TokenType, ExpressionNode> tokenTypeExpressionNodePair = termPart();
			if (tokenTypeExpressionNodePair != null) {
				expr = new BinaryOperationNode(expr, tokenTypeExpressionNodePair.getKey(), tokenTypeExpressionNodePair.getValue());
			}
			return new Pair<>(type, expr);
		} else {
			return null;
		}
	}

	/**
	 * returns a factor
	 */
	private ExpressionNode factor() throws Exception {
		switch (lookAhead.getType()) {
			case ID: {
				String id = match(TokenType.ID);
				if (lookAhead.equals(TokenType.LEFTSQUAREBRACKET)) {
					match(TokenType.LEFTSQUAREBRACKET);
					ExpressionNode expression = expression();
					match(TokenType.RIGHTSQUAREBRACKET);
					return new ArrayVarNode(id, expression, currentScope);
				} else if (lookAhead.equals(TokenType.LEFTPARANTHESIS)) {
					match(TokenType.LEFTPARANTHESIS);
					ArrayList<ExpressionNode> expressionList = expressionList();
					match(TokenType.RIGHTPARANTHESIS);
					return new FunctionExpressionNode(id, expressionList, currentScope);
				} else {
					return new VariableNode(id, currentScope);
				}
			}
			case REAL_LITERAL:
				return new RealLiteralNode(Float.parseFloat(match(TokenType.REAL_LITERAL)));
			case INT_LITERAL:
				return new IntLiteralNode(Integer.parseInt(match(TokenType.INT_LITERAL)));
			case LEFTPARANTHESIS: {
				match(TokenType.LEFTPARANTHESIS);
				ExpressionNode expression = expression();
				match(TokenType.RIGHTPARANTHESIS);
				return expression;
			}
			case NOT: {
				match(TokenType.NOT);
				ExpressionNode factor = factor();
				return new UnaryOperationNode(TokenType.NOT, factor);
			}
			default:
				throw new Exception("invalid factor");
		}
	}

	/**
	 * eats a sign
	 */
	private TokenType sign() throws Exception {
		if (lookAhead.equals(TokenType.PLUS) || lookAhead.equals(TokenType.MINUS)) {
			TokenType type = lookAhead.getType();
			match(type);
			return type;
		} else {
			throw new Exception("not even sure how this happened, you should have had a sign instead of " + lookAhead.getLexeme());
		}
	}

	/**
	 * checks if the next token is a relop: =, <>, <, <=, >=, >
	 *
	 * @return if the next token is a relop
	 */
	private boolean relop() {
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
	private boolean mulop() {
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
	private boolean addop() {
		return lookAhead.equals(TokenType.PLUS)
				|| lookAhead.equals(TokenType.MINUS)
				|| lookAhead.equals(TokenType.OR);
	}
}
