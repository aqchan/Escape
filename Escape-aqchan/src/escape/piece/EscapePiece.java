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
 * You may change this class except for the signature of the static factory 
 * method makePiece() and the getter methods for the name and player.
 * 
 * @version Mar 28, 2020
 */
public class EscapePiece
{
    private final Player player;
    private final PieceName name;
    private final MovementPatternID movementPatternID;
    private final PieceAttribute[] attributes;
    
    /**
     * Constructor that takes the player and piece name.
     * @param player
     * @param name
     */
    public EscapePiece(Player player, PieceName name, MovementPatternID movementPatternID, PieceAttribute[] attributes) 
    {
		this.player = player;
    	this.name = name;
    	this.movementPatternID = movementPatternID;
    	this.attributes = attributes;
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
	public static EscapePiece makePiece(Player player, PieceName name, MovementPatternID movementPatternID, PieceAttribute[] attributes)
	{
		return new EscapePiece(player, name, movementPatternID, attributes);
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

}
