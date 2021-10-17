package edu.gatech.seclass.wordfind6300;

class GameSettings {

    private int timeDuration = 3;
    private int boardSize = 4;
    private int[] letterWeights;
    private final int LETTERNUM = 26;


    /* The GameSettings constructor is used to initialize the letterWeights. */
    public GameSettings() {
        letterWeights = new int[LETTERNUM];
        for (int i = 0; i < LETTERNUM; i++) {
            letterWeights[i] = 1;
        }
    }

    public GameSettings(int timeDuration, int boardSize){
        this.timeDuration = timeDuration;
        this.boardSize = boardSize;
        letterWeights = new int[LETTERNUM];
        for (int i = 0; i < LETTERNUM; i++) {
            letterWeights[i] = 1;
        }
    }

    public int getTimeDuration(){
        return timeDuration;
    }

    public void setTimeDuration(int newValue){
        timeDuration=newValue;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize (int newValue) {
        boardSize=newValue;
    }

    public int[] getLetterWeights () {
        return letterWeights;
    }

    public void setLetterWeights (int[] newValue) {
        letterWeights=newValue;

    }

}
