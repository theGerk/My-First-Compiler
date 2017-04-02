/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.util.ArrayList;
import java.util.LinkedList;
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
public class TokenTest {

	public static LinkedList<Token> testTokens;

	public TokenTest() {
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
	 * Test of getLexeme method, of class Token.
	 */
	@Test
	public void testGetLexeme() {
		System.out.println("getLexeme");
		Token instance = new Token(3, "3");
		String expResult = "3";
		String result = instance.getLexeme();
		assertEquals(expResult, result);

	}

	/**
	 * Test of toString method, of class Token.
	 */
	@Test
	public void testToString() {
		System.out.println("toString");
		Token instance = new Token(":=");
		String expResult = "Token: :=";
		String result = instance.toString();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getType method, of class Token.
	 */
	@Test
	public void testGetType() {
		System.out.println("getType");
		Token instance = new Token(">=");
		TokenType expResult = TokenType.GREATERTHANEQUALS;
		TokenType result = instance.getType();
		assertEquals(expResult, result);
	}

	/**
	 * Test of equals method, of class Token.
	 */
	@Test
	public void testEquals() {
		System.out.println("equals");
		TokenType compareAgainst = TokenType.FORWARDSLASH;
		Token instance = new Token("/");
		boolean expResult = true;
		boolean result = instance.equals(compareAgainst);
		assertEquals(expResult, result);
	}

}
