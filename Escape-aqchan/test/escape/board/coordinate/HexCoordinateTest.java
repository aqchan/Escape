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
import org.junit.jupiter.api.Test;

/**
 * Description
 * @version Apr 14, 2020
 */
class HexCoordinateTest
{

	@Test
	void hexagonalDistance()
	{
    	HexCoordinate c1 = HexCoordinate.makeCoordinate(0,0);
    	HexCoordinate c2 = HexCoordinate.makeCoordinate(-1,2);
    	assertEquals(2, c1.distanceTo(c2));
    	
    	HexCoordinate c3 = HexCoordinate.makeCoordinate(0,0);
    	HexCoordinate c4 = HexCoordinate.makeCoordinate(0,3);
    	assertEquals(3, c3.distanceTo(c4));
    	
    	HexCoordinate c5 = HexCoordinate.makeCoordinate(-1,2);
    	HexCoordinate c6 = HexCoordinate.makeCoordinate(2,-2);
    	assertEquals(4, c5.distanceTo(c6));
    	
    	HexCoordinate c7 = HexCoordinate.makeCoordinate(-3,0);
    	HexCoordinate c8 = HexCoordinate.makeCoordinate(1,1);
    	assertEquals(5, c7.distanceTo(c8));
    	
    	HexCoordinate c9 = HexCoordinate.makeCoordinate(1,1);
    	HexCoordinate c10 = HexCoordinate.makeCoordinate(2,0);
    	assertEquals(1, c9.distanceTo(c10));
	}

}
