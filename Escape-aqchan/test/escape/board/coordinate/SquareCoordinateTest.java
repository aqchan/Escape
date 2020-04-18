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
import escape.piece.*;

/**
 * Description
 * @version Apr 13, 2020
 */
class SquareCoordinateTest
{
    @Test
    void onlyDiagonal()
    {
    	// diagonal up and right    	
    	SquareCoordinate c1 = SquareCoordinate.makeCoordinate(1,1);
    	SquareCoordinate c2 = SquareCoordinate.makeCoordinate(5,5);
    	assertEquals(4, c1.distanceTo(c2));
    	
    	// diagonal down and right
    	SquareCoordinate c3 = SquareCoordinate.makeCoordinate(8,3);
    	SquareCoordinate c4 = SquareCoordinate.makeCoordinate(5,6);
    	assertEquals(3, c3.distanceTo(c4));
    	
    	// diagonal up and left
    	SquareCoordinate c5 = SquareCoordinate.makeCoordinate(1,5);
    	SquareCoordinate c6 = SquareCoordinate.makeCoordinate(3,3);
    	assertEquals(2, c5.distanceTo(c6));
    	
    	// diagonal down and left
    	SquareCoordinate c7 = SquareCoordinate.makeCoordinate(4,8);
    	SquareCoordinate c8 = SquareCoordinate.makeCoordinate(1,5);
    	assertEquals(3, c7.distanceTo(c8));
    }
    
    @Test
    void onlyHorizontal()
    {
    	// horizontal left
    	SquareCoordinate c1 = SquareCoordinate.makeCoordinate(5,6);
    	SquareCoordinate c2 = SquareCoordinate.makeCoordinate(5,2);
    	assertEquals(4, c1.distanceTo(c2));
  
    	// horizontal right
    	SquareCoordinate c3 = SquareCoordinate.makeCoordinate(1,1);
    	SquareCoordinate c4 = SquareCoordinate.makeCoordinate(1,8);
    	assertEquals(7, c3.distanceTo(c4));
    }
    
    @Test
    void onlyVertical()
    {
    	// vertical up
    	SquareCoordinate c1 = SquareCoordinate.makeCoordinate(2,3);
    	SquareCoordinate c2 = SquareCoordinate.makeCoordinate(7,3);
    	assertEquals(5, c1.distanceTo(c2));
  
    	// vertical down
    	SquareCoordinate c3 = SquareCoordinate.makeCoordinate(5,7);
    	SquareCoordinate c4 = SquareCoordinate.makeCoordinate(4,7);
    	assertEquals(1, c3.distanceTo(c4));
    }

    @Test
    void mixedDirections()
    {
    	// mixed test 1
    	SquareCoordinate c1 = SquareCoordinate.makeCoordinate(1,2);
    	SquareCoordinate c2 = SquareCoordinate.makeCoordinate(3,5);
    	assertEquals(3, c1.distanceTo(c2));
    	
    	// mixed test 2
    	SquareCoordinate c3 = SquareCoordinate.makeCoordinate(1,3);
    	SquareCoordinate c4 = SquareCoordinate.makeCoordinate(5,9);
    	assertEquals(6, c3.distanceTo(c4));
    	
    	// mixed test 3
    	SquareCoordinate c5 = SquareCoordinate.makeCoordinate(7,7);
    	SquareCoordinate c6 = SquareCoordinate.makeCoordinate(1,4);
    	assertEquals(6, c5.distanceTo(c6));
    }
    
    @Test
   	void invalidCreationOfCoordinate()
   	{
   		Assertions.assertThrows(EscapeException.class, () -> {
   			SquareCoordinate c = SquareCoordinate.makeCoordinate(0,0);
   		});
   		
   		Assertions.assertThrows(EscapeException.class, () -> {
   			SquareCoordinate c = SquareCoordinate.makeCoordinate(-1,1);
   		});
   		
   		Assertions.assertThrows(EscapeException.class, () -> {
   			SquareCoordinate c = SquareCoordinate.makeCoordinate(1,-1);
   		});
   	}
    

}
