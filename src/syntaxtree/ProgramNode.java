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
				+ indent + ".text\n"
				+ indent + "main:\n"
				+ globalVariables.toMips(symbolTable, indent + '\t')
				+ main.toMips(symbolTable, indent + '\t')
				+ indent + "li $v0, 10\n"
				+ indent + "syscall"
				+ functions.toMips(symbolTable, indent + '\t');
	}

	@Override
	public void makeLabels(String labelPrefix, Scope symbolTable) {
		functions.makeLabels("", symbolTable);
	}
}
