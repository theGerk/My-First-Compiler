package syntaxtree;

import symboltable.Scope;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations
 *
 * @author Erik Steinmetz
 */
public class SubProgramDeclarationsNode extends SyntaxTreeBase implements IMakeFunctionLabels {

	private final ArrayList<SubProgramNode> functions = new ArrayList<>();

	/**
	 * adds function to node
	 *
	 * @param aSubProgram a function to be added
	 */
	public void addSubProgramDeclaration(SubProgramNode aSubProgram) {
		functions.add(aSubProgram);
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "SubProgramDeclarations\n";
		for (SubProgramNode subProg : functions) {
			answer += subProg.indentedToString(level + 1);
		}
		return answer;
	}

	/**
	 * Writes component into assembly with tabbing for readability and sets up
	 * symbol table where needed.
	 *
	 * @param symbolTable the current scope
	 * @param indent tabs
	 *
	 * @return Mips assembly
	 */
	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder output = new StringBuilder(indent).append("#SubProgramDeclarationsNode\n");
		for (SubProgramNode function : functions) {
			output.append(function.toMips(symbolTable.getScope(function.getName()), indent + '\t'));
		}
		return output.toString();
	}

	/**
	 * Adds labels for each function into the symbol table
	 *
	 * @param labelPrefix the desired prefix for the label
	 * @param symbolTable the symbol table to be used
	 */
	@Override
	public void makeLabels(String labelPrefix, Scope symbolTable) {
		for (SubProgramNode f : functions) {
			f.makeLabels(labelPrefix, symbolTable.getScope(f.getName()));
		}
	}
}
