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

import java.util.*;
import escape.board.*;
import escape.board.coordinate.EscapeCoordinate;
import escape.piece.*;
import escape.rule.*;

/**
 * Description
 * @version May 13, 2020
 */
public class EscapeGameState
{
	private Rule[] rules;
	private Map<RuleID, Integer> rulesMap;
	private Map<Player, Integer> scoreMap;
	private int turnCount;

	/**
	 * Escape Game State
	 * Determines whether game is over and winner conditions
	 */
	public EscapeGameState(Rule[] rules)
	{
		this.rules = rules;
		this.rulesMap = initializeRulesMap();
		this.scoreMap = initializeScoreMap();
		this.turnCount = 0;
	}

	/**
	 * Check which rules the game has
	 * @return true if the rules and their requirements are valid, otherwise false
	 */
	public boolean isValidPieceConfrontation(EscapeBoard board, EscapeCoordinate from, EscapeCoordinate to)
	{	
		EscapePiece fromPiece = board.getPieceAt(from);

		// The game cannot have both rules
		if (rulesMap.containsKey(RuleID.REMOVE) && rulesMap.containsKey(RuleID.POINT_CONFLICT)) {
			return false;
		}

		// Check if there is a piece at the "to" location
		if (board.getPieceAt(to) != null) {

			// Remove opposing piece from board upon confrontation
			if (rulesMap.containsKey(RuleID.REMOVE)) {
				board.removePieceAt(fromPiece, from); // first remove the from piece from the orig location
				board.putPieceAt(fromPiece, to); // implies that we removed the to piece
				return true;
			}
			// Remove piece with the lower value and update remaining piece's value 
			else if (rulesMap.containsKey(RuleID.POINT_CONFLICT)) {
				return pointConflictRule(board, from, to);
			}
			// if a game has NEITHER remove nor point_conflict, then a move may NOT terminate
			else if (!rulesMap.containsKey(RuleID.REMOVE) && !rulesMap.containsKey(RuleID.POINT_CONFLICT)) {
				return false;
			}
		}

		// Check if the "to" location is an EXIT and increment score
		if (board.getLocationMap().get(to) == LocationType.EXIT) {
			Player player = board.getPieceAt(from).getPlayer();
			int pointVal = board.getPieceAt(from).getValue();
			incrementScore(player, pointVal);
		}

		// Otherwise, there is no piece at the location, so move normally
		board.removePieceAt(fromPiece, from); // first remove the piece
		board.putPieceAt(fromPiece, to); // then place it at its new spot on the board

		return true;
	}

	/**
	 * Given the point conflict rule, determines if a move is valid
	 * @param board the current board
	 * @param from the starting coordinate
	 * @param to the ending coordinate
	 * @return true if the move is valid, otherwise false
	 */
	public boolean pointConflictRule(EscapeBoard board, EscapeCoordinate from, EscapeCoordinate to) 
	{
		EscapePiece fromPiece = board.getPieceAt(from);
		EscapePiece toPiece = board.getPieceAt(to);

		int fromValue = fromPiece.getValue();
		int toValue = toPiece.getValue();

		// Piece values are equal, so remove both of the pieces
		if (toValue == fromValue) {
			board.removePieceAt(fromPiece, from);
			board.removePieceAt(toPiece, to);
			return true;
		}
		// From value is larger, so remove piece at "to" location
		else if (fromValue > toValue) {
			board.putPieceAt(fromPiece, to); // implies removal of "to" piece
			fromPiece.setValue(fromValue - toValue); // reduce the point value of this piece
			return true;
		}
		// From value is smaller, so remove piece at "from" location
		else if (fromValue < toValue) {
			board.removePieceAt(fromPiece, from);
			toPiece.setValue(toValue - fromValue); // reduce the point value of this piece
			return true;
		}

		return false;
	}


	/**
	 * Determine if the game is over based on turn limit or score rule(s)
	 * @return true if the game is over, otherwise false
	 */
	public boolean isGameOver() 
	{
		// 1 turn consists of EACH player making a move
		if (rulesMap.containsKey(RuleID.TURN_LIMIT)) {
			int turnLimit = getTurnLimit();
			if (turnCount == turnLimit) {
				return true;
			}
		}

		if (rulesMap.containsKey(RuleID.SCORE)) {
			int scoreLimit = getScoreLimit();
			if (scoreMap.get(Player.PLAYER1) >= scoreLimit || scoreMap.get(Player.PLAYER2) >= scoreLimit) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * @return the maximum number of turns a game can have
	 */
	public int getTurnLimit()
	{
		return rulesMap.get(RuleID.TURN_LIMIT);
	}

	/**
	 * @return the maximum score a game can have
	 */
	public int getScoreLimit() 
	{
		return rulesMap.get(RuleID.SCORE);
	}

	/**
	 * Sets all of the rules into a hash map
	 * @param rules
	 */
	public Map<RuleID, Integer> initializeRulesMap()
	{
		Map<RuleID, Integer> rulesMap = new HashMap<>();
		if (rules != null) {
			for (Rule r : rules) {
				rulesMap.put(r.getId(), r.getIntValue());
			}
		}
		return rulesMap;
	}
	
	/**
	 * Initializes the scoreMap with Player 1
	 * and Player 2 with scores of 0
	 * @return hash map of each player's initial scores
	 */
	public Map<Player, Integer> initializeScoreMap()
	{
		Map<Player, Integer> scoreMap = new HashMap<>();
		scoreMap.put(Player.PLAYER1, 0);
		scoreMap.put(Player.PLAYER2, 0);
		return scoreMap;
	}

	/**
	 * Increments the player's score by the specified point value
	 * @param player
	 * @param pointVal the number of points to add
	 */
	public void incrementScore(Player player, int pointVal)
	{
		scoreMap.put(player, scoreMap.get(player) + pointVal);
	}
	/**
	 * Returns which player won the game based on their score
	 * @return a String with Player1, Player2, or a tie
	 */
	
	public String getWinner()
	{
		if (scoreMap.get(Player.PLAYER1) > scoreMap.get(Player.PLAYER2)) {
			return "Player 1 has won.";
		} else if (scoreMap.get(Player.PLAYER1) < scoreMap.get(Player.PLAYER2)) {
			return "Player 2 has won.";
		}
		return "the game was a tie.";
	}

	public void incrementTurn()
	{
		turnCount++;
	}
	
	public Player getPlayerTurn()
	{
		if (turnCount % 2 == 0) {
			return Player.PLAYER1;
		}
		return Player.PLAYER2;
	}
}
