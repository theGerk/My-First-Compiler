/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import scanner.TokenType;

/**
 * Also the symbol table
 *
 * @author Benji
 */
public class Scope {

	/**
	 * Creates head scope
	 */
	public Scope() {
		parentScope = null;
		bufferArguments = false;
	}

	/**
	 * constructs new scope child scope and set's parent's buffer to having it
	 * as scope
	 *
	 * @param parent the parent scope, null if there is none
	 */
	public Scope(Scope parent) {
		parentScope = parent;
		parent.set(this);
		parent.argumentBuffer = new ArrayList<>();
		bufferArguments = true;
	}

	public Scope getParent() {
		return parentScope;
	}

	final private Scope parentScope;
	private final HashMap<String, Symbol> map = new HashMap<>();
	private final ArrayList<String> buffer = new ArrayList<>();
	private IdentifierKind bufferKind = null;
	private TokenType bufferType = null;
	private Scope bufferScope = null;
	public boolean bufferArguments;						//when constructing a function there is a phase where arguments are being buffered
	private ArrayList<Symbol> argumentBuffer = null;		//when constructing a function we will buffer it's arguments as well

	/**
	 * Finds a variable in the current scope from the string representation.
	 *
	 * @param name string of identifier
	 * @return the kind of the variable if it exists, otherwise null
	 */
	public IdentifierKind getKind(String name) {
		Scope current = this;
		Symbol output = null;
		do {
			output = current.map.get(name);
			current = current.parentScope;
		} while (output == null && current != null);
		return (output != null) ? output.kind : null;
	}

	/**
	 * Returns if there a variable with this name that exists.
	 *
	 * @param name identifier name
	 * @return
	 */
	public boolean exists(String name) {
		return getKind(name) != null;
	}

	/**
	 * kind of identifiers available.
	 */
	public static enum IdentifierKind {
		PROG, VAR, ARR, FUNC
	}

	/**
	 * Info structure for identifier. Is private class.
	 */
	private static class Symbol {

		final public String name;					//identifier string
		final public IdentifierKind kind;
		final public TokenType type;
		final public Scope local;					//only used in procedures, functions (and programs)
		final private ArrayList<Symbol> arguments;	//only used in procedures, and functions

		@Override
		public String toString() {
			return toString("");
		}

		/**
		 * toString helper function
		 *
		 * @param t number of tabs to indent output with
		 * @return human readable string representing identifier
		 */
		private String toString(String t) {
			StringBuilder output = new StringBuilder(t);
			if (type != null) {
				output.append(name).append(" = ").append(kind.toString()).append(" of ").append(type.toString());
			} else {
				output.append(name).append(" = ").append(kind.toString());
			}

			if (arguments != null) {
				t = t + '\t';
				for (Symbol s : arguments) {
					output.append('\n').append(s.toString(t));
				}
			}

			return output.toString();
		}

		/**
		 * constructor for all types
		 *
		 * @param name string name of variable
		 * @param kind kind of identifier it is
		 * @param type type of identifier (returned by function, or type of
		 * array)
		 * @param scope scope, only used if function or procedure variable)
		 */
		public Symbol(String name, IdentifierKind kind, TokenType type, Scope scope, ArrayList<Symbol> args) {
			this.name = name;
			this.kind = kind;
			this.type = type;
			this.local = scope;
			this.arguments = args;
		}
	}

	/**
	 * checks if the string is a valid id
	 *
	 * @param name
	 * @return
	 */
	public boolean isValidId(String name) {
		return !(map.containsKey(name) || buffer.contains(name));	//TODO check if it is a reserved keyword
	}

	/**
	 * buffers the ID if it is valid
	 *
	 * @param name ID name
	 * @return if name is valid
	 */
	public boolean addId(String name) {
		boolean output = isValidId(name);
		if (output) {
			buffer.add(name);
		}
		return output;
	}

	/**
	 * flushes buffer into table, with set parameters.
	 *
	 * @throws Exception If kind is not set
	 */
	public void flushBuffer() throws Exception {
		if (bufferKind == null) {
			throw new Exception("Kind not set");
		} else {
			for (String s : buffer) {
				map.put(s, new Symbol(s, bufferKind, bufferType, bufferScope, argumentBuffer));
			}
			if (bufferArguments) {

			}
			buffer.clear();
			bufferKind = null;
			bufferType = null;
			bufferScope = null;
			argumentBuffer = null;
		}
	}

	private void appendArguments() {

	}

	/**
	 * sets kind for IDs in buffer
	 *
	 * @param kind
	 */
	public void set(IdentifierKind kind) {
		bufferKind = kind;
	}

	/**
	 * sets type for IDs in buffer
	 *
	 * @param type
	 */
	public void set(TokenType type) {
		bufferType = type;
	}

	/**
	 * sets the scope for a function in the buffer.
	 *
	 * @param scope
	 */
	public void set(Scope scope) {
		bufferScope = scope;
	}
}
