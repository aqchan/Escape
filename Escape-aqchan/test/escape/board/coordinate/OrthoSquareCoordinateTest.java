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

package escape.board.coordinate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 13, 2020
 */
class OrthoSquareCoordinateTest
{

	@Test
	void diagonalMoves()
	{
		// diagonal up and right
    	OrthoSquareCoordinate c1 = OrthoSquareCoordinate.makeCoordinate(1,1);
    	OrthoSquareCoordinate c2 = OrthoSquareCoordinate.makeCoordinate(2,2);
    	assertEquals(2, c1.distanceTo(c2));
    	
    	// diagonal down and right
    	OrthoSquareCoordinate c3 = OrthoSquareCoordinate.makeCoordinate(3,1);
    	OrthoSquareCoordinate c4 = OrthoSquareCoordinate.makeCoordinate(1,3);
    	assertEquals(4, c3.distanceTo(c4));
    	
    	// diagonal up and left
    	OrthoSquareCoordinate c5 = OrthoSquareCoordinate.makeCoordinate(1,6);
    	OrthoSquareCoordinate c6 = OrthoSquareCoordinate.makeCoordinate(5,2);
    	assertEquals(8, c5.distanceTo(c6));
    	
    	// diagonal up and left
    	OrthoSquareCoordinate c7 = OrthoSquareCoordinate.makeCoordinate(8,4);
    	OrthoSquareCoordinate c8 = OrthoSquareCoordinate.makeCoordinate(6,2);
    	assertEquals(4, c7.distanceTo(c8));	
	}
	
	@Test
	void horizontalMoves()
	{
		// horizontal left
    	OrthoSquareCoordinate c1 = OrthoSquareCoordinate.makeCoordinate(7,3);
    	OrthoSquareCoordinate c2 = OrthoSquareCoordinate.makeCoordinate(7,2);
    	assertEquals(1, c1.distanceTo(c2));
    	
    	// horizontal right
    	OrthoSquareCoordinate c3 = OrthoSquareCoordinate.makeCoordinate(4,5);
    	OrthoSquareCoordinate c4 = OrthoSquareCoordinate.makeCoordinate(4,8);
    	assertEquals(3, c3.distanceTo(c4));
	}
	
	@Test
	void verticalMoves()
	{
		// vertical up
    	OrthoSquareCoordinate c1 = OrthoSquareCoordinate.makeCoordinate(1,6);
    	OrthoSquareCoordinate c2 = OrthoSquareCoordinate.makeCoordinate(8,6);
    	assertEquals(7, c1.distanceTo(c2));
    	
    	// vertical down
    	OrthoSquareCoordinate c3 = OrthoSquareCoordinate.makeCoordinate(3,7);
    	OrthoSquareCoordinate c4 = OrthoSquareCoordinate.makeCoordinate(1,7);
    	assertEquals(2, c3.distanceTo(c4));
	}
	
	@Test
	void mixedMoves()
	{
		// mixed test 1
    	OrthoSquareCoordinate c1 = OrthoSquareCoordinate.makeCoordinate(1,2);
    	OrthoSquareCoordinate c2 = OrthoSquareCoordinate.makeCoordinate(3,5);
    	assertEquals(5, c1.distanceTo(c2));
    	
    	// mixed test 2
    	OrthoSquareCoordinate c3 = OrthoSquareCoordinate.makeCoordinate(4,4);
    	OrthoSquareCoordinate c4 = OrthoSquareCoordinate.makeCoordinate(2,8);
    	assertEquals(6, c3.distanceTo(c4));
    	
    	// mixed test 3
    	OrthoSquareCoordinate c5 = OrthoSquareCoordinate.makeCoordinate(5,7);
    	OrthoSquareCoordinate c6 = OrthoSquareCoordinate.makeCoordinate(8,1);
    	assertEquals(9, c5.distanceTo(c6));
	}
	
    @Test
	void invalidCreationOfCoordinate()
	{
		Assertions.assertThrows(EscapeException.class, () -> {
			OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(0,0);
		});
		
		Assertions.assertThrows(EscapeException.class, () -> {
			OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(-1,1);
		});
		
		Assertions.assertThrows(EscapeException.class, () -> {
			OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(1,-1);
		});
	}

}
