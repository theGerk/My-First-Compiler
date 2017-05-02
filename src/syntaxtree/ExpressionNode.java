package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * General representation of any expression.
 *
 * @author Benji
 */
public abstract class ExpressionNode extends SyntaxTreeBase {

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

	/**
	 * All expressions return their result to wherever the current stack head
	 * pointer is pointing. They may alter registers freely with the exception
	 * of $sp and they will make use of the stack if they must invoke other
	 * expressions. They will never set a variable's value, although do mess
	 * with the stack head pointer, although that SHOULD be reset after use.
	 *
	 * @param symbolTable the scope
	 * @param indent formating prefex on lines
	 * @return MIPS assembly
	 */
	@Override
	protected abstract String toMips(Scope symbolTable, String indent);
}
