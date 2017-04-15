/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symboltable;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
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
		level = 0;
	}

	/**
	 * constructs new scope child scope and set's parent's buffer to having it
	 * as scope
	 *
	 * @param parent the parent scope, null if there is none
	 */
	public Scope(Scope parent) {
		parentScope = parent;
		level = parent.level + 1;
	}

	/**
	 * getter for parent Scope
	 *
	 * @return parent Scope
	 */
	public Scope getParent() {
		return parentScope;
	}

	final private int level;
	final private Scope parentScope;
	final private HashMap<String, Symbol> map = new HashMap<>();

	/**
	 * returns a symbol that is accessible in the current scope by it's name
	 *
	 * @param name the identifier
	 * @return the symbol, null if it does not exist
	 */
	protected Symbol getSymbol(String name) {
		Scope current = this;
		Symbol output = null;
		do {
			output = current.map.get(name);
			current = current.parentScope;
		} while (output == null && current != null);
		return output;
	}

	/**
	 * returns the kind associated with an identifier
	 *
	 * @param name the identifier
	 * @return the kind, null if it does not exist
	 */
	public IdentifierKind getKind(String name) {
		Symbol output = getSymbol(name);
		return output == null ? null : output.kind;
	}

	/**
	 * returns the type associated with an identifier
	 *
	 * @param name the identifier
	 * @return the type, null if it does not exist
	 */
	public TokenType getType(String name) {
		Symbol output = getSymbol(name);
		return output == null ? null : output.type;
	}

	/**
	 * returns the scope associated with an identifier
	 *
	 * @param name the identifier
	 * @return the scope, null if it does not exist
	 */
	public Scope getScope(String name) {
		Symbol output = getSymbol(name);
		return output == null ? null : output.scope;
	}

	/**
	 * gets what level of scope something is at. 0 is global 1 is in a function
	 * that is global and so on. negative numbers are for errors. -1 =
	 * identifier not found
	 *
	 * @param name symbol identifier
	 * @return it's scope level.
	 */
	public int getLevel(String name) {
		Scope ptr = this;
		while (!ptr.map.containsKey(name)) {
			ptr = ptr.parentScope;
			if (ptr == null) {
				return -1;
			}
		}
		return level;
	}

	/**
	 * getter for level
	 *
	 * @return the level this scope is at.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Returns if there a variable with this name that exists.
	 *
	 * @param name identifier name
	 * @return a variable with this name exists
	 */
	public boolean exists(String name) {
		return getSymbol(name) != null;
	}

	/**
	 * kind of identifiers available.
	 */
	public enum IdentifierKind {
		PROG, VAR, ARR, FUNC
	}

	/**
	 * Info structure for identifier. Is private class.
	 */
	private static class Symbol {

		final public String name;
		public IdentifierKind kind;
		public TokenType type;
		public Scope scope;					//only used in procedures & functions (and programs)
		public ArrayList<Pair<String, TokenType>> args;
		public int offsetFromStackPointer;

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

		/**
		 * To string override
		 *
		 * @return string representation of Symbol
		 */
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
			if (args != null) {
				for (Pair<String, TokenType> arg : args) {
					output.append(scope.getSymbol(arg.getKey()).toString(t + '\t'));
				}
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
	
	/**
	 * adds new identifier
	 * @param id
	 * @throws Exception
	 */
	public void put(String id) throws Exception {
		if (!isValidId(id)) {
			throw new Exception("Invalid id: " + id);
		}
		map.put(id, new Symbol(id));
	}
	
	/**
	 *
	 * @param id
	 * @param type
	 * @throws Exception
	 */
	public void set(String id, TokenType type) throws Exception {
		Symbol s = map.get(id);
		if (s.type != null) {
			throw new Exception("type already set: " + id);
		}
		s.type = type;
	}

	public void set(String id, Scope scope) throws Exception {
		Symbol s = map.get(id);
		if (s.scope != null) {
			throw new Exception("scope is already set: " + id);
		}
		s.scope = scope;
	}

	public void set(String id, IdentifierKind kind) throws Exception {
		Symbol s = map.get(id);
		if (s.kind != null) {
			throw new Exception("kind is already set: " + id);
		}
		s.kind = kind;
		if (kind == IdentifierKind.FUNC) {
			s.args = new ArrayList<>();
		}
	}

	public void addArg(String funcID, String argID, TokenType argType) throws Exception {
		Symbol s = map.get(funcID);
		if (s == null || s.kind != IdentifierKind.FUNC) {
			throw new Exception(funcID + " is not a function");
		} else if (s.args.contains(argID)) {
			throw new Exception(argID + " already exists in scope");
		} else {
			s.args.add(new Pair<>(argID, argType));
		}
	}

	public ArrayList<TokenType> getArgsTypes(String id) throws Exception {
		Symbol s = getSymbol(id);
		if (s == null) {
			throw new Exception(id + " is not an identifier");
		} else if (s.args == null) {
			throw new Exception(id + " is not a function");
		} else {
			ArrayList<TokenType> output = new ArrayList();
			for (Pair<String, TokenType> p : s.args) {
				output.add(p.getValue());
			}
			return output;
		}
	}
}
