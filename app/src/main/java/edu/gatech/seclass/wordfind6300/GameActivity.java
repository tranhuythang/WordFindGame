package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.util.zip.CheckedOutputStream;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private ArrayList<Position> positions;
    private CountDownTimer timer;
    private Button[][] attr_buttonMatrix;
    private DatabaseManager db = new DatabaseManager(this);

    private void ButtonBgColorRefresh()
    {
        //int size = SharedDataHolder.getGameSettings().getBoardSize();
        int size = db.viewSetting()[1];
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++) {
                attr_buttonMatrix[i][j].setBackgroundResource(android.R.drawable.btn_default);
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int boardSize = db.viewSetting()[1];
        final int timeDuration = db.viewSetting()[0];


        GameSettings gameSetting = new GameSettings(timeDuration, boardSize);
        game = new Game(gameSetting);
        //game = new Game(SharedDataHolder.getGameSettings());

        attr_buttonMatrix = new Button[boardSize][boardSize];
        positions = new ArrayList<>();

        ScrollView gameScrollView = new ScrollView(this);
        TableLayout tableLayout = new TableLayout(this);

        final TextView[] TimeScoreWord = new TextView[6];
        for(int i = 0; i < 6; i++)
            TimeScoreWord[i] = new TextView(this);
        TimeScoreWord[0].setText("   ? min ? sec"); TimeScoreWord[0].setTextColor(Color.RED);
        TimeScoreWord[1].setText("remaining");
        TimeScoreWord[2].setText("0");  TimeScoreWord[2].setTextColor(Color.BLUE);
        TimeScoreWord[3].setText(" points ");
        TimeScoreWord[4].setText("0");  TimeScoreWord[4].setTextColor(Color.GREEN);
        TimeScoreWord[5].setText(" words   ");
        TextView emptyView1 = new TextView(this);
        TextView emptyView2 = new TextView(this);


//        final TextView timeShowView = new TextView(this);
//        timeShowView.setText("? min ? sec");
//        timeShowView.setTextColor(Color.RED);
//
//        TextView timeInstructView = new TextView(this);
//        timeInstructView.setText("remaining ");
//
//        TextView scoreInstructView = new TextView(this);
//        scoreInstructView.setText(" points ");
//
//        final TextView scoreShowView = new TextView(this);
//        scoreShowView.setText("0");
//        scoreShowView.setTextColor(Color.BLUE);
//
//        final TextView wordShowView = new TextView(this);
//        wordShowView.setText("0");
//        wordShowView.setTextColor(Color.GREEN);
//
//        TextView wordInstructView = new TextView(this);
//        wordInstructView.setText(" words");


        TableLayout TimeScoreWordTable = new TableLayout(this);
        TableRow TimeScoreWordROW = new TableRow(this);


        TimeScoreWordROW.addView(TimeScoreWord[0]);
        TimeScoreWordROW.addView(TimeScoreWord[1]);

        TimeScoreWordROW.addView(emptyView1);
        TimeScoreWordROW.addView(TimeScoreWord[2]);
        TimeScoreWordROW.addView(TimeScoreWord[3]);

        TimeScoreWordROW.addView(emptyView2);
        TimeScoreWordROW.addView(TimeScoreWord[4]);
        TimeScoreWordROW.addView(TimeScoreWord[5]);

        TimeScoreWordTable.addView(TimeScoreWordROW);
        TimeScoreWordTable.setColumnStretchable(2, true);
        TimeScoreWordTable.setColumnStretchable(5, true);
        tableLayout.addView(TimeScoreWordTable);

        final TextView inputText = new TextView (this);
        inputText.setText("");

        TableLayout tileTable = new TableLayout(this);
        tileTable.setShrinkAllColumns(true);

        View.OnClickListener myOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                inputText.setText(inputText.getText().toString() + ((Button)v).getText().toString());
                ((Button)v).setBackgroundColor(getResources().getColor(17170447));
//                ((Button)v).setBackgroundColor(Color.CYAN);
                positions.add((Position) v.getTag());
            }
        };

        final Button[][] letterTile = new Button[boardSize][boardSize];
        TableRow[] boardRow = new TableRow[boardSize];
        String[][] letterMatrix = game.generateBoard(true);
        for(int i = 0; i < boardSize; i++){
            boardRow[i] = new TableRow(this);
            for(int j = 0; j < boardSize; j++){
                letterTile[i][j] = new Button(getApplicationContext());
                letterTile[i][j].setText(letterMatrix[i][j]);
                letterTile[i][j].setTypeface(null, Typeface.BOLD);
                letterTile[i][j].setOnClickListener(myOnClickListener);
                letterTile[i][j].setBackgroundResource(android.R.drawable.btn_default);
                letterTile[i][j].setTag(new Position(i, j));
                boardRow[i].addView(letterTile[i][j]);
                boardRow[i].setGravity(Gravity.CENTER);

                attr_buttonMatrix[i][j] = letterTile[i][j];
            }
            tileTable.addView(boardRow[i]);
        }

        tileTable.setGravity(Gravity.CENTER);

        //setTextColor(Color.BLUE);

        tableLayout.addView(tileTable);

        final TableLayout inputTable = new TableLayout(this);

        TextView instructText = new TextView(this);
        instructText.setText("   TAP words: ");
        Button checkButton = new Button(this);
        checkButton.setText("SCORE it");
        checkButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String word = inputText.getText().toString();
                if (game.playWord(word, positions.toArray(new Position[positions.size()]))) {
                    TimeScoreWord[2].setText(String.valueOf(game.getScore()));
                    TimeScoreWord[4].setText(String.valueOf(game.getNumWordsPlayed()));
                    db.addToWordTable(word);
                } else {
                    Toast.makeText(GameActivity.this, "Invalid input!", Toast.LENGTH_SHORT).show();
                }
                inputText.setText("");
                positions.clear();
                ButtonBgColorRefresh();
            }
        });

        TableRow inputRow = new TableRow(this);
        inputRow.addView(instructText);
        inputRow.addView(inputText);
        inputRow.addView(checkButton);
        inputTable.addView(inputRow);
        inputTable.setColumnStretchable(1,true);
        tableLayout.addView(inputTable);

        TableLayout resetfinishTable = new TableLayout(this);
        TableRow resetfinishRow = new TableRow(this);
        final Button resetButton = new Button(this);

        resetButton.setText("RESET board (lose 5 pts)");
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int boardSize = SharedDataHolder.getGameSettings().getBoardSize();
                String[][] letterMatrix = game.generateBoard(false);
                for (int i = 0; i < boardSize; i++)
                    for (int j = 0; j < boardSize; j++){
                        letterTile[i][j].setText(letterMatrix[i][j]);
                        letterTile[i][j].setBackgroundResource(android.R.drawable.btn_default);
                    }
                TimeScoreWord[2].setText(String.valueOf(game.getScore()));
            }
        });

        final Button finishButton = new Button(this);
        finishButton.setText("Finish Early");
        TextView anotheremptyView = new TextView(this);
        resetfinishRow.addView(resetButton);
        resetfinishRow.addView(anotheremptyView);
        resetfinishRow.addView(finishButton);
        resetfinishTable.addView(resetfinishRow);
        resetfinishTable.setColumnStretchable(1, true);
        tableLayout.addView(resetfinishTable);

        gameScrollView.addView(tableLayout);
        setContentView(gameScrollView);

        timer = new CountDownTimer(timeDuration*60000, 1000) {
            public void onTick(long millisUntilFinished) {
                TimeScoreWord[0].setText("   " + (millisUntilFinished /60000) + " mins " + ((millisUntilFinished %60000)/1000) + " secs ");
            }
            public void onFinish() {
                finishButton.performClick();
            }
        };
        timer.start();

        finishButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                timer.cancel();
                timer = null;
                //game.exit();
                Intent intent = new Intent(getApplicationContext(), GameResultsActivity.class);
                db.addToScoreTable(game.getScore(),game.getNumBoardReset(),game.getNumWordsPlayed(),boardSize,timeDuration,game.getHighestScoringWord());
                intent.putExtra("score", game.getScore());
                intent.putExtra("highestScoringWord", game.getHighestScoringWord());
                startActivity(intent);
                finish();
            }
        });
    }
}

