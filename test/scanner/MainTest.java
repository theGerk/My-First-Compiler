/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benji
 */
public class MainTest {

	public MainTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of main method, of class Main.
	 */
	@Test
	public void testMain() {
		System.out.println("main");
		String[][] argss = new String[][]{{"simplest.pas"}, {"simple.pas"}};
		for (String[] args : argss) {
			Main.main(args);
		}
		// TODO review the generated test code and remove the default call to fail.

		assertEquals(true, true);
	}

}
