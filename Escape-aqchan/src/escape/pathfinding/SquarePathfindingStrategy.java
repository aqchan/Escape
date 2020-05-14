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
import escape.exception.EscapeException;
import escape.piece.*;


/**
 * Square Path Finding
 * @version May 1, 2020
 */
public class SquarePathfindingStrategy extends AbstractPathfinding implements PathfindingStrategy
{	
	/**
	 * Source: https://medium.com/@manpreetsingh.16.11.87/shortest-path-in-a-2d-array-java-653921063884
	 * @param matrix
	 * @param src
	 * @return distance of path or -1 if there is no path
	 */
	public int pathExists(EscapeBoard board, EscapeCoordinate src, EscapeCoordinate dest, EscapePiece piece) {
		char[][] matrix = EscapeBoard.createGraph(board, src, dest);

		Node source = new Node(src.getX(), src.getY(), 0);
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(source);

		while(!queue.isEmpty()) {
			Node curr = queue.poll();
			if (matrix[curr.x][curr.y] == 'X' ) {
				return curr.distance;
			}
			else {
				matrix[curr.x][curr.y] = '0'; // visited
				List<Node> neighborList = addNeighbors(board, curr, matrix, src, dest, piece);
				queue.addAll(neighborList);
			}	
		}
		return -1;
	}

	/**
	 * Gets a list of neighbors to determine movement patterns
	 * @param curr the current node
	 * @param matrix a 2D array
	 * @param src the starting location
	 * @param dest the ending location
	 * @param piece an EscapePiece
	 * @return a list of neighbors
	 */
	private static List<Node> addNeighbors(Board b, Node curr, char[][] matrix, EscapeCoordinate src, EscapeCoordinate dest, EscapePiece piece) {
		List<Node> neighbors = new LinkedList<Node>();
		MovementPatternID m = piece.getMovementPatternID();

		switch(m) {
			case DIAGONAL:
				diagonalMovement(b, curr, matrix, neighbors, piece);
				break;
			case LINEAR:
				linearMovement(b, curr, matrix, src, dest, neighbors, piece);
				break;
			case OMNI:
				omniMovement(b, curr, matrix, neighbors, piece);
				break;
			case ORTHOGONAL:
				orthogonalMovement(b, curr, matrix, neighbors, piece);
				break;
		}
		return neighbors;
	}


	/**
	 * Only gets diagonal neighbors
	 * @param curr the current node
	 * @param matrix a 2D array
	 * @param neighbors list of node's neighbors
	 * @param piece an EscapePiece
	 */
	public static void diagonalMovement(Board b, Node curr, char[][] matrix, List<Node> neighbors, EscapePiece piece) {
		// diagonally up and right
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y + 1, curr.distance + 1));
		}
		// diagonally down and left
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally up and left
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally down and right
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y + 1, curr.distance + 1));
		}
	}


	/**
	 * Only gets linear neighbors
	 * @param curr the current node
	 * @param matrix a 2D array
	 * @param src the starting location
	 * @param dest the ending location
	 * @param neighbors list of node's neighbors
	 * @param piece an EscapePiece
	 */
	public static void linearMovement(Board b, Node curr, char[][] matrix, EscapeCoordinate src, EscapeCoordinate dest, List<Node> neighbors, EscapePiece piece) {
		boolean vertical = src.getX() == dest.getX();
		boolean horizonal = src.getY() == dest.getY();
		boolean negativeDiagonal = src.getX() - dest.getX() == src.getY() - dest.getY();
		boolean positiveDiagonal = src.getX() - dest.getX() == (src.getY() - dest.getY()) * -1; // (5,1)->(3,3) and (1,5) ->(3,3)

		// up (on matrix)
		if (horizonal && isValidNode(b, matrix, curr, curr.x - 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// down
		if (horizonal && isValidNode(b, matrix, curr, curr.x + 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// left
		if (vertical && isValidNode(b, matrix, curr,  curr.x, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		// right
		if (vertical && isValidNode(b, matrix, curr, curr.x, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
		// diagonally down and right
		if (negativeDiagonal && isValidNode(b, matrix, curr, curr.x + 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y + 1, curr.distance + 1));
		}
		// diagonally up and left
		if (negativeDiagonal && isValidNode(b, matrix, curr, curr.x - 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally down and left
		if (positiveDiagonal && isValidNode(b, matrix, curr, curr.x + 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally up and right
		if (positiveDiagonal && isValidNode(b, matrix, curr, curr.x - 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y + 1, curr.distance + 1));
		}
	}


	/**
	 * Only gets omni neighbors
	 * @param curr the current node
	 * @param matrix a 2D array
	 * @param neighbors list of node's neighbors
	 * @param piece an EscapePiece
	 */
	public static void omniMovement(Board b, Node curr, char[][] matrix, List<Node> neighbors, EscapePiece piece) {
		// up (on matrix)
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// down
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// left
		if (isValidNode(b, matrix, curr, curr.x, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		// right
		if (isValidNode(b, matrix, curr, curr.x, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
		// diagonally down and right
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y + 1, curr.distance + 1));
		}
		// diagonally up and left
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally down and left
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally up and right
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y + 1, curr.distance + 1));
		}	
	}


}
