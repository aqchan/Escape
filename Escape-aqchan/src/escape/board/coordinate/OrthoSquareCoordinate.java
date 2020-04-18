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

import java.util.Objects;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 13, 2020
 */
public class OrthoSquareCoordinate implements Coordinate
{
	private final int x;
    private final int y;
    
    private OrthoSquareCoordinate(int x, int y)
    {
    	this.x = x;
    	this.y = y;
    }
    
    public static OrthoSquareCoordinate makeCoordinate(int x, int y)
    {
    	if (x < 1 || y < 1) {
			throw new EscapeException("Coordinates for orthosquare boards must start at (1,1).");
    	}
    	return new OrthoSquareCoordinate(x, y);
    }
    
    
	/*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		int xVal = Math.abs(((OrthoSquareCoordinate) c).getX() - this.x);
		int yVal = Math.abs(((OrthoSquareCoordinate) c).getY() - this.y);
		return xVal + yVal;
	}
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OrthoSquareCoordinate)) {
			return false;
		}
		OrthoSquareCoordinate other = (OrthoSquareCoordinate) obj;
		return x == other.x && y == other.y;
	}
	
}
