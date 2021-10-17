package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Button buttonSettingsMainMenu = findViewById(R.id.buttonSettingsMainMenu);
        buttonSettingsMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button buttonStatsWordStats = findViewById(R.id.buttonStatsWordStats);
        buttonStatsWordStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wordStatisticsIntent = new Intent(getApplicationContext(), WordStatsActivity.class);
                startActivity(wordStatisticsIntent);
            }
        });

        Button buttonStatsScoreStats = findViewById(R.id.buttonStatsScoreStats);
        buttonStatsScoreStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreStatisticsIntent = new Intent(getApplicationContext(), ScoreStatsActivity.class);
                startActivity(scoreStatisticsIntent);
            }
        });

    }
}