/*
package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edu.gatech.seclass.wordfind6300.Game;
import edu.gatech.seclass.wordfind6300.R;
import edu.gatech.seclass.wordfind6300.SharedDataHolder;

public class GameActivity extends AppCompatActivity {

    private Game game = new Game(SharedDataHolder.getGameSettings());
    private GridLayout gridLayoutBoardButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button buttonGameEnter = findViewById(R.id.buttonGameEnter);
        buttonGameEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonGameResetBoard = findViewById(R.id.buttonGameResetBoard);
        buttonGameResetBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonGameFinishEarly = findViewById(R.id.buttonGameFinishEarly);
        buttonGameFinishEarly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.exit();
                Intent finishGameIntent = new Intent(getApplicationContext(), GameResultsActivity.class);
                startActivity(finishGameIntent);
            }
        });
/*
        gridLayoutBoardButtons = (GridLayout) findViewById(R.id.gridLayoutBoardButtons);
        //gridLayoutBoardButtons.removeAllViews();
        int column = 4;
        int row = 4;
        int total = 16;
        gridLayoutBoardButtons.setColumnCount(column);
        gridLayoutBoardButtons.setRowCount(row + 1);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == column) {
                c = 0;
                r++;
            }
            ImageView oImageView = new ImageView(this);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            oImageView.setLayoutParams(param);
            gridLayoutBoardButtons.addView(oImageView);
                /*
                Button oButton = new Button(this);
                oButton.setHeight(30);
                oButton.setWidth(30);
                GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
                GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, 1);

                GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(
                        rowSpan, colspan);
                gridLayoutBoardButtons.addView(oButton, gridParam);



        }
        */
/*
        TableLayout tableLayoutBoard = (TableLayout) findViewById(R.id.tableLayoutBoard );


        LinearLayout.LayoutParams tableRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int j=0;j<SharedDataHolder.getGameSettings().getBoardSize();j++) {


            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableRowParams);

        /*TextView tv = new TextView(this);
        tv.setBackgroundColor(0xff12dd12);
        tv.setText("dynamic textview");



        tableRow.addView(tv);

         */

  /*          for (int i = 0; i < SharedDataHolder.getGameSettings().getBoardSize(); i++) {
                Button btn = new Button(this);
                btn.setWidth(30);
                btn.setHeight(25);

                tableRow.addView(btn);

            }

            tableLayoutBoard.addView(tableRow);
        }




    }
}
*/