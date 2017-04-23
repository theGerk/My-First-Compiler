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
public abstract class LiteralNode extends ExpressionNode {

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

	/**
	 * gets a raw integer representation of the node's value
	 *
	 * @return raw integer representation
	 */
	abstract int getRawInt();

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#LiteralNode\n");
		build.append(indent).append("lw $t0, ($sp)\t#put stack head pointer in t0\n");
		build.append(indent).append("li $t1, ").append(getRawInt()).append("\t#The actual literal value\n");
		build.append(indent).append("sw $t1, ($t0)\t#put value on stack\n");

		return build.toString();
	}

	/**
	 * constructs integer lteral node
	 *
	 * @param val int literal value
	 * @return IntLiteralNode
	 */
	public static LiteralNode make(int val) {
		return new IntLiteralNode(val);
	}

	/**
	 * constructs floating point literal node
	 *
	 * @param val real literal value
	 * @return RealLiteralNode
	 */
	public static LiteralNode make(float val) {
		return new RealLiteralNode(val);
	}

	/**
	 * constructs boolean literal node (just an integer)
	 *
	 * @param val boolean value
	 * @return IntLiteralNode
	 */
	public static LiteralNode make(boolean val) {
		return new IntLiteralNode(val);
	}
}
