package com.example.newquizzapp_meftah;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText Mail, Password;
    Button Blogin;
    TextView Register;
    ProgressBar progressBar;

    private MediaRecorder recorder;

    private String audioFilePath;
    private static final int REQUEST_MICROPHONE = 1;
    private static final int REQUEST_CAMERA_PERMISSION=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des éléments de l'interface utilisateur
        auth = FirebaseAuth.getInstance();
        Mail = findViewById(R.id.Mail);
        Password = findViewById(R.id.Password);
        Blogin = findViewById(R.id.BLogin);
        Register = findViewById(R.id.TVRegister);

        // Demande des permissions pour le micro et la caméra
        requestPermissions();

        // Action de connexion au bouton
        Blogin.setOnClickListener(v -> loginUser());
        Register.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Register.class)));
    }

    private void loginUser() {
        String email = Mail.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            startActivity(new Intent(MainActivity.this, Quizz1.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Login failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Demander les permissions pour le micro et la caméra
    private void requestPermissions() {
        // Vérifier si les permissions sont déjà accordées
        boolean microphonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
        boolean cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        // Si l'une des deux permissions est manquante, demander les deux
        if (!microphonePermission || !cameraPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA},
                    REQUEST_MICROPHONE);  // On utilise le même code pour les deux permissions
        } else {
            // Si les permissions sont déjà accordées, on démarre l'enregistrement et la caméra
            startRecording();
            openCamera();
        }
    }

    // Méthode appelée après que l'utilisateur a répondu à la demande de permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Vérifier le code de la requête pour les permissions
        switch (requestCode) {
            case REQUEST_MICROPHONE:
                // Vérifier si les permissions pour le micro et la caméra ont été accordées
                boolean microphoneGranted = false;
                boolean cameraGranted = false;

                for (int i = 0; i < permissions.length; i++) {
                    if (Manifest.permission.RECORD_AUDIO.equals(permissions[i])) {
                        microphoneGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    }
                    if (Manifest.permission.CAMERA.equals(permissions[i])) {
                        cameraGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    }
                }

                // Si les deux permissions sont accordées, démarrer les actions correspondantes
                if (microphoneGranted && cameraGranted) {
                    startRecording();
                    openCamera();
                } else {
                    Toast.makeText(this, "Les permissions requises ont été refusées", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    // Démarrer l'enregistrement audio
    private void startRecording() {
        try {
            // Définir le chemin du fichier audio pour l'enregistrement
            audioFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/audio_record.3gp";

            // Initialiser et configurer le MediaRecorder pour l'enregistrement audio
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(audioFilePath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "🎤 Enregistrement démarré", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur enregistrement", Toast.LENGTH_SHORT).show();
        }
    }

    // Ouvrir la caméra pour capturer une image
    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(cameraIntent);
        }
    }
}
