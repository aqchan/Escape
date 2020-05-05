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

package escape;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 29, 2020
 */
class OrthoSquareBetaEscapeGameTests
{
	 private EscapeGameBuilder egb;
	 private EscapeGameManager egm;
	 
	/**
	 * OrthoSquare Beta Escape GameTests
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		egb = new EscapeGameBuilder(new File("config/OrthoSquareEscapeGame.xml"));
		egm = egb.makeGameManager();
	}

	@Test
    void orthoSquareCoordinateTests() throws Exception
    { 
        // Test that you can only make an OrthoSquareCoordinate
        Coordinate  c1 = egm.makeCoordinate(2, 4);
        assertNotEquals(HexCoordinate.makeCoordinate(2, 4), c1);
    	assertEquals(OrthoSquareCoordinate.makeCoordinate(2, 4), c1);
    	assertNotEquals(SquareCoordinate.makeCoordinate(2, 4), c1);
    	
    	assertEquals(null, egm.makeCoordinate(0, 1));
    	assertEquals(null, egm.makeCoordinate(1, 0));
    	assertEquals(null, egm.makeCoordinate(0, 0));
    	assertEquals(null, egm.makeCoordinate(21, 5)); // exceeds max x-value
    	assertEquals(null, egm.makeCoordinate(5, 26)); // exceeds max y-value
    	assertEquals(null, egm.makeCoordinate(21, 26));
    }
	
	 @Test
	 void orthoSquareGetPieceAt() throws Exception
	 {
		 Coordinate c1 = egm.makeCoordinate(3, 4); 
		 Coordinate c2 = egm.makeCoordinate(3, 3); 

		 assertNotNull(egm.getPieceAt(c1));
		 assertNull(egm.getPieceAt(c2));
	 }
	 
	 @Test
	 void orthoSquareLinearMovementTests() throws Exception
	 {
		 EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/orthoconfigs/OrthoSquareLinear.xml"));
		 EscapeGameManager e = gameBuilder.makeGameManager();
		 
		 Assertions.assertThrows(EscapeException.class, () -> {
			 e.move(e.makeCoordinate(1, 1), e.makeCoordinate(2, 2)); // cannot have diagonal movement on orthosquare board
		 });	
		 
		 assertTrue(e.move(e.makeCoordinate(4, 4), e.makeCoordinate(4, 2)));
		 assertTrue(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(4, 5)));
		 assertTrue(e.move(e.makeCoordinate(4, 5), e.makeCoordinate(1, 5)));
		 assertFalse(e.move(e.makeCoordinate(1, 5), e.makeCoordinate(2, 4)));
		 assertFalse(e.move(e.makeCoordinate(1, 5), e.makeCoordinate(3, 4)));	 
	 }
	 
	 @Test
	 void orthoSquareOmniAndOrthogonalMovementTests() throws Exception
	 {
		 EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/orthoconfigs/OrthoSquareOmni.xml"));
		 EscapeGameManager e = gameBuilder.makeGameManager();
		
		 assertTrue(e.move(e.makeCoordinate(4, 4), e.makeCoordinate(4, 2)));
		 assertFalse(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(1, 5))); // not enough distance
		 assertTrue(e.move(e.makeCoordinate(4, 2), e.makeCoordinate(1, 1)));
		 assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(4, 6))); 	
		 assertNotNull(e.getPieceAt(e.makeCoordinate(4, 6)));
		 assertTrue(e.move(e.makeCoordinate(2, 2), e.makeCoordinate(1, 6))); // land on exit
		 assertNull(e.getPieceAt(e.makeCoordinate(1, 6)));
		 Assertions.assertThrows(EscapeException.class, () -> {
			 e.move(e.makeCoordinate(3, 3), e.makeCoordinate(3, 4));  // invalid movement type
		 });
		 
		 Assertions.assertThrows(EscapeException.class, () -> {
			 e.move(e.makeCoordinate(4, 6), e.makeCoordinate(5, 6)); // blocked space
		 });	
	 }
}
