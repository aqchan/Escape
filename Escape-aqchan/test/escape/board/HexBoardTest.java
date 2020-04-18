/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.board.builder.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 17, 2020
 */
class HexBoardTest
{

	private static HexBoardBuilder bb = null; 
	private HexBoard hexBoard;
	
	@BeforeAll
	public static void setupBeforeTests() throws Exception
	{
		bb = new HexBoardBuilder(new File("config/board/HexBoardConfigInfinite.xml"));
	}
	
	@BeforeEach
	public void setupTest()
	{
		hexBoard = (HexBoard) bb.makeBoard();
		assertNotNull(hexBoard); // check that a square board was created
	}

	@Test
	void getPieceThatExists()
	{
		// Test getting a piece that should be there
		HexCoordinate c = HexCoordinate.makeCoordinate(-2,1); 
		assertNotNull(hexBoard.getPieceAt(c));
	}
	
	@Test
	void getPieceThatDoesNotExist()
	{
		// Test getting a piece that should NOT be there
		HexCoordinate c = HexCoordinate.makeCoordinate(0,0);    	
		assertNull(hexBoard.getPieceAt(c));
	}

	@Test
	void testIsValidCoordsInfinite()
	{
		// Both xMax and yMax are infinite
		HexCoordinate c = HexCoordinate.makeCoordinate(-50,40); 
		assertTrue(hexBoard.isValidCoords(c));
	}
	
	@Test
	void testIsValidCoordsInfiniteX() throws Exception
	{
		HexBoardBuilder bb = new HexBoardBuilder(new File("config/board/HexBoardConfigInfiniteX.xml"));
		hexBoard = (HexBoard) bb.makeBoard();
		
		// Only xMax is infinite
		HexCoordinate c = HexCoordinate.makeCoordinate(-50,13); 
		assertTrue(hexBoard.isValidCoords(c));
		
		HexCoordinate c2 = HexCoordinate.makeCoordinate(-50,15); 
		Assertions.assertThrows(EscapeException.class, () -> {
			assertTrue(hexBoard.isValidCoords(c2));
		});		
	}
	
	@Test
	void testIsValidCoordsInfiniteY() throws Exception
	{
		HexBoardBuilder bb = new HexBoardBuilder(new File("config/board/HexBoardConfigInfiniteY.xml"));
		hexBoard = (HexBoard) bb.makeBoard();
		
		// Only xMax is infinite
		HexCoordinate c = HexCoordinate.makeCoordinate(8,200); 
		assertTrue(hexBoard.isValidCoords(c));
		
		HexCoordinate c2 = HexCoordinate.makeCoordinate(10,200); 
		Assertions.assertThrows(EscapeException.class, () -> {
			assertTrue(hexBoard.isValidCoords(c2));
		});		
	}
}
