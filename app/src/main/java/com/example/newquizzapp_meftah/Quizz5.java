package com.example.newquizzapp_meftah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Quizz5 extends AppCompatActivity {

    Timer timer;
    Button Banswer1 ,Banswer2, Banswer3;
    int score;

    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quizz5);

        Banswer1 = findViewById(R.id.BAnswer1);
        Banswer2 = findViewById(R.id.BAnswer2);
        Banswer3 = findViewById(R.id.BAnswer3);
        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);

        Banswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressed == false){
                    Banswer1.setBackgroundColor(getResources().getColor(R.color.red));
                    isPressed = true;
                    timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz5.this, Score.class);
                            intent.putExtra("score",score);
                            startActivity(intent);
                            finish();
                        }
                    },1000);
                }
            }
        });

        Banswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressed == false){
                    Banswer2.setBackgroundColor(getResources().getColor(R.color.red));
                    isPressed = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz5.this, Score.class);
                            intent.putExtra("score",score);
                            startActivity(intent);
                            finish();
                        }
                    },1000);
                }
            }
        });

        Banswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressed == false){
                    Banswer3.setBackgroundColor(getResources().getColor(R.color.green));
                    score+=1;
                    isPressed = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz5.this, Score.class);
                            intent.putExtra("score",score);
                            startActivity(intent);
                            finish();
                        }
                    },1000);
                }
            }
        });
    }
}