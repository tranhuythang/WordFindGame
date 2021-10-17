package edu.gatech.seclass.wordfind6300;

import java.util.*;

public class Game {
    
    private GameSettings gameSettings;
    private int score = 0;
    private Board board;
    private HashMap<String, Integer> wordsPlayed = new HashMap<>();
    private int numWordsPlayed = 0;
    private String highestScoringWord = "";
    private int numBoardReset = 0;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public int getScore() {
        return score;
    }

    public int getNumWordsPlayed() {
        return numWordsPlayed;
    }

    public String getHighestScoringWord() {
        return highestScoringWord;
    }

    public int getNumBoardReset(){ return numBoardReset;}

    public String[][] generateBoard(boolean isFirstTime) {
        board = new Board(gameSettings.getBoardSize(), gameSettings.getLetterWeights());
        if (!isFirstTime) {
            score -= 5;
            numBoardReset++;
        }
        return board.generateBoard();
    }

    /**
     * The method first calls checkWord method in the Board class.
     * If the word is valid, i.e, the player enters letters from the board that are each adjacent to
     * the next (horizontally, vertically, or diagonally) and no single letter on the board was used twice,
     * then the word is added to the word statistics.
     */
    public boolean playWord(String word, Position[] positions) {
        if (board.checkWord(positions)){
            if(wordsPlayed.containsKey(word)) {
                wordsPlayed.put(word, wordsPlayed.get(word) + 1);
            } else {
                wordsPlayed.put(word,1);
            }
            score += word.length();
            numWordsPlayed++; // This assumes that repeated words entered by the player will add up the number of words entered in the game
            if (highestScoringWord.length()< word.length()) {
                highestScoringWord = word;
            }
            //updateWordStats(word);
            return true;
        } else {
            return false;
        }
    }
    /*
    //The exit method is called when the player clicks FINISH EARLY button.
    //The UI will display final score and the highest scoring word.
    public void exit() {
        // On exit, record game stats to SharedDataHolder and update to ScoreStats
        GameStats gameStats = new GameStats(
                score,
                numBoardReset,
                numWordsPlayed,
                gameSettings,
                highestScoringWord
        );
        updateScoreStats(gameStats);
    }

    //The updateGameStats method is to update the score, numBoardReset, numWordsPlayed, highestScoringWord
    private void updateScoreStats(GameStats gameStats) {
        SharedDataHolder.getScoreStats().addGameStats(gameStats);
    }

    //The updateWordStats method is to update wordsPlayedList attribute,
    //which is a HashMap containing the word and the number of times the word has been played across all games
    private void updateWordStats(String word) {
        SharedDataHolder.getWordStats().addWordToStats(word);
    }
     */
}

