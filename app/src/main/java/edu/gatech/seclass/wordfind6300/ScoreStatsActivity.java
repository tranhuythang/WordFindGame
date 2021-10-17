package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

public class ScoreStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_stats);

        Button buttonScoreStatsMainMenu = findViewById(R.id.buttonScoreStatsMainMenu);
        buttonScoreStatsMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button buttonScoreStatsWordStats = findViewById(R.id.buttonScoreStatsWordStats);
        buttonScoreStatsWordStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wordStatisticsIntent = new Intent(getApplicationContext(), WordStatsActivity.class);
                startActivity(wordStatisticsIntent);
                finish();
            }
        });

        DatabaseManager db = new DatabaseManager(this);
        ScrollView scrollview = findViewById(R.id.scrollView);
        final TableLayout tableLayout = findViewById(R.id.tableLO);

        tableLayout.setStretchAllColumns(true);
        final ArrayList<ArrayList<String>> gameList = db.listAllGames();
        final int length = gameList.size();
        TextView[][] tv = new TextView[length][4];
        final TableRow[] tableRow = new TableRow[length];

        final TextView textViewScoreStatsMoreDetails = findViewById(R.id.textViewScoreStatsMoreDetails);
        final TextView textViewBoardSize = findViewById(R.id.textViewScoreStatsBoardSize);
        final TextView textViewTimeDuration = findViewById(R.id.textViewScoreStatsTimeDuration);
        final TextView textViewHighestScoringWord = findViewById(R.id.textViewScoreStatsHighestScoringWord);

        for (int i = 0; i < length; i++) {
            tv[i][0] = new TextView(this);
            tv[i][0].setText(String.valueOf(i + 1));
            tableRow[i] = new TableRow(this);
            tableRow[i].addView(tv[i][0]);
            for (int j = 1; j <= 3; j++) {
                tv[i][j] = new TextView(this);
                tv[i][j].setText(gameList.get(i).get(j-1));
                tableRow[i].addView(tv[i][j]);
            }
            tableLayout.addView(tableRow[i]);
            tableRow[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int t = tableLayout.indexOfChild(v);
                    //tableRow[t].setBackgroundColor(Color.YELLOW);
                    for(int k = 0; k < length; k++)
                        tableRow[k].setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.YELLOW);

                    textViewScoreStatsMoreDetails.setText("More details about the game # " + String.valueOf(t+1));
                    textViewBoardSize.setText(String.format("Board Size: %s * %s", gameList.get(t).get(3), gameList.get(t).get(3)));
                    textViewTimeDuration.setText("Time Duration: " + gameList.get(t).get(4));
                    textViewHighestScoringWord.setText("Highest Scoring Word: " + gameList.get(t).get(5));
                }
            });
        }
    }
}
/*

        final TableRow tr = findViewById(R.id.);

        final TableLayout tableLayout = findViewById(R.id.tableLayoutScoreStatsSortedScore);
        int rowCount = tableLayout.getChildCount();
        List<GameStats> sortedGameStats = SharedDataHolder.getScoreStats().getHistoricalGameStatsSortedByScore();
        final int numTableRowsToDisplay = min(sortedGameStats.size(), rowCount);
        sortedGameStats = sortedGameStats.subList(0, numTableRowsToDisplay);
        for(int i = 0; i < numTableRowsToDisplay; i++) {
            final GameStats gameStats = sortedGameStats.get(i);
            View rowView = tableLayout.getChildAt(i + 1);
            final TableRow tableRow = (TableRow)rowView;
            TextView textViewIndex = (TextView) tableRow.getChildAt(0);
            textViewIndex.setText(Integer.toString(i + 1));
            TextView textViewScore = (TextView) tableRow.getChildAt(1);
            textViewScore.setText(Integer.toString(gameStats.getScore()));
            TextView textViewNumBoardReset = (TextView) tableRow.getChildAt(2);
            textViewNumBoardReset.setText(Integer.toString(gameStats.getNumBoardReset()));
            TextView textViewNumWordsPlayed = (TextView) tableRow.getChildAt(3);
            textViewNumWordsPlayed.setText(Integer.toString(gameStats.getNumWordsPlayed()));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < numTableRowsToDisplay; j++) {
                        tableLayout.getChildAt(j + 1).setBackgroundColor(Color.WHITE);
                    }
                    tableRow.setBackgroundColor(Color.YELLOW);
                    final TextView textViewScoreStatsMoreDetails = findViewById(R.id.textViewScoreStatsMoreDetails);
                    textViewScoreStatsMoreDetails.setText(
                            "More details about the game # " + Integer.toString(tableLayout.indexOfChild(tableRow))
                    );
                    final TextView textViewBoardSize = findViewById(R.id.textViewScoreStatsBoardSize);
                    textViewBoardSize.setText(
                            String.format(
                                    "Board Size: %d * %d",
                                    gameStats.getGameSettings().getBoardSize(),
                                    gameStats.getGameSettings().getBoardSize()
                            )
                    );
                    final TextView textViewTimeDuration = findViewById(R.id.textViewScoreStatsTimeDuration);
                    textViewTimeDuration.setText(
                            "Time Duration: " + Integer.toString(gameStats.getGameSettings().getTimeDuration())
                    );
                    final TextView textViewHighestScoringWord = findViewById(R.id.textViewScoreStatsHighestScoringWord);
                    textViewHighestScoringWord.setText(
                            "Highest Scoring Word: " + gameStats.getHighestScoringWord()
                    );
                }
            });
 */




