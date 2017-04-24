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
	private final DeclarationsNode variables;
	private final CompoundStatementNode instructions;
	private final SubProgramDeclarationsNode subFunctions;

	@Override
	public String getName() {
		return header.getName();
	}

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
			this.variables.addVariable(var);	///here it is!! we add arguments to declared vars!
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

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#SubProgramNode\n");

		//set up function space
		build.append(variables.toMips(symbolTable, indent + '\t'));

		//copy values over
		build.append(header.toMips(symbolTable, indent + '\t'));

		//write program
		build.append(instructions.toMips(symbolTable, indent + '\t'));

		//dealocate at end of program
		build.append(indent).append("lw $t0, 4($sp)\t#put return address in t0\n");
		build.append(indent).append("lw $t1, 20($sp)\t#put return value in t1\n");
		build.append(indent).append("lw $sp, 8($sp)\t#put stack pointer back up\n");
		build.append(indent).append("lw $t2, ($sp)\t#put t2 to stack head ptr in calling function\n");
		build.append(indent).append("sw $t1, ($t2)\t#put return value on stack\n");
		build.append(indent).append("jr $t0\n");

		//insert sub functions
		build.append(subFunctions.toMips(symbolTable, indent + '\t'));

		return build.toString();
	}
}
