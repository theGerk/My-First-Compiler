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
public class SubProgramNode extends SyntaxTreeBase {

	private final SubProgramHeadNode header;
	private final DeclarationsNode variables;
	private final CompoundStatementNode instructions;
	private final SubProgramDeclarationsNode subFunctions;

	/**
	 * Constructor
	 *
	 * @param header the header of the node
	 * @param variables
	 * @param subFunctions
	 * @param instructions
	 */
	public SubProgramNode(SubProgramHeadNode header, DeclarationsNode variables, SubProgramDeclarationsNode subFunctions, CompoundStatementNode instructions) {
		this.header = header;
		this.variables = variables;
		this.instructions = instructions;
		this.subFunctions = subFunctions;
		for (String var : header.getArguments()) {
			this.variables.addVariable(var);
		}
	}

	@Override
	public String indentedToString(int level) {
		StringBuilder answer = new StringBuilder(this.indentation(level));
		answer.append("Program: ").append("\n");
		answer.append(header.indentedToString(level + 1));
		answer.append(variables.indentedToString(level + 1));
		answer.append(instructions.indentedToString(level + 1));
		return answer.toString();
	}
}
