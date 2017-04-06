/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scanner.TokenType;

/**
 *
 * @author Benji
 */
public class ParserTest {

	public ParserTest() {
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
	 * Test of match method, of class Parser.
	 */
	@Test
	public void testMatch() {
		System.out.println("match");
		TokenType expected = null;
		Parser instance = null;
		instance.match(expected);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of program method, of class Parser.
	 */
	@Test
	public void testProgram() {
		System.out.println("program");
		Parser instance = null;
		instance.program();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of identifierList method, of class Parser.
	 */
	@Test
	public void testIdentifierList() {
		System.out.println("identifierList");
		Parser instance = null;
		instance.identifierList();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of declarations method, of class Parser.
	 */
	@Test
	public void testDeclarations() {
		System.out.println("declarations");
		Parser instance = null;
		instance.declarations();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of type method, of class Parser.
	 */
	@Test
	public void testType() {
		System.out.println("type");
		Parser instance = null;
		instance.type();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of standardType method, of class Parser.
	 */
	@Test
	public void testStandardType() {
		System.out.println("standardType");
		Parser instance = null;
		instance.standardType();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of subprogramDeclarations method, of class Parser.
	 */
	@Test
	public void testSubprogramDeclarations() {
		System.out.println("subprogramDeclarations");
		Parser instance = null;
		instance.subprogramDeclarations();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of subprogramDeclaration method, of class Parser.
	 */
	@Test
	public void testSubprogramDeclaration() {
		System.out.println("subprogramDeclaration");
		Parser instance = null;
		instance.subprogramDeclaration();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of subprogramHead method, of class Parser.
	 */
	@Test
	public void testSubprogramHead() {
		System.out.println("subprogramHead");
		Parser instance = null;
		instance.subprogramHead();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of arguments method, of class Parser.
	 */
	@Test
	public void testArguments() {
		System.out.println("arguments");
		Parser instance = null;
		instance.arguments();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of parameterList method, of class Parser.
	 */
	@Test
	public void testParameterList() {
		System.out.println("parameterList");
		Parser instance = null;
		instance.parameterList();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of compoundStatement method, of class Parser.
	 */
	@Test
	public void testCompoundStatement() {
		System.out.println("compoundStatement");
		Parser instance = null;
		instance.compoundStatement();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of optionalStatements method, of class Parser.
	 */
	@Test
	public void testOptionalStatements() {
		System.out.println("optionalStatements");
		Parser instance = null;
		instance.optionalStatements();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of statementList method, of class Parser.
	 */
	@Test
	public void testStatementList() {
		System.out.println("statementList");
		Parser instance = null;
		instance.statementList();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of statement method, of class Parser.
	 */
	@Test
	public void testStatement() {
		System.out.println("statement");
		Parser instance = null;
		instance.statement();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of variable method, of class Parser.
	 */
	@Test
	public void testVariable() {
		System.out.println("variable");
		Parser instance = null;
		instance.variable();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of procedureStatement method, of class Parser.
	 */
	@Test
	public void testProcedureStatement() {
		System.out.println("procedureStatement");
		Parser instance = null;
		instance.procedureStatement();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of expressionList method, of class Parser.
	 */
	@Test
	public void testExpressionList() {
		System.out.println("expressionList");
		Parser instance = null;
		instance.expressionList();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of expression method, of class Parser.
	 */
	@Test
	public void testExpression() {
		System.out.println("expression");
		Parser instance = null;
		instance.expression();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of simpleExpression method, of class Parser.
	 */
	@Test
	public void testSimpleExpression() {
		System.out.println("simpleExpression");
		Parser instance = null;
		instance.simpleExpression();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of simplePart method, of class Parser.
	 */
	@Test
	public void testSimplePart() {
		System.out.println("simplePart");
		Parser instance = null;
		instance.simplePart();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of term method, of class Parser.
	 */
	@Test
	public void testTerm() {
		System.out.println("term");
		Parser instance = null;
		instance.term();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of termPart method, of class Parser.
	 */
	@Test
	public void testTermPart() {
		System.out.println("termPart");
		Parser instance = null;
		instance.termPart();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of factor method, of class Parser.
	 */
	@Test
	public void testFactor() {
		System.out.println("factor");
		Parser instance = null;
		instance.factor();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of sign method, of class Parser.
	 */
	@Test
	public void testSign() {
		System.out.println("sign");
		Parser instance = null;
		instance.sign();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of relop method, of class Parser.
	 */
	@Test
	public void testRelop() {
		System.out.println("relop");
		Parser instance = null;
		boolean expResult = false;
		boolean result = instance.relop();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of mulop method, of class Parser.
	 */
	@Test
	public void testMulop() {
		System.out.println("mulop");
		Parser instance = null;
		boolean expResult = false;
		boolean result = instance.mulop();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of addop method, of class Parser.
	 */
	@Test
	public void testAddop() {
		System.out.println("addop");
		Parser instance = null;
		boolean expResult = false;
		boolean result = instance.addop();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

}
