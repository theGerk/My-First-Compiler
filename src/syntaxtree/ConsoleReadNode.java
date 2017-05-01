/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 *
 * @author Benji
 */
public class ConsoleReadNode extends ExpressionNode {

	/**
	 * node puts value from console onto stack
	 *
	 * @param returnType type to be read from console
	 */
	public ConsoleReadNode(TokenType returnType) {
		super(returnType);
	}

	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public LiteralNode fold() {
		return null;
	}

	@Override
	public String indentedToString(int level) {
		return indentation(level) + "read " + returnType + " from console\n";
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		switch (returnType) {
			case REAL:
				return indent + "#ConsoleReadNode\n"
						+ indent + "lw $t0, ($sp)\n"
						+ indent + "li $v0, 6\n"
						+ indent + "syscall\n"
						+ indent + "swc1 $f0, ($t0)\n";
			case INTEGER:
				return indent + "#ConsoleReadNode\n"
						+ indent + "lw $t0, ($sp)\n"
						+ indent + "li $v0, 5\n"
						+ indent + "syscall\n"
						+ indent + "sw $v0, ($t0)\n";
		}
		return null;
	}

}
