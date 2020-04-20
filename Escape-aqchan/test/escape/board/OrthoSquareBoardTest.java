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
import escape.piece.*;

/**
 * Description
 * @version Apr 17, 2020
 */
class OrthoSquareBoardTest
{

	private static BoardBuilder bb = null; 
	private OrthoSquareBoard orthoSquareBoard;
	
	@BeforeAll
	public static void setupBeforeTests() throws Exception
	{
		bb = new OrthoSquareBoardBuilder(new File("config/board/OrthoSquareBoardConfig.xml"));
	}
	
	@BeforeEach
	public void setupTest()
	{
		orthoSquareBoard = (OrthoSquareBoard) bb.makeBoard();
		assertNotNull(orthoSquareBoard); // check that a square board was created
	}
	
	@Test
	void consumeIncorrectFileType () throws Exception
	{
		BoardBuilder bb = new OrthoSquareBoardBuilder(new File("config/board/HexBoardConfigInfinite.xml"));
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard = (OrthoSquareBoard) bb.makeBoard();
		});		
	}
	
	@Test
	void getPieceThatExists()
	{
		// Test getting a piece that should be there
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(5,4); 
		assertNotNull(orthoSquareBoard.getPieceAt(c));
	}
	
	@Test
	void getPieceThatDoesNotExist()
	{
		// Test getting a piece that should NOT be there
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(1,2);    	
		assertNull(orthoSquareBoard.getPieceAt(c));
	}
	
	@Test
	void placePieceInValidLocation()
	{
		// Test placing a piece in valid location and then retrieving it
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(7,4); 
		orthoSquareBoard.putPieceAt(p, c);
		assertNotNull(orthoSquareBoard.getPieceAt(c));
	}
	
	@Test
	void placePieceOutsideFiniteBoard()
	{
		// Test placing a piece outside of the board dimensions
		// Both dimensions
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
		OrthoSquareCoordinate c1 = OrthoSquareCoordinate.makeCoordinate(11,11); 

		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c1);
		});		
		
		// X dimension is out of bounds
		OrthoSquareCoordinate c2 = OrthoSquareCoordinate.makeCoordinate(12,7); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c2);
		});		
		
		// Y dimension is out of bounds
		OrthoSquareCoordinate c3 = OrthoSquareCoordinate.makeCoordinate(3,11); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c3);
		});		
		
		// X and Y coordinates are less than (1,1)
		OrthoSquareCoordinate c4 = OrthoSquareCoordinate.makeCoordinate(0,0); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c4);
		});
		
		// X coordinate is less than 1
		OrthoSquareCoordinate c5 = OrthoSquareCoordinate.makeCoordinate(-1,1); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c5);
		});
		
		// Y coordinate is less than 1
		OrthoSquareCoordinate c6 = OrthoSquareCoordinate.makeCoordinate(1,-1); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c6);
		});
	}
	
	

	@Test
	void placePieceOnBlockedLocation()
	{
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(1,1); 
		Assertions.assertThrows(EscapeException.class, () -> {
			orthoSquareBoard.putPieceAt(p, c);
		});		
	}
	
	@Test
	void placePieceOnExitLocation()
	{
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(4,2); 
		orthoSquareBoard.setLocationType(c, LocationType.EXIT); // make this coord an exit location
		orthoSquareBoard.putPieceAt(p, c); // place piece on exit location
		assertNull(orthoSquareBoard.getPieceAt(c)); // try to retrieve piece... should be null
	}
}
