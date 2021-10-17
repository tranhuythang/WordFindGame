package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.*;

import static java.lang.Math.min;

public class WordStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_stats);

        Button buttonWordStatsMainMenu = findViewById(R.id.buttonWordStatsMainMenu);
        buttonWordStatsMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button buttonWordStatsScoreStats = findViewById(R.id.buttonWordStatsScoreStats);
        buttonWordStatsScoreStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreStatisticsIntent = new Intent(getApplicationContext(), ScoreStatsActivity.class);
                startActivity(scoreStatisticsIntent);
                finish();
            }
        });

        DatabaseManager db = new DatabaseManager(this);
        final ArrayList<ArrayList<String>> wordList = db.listAllWords();

        ScrollView scrollview = findViewById(R.id.scrollView);
        final TableLayout tableLayout = findViewById(R.id.tableLO);
        tableLayout.setStretchAllColumns(true);
        final int length = wordList.size();
        TextView[][] tv = new TextView[length][3];
        final TableRow[] tableRow = new TableRow[length];

        for (int i = 0; i < length; i++) {
            tv[i][0] = new TextView(this);
            tv[i][0].setText(String.valueOf(i + 1));
            tableRow[i] = new TableRow(this);
            tableRow[i].addView(tv[i][0]);
            for (int j = 1; j <= 2; j++) {
                tv[i][j] = new TextView(this);
                tv[i][j].setText(wordList.get(i).get(j-1));
                tableRow[i].addView(tv[i][j]);
            }
            tableLayout.addView(tableRow[i]);
        }
    }
}
