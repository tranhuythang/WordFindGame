package edu.gatech.seclass.wordfind6300;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

class Board {
    private int boardSize;
    private int[] letterWeights;
    private ArrayList<Position[]> attr_positions;

    public Board(int boardSize, int[] letterWeights) {
        this.boardSize = boardSize;
        this.letterWeights = letterWeights;
        attr_positions = new ArrayList<Position[]>();
    }

    public String[][] generateBoard() {
        String vowels = "AEIOU";
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        char[] vowels_char = vowels.toCharArray();
        char[] consonants_char = consonants.toCharArray();
        StringBuilder sb_vowel = new StringBuilder();
        StringBuilder sb_consonant = new StringBuilder();

        /* Generate the vowel characters considering weights.*/
        for (int i = 0; i < 5; i++) {
            int vowel_location = vowels_char[i] - 65;
            for (int k = 0; k < letterWeights[vowel_location]; k++)
                sb_vowel.append(vowels_char[i]);
        }

        /* Generate the consonant characters considering weights.*/
        for (int i = 0; i < 21; i++) {
            int consonant_location = consonants_char[i] - 65;
            for (int k = 0; k < letterWeights[consonant_location]; k++)
                sb_consonant.append(consonants_char[i]);
        }

        /*The loop is used to choose vowel and consonants.
        The total number of letters is boardSize*boardSize.
        And ⅕ (rounded up) of the letters will be vowels (a,e,i,o,u). ⅘ will be consonants */
        int numConsonants = boardSize * boardSize * 4 / 5;
        int numVowels = boardSize * boardSize - numConsonants;
        List<Character> letters = new ArrayList<>();

        for (int i = 0; i < numConsonants; i++) {
            Random ran = new Random();
            int ran_int = ran.nextInt(sb_consonant.length());
            char ran_char = sb_consonant.charAt(ran_int);
            letters.add(ran_char);
        }

        for (int i = 0; i < numVowels; i++) {
            Random ran = new Random();
            int ran_int = ran.nextInt(sb_vowel.length());
            char ran_char = sb_vowel.charAt(ran_int);
            letters.add(ran_char);
        }

        Collections.shuffle(letters);

        /*This method is to allocate each char in the letterMatrix to individual location on the board.*/
        String letterMatrix[][] = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String ss = letters.get(i * boardSize + j).toString();
                if (ss.equals("Q")) {
                    letterMatrix[i][j] = "Qu";
                } else {
                    letterMatrix[i][j] = ss;
                }
            }
        }

        /* letterMatrix[0][0] = "G";
        letterMatrix[0][1] = "U";
        letterMatrix[0][2] = "S";
        letterMatrix[0][3] = "Y";
        letterMatrix[0][4] = "M";
        letterMatrix[1][0] = "P";
        letterMatrix[1][1] = "A";
        letterMatrix[1][2] = "N";
        letterMatrix[1][3] = "T";
        letterMatrix[1][4] = "X";
        letterMatrix[2][0] = "I";
        letterMatrix[2][1] = "E";
        letterMatrix[2][2] = "T";
        letterMatrix[2][3] = "R";
        letterMatrix[2][4] = "W";
        letterMatrix[3][0] = "L";
        letterMatrix[3][1] = "B";
        letterMatrix[3][2] = "K";
        letterMatrix[3][3] = "E";
        letterMatrix[3][4] = "Qu";
        letterMatrix[4][0] = "C";
        letterMatrix[4][1] = "D";
        letterMatrix[4][2] = "N";
        letterMatrix[4][3] = "F";
        letterMatrix[4][4] = "H"; */
        return letterMatrix;
    }

    /**
     * The method is to check if the player enters letters from the board that are each adjacent to
     * the next (horizontally, vertically, or diagonally) and no single letter on the board was used twice
     */
    public Boolean checkWord(Position[] positionArray) {

        if(positionArray.length <= 1)
            return  false;

        for(Position[] currentPositon:attr_positions)
        {
            if(currentPositon.length == positionArray.length) {
                for (int i = 0; i < currentPositon.length; i++) {
                    if (currentPositon[i].getX() != positionArray[i].getX() || currentPositon[i].getY() != positionArray[i].getY()) {
                        break;
                    }
                    if(i==currentPositon.length-1)
                    {
                        return false;
                    }
                }
            }

        }
        attr_positions.add(positionArray);


        boolean[] isLetterUsed = new boolean[boardSize * boardSize];
        for (int i = 0; i < positionArray.length; i++) {
            int location = positionArray[i].getX() * boardSize + positionArray[i].getY();
            if (isLetterUsed[location]) {
                return false;
            } else {
                isLetterUsed[location] = true;
            }
            if (i == positionArray.length - 1) {
                continue;  // The letter is at the last position of the userInputString
            } else {
                if (!isAdjacent(positionArray[i], positionArray[i + 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAdjacent(Position p1, Position p2) {
        return Math.abs(p1.getX() - p2.getX()) <= 1 && Math.abs(p1.getY() - p2.getY()) <= 1;
    }

}
