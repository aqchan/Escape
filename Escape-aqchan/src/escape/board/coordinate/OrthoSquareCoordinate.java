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

/**
 * Description
 * @version Apr 13, 2020
 */
public class OrthoSquareCoordinate extends EscapeCoordinate
{
	private final int x;
	private final int y;

	/**
	 * OrthoSquareCoordinate
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */ 
	private OrthoSquareCoordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Static factory method that creates a coordinate
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return an OrthoSquareCoordinate
	 */
	public static OrthoSquareCoordinate makeCoordinate(int x, int y)
	{
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

	/*
	 * @see escape.board.coordinate.EscapeCoordinate#getX()
	 */
	public int getX()
	{
		return x;
	}

	/*
	 * @see escape.board.coordinate.EscapeCoordinate#getY()
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
