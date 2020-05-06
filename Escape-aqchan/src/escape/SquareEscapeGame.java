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

import escape.board.*;
import escape.board.coordinate.*;
import escape.pathfinding.*;
import escape.piece.*;
import escape.util.*;

/**
 * Description
 * @version Apr 29, 2020
 */
public class SquareEscapeGame extends AbstractEscapeGame
{
	SquareBoard board = (SquareBoard) getBoard();

	/**
	 * Square Escape Game
	 * @param escapeGameInitializer
	 */
	public SquareEscapeGame(EscapeGameInitializer escapeGameInitializer)
	{
		super(escapeGameInitializer);
	}
	
	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		EscapePiece piece = getPieceAt(from);
		SquareCoordinate sFrom = (SquareCoordinate) from;
		SquareCoordinate sTo = (SquareCoordinate) to;

		// Get from piece type and give it the movement
		if (isFromPieceAtLocation(from) && !from.equals(to)) {
	    	int distance = SquarePathfinding.pathExists(board, sFrom, sTo, piece);
			
			if (checkDistanceRequirements(distance, piece)) {
				board.removePieceAt(piece, sFrom); // first remove the piece
				board.putPieceAt(piece, sTo); // then place it at its new spot on the board
				return true;
			}
		}
		return false;
	}
}
