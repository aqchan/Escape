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

package escape.board.builder;

import static escape.board.LocationType.CLEAR;
import java.io.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * Description
 * @version Apr 14, 2020
 */
public class HexBoardBuilder implements BoardBuilder
{
	private BoardInitializer bi;
	/**
	 * The constructor for this takes a file name. It is either an absolute path
	 * or a path relative to the beginning of this project.
	 * @param fileName
	 * @throws Exception 
	 */
	public HexBoardBuilder (File fileName) throws Exception
	{
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        bi = (BoardInitializer)mub.unmarshal(new FileReader(fileName));
	}
	
	public Board makeBoard()
	{
		if (bi.getCoordinateId() == CoordinateID.HEX) {
			HexBoard board = new HexBoard(bi.getxMax(), bi.getyMax());
	        initializeBoard(board, bi.getLocationInitializers());
	        return board;
		}
		throw new EscapeException("The coordinate id does not align with the board type of Hex.");
	}
	
	public void initializeBoard(Board b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			HexCoordinate c = HexCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				((HexBoard) b).setLocationType(c, li.locationType);
			}
		}
	}

}
