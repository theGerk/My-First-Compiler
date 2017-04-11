package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations
 *
 * @author Erik Steinmetz
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {

	private ArrayList<SubProgramNode> procs = new ArrayList<SubProgramNode>();

	public void addSubProgramDeclaration(SubProgramNode aSubProgram) {
		procs.add(aSubProgram);
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "SubProgramDeclarations\n";
		for (SubProgramNode subProg : procs) {
			answer += subProg.indentedToString(level + 1);
		}
		return answer;
	}

}
