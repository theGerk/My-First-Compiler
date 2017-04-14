package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 *
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {

	protected ArrayList<VariableNode> vars = new ArrayList<>();

	public void addVariable(VariableNode aVariable) {
		vars.add(aVariable);
	}

	public ArrayList<VariableNode> getVars() {
		return vars;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		for (VariableNode variable : vars) {
			answer += variable.indentedToString(level + 1);
		}
		return answer;
	}
}
