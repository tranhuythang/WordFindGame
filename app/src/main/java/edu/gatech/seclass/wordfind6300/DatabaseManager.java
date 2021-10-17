package edu.gatech.seclass.wordfind6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WordFindTeam77";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORD_TABLE =
                " create table WordStatistics " +
                        " (word text primary key, " +
                        " frequency integer) ";
        String CREATE_SCORE_TABLE =
                " create table ScoreStatistics " +
                        " (id integer primary key autoincrement, " +
                        " score integer, " +
                        " resetCount integer, " +
                        " wordCount integer, " +
                        " boardSize integer, " +
                        " duration integer, " +
                        " highestScoreWord text) ";
        String CREATE_SETTING_TABLE =
                " create table Setting " +
                        " (id integer primary key autoincrement, " +
                        " duration integer, " +
                        " boardSize integer," +
                        " A integer, " +
                        " B integer, " +
                        " C integer, " +
                        " D integer, " +
                        " E integer, " +
                        " F integer, " +
                        " G integer, " +
                        " H integer, " +
                        " I integer, " +
                        " J integer, " +
                        " K integer, " +
                        " L integer, " +
                        " M integer, " +
                        " N integer, " +
                        " O integer, " +
                        " P integer, " +
                        " Qu integer, " +
                        " R integer, " +
                        " S integer, " +
                        " T integer, " +
                        " U integer, " +
                        " V integer, " +
                        " W integer, " +
                        " X integer, " +
                        " Y integer, " +
                        " Z integer) ";

        String CREATE_DICTIONARY_TABLE = " create table Dictionary (word text primary key)";

        String Initial_Setting_Insert = "insert into Setting values(0, 3, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)";

        db.execSQL(CREATE_WORD_TABLE);
        db.execSQL(CREATE_SCORE_TABLE);
        db.execSQL(CREATE_SETTING_TABLE);
        db.execSQL(Initial_Setting_Insert);
        db.execSQL(CREATE_DICTIONARY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS WordStatistics");
        db.execSQL("DROP TABLE IF EXISTS ScoreStatistics");
        db.execSQL("DROP TABLE IF EXISTS Dictionary");
        db.execSQL("DROP TABLE IF EXISTS Setting");

        // Create tables again
        onCreate(db);
    }

    // Downgrading database
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS WordStatistics");
        db.execSQL("DROP TABLE IF EXISTS ScoreStatistics");
        db.execSQL("DROP TABLE IF EXISTS Dictionary");
        db.execSQL("DROP TABLE IF EXISTS Setting");

        // Create tables again
        onCreate(db);
    }


    // View setting
    public int[] viewSetting(){
        int[] setting = new int[28];
        SQLiteDatabase db = this.getReadableDatabase();
        String querry = "select * from Setting where id = 0";
        Cursor cursor = db.rawQuery(querry, null);
        if(cursor.moveToFirst())
            for(int i = 0; i < 28; i++)
                setting[i] = cursor.getInt(i+1);
        return setting;
    }

    // Update setting
    public void updateSetting(int[] conf){
        SQLiteDatabase db = this.getWritableDatabase();
        String querry =
                "update Setting " +
                        " set " +
                        " duration = " + conf[0] + ", " +
                        " boardSize = " + conf[1] + ", " +
                        " A = " + conf[2] + ", " +
                        " B = " + conf[3] + ", " +
                        " C = " + conf[4] + ", " +
                        " D = " + conf[5] + ", " +
                        " E = " + conf[6] + ", " +
                        " F = " + conf[7] + ", " +
                        " G = " + conf[8] + ", " +
                        " H = " + conf[9] + ", " +
                        " I = " + conf[10] + ", " +
                        " J = " + conf[11] + ", " +
                        " K = " + conf[12] + ", " +
                        " L = " + conf[13] + ", " +
                        " M = " + conf[14] + ", " +
                        " N = " + conf[15] + ", " +
                        " O = " + conf[16] + ", " +
                        " P = " + conf[17] + ", " +
                        " Qu = " + conf[18] + ", " +
                        " R = " + conf[19] + ", " +
                        " S = " + conf[20] + ", " +
                        " T = " + conf[21] + ", " +
                        " U = " + conf[22] + ", " +
                        " V = " + conf[23] + ", " +
                        " W = " + conf[24] + ", " +
                        " X = " + conf[25] + ", " +
                        " Y = " + conf[26] + ", " +
                        " Z = " + conf[27] +
                        " where id = 0";
        db.execSQL(querry);
    }

    // Add a game to the ScoreStatistics
    public void addToScoreTable(int score, int resetCount, int wordCount, int boardSize, int duration, String highestScoreWord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", score);
        values.put("resetCount", resetCount);
        values.put("wordCount", wordCount);
        values.put("boardSize", boardSize);
        values.put("duration", duration);
        values.put("highestScoreWord", highestScoreWord);

        db.insert("ScoreStatistics", null, values);
    }

    // Add a word to the WordStatistics table - 1 argument
    public void addToWordTable(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        String querry1 = " update WordStatistics set frequency = frequency + 1 where  word = '" + word + "'";
        String querry2 = " insert or ignore into WordStatistics(word, frequency) values ('" + word + "', 1)";

        db.execSQL(querry1);
        db.execSQL(querry2);
    }

    // Add a word to the WordStatistics table - 2 arguments
    public void addToWordTable(String word, int frq) {
        SQLiteDatabase db = this.getWritableDatabase();

        String querry1 = " update WordStatistics set frequency = frequency + " + String.valueOf(frq) + " where  word = '" + word + "'";
        String querry2 = " insert or ignore into WordStatistics(word, frequency) values ('" + word + "', " + String.valueOf(frq) + ") ";

        db.execSQL(querry1);
        db.execSQL(querry2);
    }

    // List all words
    public ArrayList<ArrayList<String>> listAllWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ArrayList<String>> wordList = new ArrayList<>();

        String selectQuery = "select * from WordStatistics order by frequency desc";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> temp;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                temp = new ArrayList<>();
                temp.add(cursor.getString(0));
                temp.add(String.valueOf(cursor.getInt(1)));
                wordList.add(temp);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public ArrayList<ArrayList<String>> listAllGames() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<ArrayList<String>> gameList = new ArrayList<>();

        String selectQuery = "select * from ScoreStatistics order by score desc";

        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> temp;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                temp = new ArrayList<>();
                for(int i = 1; i <= 5; i++)
                    temp.add(String.valueOf(cursor.getInt(i)));
                temp.add(cursor.getString(6));
                gameList.add(temp);
            } while (cursor.moveToNext());
        }
        return gameList;
    }
}