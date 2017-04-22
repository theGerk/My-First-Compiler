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
		StringBuilder build = new StringBuilder(indent).append("#putting ptr to ").append(variable).append("'s function call in V0\n");
		build.append(indent).append("move $v0, $sp\n");
		for (int i = scope.getLevel(variable); i < scope.getLevel(); i++) {
			build.append(indent).append("lw $v0, 12($v0)\t#").append(i).append("\n");
		}
		return build.toString();
	}

	static String getVarPtrInV0(Scope scope, String variable, String indent) {
		return getFuncPtrInV0(scope, variable, indent)
				+ indent + "addi $v0, $v0, " + scope.getMemoryOffset(variable) + "\t#offset to variable\n";
	}
}
