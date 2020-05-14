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

package escape.piece;

import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * This is a class for Pieces.
 * 
 * 
 * @version Mar 28, 2020
 */
public class EscapePiece
{
	private final Player player;
	private final PieceName name;
	private final MovementPatternID movementPatternID;
	private final PieceAttribute[] attributes;
	private int value;

	/**
	 * Constructor that takes the player name, piece name, movementPatternID, and attributes.
	 * @param player
	 * @param name
	 */
	public EscapePiece(Player player, PieceName name, MovementPatternID movementPatternID, PieceAttribute[] attributes) 
	{
		this.player = player;
		this.name = name;
		this.movementPatternID = movementPatternID;
		this.attributes = attributes;
		this.value = setInitialValue();
	}

	/**
	 * Constructor that takes the player and piece name.
	 * @param player
	 * @param name
	 */
	public EscapePiece(Player player, PieceName name) 
	{
		this.player = player;
		this.name = name;
		this.movementPatternID = null;
		this.attributes = null;
		this.value = 1;
	}
	
	/**
	 * Static factory method. This creates and returns the specified
	 * Escape piece for the current game version.
	 * 
	 * DO NOT CHANGE THE SIGNATURE.
	 * @param player the player the piece belongs to
	 * @param name the piece name
	 * @return the piece
	 */
	public static EscapePiece makePiece(Player player, PieceName name)
	{
		return new EscapePiece(player, name);
	}

	
	/**
	 * @return the name
	 */
	public PieceName getName()
	{
		return name;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return the movement pattern
	 */
	public MovementPatternID getMovementPatternID()
	{
		return movementPatternID;
	}

	/**
	 * @return the attributes
	 */
	public PieceAttribute[] getAttributes()
	{
		return attributes;
	}

	/**
	 * @return the value of the piece
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * Checks if a piece has the value attribute
	 * If the piece does, set the value. Otherwise, the value is 1
	 * @return
	 */
	public int setInitialValue()
	{
		int v = 1;
		for (PieceAttribute a : attributes) {
			if (a.getId() == PieceAttributeID.VALUE) {
				v = a.getIntValue();
			}
		}
		return v;
	}
	
	
	/**
	 * @return sets the value of the piece
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
}
