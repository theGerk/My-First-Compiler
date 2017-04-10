

package syntaxtree;

/**
 * The base class for all nodes in a syntax tree.
 * @author Erik Steinmetz
 */
public abstract class SyntaxTreeNode {
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    public abstract String indentedToString( int level);
    
    /**
     * Creates an indentation String for the indentedToString.
     * @param level The amount of indentation.
     * @return A String displaying the given amount of indentation.
     */
    protected String indentation( int level) {
        String answer = "";
        if( level > 0) {
            answer = "|-- ";
        }
        for( int indent = 1; indent < level; indent++) answer += "--- ";
        return( answer);        
    }

}
