/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.Map;
import java.util.Stack;
import scanner.Token;

/**
 * The symbol table will determine what tokens reference what values
 *
 * @author Benji
 */
public class SymbolTable {

	private Stack<Map<String, Token>> internal;
}
