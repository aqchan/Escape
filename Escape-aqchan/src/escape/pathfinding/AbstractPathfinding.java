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

import java.util.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.*;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * Description
 * @version May 4, 2020
 */
public abstract class AbstractPathfinding
{

	/**
	 * Determines if the given node is valid to add to the neighbors list
	 * @param matrix a 2D array
	 * @param currX the current x-coordinate
	 * @param currY the current y-coordinate
	 * @param x the future x-coordinate
	 * @param y the future y-coordinate
	 * @param piece an EscapePiece
	 * @return true if the node is valid, otherwise false
	 */
	public static boolean isValidNode(Board b, char[][] matrix, Node currNode, int x, int y, EscapePiece piece, List<Node> neighbors)
	{
		boolean boardConstraints;
		if (b.getClass() == HexBoard.class) {
			boardConstraints = !(x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length);
		} else {
			boardConstraints = !(x < 1 || x >= matrix.length || y < 1 || y >= matrix[0].length);
		}

		Map<PieceAttributeID, PieceAttribute> map = new HashMap<>();
		for (PieceAttribute p : piece.getAttributes()) { map.put(p.getId(), p); } // initialize hash map

		if (map.containsKey(PieceAttributeID.FLY)) {
			return boardConstraints && (matrix[x][y] != '0');
		}
		else if (boardConstraints && matrix[x][y] == 'b' && map.containsKey(PieceAttributeID.DISTANCE) && 
				map.containsKey(PieceAttributeID.UNBLOCK) && map.get(PieceAttributeID.UNBLOCK).isBooleanValue()) {
			return (matrix[x][y] != 'p') && (matrix[x][y] != '0') && (matrix[x][y] != 'e');    		
		}
		else if (boardConstraints && matrix[x][y] == 'p' && map.containsKey(PieceAttributeID.DISTANCE) && 
				map.containsKey(PieceAttributeID.JUMP) && map.get(PieceAttributeID.JUMP).isBooleanValue() && isValidJump(currNode, x, y, matrix, neighbors)) {
			return (matrix[x][y] != 'p') && (matrix[x][y] != '0') && (matrix[x][y] != 'b') && (matrix[x][y] != 'e');
		}
		return boardConstraints && (matrix[x][y] != 'p') && (matrix[x][y] != '0') && (matrix[x][y] != 'b') && (matrix[x][y] != 'e');
	}

	/**
	 * Determines if the current node can jump over another node
	 * @param currX the current x-coordinate
	 * @param currY the current y-coordinate
	 * @param futureX the future x-coordinate
	 * @param futureY the future y-coordinate
	 * @param matrix a 2D array
	 * @param neighbors a list of the node's neighbors
	 * @return true if it is a valid jump, otherwise false
	 */
	public static boolean isValidJump(Node curr, int futureX, int futureY, char[][] matrix, List<Node> neighbors)
	{
		// up
		if (futureX - curr.x == -1 && futureY == curr.y && checkConstraintsForJump(futureX-1, futureY, curr.distance, matrix, neighbors)) {
			return true;
		}
		// down
		else if (futureX - curr.x == 1 && futureY == curr.y && checkConstraintsForJump(futureX+1, futureY, curr.distance, matrix, neighbors)) {
			return true;
		}
		// left
		else if (futureX == curr.x && futureY - curr.y == -1 && checkConstraintsForJump(futureX, futureY-1, curr.distance, matrix, neighbors)) {
			return true;
		}
		// right
		else if (futureX == curr.x && futureY - curr.y == 1 && checkConstraintsForJump(futureX, futureY+1, curr.distance, matrix, neighbors)) {
			return true;
		}
		// diagonally down and right
		else if (futureX - curr.x == 1 && futureY - curr.y == 1 && checkConstraintsForJump(futureX+1, futureY+1, curr.distance, matrix, neighbors)) {    
			return true;
		}
		// diagonally up and left
		else if (futureX - curr.x == -1 && futureY - curr.y == -1 && checkConstraintsForJump(futureX-1, futureY-1, curr.distance, matrix, neighbors)) {
			return true;
		}
		// diagonally down and left
		else if (futureX - curr.x == 1 && futureY - curr.y == -1 && checkConstraintsForJump(futureX+1, futureY-1, curr.distance, matrix, neighbors)) {
			return true;
		}
		// diagonally up and right
		else if (futureX - curr.x == -1 && futureY - curr.y == 1 && checkConstraintsForJump(futureX-1, futureY+1, curr.distance, matrix, neighbors)) {
			return true;
		}	
		return false;
	}

	/**
	 * Checks the constraints to help determine if a piece can jump over another piece
	 * Adds the node to the neighbor list if a piece can jump over another piece
	 * @param x the future x-coordinate
	 * @param y the future y-coordinate
	 * @param matrix a 2D array
	 * @param neighbors a list of the node's neighbors
	 * @return true if the piece can jump over another piece
	 */
	public static boolean checkConstraintsForJump(int x, int y, int currDistance, char[][] matrix, List<Node> neighbors)
	{
		boolean boardConstraints = !(x < 1 || x >= matrix.length || y < 1 || y >= matrix[0].length);
		if (boardConstraints && matrix[x][y] != 'p' && matrix[x][y] != 'b' && matrix[x][y] != '0' && matrix[x][y] != 'e') {
			neighbors.add(new Node(x, y, currDistance + 2));
			return true;
		}
		return false;
	}


	/**
	 * Only gets orthogonal neighbors
	 * @param curr the current node
	 * @param matrix a 2D array
	 * @param neighbors list of node's neighbors
	 * @param piece an EscapePiece
	 */
	public static void orthogonalMovement(Board b, Node curr, char[][] matrix, List<Node> neighbors, EscapePiece piece) {
		// down
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// up
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// left
		if (isValidNode(b, matrix, curr, curr.x, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		//right
		if (isValidNode(b, matrix, curr, curr.x, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
	}
}
