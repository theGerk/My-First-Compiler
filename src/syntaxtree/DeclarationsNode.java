package syntaxtree;

import symboltable.Scope;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 *
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {

	protected ArrayList<String> vars = new ArrayList<>();

	public void addVariable(String aVariable) {
		vars.add(aVariable);
	}

	public ArrayList<String> getVars() {
		return vars;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		for (String variable : vars) {
			answer += variable;
		}
		return answer;
	}

}
