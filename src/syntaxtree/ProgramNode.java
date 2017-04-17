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

	public ProgramNode(String aName, DeclarationsNode globals, SubProgramDeclarationsNode functions, CompoundStatementNode main) {
		this.name = aName;
		globalVariables = globals;
		this.functions = functions;
		this.main = main;
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

		//then return
		return indent + "#ProgramNode\n"
				+ indent + ".data\n"
				+ indent + Constant.ARRAY_OUT_OF_BOUNDS_MSG_LABEL + ": .asciiz \"You may not access an index in an array out of it's range.\\nPrepare to die.\"\n"
				+ indent + ".text\n"
				+ indent + "main:\n"
				+ globalVariables.toMips(symbolTable, indent + '\t')
				+ main.toMips(symbolTable, indent + '\t')
				+ indent + "li $v0, 10\n"
				+ indent + "syscall\n"
				+ functions.toMips(symbolTable, indent + '\t')
				+ indent + "#ERRORs\n"
				+ indent + Constant.ARRAY_OUT_OF_BOUNDS_LABEL + ":\n"
				+ indent + "la $a0, " + Constant.ARRAY_OUT_OF_BOUNDS_MSG_LABEL + "\n"
				+ indent + "li $v0, 4\n"
				+ indent + "syscall\n"
				+ indent + "li $v0, 17\n"
				+ indent + "li $a0, " + Constant.ARRAY_OUT_OF_BOUNDS_ERROR_CODE + "\n"
				+ indent + "syscall\n";
	}

	@Override
	public void makeLabels(String labelPrefix, Scope symbolTable) {
		functions.makeLabels("", symbolTable);
	}
}
