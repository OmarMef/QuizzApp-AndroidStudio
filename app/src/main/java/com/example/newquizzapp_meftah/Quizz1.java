package com.example.newquizzapp_meftah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class Quizz1 extends AppCompatActivity {

    Timer timer;
    Button Banswer1, Banswer2, Banswer3;
    int score = 0;
    boolean isPressed = false;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quizz1);

        Banswer1 = findViewById(R.id.BAnswer1);
        Banswer2 = findViewById(R.id.BAnswer2);
        Banswer3 = findViewById(R.id.BAnswer3);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        Banswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPressed == false){
                    Banswer1.setBackgroundColor(getResources().getColor(R.color.red));
                    isPressed=true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz1.this , Quizz2.class);
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
                    Banswer2.setBackgroundColor(getResources().getColor(R.color.green));
                    score+=1;
                    isPressed = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz1.this,Quizz2.class);
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
                if (isPressed == false){
                    Banswer3.setBackgroundColor(getResources().getColor(R.color.red));
                    isPressed = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Quizz1.this,Quizz2.class);
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