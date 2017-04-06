/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.io.FileInputStream;
import java.io.InputStreamReader;

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
		System.out.println(args[0]);
		// TODO code application logic here
		String filename = args[0];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(fis);
		Scanner scanner = new Scanner(isr);
		Token lex = null;
		do {
			try {
				lex = scanner.nextToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (lex != null);
	}
}
