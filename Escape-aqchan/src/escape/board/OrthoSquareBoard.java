/*******************************************************************************
F * This files was developed for CS4233: Object-Oriented Analysis & Design.
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
public class OrthoSquareBoard extends EscapeBoard
{
	Map<EscapeCoordinate, LocationType> orthoSquares;
	Map<EscapeCoordinate, EscapePiece> pieces;
	
	private final int xMax, yMax;
	
	/**
	 * OrthoSquare Board
	 * @param xMax the maximum x-dimension of board
	 * @param yMax the maximum y-dimension of board
	 */
	public OrthoSquareBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<EscapeCoordinate, EscapePiece>();
		orthoSquares = new HashMap<EscapeCoordinate, LocationType>();
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(EscapeCoordinate coord)
	{
		return pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, EscapeCoordinate coord)
	{
		// check if there is already a piece at the given coordinate
		if (pieces.containsKey(coord)) {
			if (isValidCapture(p, coord)) {

				if (isValidCoords(coord) && isValidLocation(coord)) {
					pieces.put(coord, p);
				}
										
				// remove the piece from the EXIT location after putting it down
				if (orthoSquares.get(coord) == LocationType.EXIT) {
					pieces.remove(coord, p);
				}
			}
		}
		else {
			// orthosquare boards can only be finite
			if (isValidCoords(coord) && isValidLocation(coord)) {
				pieces.put(coord, p);
			}
					
			// remove the piece from the EXIT location after putting it down
			if (orthoSquares.get(coord) == LocationType.EXIT) {
				pieces.remove(coord, p);
			}
		}
	}
	
	/**
	 * Removes piece at specified coordinate
	 * @param p an EscapePiece
	 * @param coord the coordinate to remove the piece at
	 */
	public void removePieceAt(EscapePiece p, EscapeCoordinate coord) 
	{
		pieces.remove(coord, p);
	}
	
	/**
	 * Determines if coordinates are within the constraints of the board
	 * @param coord an EscapeCoordinate
	 * @return true if the coordinate is within the boundaries of the board or false if it is not
	 */
	public boolean isValidCoords(EscapeCoordinate coord)
	{
		if (coord.getX() > xMax || coord.getY() > yMax || coord.getX() < 1 || coord.getY() < 1) {
			throw new EscapeException("At least one of the given coordinates are outside the boundaries of the board.");
		} 
		return true;
	}
	
	/**
	 * Determines if a piece can be placed on a particular coordinate
	 * @param coord an EscapeCoordinate
	 * @return true if the coordinate is a valid location to put a piece or false if it is not
	 */
	public boolean isValidLocation(EscapeCoordinate coord)
	{
		if (orthoSquares.get(coord) == LocationType.BLOCK) {
			throw new EscapeException("The given coordinate is not a valid location type.");
		} 
		return true;
	}
	
	/**
	 * Determines if a piece can be placed onto another piece to capture it
	 * @param p an EscapePiece
	 * @param coord the EscapeCoordinate the piece will land on
	 * @return true if the piece can capture the piece located at the given coordinate
	 */
	public boolean isValidCapture(EscapePiece p, EscapeCoordinate coord) {
		// check if the player type is the same
		if (p.getPlayer() == pieces.get(coord).getPlayer()) {
			throw new EscapeException("The piece cannot land on a piece of the same player type.");
		}
		return true;
	}
	
	/*
	 * 
	 * @see escape.board.Board#setLocationType(escape.board.coordinate.Coordinate, escape.board.LocationType)
	 */
	public void setLocationType(Coordinate c, LocationType lt)
	{
		orthoSquares.put((EscapeCoordinate) c, lt);
	}

	/**
	 * @return a piece map
	 */
	public Map<EscapeCoordinate, EscapePiece> getPieceMap()
	{
		return pieces;
	}
	
	/**
	 * @return a location map
	 */
	public Map<EscapeCoordinate, LocationType> getLocationMap()
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
