///*******************************************************************************
// * This files was developed for CS4233: Object-Oriented Analysis & Design.
// * The course was taken at Worcester Polytechnic Institute.
// *
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Copyright ©2016 Gary F. Pollice
// *******************************************************************************/
//
//package escape.board;
//
//import static org.junit.jupiter.api.Assertions.*;
//import java.io.File;
//import org.junit.jupiter.api.*;
//import escape.board.builder.*;
//import escape.board.coordinate.SquareCoordinate;
//import escape.exception.EscapeException;
//import escape.piece.*;
//
///**
// * Description
// * @version Apr 16, 2020
// */
//class SquareBoardTest
//{
//	private static BoardBuilder bb = null; 
//	private SquareBoard squareBoard;
//	
//	@BeforeAll
//	public static void setupBeforeTests() throws Exception
//	{
//		bb = new SquareBoardBuilder(new File("config/board/SquareBoardConfig.xml"));
//	}
//	
//	@BeforeEach
//	public void setupTest()
//	{
//		squareBoard = (SquareBoard) bb.makeBoard();
//		assertNotNull(squareBoard); // check that a square board was created
//	}
//	
//	@Test
//	void consumeIncorrectFileType () throws Exception
//	{
//		BoardBuilder bb = new SquareBoardBuilder(new File("config/board/OrthoSquareBoardConfig.xml"));
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard = (SquareBoard) bb.makeBoard();
//		});		
//	}
//	
//	@Test
//	void getPieceThatExists()
//	{
//		// Test getting a piece that should be there
//		SquareCoordinate c = SquareCoordinate.makeCoordinate(2,2); 
//		assertNotNull(squareBoard.getPieceAt(c));
//	}
//	
//	@Test
//	void getPieceThatDoesNotExist()
//	{
//		// Test getting a piece that should NOT be there
//		SquareCoordinate c = SquareCoordinate.makeCoordinate(1,2);    	
//		assertNull(squareBoard.getPieceAt(c));
//	}
//	
//	@Test
//	void placePieceInValidLocation()
//	{
//		// Test placing a piece in valid location and then retrieving it
//		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
//		SquareCoordinate c = SquareCoordinate.makeCoordinate(3,1); 
//		squareBoard.putPieceAt(p, c);
//		assertNotNull(squareBoard.getPieceAt(c));
//	}
//	
//	@Test
//	void placePieceOutsideFiniteBoard()
//	{
//		// Test placing a piece outside of the board dimensions
//		// Both dimensions
//		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
//		SquareCoordinate c1 = SquareCoordinate.makeCoordinate(9,9); 
//
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c1);
//		});		
//		
//		// X dimension is out of bounds
//		SquareCoordinate c2 = SquareCoordinate.makeCoordinate(9,2); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c2);
//		});		
//		
//		// Y dimension is out of bounds
//		SquareCoordinate c3 = SquareCoordinate.makeCoordinate(1,9); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c3);
//		});		
//		
//		// X and Y coordinates are less than (1,1)
//		SquareCoordinate c4 = SquareCoordinate.makeCoordinate(0,0); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c4);
//		});
//		
//		// X coordinate is less than 1
//		SquareCoordinate c5 = SquareCoordinate.makeCoordinate(-1,1); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c5);
//		});
//		
//		// Y coordinate is less than 1
//		SquareCoordinate c6 = SquareCoordinate.makeCoordinate(1,-1); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c6);
//		});
//	}
//	
//	@Test
//	void placePieceOnBlockedLocation()
//	{
//		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
//		SquareCoordinate c = SquareCoordinate.makeCoordinate(3,5); 
//		Assertions.assertThrows(EscapeException.class, () -> {
//			squareBoard.putPieceAt(p, c);
//		});		
//	}
//	
//	@Test
//	void placePieceOnExitLocation()
//	{
//		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.SNAIL);
//		SquareCoordinate c = SquareCoordinate.makeCoordinate(8,8); 
//		squareBoard.setLocationType(c, LocationType.EXIT); // make this coord an exit location
//		squareBoard.putPieceAt(p, c); // place piece on exit location
//		assertNull(squareBoard.getPieceAt(c)); // try to retrieve piece... should be null
//	}
//	
//}
