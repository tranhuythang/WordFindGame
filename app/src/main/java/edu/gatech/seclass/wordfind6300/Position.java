package edu.gatech.seclass.wordfind6300;

/*The UI provides the letter and the corresponding letter position (x,y).*/
public class Position {
    private int x;
    private int y;

    public Position(int i, int j) {
        x = i;
        y = j;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
