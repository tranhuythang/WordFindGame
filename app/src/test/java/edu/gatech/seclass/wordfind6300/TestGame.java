package edu.gatech.seclass.wordfind6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestGame {
    private Game testGame;
    private GameSettings testGameSetting;

    @Before
    public void setUp(){
        testGameSetting = new GameSettings(3, 7);
        testGame = new Game(testGameSetting);
        testGame.generateBoard(true);
    }

    @After
    public void tearDown(){
        testGame = null;
    }

    @Test
    public void testNumWord(){
        Position [] positionArray = new Position[4];
        positionArray[0] = new Position(1, 1);
        positionArray[1] = new Position(1, 0);
        positionArray[2] = new Position(0, 0);
        positionArray[3] = new Position(0, 1);

        Position [] positionArray2 = new Position[4];
        positionArray2[0] = new Position(2, 1);
        positionArray2[1] = new Position(2, 0);
        positionArray2[2] = new Position(1, 0);
        positionArray2[3] = new Position(1, 1);

        Position[] positionArray3 = new Position[3];
        positionArray3[0] = new Position(1, 1);
        positionArray3[1] = new Position(1, 0);
        positionArray3[2] = new Position(2, 2);

        testGame.playWord("test", positionArray);
        testGame.playWord("word", positionArray2);
        testGame.playWord("cat", positionArray3);

        assertEquals(testGame.getNumWordsPlayed(), 2);
    }

    @Test
    public void testHighScoringWord(){
        Position [] positionArray = new Position[4];
        positionArray[0] = new Position(1, 1);
        positionArray[1] = new Position(1, 0);
        positionArray[2] = new Position(0, 0);
        positionArray[3] = new Position(0, 1);

        Position [] positionArray2 = new Position[4];
        positionArray2[0] = new Position(2, 1);
        positionArray2[1] = new Position(2, 0);
        positionArray2[2] = new Position(1, 0);
        positionArray2[3] = new Position(1, 1);

        testGame.playWord("test", positionArray);
        testGame.playWord("word", positionArray2);

        assertEquals(testGame.getHighestScoringWord(), "test");
    }

    @Test
    public void testScore(){
        Position [] positionArray = new Position[4];
        positionArray[0] = new Position(1, 1);
        positionArray[1] = new Position(1, 0);
        positionArray[2] = new Position(0, 0);
        positionArray[3] = new Position(0, 1);

        Position [] positionArray2 = new Position[4];
        positionArray2[0] = new Position(2, 1);
        positionArray2[1] = new Position(2, 0);
        positionArray2[2] = new Position(1, 0);
        positionArray2[3] = new Position(1, 1);

        Position[] positionArray3 = new Position[3];
        positionArray3[0] = new Position(1, 1);
        positionArray3[1] = new Position(1, 0);
        positionArray3[2] = new Position(2, 2);

        testGame.playWord("test", positionArray);
        testGame.playWord("word", positionArray2);
        testGame.playWord("cat", positionArray3);

        assertEquals(testGame.getScore(), 8);

        testGame.generateBoard(false);

        assertEquals(testGame.getScore(), 3);
    }
}
