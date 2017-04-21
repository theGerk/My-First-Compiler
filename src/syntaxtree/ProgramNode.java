package syntaxtree;

import symboltable.Scope;

/**
 * Represents a Pascal Program
 *
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeBase implements IMakeFunctionLabels {

	private final String name;
	private final DeclarationsNode globalVariables;
	private final SubProgramDeclarationsNode functions;
	private final CompoundStatementNode main;
	private final Scope symbolTable;

	public ProgramNode(String aName, DeclarationsNode globals, SubProgramDeclarationsNode functions, CompoundStatementNode main, Scope scope) {
		this.name = aName;
		globalVariables = globals;
		this.functions = functions;
		this.main = main;
		symbolTable = scope;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Program: " + name + "\n";
		answer += globalVariables.indentedToString(level + 1);
		answer += functions.indentedToString(level + 1);
		answer += main.indentedToString(level + 1);
		return answer;
	}

	/**
	 * generates MIPS assembly of program
	 *
	 * @param symbolTable global scope
	 * @param indent indentation for formating
	 *
	 * @return StringBuilder with entire program in it
	 */
	@Override
	public String toMips(Scope symbolTable, String indent) {
		//first set up labels
		makeLabels("", symbolTable);

		//init builder
		StringBuilder build = new StringBuilder(indent).append("#ProgramNode\n");

		//data section, error messages	//TODO add globals here
		build.append(indent).append(".data\n");
		build.append(indent).append(Constant.ARRAY_OUT_OF_BOUNDS_MSG_LABEL).append(": .asciiz \"You may not access an index in an array out of it's range.\\nPrepare to die.\"\n");

		//text section
		build.append(indent).append(".text\n");

		//main function start
		build.append(indent).append("main:\n");

		//add in global variables:
		build.append(globalVariables.toMips(symbolTable, indent + '\t'));

		//add in main function
		build.append(main.toMips(symbolTable, indent + '\t'));

		//load end of program
		build.append(indent).append("li $v0, 10\n");
		build.append(indent).append("syscall\n");

		//put in functions
		build.append(functions.toMips(symbolTable, indent + '\t'));

		//error messages
		build.append(indent).append("#ERRORs\n");
		build.append(indent).append(Constant.ARRAY_OUT_OF_BOUNDS_LABEL).append(":\n");
		build.append(indent).append("la $a0, ").append(Constant.ARRAY_OUT_OF_BOUNDS_MSG_LABEL).append("\n");
		build.append(indent).append("li $v0, 4\n");
		build.append(indent).append("syscall\n");
		build.append(indent).append("li $v0, 17\n");
		build.append(indent).append("li $a0, ").append(Constant.ARRAY_OUT_OF_BOUNDS_ERROR_CODE).append("\n");
		build.append(indent).append("syscall\n");

		//then return
		return build.toString();
	}

	public String toMips() {
		return toMips(symbolTable, "");
	}

	@Override
	public void makeLabels(String labelPrefix, Scope symbolTable) {
		functions.makeLabels("", symbolTable);
	}
}
