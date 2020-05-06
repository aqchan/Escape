/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece; 


/**
 * An example of how a Board might be implemented. This board has
 * square coordinates and finite bounds, represented by xMax and yMax.
 * All methods required by the Board interface have been implemented. Students
 * would naturally add methods based upon their design.
 * @version Apr 2, 2020
 */
public class SquareBoard implements Board<SquareCoordinate>
{
	Map<SquareCoordinate, EscapePiece> pieces;
	Map<SquareCoordinate, LocationType> squares;
	
	private final int xMax, yMax;
	
	/**
	 * Square Board
	 * @param xMax the maximum x-dimension of board
	 * @param yMax the maximum y-dimension of board
	 */
	public SquareBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<SquareCoordinate, EscapePiece>();
		squares = new HashMap<SquareCoordinate, LocationType>();
	}
	
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(SquareCoordinate coord)
	{
		return pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, SquareCoordinate coord)
	{
		// square boards can only be finite
		if (isValidCoords(coord) && isValidLocation(coord)) {
			pieces.put(coord, p);
		}
		
		// remove the piece from the EXIT location after putting it down
		if (squares.get(coord) == LocationType.EXIT) {
			pieces.remove(coord, p);
		}
	}
	
	/**
	 * Removes piece at specified coordinate
	 * @param p an EscapePiece
	 * @param coord the coordinate to remove the piece at
	 */
	public void removePieceAt(EscapePiece p, SquareCoordinate coord) 
	{
		pieces.remove(coord, p);
	}
	
	/**
	 * Determines if coordinates are within the constraints of the board
	 * @param coord a SquareCoordinate
	 * @return true if the coordinate is within the boundaries of the board or false if it is not
	 */
	public boolean isValidCoords(SquareCoordinate coord)
	{
		if (coord.getX() > xMax || coord.getY() > yMax || coord.getX() < 1 || coord.getY() < 1) {
			throw new EscapeException("At least one of the given coordinates are outside the boundaries of the board.");
		} 
		return true;
	}
	
	/**
	 * Determines if a piece can be placed on a particular coordinate
	 * @param coord a SquareCoordinate
	 * @return true if the coordinate is a valid location to put a piece or false if it is not
	 */
	public boolean isValidLocation(SquareCoordinate coord)
	{
		if (squares.get(coord) == LocationType.BLOCK) {
			throw new EscapeException("The given coordinate is not a valid location type.");
		} 
		return true;
	}
	
	/*
	 * @see escape.board.Board#setLocationType(escape.board.coordinate.Coordinate, escape.board.LocationType)
	 */
	public void setLocationType(Coordinate c, LocationType lt)
	{
		squares.put((SquareCoordinate) c, lt);
	}
	
	/**
	 * @return a piece map
	 */
	public Map<SquareCoordinate, EscapePiece> getPieceMap()
	{
		return pieces;
	}
	
	/**
	 * @return a location map
	 */
	public Map<SquareCoordinate, LocationType> getLocationMap()
	{
		return squares;
	}
	
	/**
	 * @return maximum x-dimension of board
	 */
	public int getxMax() {
		return xMax;
	}
	
	/**
	 * @return maximum y-dimension of board
	 */
	public int getyMax() {
		return yMax;
	}
}
