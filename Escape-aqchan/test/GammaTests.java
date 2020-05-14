import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;

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

/**
 * Description
 * @version May 13, 2020
 */
class GammaTests
{

	private EscapeGameBuilder egb;
	private EscapeGameManager egm;
	 
	/**
	 * Square Beta Escape GameTests
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		egb = new EscapeGameBuilder(new File("config/gammaconfigs/Gamma.xml"));
		egm = egb.makeGameManager();
	}

	@Test
	void GammaTest() throws Exception
	{
		egm.move(egm.makeCoordinate(1, 3), egm.makeCoordinate(1, 1));
	}
	
	@Test
	void Remove_PointConflictTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/Remove_PointConflict.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertFalse(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 2))); // cannot have BOTH remove and point_conflict
	}
	
	@Test
	void PointConflictTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/PointConflict.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(2, 1))); // horse > snail
		assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(5, 4))); // filler so that player turn works
		assertTrue(e.move(e.makeCoordinate(2, 1), e.makeCoordinate(3, 1))); // horse = snail
		assertTrue(e.move(e.makeCoordinate(5, 4), e.makeCoordinate(5, 5))); // filler so that player turn works
		assertTrue(e.move(e.makeCoordinate(5, 1), e.makeCoordinate(4, 1))); // frog < snail
	}
	
	@Test
	void NoPieceConfrontationTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/NoPieceConfrontationRules.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		// piece cannot capture another piece without rules
		assertFalse(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 2)));
	}
	
	@Test
	void ExitConditionTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/ExitLocation.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 3))); // move to exit location
		assertTrue(e.move(e.makeCoordinate(2, 3), e.makeCoordinate(1, 3))); // move to exit location
		assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(5, 6))); // filler so that player turn works
		assertTrue(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(1, 3))); // move to exit location
	}
	
	@Test
	void ScoreRuleTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/ScoreRule.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 3))); // P1 = 7pts
		assertTrue(e.move(e.makeCoordinate(2, 3), e.makeCoordinate(1, 3))); // P1 = 7pts, P2 = 4pts
		assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(5, 6))); // filler so that player turn works
		assertTrue(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(1, 3))); // P1 = 7pts, P2 = 8pts
		// game is over because max score was reached
		assertFalse(e.move(e.makeCoordinate(4, 3), e.makeCoordinate(1, 3))); // game over, can't move again
	}

	@Test
	void TurnLimitTest() throws Exception
	{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/gammaconfigs/TurnLimitRule.xml"));
		EscapeGameManager e = gameBuilder.makeGameManager();
		
		assertTrue(e.move(e.makeCoordinate(1, 1), e.makeCoordinate(1, 3))); // P1 = 7pts
		assertTrue(e.move(e.makeCoordinate(2, 3), e.makeCoordinate(1, 3))); // P1 = 7pts, P2 = 4pts
		assertTrue(e.move(e.makeCoordinate(5, 5), e.makeCoordinate(5, 6))); // filler so that player turn works
		assertTrue(e.move(e.makeCoordinate(3, 3), e.makeCoordinate(1, 3))); // P1 = 7pts, P2 = 8pts
	}
}
