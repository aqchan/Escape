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

package escape.board;

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * Description
 * @version Apr 14, 2020
 */
public class OrthoSquareBoard implements Board<OrthoSquareCoordinate>
{
	Map<OrthoSquareCoordinate, LocationType> orthoSquares;
	Map<OrthoSquareCoordinate, EscapePiece> pieces;
	
	private final int xMax, yMax;
	public OrthoSquareBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<OrthoSquareCoordinate, EscapePiece>();
		orthoSquares = new HashMap<OrthoSquareCoordinate, LocationType>();
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(OrthoSquareCoordinate coord)
	{
		return pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, OrthoSquareCoordinate coord)
	{
		// orthosquare boards can only be finite
		if (isValidCoords(coord) && isValidLocation(coord)) {
			pieces.put(coord, p);
		}
				
		// remove the piece from the EXIT location after putting it down
		if (orthoSquares.get(coord) == LocationType.EXIT) {
			pieces.remove(coord, p);
		}
	}
	
	/**
	 * Removes piece at specified coordinate
	 * @param p
	 * @param coord
	 */
	public void removePieceAt(EscapePiece p, OrthoSquareCoordinate coord) 
	{
		pieces.remove(coord, p);
	}
	
	/**
	 * Determines if coordinates are within the constraints of the board
	 * @param coord
	 * @return true if the coordinate is within the boundaries of the board or false if it is not
	 */
	public boolean isValidCoords(OrthoSquareCoordinate coord)
	{
		if (coord.getX() > xMax || coord.getY() > yMax || coord.getX() < 1 || coord.getY() < 1) {
			throw new EscapeException("At least one of the given coordinates are outside the boundaries of the board.");
		} 
		return true;
	}
	
	/**
	 * Determines if a piece can be placed on a particular coordinate
	 * @param coord
	 * @return true if the coordinate is a valid location to put a piece or false if it is not
	 */
	public boolean isValidLocation(OrthoSquareCoordinate coord)
	{
		if (orthoSquares.get(coord) == LocationType.BLOCK) {
			throw new EscapeException("The given coordinate is not a valid location type.");
		} 
		return true;
	}
	
	public void setLocationType(Coordinate c, LocationType lt)
	{
		orthoSquares.put((OrthoSquareCoordinate) c, lt);
	}

	
	public Map<OrthoSquareCoordinate, EscapePiece> getPieceMap()
	{
		return pieces;
	}
	
	public Map<OrthoSquareCoordinate, LocationType> getLocationMap()
	{
		return orthoSquares;
	}
	
	/**
	 * @return xMax
	 */
	public int getxMax()
	{
		return xMax;
	}
	
	/**
	 * @return yMax
	 */
	public int getyMax()
	{
		return yMax;
	}
	
	
}
