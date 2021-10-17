package edu.gatech.seclass.wordfind6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBoard {
    private Board testBoard;

    @Before
    public void setUp(){
        int[] letterWeights = new int[26];
        for(int i = 0; i < 26; i++ ) {
            Random randomNumGenerator = new Random();
            int randomNum = randomNumGenerator.nextInt(5) + 1;
            letterWeights[i] = randomNum;
        }
        testBoard = new Board(7, letterWeights);
    }

    @After
    public void tearDown(){
        testBoard = null;
    }

    @Test
    public void testGenerateBoard(){
        String[][] letterMatrix = testBoard.generateBoard();
        List<String> vowels = Arrays.asList("A", "E", "I", "O", "U");
        int numVowel = 0;
        for (int i = 0; i < 7; i ++){
            for (int j = 0; j < 7; j++){
                if (vowels.contains(letterMatrix[i][j])){
                    numVowel += 1;
                }
            }
        }
        assertEquals(numVowel, 10);
    }

    @Test
    public void testCheckWordPositionArrayTooShort(){
        assertFalse(testBoard.checkWord(new Position[0]));
        assertFalse(testBoard.checkWord(new Position[1]));
    }

    @Test
    public void testCheckWordPositionArrayAdjacent(){
        Position [] positionArray = new Position[3];
        positionArray[0] = new Position(1, 1);
        positionArray[1] = new Position(1, 0);
        positionArray[2] = new Position(0, 0);
        assertTrue(testBoard.checkWord(positionArray));
    }

    @Test
    public void testCheckWordPositionArrayNonAdjacent(){
        Position [] positionArray = new Position[3];
        positionArray[0] = new Position(1, 1);
        positionArray[1] = new Position(1, 0);
        positionArray[2] = new Position(2, 2);
        assertFalse(testBoard.checkWord(positionArray));
    }

    @Test
    public void testLetterWeight(){
        int[] letterWeights = new int[26];
        for(int i = 0; i < 26; i++ ) {
            letterWeights[i] = 1;
        }
        //Set 'B' weight to 3
        letterWeights[1] = 3;
        testBoard = new Board(7, letterWeights);
        int letterB = 0;
        int letterC = 0;
        int weight = 0;
        for(int k = 0; k < 1000; k++) {
            String[][] letterMatrix = testBoard.generateBoard();
            for (int i = 0; i < 7; i ++) {
                for (int j = 0; j < 7; j++) {
                    if (letterMatrix[i][j].equals("B")) {
                        letterB += 1;
                    }
                    if (letterMatrix[i][j].equals("C")){
                        letterC += 1;
                    }
                }
            }

        }

        weight = letterB/letterC;

        assertEquals(weight, 3);
    }
}
