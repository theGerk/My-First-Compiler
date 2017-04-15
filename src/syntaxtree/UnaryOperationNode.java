/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.LookUp;
import scanner.TokenType;

/**
 *
 * @author Benji
 */
public class UnaryOperationNode extends ExpressionNode {

	private final TokenType operator;
	private final ExpressionNode expression;

	public UnaryOperationNode(TokenType op, ExpressionNode expr) throws Exception {
		super(outType(op, expr.getType()));
		operator = op;
		expression = expr;
	}

	private static TokenType outType(TokenType op, TokenType expr) throws Exception {
		Exception expt = new Exception(String.format(OUT_TYPE_EXCEPTION_FORMATE_STRING, LookUp.LOOKUP.teg(op), expr));
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
	private static final String OUT_TYPE_EXCEPTION_FORMATE_STRING = "%s %s is not valid";

	@Override
	public String indentedToString(int level) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public boolean isFoldable() {
		return expression.isFoldable();
	}
}
