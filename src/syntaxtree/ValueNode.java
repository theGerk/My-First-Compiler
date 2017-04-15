/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.TokenType;

/**
 * Represents a value or number literal in an expression.
 *
 * @author Erik Steinmetz
 */
public abstract class ValueNode extends ExpressionNode {	//TODO add folding

	/**
	 * Creates a ValueNode with the given attribute.
	 *
	 * @param type The type of node it is
	 */
	public ValueNode(TokenType type) {
		super(type);
	}

	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public boolean isFoldable() {
		return true;
	}
}
