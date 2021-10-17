package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMainPlayANewGame = findViewById(R.id.buttonMainPlayANewGame);
        buttonMainPlayANewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playANewGameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(playANewGameIntent);
            }
        });

        Button buttonMainAdjustSettings = findViewById(R.id.buttonMainAdjustSettings);
        buttonMainAdjustSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adjustSettingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(adjustSettingsIntent);
            }
        });

        Button buttonMainViewStats = findViewById(R.id.buttonMainViewStats);
        buttonMainViewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewStatisticsIntent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(viewStatisticsIntent);
            }
        });

        Button buttonMainExit = findViewById(R.id.buttonMainExit);
        buttonMainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        //Thang's code
        /*
        Button playThang = findViewById(R.id.Play_Thang);
        playThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playANewGameIntent = new Intent(getApplicationContext(), GameActivity_Thang.class);
                playANewGameIntent.putExtra("boardSize", 6);
                playANewGameIntent.putExtra("timeDuration", 3);
                //playANewGameIntent.putExtra("letterWeight",letterWeight);
                startActivity(playANewGameIntent);
            }
        });

         */
    }
}