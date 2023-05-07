package com.example.xinuaut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText passwordET;
    EditText userEmailET;
    private NotiHandler noti;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        passwordET = findViewById(R.id.registerPassword);
        userEmailET = findViewById(R.id.registerEmail);

        auth = FirebaseAuth.getInstance();

        noti = new NotiHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void registration(View view) {
        String pw = passwordET.getText().toString();
        String email = userEmailET.getText().toString();

        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    noti.send("Üdv nálunk!");
                    startItemList();
                }else {
                    Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, AppTheme.class);

        startActivity(intent);
    }

    private void startItemList(/*user*/){
        Intent intent = new Intent(this, CarPartsActivity.class);

        startActivity(intent);
    }


}