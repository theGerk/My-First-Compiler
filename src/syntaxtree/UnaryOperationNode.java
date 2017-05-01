/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.LookUp;
import scanner.TokenType;
import symboltable.Scope;

/**
 *
 * @author Benji
 */
public class UnaryOperationNode extends ExpressionNode {

	private final TokenType operator;
	private final ExpressionNode expression;

	/**
	 * constuctor
	 *
	 * @param op operation to be done
	 * @param expr expression for operation to be done on
	 * @throws Exception
	 */
	public UnaryOperationNode(TokenType op, ExpressionNode expr) throws Exception {
		super(outType(op, expr.getType()));
		operator = op;
		expression = expr;
	}

	private static TokenType outType(TokenType op, TokenType expr) throws Exception {
		Exception expt = new Exception(String.format(OUT_TYPE_EXCEPTION_FORMAT_STRING, LookUp.LOOKUP.teg(op), expr));
		switch (op) {
			//bitwise operations
			// int -> int
			case NOT:
				if (expr == TokenType.INTEGER) {
					return TokenType.INTEGER;
				} else {
					throw expt;
				}

			//aritmatic operations
			// int -> int
			// real -> real
			case PLUS:
			case MINUS:
				return expr;

			default:
				throw new Exception(LookUp.LOOKUP.teg(op) + " is not a valid unary operator");
		}
	}
	private static final String OUT_TYPE_EXCEPTION_FORMAT_STRING = "%s %s is not valid";

	@Override
	public String indentedToString(int level) {
		return indentation(level) + "UNARY OPERATION: " + operator.toString() + '\n' + expression.indentedToString(level + 1);
	}

	/**
	 * folds code, returns value node if possible, null otherwise
	 *
	 * @return the folded value, null if not possible
	 */
	@Override
	public LiteralNode fold() {
		LiteralNode input = expression.fold();
		if (input == null) {
			return null;
		} else {
			switch (input.getType()) {
				case REAL: {
					RealLiteralNode val = (RealLiteralNode) input;
					switch (operator) {
						case PLUS:
							return val;
						case MINUS:
							return new RealLiteralNode(-val.getValue());
					}
				}
				break;
				case INTEGER: {
					IntLiteralNode val = (IntLiteralNode) input;
					switch (operator) {
						case NOT:
							return new IntLiteralNode(~val.getValue());
						case PLUS:
							return val;
						case MINUS:
							return new IntLiteralNode(-val.getValue());
					}
				}
				break;
			}
		}
		return null;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		//check for folding
		LiteralNode fold = fold();
		if (fold != null) {
			return fold.toMips(symbolTable, indent);
		}

		StringBuilder build = new StringBuilder(indent).append("#UnaryOperationNode\n");

		//evaluate expression and load onto stack
		build.append(expression.toMips(symbolTable, indent + '\t'));

		//if a plus do nothing
		if (operator == TokenType.PLUS) {
			build.append(indent).append("#do nothing for +");
		} else {
			//put stack head ptr in t0
			build.append(indent).append("lw $t0, ($sp)\n");

			switch (expression.getType()) {
				case REAL:	//can only be + or - and + is already dealt with so must be a- operation
					build.append(indent).append("lwc1 $f0, ($t0)\t#put operand into f0\n");
					build.append(indent).append("neg.s $f0, $f0\t#make negation\n");
					build.append(indent).append("swc1 $f0, ($t0)\t#put return value on stack\n");
					break;
				case INTEGER:
					build.append("lw $t1, ($t0)\t#put operand into t1\n");
					switch (operator) {
						case NOT:
							build.append("not $t1, $t1\t#make bitwise negation\n");
							break;
						case MINUS:
							build.append("neg $t1, $t1\t#make arithmatic negation\n");
							break;
					}
					build.append("sw $t1, ($t0)\t#put return value on stack");
			}
		}
		return build.toString();
	}
}
