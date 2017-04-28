/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.Parser;
import syntaxtree.ProgramNode;

/**
 *
 * @author Benji
 */
public class Main {

	/**
	 * make main static class
	 */
	private Main() {
	}

	/**
	 * Main function
	 *
	 * @param args the command line arguments, args[0] is the input file
	 */
	public static void main(String[] args) {
		Parser parse = new Parser(args[0]);
		ProgramNode program = parse.program();
		String str = program.toMips();
		Path file = Paths.get("output.asm");
		try {
			Files.write(file, Arrays.asList(str), Charset.forName("UTF-8"));
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		file = Paths.get("symbolTable.txt");
		try {
			Files.write(file, Arrays.asList(program.symbolTable.toString()), Charset.forName("UTF-8"));
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Makes string with same length as input, but only spaces.
	 *
	 * @param input input string
	 * @return string with same size as input and only made of spaces.
	 */
	public static String makeSpaces(String input) {
		char[] chars = new char[input.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = ' ';
		}
		return String.valueOf(chars);
	}
}
