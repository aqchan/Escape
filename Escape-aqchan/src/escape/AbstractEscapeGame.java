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

package escape;

import static escape.board.coordinate.CoordinateID.*;
import java.util.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.*;
import escape.util.*;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * Description
 * @version Apr 29, 2020
 */
public abstract class AbstractEscapeGame implements EscapeGameManager<Coordinate>
{
	private EscapeGameInitializer e;
	private Board board;
	
	/**
	 * Abstract class
	 */
	public AbstractEscapeGame(EscapeGameInitializer escapeGameInitializer)
	{
		this.e = escapeGameInitializer;
		this.board = EscapeGameBuilder.makeBoard(escapeGameInitializer);
	}
	
	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 * Gets the piece at a given coordinate, returns null if there is none
	 * @param coordinate the given coordinate
	 * @return an EscapePiece
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coordinate)
	{
	    return board.getPieceAt(coordinate);
	}

	/*
	 * @see escape.EscapeGameManager#makeCoordinate(int, int)
	 * Determines the coordinate type and creates the respective coordinate
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return a Coordinate
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y)
	{	
		boolean boardConstraints = x > 0 && y > 0 && x <= e.getxMax() && y <= e.getyMax();
		
		return e.getCoordinateType() == HEX ? HexCoordinate.makeCoordinate(x, y)
	        	: e.getCoordinateType() == ORTHOSQUARE && boardConstraints ? OrthoSquareCoordinate.makeCoordinate(x, y)
	        	: e.getCoordinateType() == SQUARE && boardConstraints ? SquareCoordinate.makeCoordinate(x, y)
	        	: null;
	}
	
	/**
	 * Determines if a piece exists at the from location
	 * @param from a Coordinate
	 * @return true if there exists a piece at the from location, otherwise false
	 */
	public boolean isFromPieceAtLocation(Coordinate from)
	{
		return getPieceAt(from) == null ? false : true;
	}
	
	/**
	 * Determines if the move is within the distance requirements
	 * @param distance the total distance of the generated path
	 * @param piece an EscapePiece
	 * @return true if the distance is within the limit, otherwise false
	 */
	public boolean checkDistanceRequirements(int distance, EscapePiece piece)
	{
		Map<PieceAttributeID, PieceAttribute> map = new HashMap<>();
    	for (PieceAttribute p : piece.getAttributes()) { map.put(p.getId(), p); } // initialize hash map
    	
		if (distance > 0) { 
			if (map.containsKey(PieceAttributeID.FLY) && map.containsKey(PieceAttributeID.DISTANCE)) {
				return false;
			}
			else if (map.containsKey(PieceAttributeID.FLY)) {
				return distance <= map.get(PieceAttributeID.FLY).getIntValue();

			} else if (map.containsKey(PieceAttributeID.DISTANCE)) {
				return distance <= map.get(PieceAttributeID.DISTANCE).getIntValue();
			}
		}
		return false;
	}
	
	/**
	 * Gets the board
	 * @return
	 */
	public Board getBoard()
	{
		return this.board;
	}
}
