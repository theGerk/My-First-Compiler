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

	@Override
	public String indentedToString(int level) {
		return indentation(level) + "read " + returnType + " from console";
	}

}
