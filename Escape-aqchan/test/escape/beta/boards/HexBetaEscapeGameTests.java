/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.beta.boards;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * @version Apr 24, 2020
 */
class HexBetaEscapeGameTests
{
    private EscapeGameBuilder egb;
    private EscapeGameManager egm;
    
	/**
	 * Hex Beta Escape GameTests
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		egb = new EscapeGameBuilder(new File("config/betaboardconfigs/HexEscapeGame.xml"));
		egm = egb.makeGameManager();
	}
	
    // makeCoordinate() tests
    @Test
    void hexCoordinateTests() throws Exception
    {
        // Test that you only made a HexCoordinate
        Coordinate  c1 = egm.makeCoordinate(-1, 7);
        assertEquals(HexCoordinate.makeCoordinate(-1, 7), c1);
    	assertNotEquals(OrthoSquareCoordinate.makeCoordinate(-1, 7), c1);
    	assertNotEquals(SquareCoordinate.makeCoordinate(-1, 7), c1);
    }
    
    // getPieceAt() tests
    @Test
    void hexGetPieceAt() throws Exception
    {
        Coordinate  c1 = egm.makeCoordinate(3, 4); 
        Coordinate  c2 = egm.makeCoordinate(3, 3); 

        assertNotNull(egm.getPieceAt(c1));
        assertNull(egm.getPieceAt(c2));
    }
    
       
    // move() tests
    @Test
    void hexLinearTests() throws Exception
    {
    	EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/hexconfigs/HexLinear.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		 
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		assertFalse(e.move(e.makeCoordinate(1, 0), e.makeCoordinate(2, 0))); // from is null
//    	assertFalse(e.move(e.makeCoordinate(0, 0), e.makeCoordinate(0, 0))); // from = to
    	
    	// LINEAR movement pattern
//    	assertTrue(e.move(e.makeCoordinate(-2, 2), e.makeCoordinate(2, -2))); 
//    	assertTrue(e.move(e.makeCoordinate(0, -2), e.makeCoordinate(0, 1))); 
//    	assertFalse(e.move(e.makeCoordinate(0, 1), e.makeCoordinate(2, 0))); 
//    	assertTrue(e.move(e.makeCoordinate(0, 1), e.makeCoordinate(0, -3))); 
//    	assertTrue(e.move(e.makeCoordinate(0, -3), e.makeCoordinate(-3, 0))); 
//    	assertTrue(e.move(e.makeCoordinate(-3, 0), e.makeCoordinate(2, 0))); 
//    	assertTrue(e.move(e.makeCoordinate(2, 0), e.makeCoordinate(1, 0))); 
    }
    
    @Test
    void hexOmniTests() throws Exception
    {
    	EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/hexconfigs/HexOmni.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		 
    	// OMNI movement pattern
    	assertTrue(e.move(e.makeCoordinate(-2, 2), e.makeCoordinate(2, -2))); 
    	
    	// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//    	assertTrue(e.move(e.makeCoordinate(0, -2), e.makeCoordinate(0, 1))); 
//    	assertTrue(e.move(e.makeCoordinate(0, 1), e.makeCoordinate(1, -1))); 
//    	assertTrue(e.move(e.makeCoordinate(1, -1), e.makeCoordinate(-3, 0))); 
    }
    
    @Test
    void hexInfiniteX() throws Exception 
    {
    	EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/hexconfigs/HexInfiniteX.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();

		assertTrue(e.move(e.makeCoordinate(0, 5), e.makeCoordinate(-3, -2))); 
		
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		Assertions.assertThrows(EscapeException.class, () -> {
//			e.move(e.makeCoordinate(0, 0), e.makeCoordinate(0, 1)); // nonexistent movement type		
//		});
//		 	
//		Assertions.assertThrows(EscapeException.class, () -> {
//			e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 2)); // invalid movement type
//		});
	}
    
    @Test
    void hexInfiniteY() throws Exception 
    {
    	EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/betaboardconfigs/hexconfigs/HexInfiniteY.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertTrue(e.move(e.makeCoordinate(5, 0), e.makeCoordinate(0, 0))); 
		
		// COMMENTED OUT BECAUSE OF PLAYER TURN RULE
//		assertTrue(e.move(e.makeCoordinate(1, -1), e.makeCoordinate(-1, 1)));  // jump
    }

}
