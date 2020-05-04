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

package escape;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.*;
import escape.board.coordinate.*;
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
		egb = new EscapeGameBuilder(new File("config/HexEscapeGame.xml"));
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
    
   
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    // move() tests
    @Test
    void hexMoveTests() throws Exception
    {
        // Exercise the game now: make moves, check the board, etc.
//    	assertFalse(egm.move(egm.makeCoordinate(1, 0), egm.makeCoordinate(2, 0))); // from is null
//    	assertFalse(egm.move(egm.makeCoordinate(0, 0), egm.makeCoordinate(0, 0))); // from = to
//    	assertFalse(egm.move(egm.makeCoordinate(0, 0), egm.makeCoordinate(0, -3))); // to is blocked
    	
    	// LINEAR movement pattern

        // test for a piece in the way of the path
        
    }
    


}
