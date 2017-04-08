/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable;

import java.util.HashMap;
import scanner.TokenType;

/**
 * The symbol table will determine what tokens reference what values
 *
 * @author Benji
 */
public class SymbolTable {

	private SymbolTable parentScope;
	private HashMap<String, Info> map;

	/**
	 * Finds a variable in the current scope from the string representation.
	 *
	 * @param name string of identifier
	 * @return the kind of the variable if it exists, otherwise null
	 */
	public IdentifierKind kindOf(String name) {
		SymbolTable current = this;
		Info output = null;
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
	private static class Info {

		final private String name;					//identifier string
		private IdentifierKind kind;
		private TokenType type;
		final private ToStringType toStringType;	//type is used for printing as string

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
		 * General usage constructor
		 *
		 * @param name string name of identifier
		 * @param type type of identifier
		 */
		public Info(String name, IdentifierKind kind) {
			this.name = name;
			this.kind = kind;
			this.type = null;
			toStringType = ToStringType.NOTYPE;
		}

		/**
		 * constructor for identifier with a type
		 *
		 * @param name string name of variable
		 * @param kind kind of identifier it is
		 * @param type type of identifier (returned by function, or type of
		 * variable)
		 */
		public Info(String name, IdentifierKind kind, TokenType type) {
			this.name = name;
			this.kind = kind;
			this.type = type;
			toStringType = ToStringType.HASTYPE;
		}

		/**
		 * Constructor for array
		 *
		 * @param name identifier for array
		 * @param type type of array
		 */
		public Info(String name, TokenType type) {
			this.name = name;
			this.kind = IdentifierKind.ARR;
			this.type = type;
			toStringType = ToStringType.ARRAY;
		}
	}

	/**
	 * adds identifier without type
	 *
	 * @param name
	 * @param kind
	 */
	public void put(String name, IdentifierKind kind) {
		map.put(name, new Info(name, kind));
	}

	/**
	 * adds identifier with type (not array)
	 *
	 * @param name
	 * @param kind
	 * @param type
	 */
	public void put(String name, IdentifierKind kind, TokenType type) {
		map.put(name, new Info(name, kind, type));
	}

	/**
	 * adds array identifier
	 *
	 * @param name
	 * @param type
	 */
	public void putArr(String name, TokenType type) {
		map.put(name, new Info(name, type));
	}
}
