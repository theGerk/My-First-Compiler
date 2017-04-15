package syntaxtree;

import scanner.TokenType;

/**
 * General representation of any expression.
 *
 * @author erik
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

	/**
	 * Type the expression returns
	 */
	protected TokenType returnType;

	/**
	 * gets the expression's return type
	 *
	 * @return the return type of the expression
	 */
	public TokenType getType() {
		return returnType;
	}

	/**
	 * sets the output type of the expression
	 *
	 * @param returnType the expression return type
	 */
	ExpressionNode(TokenType returnType) {
		this.returnType = returnType;
	}

	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	public abstract LiteralNode fold();
}
