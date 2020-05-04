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
 * @version May 3, 2020
 */
public class HexPathfinding
{

	/**
	 * Creates 2D matrix based on coordinates on board, modeling a graph
	 * Initializes 2D matrix with pieces and location types
	 * Block locations are filled with 'b'
	 * Exit locations are filled with 'e'
	 * Pieces are filled with 'p'
	 * @return a 2D array of size [xMax+1][yMax+1] of 1's
	 */
	public static char[][] createGraph(HexBoard b, HexCoordinate src, HexCoordinate dest) {
		int xMax = b.getxMax();
		int yMax = b.getyMax();
		
		Map<HexCoordinate, EscapePiece> pieces = b.getPieceMap();
		Map<HexCoordinate, LocationType> locations = b.getLocationMap();
		
		HexCoordinate sFrom = (HexCoordinate) src;
		HexCoordinate sTo = (HexCoordinate) dest;
		
		char[][] matrix = new char[xMax + 1][yMax + 1];
		
		// initially set each space to '1'
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				matrix[i][j] = '1';
			}
		}
		
		// go through pieces hash map and put them on board if any
		for (Map.Entry mEntry : pieces.entrySet()) {
			matrix[((SquareCoordinate)mEntry.getKey()).getX()][((SquareCoordinate)mEntry.getKey()).getY()] = 'p';
		}
		
		// go through locations hash map and mark them as blocked or exit
		for (Map.Entry mEntry : locations.entrySet()) {
			if (mEntry.getValue() == LocationType.BLOCK) {
				matrix[((SquareCoordinate)mEntry.getKey()).getX()][((SquareCoordinate)mEntry.getKey()).getY()] = 'b';
			}
			else if (mEntry.getValue() == LocationType.EXIT) {
				matrix[((SquareCoordinate)mEntry.getKey()).getX()][((SquareCoordinate)mEntry.getKey()).getY()] = 'e';
			}
		}
		
		matrix[sFrom.getX()][sFrom.getY()] = 's';  // source
		matrix[sTo.getX()][sTo.getY()] = 'X';      // destination
		
		return matrix;
	}
	
	
	/**
	 * Pretty prints matrix
	 * @param matrix
	 */
	public static void printMatrix(char[][] matrix)
	{
		for (char[] x : matrix) {
			for (int y : x) {
				System.out.print((char)y + " ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Source: https://medium.com/@manpreetsingh.16.11.87/shortest-path-in-a-2d-array-java-653921063884
	 * @param matrix
	 * @param src
	 * @return distance of path or -1 if there is no path
	 */
	/*
	 * @see escape.pathfinding.PathfinderStrategy#pathExists(escape.board.Board, escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate, escape.piece.EscapePiece)
	 */
//	@Override
	public static int pathExists(HexBoard board, HexCoordinate src, HexCoordinate dest, EscapePiece piece) {

		char[][] matrix = createGraph(board, src, dest);
		printMatrix(matrix);
		
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
				
				List<Node> neighborList = addNeighbors(curr, matrix, src, dest, piece);
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
	private static List<Node> addNeighbors(Node curr, char[][] matrix, HexCoordinate src, HexCoordinate dest, EscapePiece piece) {
		List<Node> neighbors = new LinkedList<Node>();
		
		if (piece.getMovementPatternID() == MovementPatternID.LINEAR) {
			linearMovement(curr, matrix, src, dest, neighbors, piece);
		} else if (piece.getMovementPatternID() == MovementPatternID.OMNI) {
			omniMovement(curr, matrix, neighbors, piece);
		}	
		
		return neighbors;
	}
	
	
    public static boolean isValidNode(char[][] matrix, int x, int y, EscapePiece piece) {
    	Map<PieceAttributeID, PieceAttribute> map = new HashMap<>();
    	
    	for (PieceAttribute p : piece.getAttributes()) { map.put(p.getId(), p); } // initialize hash map
    	
    	boolean boardConstraints = !(x < 1 || x >= matrix.length || y < 1 || y >= matrix[0].length);
    	
    	if (map.containsKey(PieceAttributeID.FLY)) {
    		 return boardConstraints && (matrix[x][y] != '0');
    	}
    	else if (map.containsKey(PieceAttributeID.DISTANCE) && 
    			map.containsKey(PieceAttributeID.UNBLOCK) && map.get(PieceAttributeID.UNBLOCK).isBooleanValue() == true &&
    			map.containsKey(PieceAttributeID.JUMP) && map.get(PieceAttributeID.JUMP).isBooleanValue() == true) {
    		return boardConstraints &&
    	    		(matrix[x][y] != 'p') && // do something with this
    	    		(matrix[x][y] != '0') &&
    	    		(matrix[x][y] != 'e');
    	}
    	else if (map.containsKey(PieceAttributeID.DISTANCE) && map.containsKey(PieceAttributeID.UNBLOCK) &&
    			map.get(PieceAttributeID.UNBLOCK).isBooleanValue() == true ) {
    		return	boardConstraints &&
    	    		(matrix[x][y] != 'p') &&
    	    		(matrix[x][y] != '0') &&
    	    		(matrix[x][y] != 'e');
    	}
    	else if (map.containsKey(PieceAttributeID.DISTANCE) && map.containsKey(PieceAttributeID.JUMP) && 
    			map.get(PieceAttributeID.JUMP).isBooleanValue() == true) {
    		return boardConstraints &&
    	    		(matrix[x][y] != 'b') &&
    	    		(matrix[x][y] != '0') &&
    	    		(matrix[x][y] != 'e');
    		
    	} // otherwise, just distance
    	return boardConstraints &&
    			(matrix[x][y] != 'p') &&
    			(matrix[x][y] != '0') &&
    			(matrix[x][y] != 'b') &&
    			(matrix[x][y] != 'e');
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
    public static void linearMovement(Node curr, char[][] matrix, HexCoordinate src, HexCoordinate dest, List<Node> neighbors, EscapePiece piece) {
    	boolean vertical = src.getX() == dest.getX();
		boolean horizonal = src.getY() == dest.getY();
		
		// diagonal up & left and down & right (on matrix)
		boolean negativeDiagonal = src.getX() - dest.getX() == src.getY() - dest.getY();
		
		// diagonal up & right and down & left (on matrix)
		boolean positiveDiagonal = src.getX() - dest.getX() == (src.getY() - dest.getY()) * -1; // (5,1)->(3,3) and (1,5) ->(3,3)

		// up (on matrix)
		if (horizonal && isValidNode(matrix, curr.x - 1, curr.y, piece)) {
			neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
		}
		// down
		if (horizonal && isValidNode(matrix, curr.x + 1, curr.y, piece)) {
			neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
		}
		// left
		if (vertical && isValidNode(matrix, curr.x, curr.y - 1, piece)) {
			neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
		}
		// right
		if (vertical && isValidNode(matrix, curr.x, curr.y + 1, piece)) {
			neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
		}
		// diagonally down and right
		if (negativeDiagonal && isValidNode(matrix, curr.x + 1, curr.y + 1, piece)) {
			neighbors.add(new Node(curr.x + 1, curr.y + 1, curr.distance + 1));
		}
		// diagonally up and left
		if (negativeDiagonal && isValidNode(matrix, curr.x - 1, curr.y - 1, piece)) {
			neighbors.add(new Node(curr.x - 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally down and left
		if (positiveDiagonal && isValidNode(matrix, curr.x + 1, curr.y - 1, piece)) {
			neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
		}
		// diagonally up and right
		if (positiveDiagonal && isValidNode(matrix, curr.x - 1, curr.y + 1, piece)) {
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
    public static void omniMovement(Node curr, char[][] matrix, List<Node> neighbors, EscapePiece piece) {
    	// up (on matrix)
    	if (isValidNode(matrix, curr.x - 1, curr.y, piece)) {
    		neighbors.add(new Node(curr.x - 1, curr.y, curr.distance + 1));
    	}
    	// down
    	if (isValidNode(matrix, curr.x + 1, curr.y, piece)) {
    		neighbors.add(new Node(curr.x + 1, curr.y, curr.distance + 1));
    	}
    	// left
    	if (isValidNode(matrix, curr.x, curr.y - 1, piece)) {
    		neighbors.add(new Node(curr.x, curr.y - 1, curr.distance + 1));
    	}
    	// right
    	if (isValidNode(matrix, curr.x, curr.y + 1, piece)) {
    		neighbors.add(new Node(curr.x, curr.y + 1, curr.distance + 1));
    	}
    	// diagonally down and right
    	if (isValidNode(matrix, curr.x + 1, curr.y + 1, piece)) {
    		neighbors.add(new Node(curr.x + 1, curr.y + 1, curr.distance + 1));
    	}
    	// diagonally up and left
    	if (isValidNode(matrix, curr.x - 1, curr.y - 1, piece)) {
    		neighbors.add(new Node(curr.x - 1, curr.y - 1, curr.distance + 1));
    	}
    	// diagonally down and left
    	if (isValidNode(matrix, curr.x + 1, curr.y - 1, piece)) {
    		neighbors.add(new Node(curr.x + 1, curr.y - 1, curr.distance + 1));
    	}
    	// diagonally up and right
    	if (isValidNode(matrix, curr.x - 1, curr.y + 1, piece)) {
    		neighbors.add(new Node(curr.x - 1, curr.y + 1, curr.distance + 1));
    	}	
    }
}