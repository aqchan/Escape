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

import java.util.List;
import escape.board.coordinate.*;
import escape.pathfinding.Node;
import escape.piece.EscapePiece;

/**
 * Description
 * @version May 3, 2020
 */
public interface EscapeCoordinate
{
    
	
	List<Node> addNeighbors(Node curr, char[][] matrix, EscapeCoordinate src, SquareCoordinate dest, EscapePiece piece);

    void diagonalMovement(Node curr, char[][] matrix, List<Node> neighbors, EscapePiece piece);
    
    

}
