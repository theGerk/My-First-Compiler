package syntaxtree;

import symboltable.Scope;

/**
 * Created by Benji on 4/16/2017.
 */
interface IPublicName {

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	String getName();

	static String getFuncPtrInV0(Scope scope, String variable, String indent) {
		StringBuilder output = new StringBuilder(indent).append("#putting ptr to ").append(variable).append("'s function call in V0\n")
				.append(indent).append("lw $v0, ($sp)\n");
		for (int i = scope.getLevel(variable); i < scope.getLevel(); i++) {
			output.append(indent).append("lw $v0, ($v0)\t#").append(i).append("\n");
		}
		return output.toString();
	}

	static String getVarPtrInV0(Scope scope, String variable, String indent) {
		return getFuncPtrInV0(scope, variable, indent)
				+ indent + "addi $v0, $v0, " + scope.getMemoryOffset(variable) + "\t#offset to variable\n";
	}
}
