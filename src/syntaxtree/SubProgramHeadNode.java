package syntaxtree;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Benji on 4/14/2017.
 */
public class SubProgramHeadNode extends SyntaxTreeBase {

	private final String name;
	private final String[] arguments;

	public SubProgramHeadNode(String name, ArrayList<String> arguments) {
		this.name = name;
		this.arguments = (String[]) arguments.toArray();
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
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
}
