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
public class HexBoard extends EscapeBoard
{
	Map<EscapeCoordinate, LocationType> hexagons;
	Map<EscapeCoordinate, EscapePiece> pieces;
	
	private final int xMax, yMax;
	
	/**
	 * Hex Board
	 * @param xMax the maximum x-dimension of board
	 * @param yMax the maximum y-dimension of board
	 */
	public HexBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<EscapeCoordinate, EscapePiece>();
		hexagons = new HashMap<EscapeCoordinate, LocationType>();
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
				if (hexagons.get(coord) == LocationType.EXIT) {
					pieces.remove(coord, p);
				}
			}
		}
		else {
			// hex boards must have at least one infinite direction
			if (isValidCoords(coord) && isValidLocation(coord)) {
				pieces.put(coord, p);
			}
			
			// remove the piece from the EXIT location after putting it down
			if (hexagons.get(coord) == LocationType.EXIT) {
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
	 * @param coord a EscapeCoordinate
	 * @return true if the coordinate is within the boundaries of the board or false if it is not
	 */
	public boolean isValidCoords(EscapeCoordinate coord)
	{
		// infinite in both directions
		if (xMax == 0 && yMax == 0) {
			return true;
		}
		// infinite in x direction
		else if (xMax == 0 && yMax != 0) {
			if (coord.getY() <= yMax) {
				return true;
			}
		}
		// infinite in y direction
		else if (yMax == 0 && xMax != 0) {
			if (coord.getX() <= xMax) {
				return true;
			}
		}
		throw new EscapeException("At least one of the given coordinates are outside the boundaries of the board.");
	}
	
	/**
	 * Determines if a piece can be placed on a particular coordinate
	 * @param coord a EscapeCoordinate
	 * @return true if the coordinate is a valid location to put a piece or false if it is not
	 */
	public boolean isValidLocation(EscapeCoordinate coord)
	{
		if (hexagons.get(coord) == LocationType.BLOCK) {
			throw new EscapeException("The given coordinate is not a valid location type.");
		} 
		return true;
	}
	
	/**
	 * Determines if a piece can be placed onto another piece to capture it
	 * @param p an EscapePiece
	 * @param coord the SquareCoordinate the piece will land on
	 * @return true if the piece can capture the piece located at the given coordinate
	 */
	public boolean isValidCapture(EscapePiece p, EscapeCoordinate coord) {
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
		hexagons.put((EscapeCoordinate) c, lt);
	}

	/**
	 * @return the piece map
	 */
	public Map<EscapeCoordinate, EscapePiece> getPieceMap()
	{
		return pieces;
	}
	
	/**
	 * @return the location map of hexes
	 */
	public Map<EscapeCoordinate, LocationType> getLocationMap()
	{
		return hexagons;
	}
	
	/**
	 * @return xMax
	 */
	public int getxMax() {
		return xMax;
	}
	
	/**
	 * @return yMax
	 */
	public int getyMax() {
		return yMax;
	}
}
