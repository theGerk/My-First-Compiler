package syntaxtree;

import symboltable.Scope;

/**
 * Represents a Pascal Program
 *
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeNode {

	private final String name;
	private final DeclarationsNode variables;
	private final SubProgramDeclarationsNode functions;
	private final CompoundStatementNode main;

	public ProgramNode(String aName, DeclarationsNode globals, SubProgramDeclarationsNode functions, CompoundStatementNode main) {
		this.name = aName;
		variables = globals;
		this.functions = functions;
		this.main = main;
	}

	public DeclarationsNode getVariables() {
		return variables;
	}

	public SubProgramDeclarationsNode getFunctions() {
		return functions;
	}

	public CompoundStatementNode getMain() {
		return main;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Program: " + name + "\n";
		answer += variables.indentedToString(level + 1);
		answer += functions.indentedToString(level + 1);
		answer += main.indentedToString(level + 1);
		return answer;
	}

	/**
	 * generates MIPS assembly of program
	 *
	 * @param symbolTable global scope
	 * @return StringBuilder with entire program in it
	 */
	@Override
	public String toMips(Scope symbolTable, String indent) {
		//first set up declarations
		String declarations = variables.toMips(symbolTable, indent + '\t');
		String functions = this.functions.toMips(symbolTable, indent + '\t');
		String main = this.main.toMips(symbolTable, indent + '\t');
		return indent + ".text\n"
			+ indent + "main:\n"
			+ declarations
			+ main
			+ functions
			+ indent + '\t' + "li $v0, 10\n"
			+ indent + '\t' + "syscall";
	}
}
