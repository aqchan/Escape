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

package escape.pathfinding;

import escape.board.EscapeBoard;
import escape.board.coordinate.EscapeCoordinate;
import escape.piece.EscapePiece;

/**
 * Pathfinding Context
 * @version May 13, 2020
 */
public class PathfindingContext
{
	private PathfindingStrategy strategy;
	
	public void setPathfindingStrategy(PathfindingStrategy strategy) {
		this.strategy = strategy;
	}

	public int pathfind(EscapeBoard board, EscapeCoordinate src, EscapeCoordinate dest, EscapePiece piece) {
		return strategy.pathExists(board, src, dest, piece);
	}
}
