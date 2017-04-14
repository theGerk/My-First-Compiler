/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

/**
 *
 * @author Benji
 */
public class SubProgramNode extends SyntaxTreeNode {

	public final String name;
	public SubProgramDeclarationsNode subFunctions;
	public DeclarationsNode arguments;
	public DeclarationsNode variables;
	public CompoundStatementNode instructions;

	public SubProgramNode(String name) {
		this.name = name;
	}
	
	@Override
	public String indentedToString(int level) {
		StringBuilder answer = new StringBuilder(this.indentation(level));
		answer.append("Program: ").append(name).append("\n");
		answer.append(arguments.indentedToString(level + 1));
		answer.append(variables.indentedToString(level + 1));
		answer.append(subFunctions.indentedToString(level + 1));
		answer.append(instructions.indentedToString(level + 1));
		return answer.toString();
	}
}
