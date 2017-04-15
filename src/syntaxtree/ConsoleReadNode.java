/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.TokenType;

/**
 *
 * @author Benji
 */
public class ConsoleReadNode extends ExpressionNode {

	public ConsoleReadNode(TokenType returnType) {
		super(returnType);
	}
	
	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public boolean isFoldable() {
		return false;
	}
	
	@Override
	public String indentedToString(int level) {
		return indentation(level) + "read " + returnType + " from console";
	}

}
