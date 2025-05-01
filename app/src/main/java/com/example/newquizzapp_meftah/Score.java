package com.example.newquizzapp_meftah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class Score extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView progressText , TVexprience;
    Button TryAgain, Logout, Map;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.PBprogressbar);
        progressText = findViewById(R.id.TVprogresstext);
        TVexprience = findViewById(R.id.TVExperience);
        TryAgain = findViewById(R.id.BTryAgain);
        Logout = findViewById(R.id.BLogOut);
        Map = findViewById(R.id.BMap);
        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);
        int percentage = 100 * score /5;
        progressBar.setProgress(percentage);
        progressText.setText(percentage +"%");

        if(percentage == 0){
            TVexprience.setText("Very Bad try again !");
        }
        if(percentage > 0  && percentage <= 40 ){
                TVexprience.setText("Not Bad , you can do better !");
        }
        if(percentage > 40 && percentage < 60){
            TVexprience.setText("Good !!");
        }
        if(percentage >= 60 && percentage <= 80){
            TVexprience.setText("Very Good !!");
        }
        if(percentage == 100){
            TVexprience.setText("Excellent !!!");
        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Thanks for your participation",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Score.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Quizz1.class));
                finish();
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Map.class));
            }
        });



    }
}