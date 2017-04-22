package syntaxtree;

import java.util.ArrayList;
import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017.
 */
public class SubProgramHeadNode extends SyntaxTreeBase implements IPublicName {

	private final String name;
	private final String[] arguments;

	public SubProgramHeadNode(String name, ArrayList<String> arguments) {
		this.name = name;
		this.arguments = new String[arguments.size()];
		arguments.toArray(this.arguments);
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 *
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		StringBuilder build = new StringBuilder(indentation(level)).append("FUNCTION HEADER: ").append(name).append('\n');
		for (String arg : arguments) {
			build.append(indentation(level)).append(arg).append('\n');
		}
		return build.toString();
	}

	public final String[] getArguments() {
		return arguments;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#SubProgramHeadNode\n");

		//load ptr to previous stack's head to pick up values
		build.append(indent).append("lw $t0, 4($sp)\n");
		build.append(indent).append("lw $t0, ($t0)\n");

		//iterate across arguments
		for (int i = 0; i < arguments.length; i++) {
			build.append(indent).append("lw $t1, ").append((i + 1) * 4).append("($t0)\t#get loaded value from stack\n");
			build.append(indent).append("sw $t1, ").append(symbolTable.getMemoryOffset(arguments[i])).append("($sp)\t#save word in correct spot (hopefully?)\n");	//TODO confirm where arguments are added to declared vars
		}

		return build.toString();
	}
}
