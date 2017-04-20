/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import parser.Parser;

/**
 *
 * @author Benji
 */
public class Main {

	/**
	 * Main function
	 *
	 * @param args the command line arguments, args[0] is the input file
	 */
	public static void main(String[] args) {
		Parser parse = new Parser("simple.pas");
		String str = parse.program().toMips();
		System.out.print(str);
	}
}
