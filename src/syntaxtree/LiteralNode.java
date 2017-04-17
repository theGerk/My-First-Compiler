/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents a value or number literal in an expression.
 *
 * @author Erik Steinmetz
 */
public abstract class LiteralNode extends ExpressionNode {	//TODO code gen?

	/**
	 * Creates a LiteralNode with the given attribute.
	 *
	 * @param type The type of node it is
	 */
	public LiteralNode(TokenType type) {
		super(type);
	}

	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public LiteralNode fold() {
		return this;
	}

	abstract int getRawInt();

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		return indent + "#LiteralNode"
				+ indent + "lw $t0, ($sp)\n"
				+ indent + "li $t1, " + getRawInt() + "\n"
				+ indent + "sw $t1, ($t0)\n";
	}
}
