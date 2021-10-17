package edu.gatech.seclass.wordfind6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final DatabaseManager db = new DatabaseManager(this);

        final SeekBar seekBarSettingsTimeDuration = findViewById(R.id.seekBarSettingsTimeDuration);
        final RadioGroup radioGroupSettingsBoardSize = findViewById(R.id.radioGroupSettingsBoardSize);
        final TableLayout tableLayoutSettingsLetterWeights = findViewById(R.id.tableLayoutSettingsLetterWeights);

        //int boardSize = SharedDataHolder.getGameSettings().getBoardSize();
        int boardSize = db.viewSetting()[1];
        //int timeDuration = SharedDataHolder.getGameSettings().getTimeDuration();
        int timeDuration = db.viewSetting()[0];
        int letterNum = 26;
        //int[] letterWeights = SharedDataHolder.getGameSettings().getLetterWeights();
        int[] letterWeights = Arrays.copyOfRange(db.viewSetting(),2,28 );
        seekBarSettingsTimeDuration.setProgress(timeDuration - 1);
        ((RadioButton) radioGroupSettingsBoardSize.getChildAt(boardSize - 4)).setChecked(true);

        for(int i = 0; i < letterNum; i++) {
            int indexOfRow = i / 3;
            int indexOfCol = 2 * (i % 3) + 1;
            View rowView = tableLayoutSettingsLetterWeights.getChildAt(indexOfRow);
            TableRow tableRow = (TableRow)rowView;
            Spinner spinner = (Spinner) tableRow.getChildAt(indexOfCol);
            spinner.setSelection(letterWeights[i] - 1);
        }

        Button buttonSettingsMainMenu = findViewById(R.id.buttonSettingsMainMenu);
        buttonSettingsMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button buttonSettingsSave = findViewById(R.id.buttonSettingsSave);
        buttonSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int letterNum = 26;
                int[] letterWeights = new int[letterNum];
                int timeDuration = seekBarSettingsTimeDuration.getProgress() + 1;
                RadioButton selectedButton = findViewById(radioGroupSettingsBoardSize.getCheckedRadioButtonId());
                int boardSize = Integer.parseInt(selectedButton.getText().toString());
                for(int i = 0; i < letterNum; i++) {
                    int indexOfRow = i / 3;
                    int indexOfCol = 2 * (i % 3) + 1;
                    View rowView = tableLayoutSettingsLetterWeights.getChildAt(indexOfRow);
                    TableRow tableRow = (TableRow)rowView;
                    Spinner spinner = (Spinner) tableRow.getChildAt(indexOfCol);
                    letterWeights[3 * indexOfRow + (indexOfCol - 1) / 2] = spinner.getSelectedItemPosition() + 1;
                }
                //SharedDataHolder.getGameSettings().setTimeDuration(timeDuration);
                //SharedDataHolder.getGameSettings().setBoardSize(boardSize);
                //SharedDataHolder.getGameSettings().setLetterWeights(letterWeights);
                int[] setting = new int[28];
                setting[1] = boardSize;
                setting[0] = timeDuration;
                for(int i = 2; i < 28; i++)
                    setting[i] = letterWeights[i-2];
                db.updateSetting(setting);
                Toast.makeText(SettingsActivity.this, "Settings are saved.", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonSettingsReset = findViewById(R.id.buttonSettingsReset);
        buttonSettingsReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBarSettingsTimeDuration.setProgress(2);
                ((RadioButton)radioGroupSettingsBoardSize.findViewById(R.id.radioButtonSettingsBoardSize4)).setChecked(true);
                int letterNum = 26;
                int[] letterWeights = new int[letterNum];
                for(int i = 0; i < letterNum; i++) {
                    int indexOfRow = i / 3;
                    int indexOfCol = 2 * (i % 3) + 1;
                    View rowView = tableLayoutSettingsLetterWeights.getChildAt(indexOfRow);
                    TableRow tableRow = (TableRow)rowView;
                    Spinner spinner = (Spinner) tableRow.getChildAt(indexOfCol);
                    spinner.setSelection(0);
                    letterWeights[i] = 1;
                }

                int[] setting = new int[28];
                setting[0] = 3;
                setting[1] = 4;
                for(int i = 2; i < 28; i++)
                    setting[i] = 1;
                db.updateSetting(setting);
                //SharedDataHolder.getGameSettings().setBoardSize(4);
                //SharedDataHolder.getGameSettings().setTimeDuration(3);
                //SharedDataHolder.getGameSettings().setLetterWeights(letterWeights);
            }
        });
    }
}
