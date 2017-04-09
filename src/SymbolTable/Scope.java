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

	/**
	 * Finds a variable in the current scope from the string representation.
	 *
	 * @param name string of identifier
	 * @return the kind of the variable if it exists, otherwise null
	 */
	public IdentifierKind kindOf(String name) {
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
		return kindOf(name) != null;
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

		final private String name;					//identifier string
		final private IdentifierKind kind;
		final private TokenType type;
		final private ToStringType toStringType;	//type is used for printing as string
		final private Scope local;

		/**
		 * Type used for printing as string
		 */
		private static enum ToStringType {
			ARRAY, HASTYPE, NOTYPE
		}

		/**
		 * @return human readable string representation of Info
		 */
		public String toString() {
			switch (toStringType) {
				case ARRAY:
					return name + " = [" + type.toString() + "]";
				case HASTYPE:
					return name + " = " + kind.toString() + " of " + type.toString();
				case NOTYPE:
					return name + " = " + kind.toString();
				default:
					return "ERROR";
			}
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
		public Symbol(String name, IdentifierKind kind, TokenType type, Scope scope) {
			this.name = name;
			this.kind = kind;
			this.type = type;
			this.local = scope;
			toStringType = kind == IdentifierKind.ARR ? ToStringType.ARRAY : (type == null ? ToStringType.NOTYPE : ToStringType.HASTYPE); //TODO don't be a fucking retard
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
	 * flushes buffer into table, with set type and kind.
	 *
	 * @throws Exception If kind or type is not set
	 */
	public void flushBuffer() throws Exception {
		if (bufferKind == null) {
			throw new Exception("Kind not set");
		} else {
			for (String s : buffer) {
				map.put(s, new Symbol(s, bufferKind, bufferType, bufferScope));
			}
			buffer.clear();
			bufferKind = null;
			bufferType = null;
		}
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
