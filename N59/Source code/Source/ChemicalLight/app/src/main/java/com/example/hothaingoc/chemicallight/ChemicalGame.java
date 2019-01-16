package com.example.hothaingoc.chemicallight;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ChemicalGame extends AppCompatActivity {

    TextView textView_element;
    TextView textView_score;
    Button textView_time;

    Button buttonA, buttonB, buttonStart, buttonHigh;
    int isAns, trueFalse;
    int status; //-1: start; 0: run; 1: stop
    int currentScore;
    int highScore;
    int winLose;
    ProgressBar progressBar_time;

    //timer
    CountDownTimer countDownTimer;
    private static final long LIMIT_TIME = 10000;
    private long timeLeftinmills;


    final String DATABASE_NAME = "arenadata.sqlite";
    SQLiteDatabase sqLiteDatabase;

    //test high score
    final String DATA_SCORE = "note.sqlite";
    SQLiteDatabase sqlHigh;

    ArrayList<data_Element> arrayList;
    adapter_Element dataA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_game);

        initView();
        actionAns();
    }

    private void stateStatus(int currentStatus){
        switch (currentStatus){
            case -1: {
                status = 0;
                buttonStart.setText("Stop");
                buttonA.setVisibility(View.VISIBLE);
                buttonB.setVisibility(View.VISIBLE);
                textView_time.setVisibility(View.VISIBLE);
                requestion();
                break;
            }
            //run
            case 0: {
                status = 1;
                buttonStart.setText("Continue");
                buttonA.setVisibility(View.INVISIBLE);
                buttonB.setVisibility(View.INVISIBLE);
                textView_time.setVisibility(View.INVISIBLE);
                break;
            }
            //stop
            case 1: {
                status = 0;
                buttonStart.setText("Stop");
                buttonA.setVisibility(View.VISIBLE);
                buttonB.setVisibility(View.VISIBLE);
                textView_time.setVisibility(View.VISIBLE);
                break;
            }
            default: break;
        }
    }

    private void actionAns() {

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stateStatus(status);
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAns == 0){
                    //answer true
                    requestion();
                    updateCore(10);
                }
                else {
                    //answer false
                    statusWinLose();
                }
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAns == 1){
                    //answer true
                    requestion();
                    updateCore(10);
                }
                else {
                    //answer false
                    statusWinLose();
                }
            }
        });
    }

    private void updateCore(int corePlus) {
        currentScore += corePlus;
        textView_score.setText(String.valueOf(currentScore));

        //test high score
        if(currentScore > highScore){
            highScore = currentScore;
            buttonHigh.setText(String.valueOf(highScore));
        }

    }

    private void startTick() {

        //reset tool timer
        timeLeftinmills = LIMIT_TIME;
        progressBar_time.setProgress(0);

        if(countDownTimer != null)
        {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(timeLeftinmills,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinmills = millisUntilFinished;
                updateCountdownText();
                //test progressbar
                updateCountDownPro();
            }

            @Override
            public void onFinish() {
                //Toast.makeText(ChemicalGame.this, "Over", Toast.LENGTH_SHORT).show();
                timeLeftinmills = 0;
                updateCountdownText();
                //test progressbar
                updateCountDownPro();
                statusWinLose();
            }
        }.start();
    }

    private void updateCountDownPro() {
        int curprogress = progressBar_time.getProgress();
        if(curprogress > progressBar_time.getMax()){
            progressBar_time.setProgress(0);
        }
        else{
            progressBar_time.setProgress(curprogress+10);
        }
    }

    private void updateCountdownText() {
        int tempMI = (int)(timeLeftinmills/1000) / 60;
        int tempSe = (int)(timeLeftinmills/1000) % 60;
        String timeTemp = String.format(Locale.getDefault(),"%02d:%02d",tempMI,tempSe);
        textView_time.setText(timeTemp);

        if(timeLeftinmills < 5000){
            textView_time.setTextColor(Color.RED);
        }
        else{
            textView_time.setTextColor(Color.GREEN);
        }
    }

    private void requestion(){

        //random
        int min = 1;
        int max = 118;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        int ar = r.nextInt(2);

        //timer progessbar
        //progressBar_time.setProgress(0);
        //countTimer(10000,100);
        startTick();

        //update core
        textView_score.setText(String.valueOf(currentScore));


        //data question
        sqLiteDatabase = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Element WHERE Atomic_Number = ? ",new String[]{String.valueOf(i1)});
        cursor.moveToFirst();

        //init questions
        String quesName = cursor.getString(3);
        Float ansTrue = cursor.getFloat(1);
        Float ansFalse = ansTrue + Float.valueOf(i1);
        textView_element.setText(quesName);


        //Toast.makeText(this, String.valueOf(ar), Toast.LENGTH_SHORT).show();
        isAns = ar;
        switch (isAns){

            case 0: {
                buttonA.setText(String.valueOf(ansTrue));
                buttonB.setText(String.valueOf(ansFalse));
                break;
            }
            case 1: {
                buttonA.setText(String.valueOf(ansFalse));
                buttonB.setText(String.valueOf(ansTrue));
                break;
            }
        }

    }

    private void statusWinLose(){
        //Toast.makeText(ChemicalGame.this, "gameOver", Toast.LENGTH_SHORT).show();
        textView_element.setText("Game Over");
        status = -1;
        buttonStart.setText("Start");
        currentScore = 0;
        textView_score.setText("0");
        buttonA.setVisibility(View.INVISIBLE);
        buttonB.setVisibility(View.INVISIBLE);
        textView_time.setVisibility(View.INVISIBLE);

        sqlHigh= Database.initDatabase(this,DATA_SCORE);
        ContentValues contentValues = new ContentValues();
        contentValues.put("HC",String.valueOf(highScore));
        sqlHigh.update("HIGHGAME",contentValues,"ID=?",new String[]{"1"});
    }

    private void initView() {

        isAns = -1;
        currentScore = 0;
        status = -1;
        winLose = 0;

        textView_element = (TextView) findViewById(R.id.txt_NameElement);
        textView_score = (TextView) findViewById(R.id.txt_score);
        textView_time = (Button) findViewById(R.id.btn_timetick);

        buttonA = (Button) findViewById(R.id.btn_anA);
        buttonB = (Button) findViewById(R.id.btn_anB);
        buttonA.setVisibility(View.INVISIBLE);
        buttonB.setVisibility(View.INVISIBLE);
        textView_time.setVisibility(View.INVISIBLE);
        buttonStart = (Button) findViewById(R.id.btn_start);
        buttonHigh = (Button) findViewById(R.id.high_game);

        progressBar_time = (ProgressBar) findViewById(R.id.pro_time);
        progressBar_time.setMax(100);
        progressBar_time.setProgress(0);

        sqlHigh = Database.initDatabase(this,DATA_SCORE);
        Cursor cursor = sqlHigh.rawQuery("SELECT * FROM HIGHGAME WHERE ID = ? ",new String[]{"1"});
        cursor.moveToFirst();
        highScore = cursor.getInt(1);
        buttonHigh.setText(String.valueOf(highScore));

    }
}
