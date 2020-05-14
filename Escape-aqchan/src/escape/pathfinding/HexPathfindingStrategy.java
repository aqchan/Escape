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
 * Description
 * @version May 3, 2020
 */
public class HexPathfindingStrategy extends AbstractPathfinding implements PathfindingStrategy
{
	static Map<EscapeCoordinate, EscapePiece> pieces;
	static Map<EscapeCoordinate, LocationType> locations;

	/**
	 * Gets the new max values if the board is infinite
	 * @param b the board
	 * @param src the starting coordinate
	 * @param dest the ending coordinate
	 * @return a EscapeCoordinate with the maximum values
	 */
	public static HexCoordinate getMaxValues(EscapeBoard b, EscapeCoordinate src, EscapeCoordinate dest) {		
		int xMax = b.getxMax(), yMax = b.getyMax();

		// "infinite" in x direction		
		if (xMax == 0) { 	
			for (Map.Entry mEntry : pieces.entrySet()) {	
				xMax = Math.max(((EscapeCoordinate)mEntry.getKey()).getX(), xMax);
			}

			for (Map.Entry mEntry : locations.entrySet()) {	
				xMax = Math.max(((EscapeCoordinate)mEntry.getKey()).getX(), xMax);
			}

			xMax = Math.max(src.getX(), Math.max(dest.getX(), xMax));
		} 

		// "infinite" in y direction		
		if (yMax == 0) {
			for (Map.Entry mEntry : pieces.entrySet()) {	
				yMax = Math.max(((EscapeCoordinate)mEntry.getKey()).getY(), yMax);							
			}

			for (Map.Entry mEntry : locations.entrySet()) {	
				yMax = Math.max(((EscapeCoordinate)mEntry.getKey()).getY(), yMax);
			}

			yMax = Math.max(src.getY(), Math.max(dest.getY(), yMax));
		}
		return HexCoordinate.makeCoordinate(xMax, yMax);
	}


	/**
	 * Gets the smallest x- and y-values on the board to later use as offsets
	 * @param b the board
	 * @param src the starting coordinate
	 * @param dest the ending coordinate
	 * @return a EscapeCoordinate with the minimum values
	 */
	public static HexCoordinate getMinValues(EscapeBoard b, EscapeCoordinate src, EscapeCoordinate dest) {
		int minX = 0, minY = 0;

		// go through pieces to find smallest x or y value
		for (Map.Entry mEntry : pieces.entrySet()) {	
			minX = Math.min(((EscapeCoordinate)mEntry.getKey()).getX(), minX);
			minY = Math.min(((EscapeCoordinate)mEntry.getKey()).getY(), minY);
		}

		// go through locations to find smallest x or y value
		for (Map.Entry mEntry : locations.entrySet()) {	
			minX = Math.min(((EscapeCoordinate)mEntry.getKey()).getX(), minX);
			minY = Math.min(((EscapeCoordinate)mEntry.getKey()).getY(), minY);
		}

		// check source and destination x and y values
		minX = Math.min(src.getX(), Math.min(dest.getX(), minX));
		minY = Math.min(src.getY(), Math.min(dest.getY(), minY));

		return HexCoordinate.makeCoordinate(minX, minY);
	}


	/**
	 * Source: https://medium.com/@manpreetsingh.16.11.87/shortest-path-in-a-2d-array-java-653921063884
	 * @param matrix
	 * @param src
	 * @return distance of path or -1 if there is no path
	 */
	public int pathExists(EscapeBoard board, EscapeCoordinate src, EscapeCoordinate dest, EscapePiece piece) {
		pieces = board.getPieceMap();
		locations = board.getLocationMap();

		EscapeCoordinate minValues = getMinValues(board, src, dest);
		EscapeCoordinate maxValues = getMaxValues(board, src, dest);

		char[][] matrix = EscapeBoard.createGraph(board, src, dest, minValues, maxValues);

		int xOffset = Math.abs(minValues.getX());
		int yOffset = Math.abs(minValues.getY());

		Node source = new Node(src.getX() + xOffset, src.getY() + yOffset, 0);
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
			case LINEAR:
				linearMovement(b, curr, matrix, src, dest, neighbors, piece);
				break;
			case OMNI:
				omniMovement(b, curr, matrix, neighbors, piece);
				break;
			default:
				throw new EscapeException("Cannot have diagonal movement on an orthosquare board.");
		}
		return neighbors;
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
		boolean xValConstant = src.getX() == dest.getX();
		boolean yValConstant = src.getY() == dest.getY();
		boolean negativeDiagonal = (Math.abs(src.getX() - dest.getX()) == Math.abs(src.getY() - dest.getY()));

		// up (hex board)
		if (xValConstant && isValidNode(b, matrix, curr, curr.x, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
		// up and right
		if (yValConstant && isValidNode(b, matrix, curr, curr.x + 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// down and right
		if (negativeDiagonal && isValidNode(b, matrix, curr,  curr.x + 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// down
		if (xValConstant && isValidNode(b, matrix, curr, curr.x, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		// down and left
		if (yValConstant && isValidNode(b, matrix, curr, curr.x - 1, curr.y , piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// up and left
		if (negativeDiagonal && isValidNode(b, matrix, curr, curr.x - 1, curr.y + 1, piece, neighbors)) {
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
		// up (hex board)
		if (isValidNode(b, matrix, curr, curr.x, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
		// up and right
		if (isValidNode(b, matrix, curr, curr.x + 1, curr.y, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// down and right
		if (isValidNode(b, matrix, curr,  curr.x + 1, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// down
		if (isValidNode(b, matrix, curr, curr.x, curr.y - 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		// down and left
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y , piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// up and left
		if (isValidNode(b, matrix, curr, curr.x - 1, curr.y + 1, piece, neighbors)) {
			neighbors.add(new Node(curr.x - 1, curr.y + 1, curr.distance + 1));
		}
	}
}