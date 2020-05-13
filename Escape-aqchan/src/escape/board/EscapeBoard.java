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

import java.util.Map;
import escape.board.coordinate.EscapeCoordinate;
import escape.piece.EscapePiece;

/**
 * Description
 * @version May 13, 2020
 */
public abstract class EscapeBoard implements Board<EscapeCoordinate>
{
	/**
	 * @return the piece map
	 */
	public abstract Map<EscapeCoordinate, EscapePiece> getPieceMap();
	
	/**
	 * @return the location map
	 */
	public abstract Map<EscapeCoordinate, LocationType> getLocationMap();
	
	/**
	 * @return xMax
	 */
	public abstract int getxMax();
	
	/**
	 * @return yMax
	 */
	public abstract int getyMax();
	
	
	/**
	 * Creates 2D matrix based on coordinates on either Square or OrthoSquare boards, modeling a graph
	 * Initializes 2D matrix with pieces and location types
	 * Block locations are filled with 'b'
	 * Exit locations are filled with 'e'
	 * Pieces are filled with 'p'
	 * @return a 2D array of size [xMax+1][yMax+1] of 1's
	 */
	public static char[][] createGraph(EscapeBoard b, EscapeCoordinate src, EscapeCoordinate dest) {
		int xMax = b.getxMax();
		int yMax = b.getyMax();
		
		Map<EscapeCoordinate, EscapePiece> pieces = b.getPieceMap();
		Map<EscapeCoordinate, LocationType> locations = b.getLocationMap();
		
		EscapeCoordinate sFrom = (EscapeCoordinate) src;
		EscapeCoordinate sTo = (EscapeCoordinate) dest;
		
		char[][] matrix = new char[xMax + 1][yMax + 1];
		
		// initially set each space to '1'
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				matrix[i][j] = '1';
			}
		}
		
		// go through pieces hash map and put them on board if any
		for (Map.Entry mEntry : pieces.entrySet()) {
			matrix[((EscapeCoordinate)mEntry.getKey()).getX()][((EscapeCoordinate)mEntry.getKey()).getY()] = 'p';
		}
		
		// go through locations hash map and mark them as blocked or exit
		for (Map.Entry mEntry : locations.entrySet()) {
			if (mEntry.getValue() == LocationType.BLOCK) {
				matrix[((EscapeCoordinate)mEntry.getKey()).getX()][((EscapeCoordinate)mEntry.getKey()).getY()] = 'b';
			}
			else if (mEntry.getValue() == LocationType.EXIT) {
				matrix[((EscapeCoordinate)mEntry.getKey()).getX()][((EscapeCoordinate)mEntry.getKey()).getY()] = 'e';
			}
		}
		
		matrix[sFrom.getX()][sFrom.getY()] = 's';  // source
		matrix[sTo.getX()][sTo.getY()] = 'X';      // destination
		
		return matrix;
	}
	
	
	/**
	 * Creates 2D matrix based on coordinates on Hex boards, modeling a graph
	 * Initializes 2D matrix with pieces and location types based on offset
	 * Block locations are filled with 'b'
	 * Exit locations are filled with 'e'
	 * Pieces are filled with 'p'
	 * @return a 2D array of size [xMax+1][yMax+1] of 1's
	 */
	public static char[][] createGraph(EscapeBoard b, EscapeCoordinate src, EscapeCoordinate dest, EscapeCoordinate minValues, EscapeCoordinate maxValues) {
		Map<EscapeCoordinate, EscapePiece> pieces = b.getPieceMap();
		Map<EscapeCoordinate, LocationType> locations = b.getLocationMap();
		
		int xOffset = Math.abs(minValues.getX());
		int yOffset = Math.abs(minValues.getY());
		
		int newMaxX = maxValues.getX() + xOffset + 1;
		int newMaxY = maxValues.getY() + yOffset + 1;

		char[][] matrix = new char[newMaxX][newMaxY]; // create the grid dimensions

		// initially set each space to '1'
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = '1';
			}
		}
		
		// go through pieces hash map and put them on board if any
		for (Map.Entry mEntry : pieces.entrySet()) {	
			matrix[((EscapeCoordinate)mEntry.getKey()).getX() + xOffset][((EscapeCoordinate)mEntry.getKey()).getY() + yOffset] = 'p';
		}

		// go through locations hash map and mark them as blocked or exit
		for (Map.Entry mEntry : locations.entrySet()) {
			if (mEntry.getValue() == LocationType.BLOCK) {
				matrix[((EscapeCoordinate)mEntry.getKey()).getX() + xOffset][((EscapeCoordinate)mEntry.getKey()).getY() + yOffset] = 'b';
			}
			else if (mEntry.getValue() == LocationType.EXIT) {
				matrix[((EscapeCoordinate)mEntry.getKey()).getX() + xOffset][((EscapeCoordinate)mEntry.getKey()).getY() + yOffset] = 'e';
			}
		}
	
		matrix[src.getX() + xOffset][src.getY() + yOffset] = 's';  // source
		matrix[dest.getX() + xOffset][dest.getY() + yOffset] = 'X';      // destination
		
		return matrix;
	}	
}
