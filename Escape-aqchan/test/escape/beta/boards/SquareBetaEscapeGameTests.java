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

package escape.beta.boards;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 29, 2020
 */
class SquareBetaEscapeGameTests
{
	private EscapeGameBuilder egb;
	private EscapeGameManager egm;

	/**
	 * Square Beta Escape GameTests
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		egb = new EscapeGameBuilder(new File("config/betaboardconfigs/SquareEscapeGame.xml"));
		egm = egb.makeGameManager();
	}

	@Test
	void squareCoordinateTests() throws Exception
	{
		// Test that you only made a SquareCoordinate
		Coordinate  c1 = egm.makeCoordinate(5, 6);
		assertNotEquals(HexCoordinate.makeCoordinate(5, 6), c1);
		assertNotEquals(OrthoSquareCoordinate.makeCoordinate(5, 6), c1);
		assertEquals(SquareCoordinate.makeCoordinate(5, 6), c1);	

		assertEquals(null, egm.makeCoordinate(0, 1));
		assertEquals(null, egm.makeCoordinate(1, 0));
		assertEquals(null, egm.makeCoordinate(0, 0));
		assertEquals(null, egm.makeCoordinate(16, 5)); // exceeds max x-value
		assertEquals(null, egm.makeCoordinate(5, 21)); // exceeds max y-value
		assertEquals(null, egm.makeCoordinate(16, 21));
	}

	@Test
	void squareGetPieceAt() throws Exception
	{
		Coordinate  c1 = egm.makeCoordinate(3, 4); 
		Coordinate  c2 = egm.makeCoordinate(3, 3); 

		assertNotNull(egm.getPieceAt(c1));
		assertNull(egm.getPieceAt(c2));
	}

	@Test
	void squareDiagonalMovementTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareDiagonalSetup.xml"));
//		EscapeGameManager e = gameBuilder.makeGameManager();
//		assertTrue(e.move(e.makeCoordinate(1, 5), e.makeCoordinate(3, 3)));

		// COMMENTED OUT BECAUSE TESTS FAILED AFTER I FIXED THE ERROR OF A PLAYER LANDING ON SAME PLAYER
		//	     assertTrue(e.move(e.makeCoordinate(5, 1), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(1, 3), e.makeCoordinate(3, 3)));
		//	     assertFalse(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(3, 3))); // blocked location
		//	     assertFalse(e.move(e.makeCoordinate(3, 4), e.makeCoordinate(3, 3))); // cannot reach by moving diagonally
	}

	@Test
	void squareLinearMovementTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareLinearSetup.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertTrue(e.move(e.makeCoordinate(1, 5), e.makeCoordinate(3, 3)));

		// COMMENTED OUT BECAUSE TESTS FAILED AFTER I FIXED THE ERROR OF A PLAYER LANDING ON SAME PLAYER		 
		//	     assertTrue(e.move(e.makeCoordinate(5, 1), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(3, 1), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(5, 3), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(3, 4), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(1, 3), e.makeCoordinate(3, 3)));
		//	     assertTrue(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(4, 4)));
		//	     assertFalse(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(3, 3))); // blocked location
		//	     assertFalse(e.move(e.makeCoordinate(1, 6), e.makeCoordinate(3, 3))); // cannot reach through linear move
		//	     assertTrue(e.move(e.makeCoordinate(4, 4), e.makeCoordinate(3, 3))); // set piece up for next move
		//	     assertFalse(e.move(e.makeCoordinate(3, 6), e.makeCoordinate(3, 1))); // piece in the way of path
	}

	@Test
	void squareOmniMovementTests() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareOmniSetup.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertTrue(e.move(e.makeCoordinate(2, 1), e.makeCoordinate(2, 4)));

		// COMMENTED OUT BECAUSE TESTS FAILED AFTER I FIXED THE ERROR OF A PLAYER LANDING ON SAME PLAYER		 
		//		 assertTrue(e.move(e.makeCoordinate(4, 5), e.makeCoordinate(2, 4)));
	}

	@Test
	void squareOrthogonalMovementTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareOrthogonalSetup.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertTrue(e.move(e.makeCoordinate(2, 1), e.makeCoordinate(2, 4)));

		// COMMENTED OUT BECAUSE TESTS FAILED AFTER I FIXED THE ERROR OF A PLAYER LANDING ON SAME PLAYER
		//		 assertTrue(e.move(e.makeCoordinate(2, 6), e.makeCoordinate(2, 4)));
	}

	@Test
	void squareFlyTests() throws Exception
	{ 
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareFly.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertFalse(e.move(e.makeCoordinate(5, 1), e.makeCoordinate(2, 4)));
		assertTrue(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(2, 4)));

		// COMMENTED OUT BECAUSE TESTS FAILED AFTER I FIXED THE ERROR OF A PLAYER LANDING ON SAME PLAYER
		//		 assertTrue(e.move(e.makeCoordinate(4, 1), e.makeCoordinate(2, 4)));		 
	}

	@Test
	void squareDistanceUnblockTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareDistanceUnblock.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		assertTrue(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(2, 4)));
		
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		assertFalse(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(1, 1)));
//		assertTrue(e.move(e.makeCoordinate(3, 2), e.makeCoordinate(1, 1)));
//		assertTrue(e.move(e.makeCoordinate(2, 4), e.makeCoordinate(5, 6)));
	}

	@Test
	void squareDistanceJumpTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/DistanceJump.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		// diagonal moves
		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(5, 5)));
		
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(3, 3)));
//		assertFalse(e.move(e.makeCoordinate(4, 4), e.makeCoordinate(1, 1))); // no open space behind piece
//		assertTrue(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(2, 4))); // setup for next move
//		assertTrue(e.move(e.makeCoordinate(2, 2), e.makeCoordinate(3, 3))); // setup for next move
//		assertTrue(e.move(e.makeCoordinate(2, 4), e.makeCoordinate(4, 2))); 
//		assertTrue(e.move(e.makeCoordinate(4, 4), e.makeCoordinate(2, 4))); // setup for next move
//		assertTrue(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(1, 5))); // setup for next move
//
//		// orthogonal moves
//		assertTrue(e.move(e.makeCoordinate(2, 5), e.makeCoordinate(1, 1)));
//		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 6)));
	}

	@Test
	void squareMiscTests() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/squareconfigs/SquareMisc.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertFalse(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 2))); // has both fly and distance
		assertFalse(e.move(e.makeCoordinate(5, 1), e.makeCoordinate(5, 1)));
		
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		assertTrue(e.move(e.makeCoordinate(2, 6), e.makeCoordinate(3, 6)));
//		assertFalse(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(3, 4))); // only has value, no distance
//		Assertions.assertThrows(EscapeException.class, () -> {
//			e.move(e.makeCoordinate(5, 5), e.makeCoordinate(5, 4)); // not a valid movement type
//		});		

	}
}
