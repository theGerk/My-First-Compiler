/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symboltable;

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
	}

	/**
	 * getter for parent Scope
	 *
	 * @return parent Scope
	 */
	public Scope getParent() {
		return parentScope;
	}

	final private Scope parentScope;
	private final HashMap<String, Symbol> map = new HashMap<>();

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

		final public String name;
		public IdentifierKind kind;
		public TokenType type;
		public Scope local;					//only used in procedures & functions (and programs)

		/**
		 * trivial constructor
		 *
		 * @param name identifier string
		 * @param kind kind of identifier it is
		 */
		public Symbol(String name) {
			this.name = name;
			this.kind = kind;
		}

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
				output.append(kind.toString()).append(" of ").append(type.toString());
			} else {
				output.append(kind.toString());
			}

			return output.toString();
		}
	}

	/**
	 * checks if the string is a valid id
	 *
	 * @param name
	 * @return
	 */
	public boolean isValidId(String name) {
		return !(map.containsKey(name));
	}

	public void put(String id) throws Exception {
		if (!isValidId(id)) {
			throw new Exception("Invalid ID");
		}
		map.put(id, new Symbol(id));
	}

	public void set(String id, TokenType type) throws Exception {
		Symbol s = map.get(id);
		if (s.type == null) {
			throw new Exception("type already set");
		}
		s.type = type;
	}

	public void set(String id, Scope scope) throws Exception {
		Symbol s = map.get(id);
		if (s.type == null) {
			throw new Exception("scope is already set");
		}
		s.local = scope;
	}

	public void set(String id, IdentifierKind kind) throws Exception {
		Symbol s = map.get(id);
		if (s.kind == null) {
			throw new Exception("kind is already set");
		}
		s.kind = kind;
	}
}
