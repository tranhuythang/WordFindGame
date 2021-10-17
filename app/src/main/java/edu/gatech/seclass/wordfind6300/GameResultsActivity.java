package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        Intent intent = getIntent();
        TextView textViewGameResultsScoreResultValue = findViewById(R.id.textViewGameResultsScoreResultValue);
        textViewGameResultsScoreResultValue.setText(String.valueOf(intent.getIntExtra("score", 0)));
        TextView textViewGameResultsHighestScoringWordValue = findViewById(R.id.textViewGameResultsHighestScoringWordValue);
        textViewGameResultsHighestScoringWordValue.setText(intent.getStringExtra("highestScoringWord"));

        Button buttonGameResultsMainMenu = findViewById(R.id.buttonGameResultsMainMenu);
        buttonGameResultsMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button buttonGameResultsPlayANewGame = findViewById(R.id.buttonGameResultsPlayANewGame);
        buttonGameResultsPlayANewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playANewGameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(playANewGameIntent);
                finish();
            }
        });


        Button buttonGameResultsExit = findViewById(R.id.buttonGameResultsExit);
        buttonGameResultsExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }
            });
    }
}
