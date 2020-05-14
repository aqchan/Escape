/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.rule.*;
import escape.util.*;

/**
 * This class is what a client will use to create an instance of a game, given
 * an Escape game configuration file. The configuration file contains the 
 * information needed to create an instance of the Escape game.
 * @version Apr 22, 2020
 */
public class EscapeGameBuilder
{
	private EscapeGameInitializer gameInitializer;


	/**
	 * The constructor takes a file that points to the Escape game
	 * configuration file. It should get the necessary information 
	 * to be ready to create the game manager specified by the configuration
	 * file and other configuration files that it links to.
	 * @param fileName the file for the Escape game configuration file.
	 * @throws Exception 
	 */
	public EscapeGameBuilder(File fileName) throws Exception
	{
		JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
		Unmarshaller mub = contextObj.createUnmarshaller();
		gameInitializer = 
				(EscapeGameInitializer)mub.unmarshal(new FileReader(fileName));
	}

	/**
	 * Once the builder is constructed, this method creates the
	 * EscapeGameManager instance.
	 * @return
	 */
	public EscapeGameManager makeGameManager()
	{
		return new EscapeGame(gameInitializer);
	}


	/**
	 * Creates the Board based on the coordinate type
	 * @param e the game initializer
	 * @return
	 */
	public static Board makeBoard(EscapeGameInitializer e) {	
		Board board = null;

		if (e.getCoordinateType() == CoordinateID.HEX) {
			board = new HexBoard(e.getxMax(), e.getyMax());
		} else if (e.getCoordinateType() == CoordinateID.ORTHOSQUARE) {
			board = new OrthoSquareBoard(e.getxMax(), e.getyMax());
		} else if (e.getCoordinateType() == CoordinateID.SQUARE) {
			board = new SquareBoard(e.getxMax(), e.getyMax());
		} 

		if (e.getLocationInitializers() == null) {
			return board;
		}
		initializeBoard(e, board);
		return board;
	}

	/**
	 * Initializes the board with locations and pieces
	 * @param e the game initializer
	 * @param b the board
	 * @param initializers
	 */
	public static void initializeBoard(EscapeGameInitializer e, Board b)
	{
		for (LocationInitializer li : e.getLocationInitializers()) {
			Coordinate c = null;

			if (e.getCoordinateType() == CoordinateID.HEX) {
				c = HexCoordinate.makeCoordinate(li.x, li.y);
			} else if (e.getCoordinateType() == CoordinateID.ORTHOSQUARE) {
				c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
			} else if (e.getCoordinateType() == CoordinateID.SQUARE) {
				c = SquareCoordinate.makeCoordinate(li.x, li.y);
			}

			if (li.pieceName != null) {

				// iterate through piece types
				for (PieceTypeInitializer p : e.getPieceTypes()) {

					// if the names are the same and there are more than 1 attributes
					if (p.getPieceName() == li.pieceName && p.getAttributes().length > 0) {

						// then initialize
						b.putPieceAt(new EscapePiece(li.player, li.pieceName, p.getMovementPattern(), p.getAttributes()), c);
					}
				}
			}

			if (li.locationType != null && li.locationType != LocationType.CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}


	/**
	 * Initializes the rules
	 * @param e the game initializer
	 * @return an array of rules
	 */
	public static Rule[] initializeRules(EscapeGameInitializer e)
	{
		Rule rules[] = e.getRules();
		return rules;
	}

}
