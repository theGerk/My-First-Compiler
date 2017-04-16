/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import symboltable.Scope;

/**
 *
 * @author Benji
 */
public class SubProgramNode extends SyntaxTreeBase implements IMakeFunctionLabels, IPublicName {

	private final SubProgramHeadNode header;

	@Override
	public String getName() {
		return header.getName();
	}

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
	
	/**
	 * Writes component into assembly with tabbing for readability and sets up symbol table where needed.
	 *
	 * @param symbolTable the current scope
	 * @param indent      tabs
	 *
	 * @return Mips assembly
	 */
	@Override
	protected String toMips(Scope symbolTable, String indent) {
	
	}
	
	/**
	 * Adds labels for each function into the symbol table
	 *
	 * @param labelPrefix the desired prefix for the label
	 * @param symbolTable the symbol table to be used
	 */
	@Override
	public void makeLabels(String labelPrefix, Scope symbolTable) {
		String myLabel = Scope.labelGenerator.getId(labelPrefix + getName());
		symbolTable.setLabel(getName(), myLabel);
		subFunctions.makeLabels(myLabel + '_', symbolTable);
	}
}
