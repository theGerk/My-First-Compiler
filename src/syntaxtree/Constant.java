/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import symboltable.Scope;

/**
 * Static class only has constants that may be useful in the syntax tree
 *
 * @author Benji
 */
public class Constant {

	private Constant() {
	}
	public static final String ARRAY_OUT_OF_BOUNDS_MSG_LABEL = Scope.labelGenerator.getId("ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE");
	public static final String ARRAY_OUT_OF_BOUNDS_LABEL = Scope.labelGenerator.getId("ARRAY_OUT_OF_BOUNDS_ERROR");
	public static final int ARRAY_OUT_OF_BOUNDS_ERROR_CODE = 1;
}
