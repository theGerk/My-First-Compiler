package syntaxtree;

import symboltable.Scope;

import java.util.ArrayList;

/**
 * Represents a setLabel of declarations in a Pascal program.
 *
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeBase {

	protected ArrayList<String> vars = new ArrayList<>();

	public void addVariable(String aVariable) {
		vars.add(aVariable);
	}

	public String[] getVars() {
		return (String[]) vars.toArray();
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		for (String variable : vars) {
			answer += variable;
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
		//TODO document the following:
		// Stack pointer moves to bottom of function's stack
		// offsets are measured in bytes above the stack pointer
		//offset from stack pointer:
		// 0  - current stack head														4 bytes
		// 4  - return address															4 bytes
		// 8  - previous stack pointer value											4 bytes
		// 12 - one level up function (used for finding variables with higher scope)	4 bytes
		// 16 - current scope level														4 bytes
		// 20 - return value															4 bytes

		//add offsets to every symbol being declared, and calculate total offset
		int offset = 24;
		for (String var : vars) {
			symbolTable.setOffset(var, offset);
			offset += symbolTable.sizeInBytes(var);
		}

		//get labels to be used
		String startLabel = symbolTable.getLevel() == 0 ? "" : Scope.labelGenerator.getId();		//not needed in global scope
		String endLabel = symbolTable.getLevel() == 0 ? "" : Scope.labelGenerator.getId();

		return indent + "#DeclarationsNode\n"
				+ indent + "move $t0, $sp\t#saves stack pointer for later useage\n" // save previous stack pointer in t0
				+ indent + "addi $sp, $sp, " + -offset + "\t#move stack pointer for function start\n" // move stack pointer
				+ indent + "sw $sp, ($sp)\t#set up stack pointer for new function\n" // setLabel current stack head
				+ indent + "sw $ra, 4($sp)\t#save return address\n" // setLabel return address in memory
				+ indent + "sw $t0, 8($sp)\t#save old stack pointer\n" // setLabel previous stack pointer value

				// determine what one level up function is and put it in memory
				+ (symbolTable.getLevel() == 0 // if in global scope
				? "" // then do nothing
				: indent + "\n" // otherwise implement loop to find nearest function who's level is just one up
				+ indent + "#search for one level up function\n"
				+ indent + "li $t2, " + (symbolTable.getLevel() - 1) + "\t#load current level\n" // put current function level - 1 in t2 (what we are searching for)
				+ indent + "lw $t1, 16($t0)\t#load previous level\n" // we will now use t0 for a function pointer and t1 as the level of t0's function
				+ indent + "beq $t1, $t2, " + endLabel + "\n" // branch skip if we are already done
				+ indent + startLabel + ":\n"
				+ indent + "lw $t0, 12($t0)\t#go one level up\n" // goes to one up function in t0
				+ indent + "lw $t1, 16($t0)\t#get level of function\n" // put it's level in t1
				+ indent + "bne $t1, $t2, " + startLabel + "\n"
				+ indent + endLabel + ":\n"
				+ indent + "sw $t0, 12($sp)\t#save one level up pointer\n") // puts pointer onto stack

				//load final values
				+ indent + "li $t0, " + symbolTable.getLevel() + "\n" // puts scope level in t0
				+ indent + "sw $t0, 16($sp)\t#save scope level of this function\n";
	}
}
