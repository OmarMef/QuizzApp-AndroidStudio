package com.example.newquizzapp_meftah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.StartupTime;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    EditText UserName , Password , Mail , ConfirmP;
    Button BRgister;
    TextView TVLogin;
    ProgressBar progressBar;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        UserName = findViewById(R.id.UserName);
        Password = findViewById(R.id.Password);
        Mail = findViewById(R.id.Mail);
        ConfirmP = findViewById(R.id.ConfirmP);
        BRgister = findViewById(R.id.BRegister);
        TVLogin = findViewById(R.id.TVLog);
        progressBar = findViewById(R.id.PBprogressbar);


        TVLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });


        BRgister.setOnClickListener(View -> {

                    progressBar.setVisibility(View.VISIBLE);

                    String username = UserName.getText().toString();
                    String mail = Mail.getText().toString();
                    String password = Password.getText().toString();
                    String confirmP = ConfirmP.getText().toString();

                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(mail)) {
                        Toast.makeText(getApplicationContext(), "Enter e-mail", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(confirmP)) {
                        Toast.makeText(Register.this, "Enter password to confirm it", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }

                    auth.createUserWithEmailAndPassword(mail, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();

                                        Map<String, Object> user = new HashMap<>();
                                        user.put("UserName", username);
                                        user.put("Mail", mail);
                                        user.put("Password", password);

                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(Register.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Register.this, "Failed to store data", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                    });
        });

        /*if(!password.equals(confirmP)){
            runOnUiThread(() -> {
                ConfirmP.setError("Password doesn't match");
                progressDialog.dismiss();

            });
            return;
        }
        auth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener((OnCompleteListener<AuthResult>) task ->{
            if(task.isSuccessful()){
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference ref = database.child("Users").child(user.getUid());
                ref.child("UserName").setValue(username);
                runOnUiThread(()->{
                    progressDialog.dismiss();
                    Intent i = new Intent(Register.this,MainActivity.class);
                    i.putExtra("User UID",user.getUid());
                    startActivity(i);
                    finish();
                });
            }else{
                Toast.makeText(Register.this,"Operation Failed",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    });
    thread.start();
});*/
    }
}
