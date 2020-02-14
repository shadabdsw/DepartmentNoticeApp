package com.darkshadows.shady.studentsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmailText;
    private EditText registerPassText;
    private EditText registerConfirmPassText;
    private Button registerBtn;
    private Button registerLoginBtn;
    private FirebaseAuth mAuth;
    private ProgressBar registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerEmailText = (EditText)findViewById(R.id.register_email);
        registerPassText = (EditText)findViewById(R.id.register_password);
        registerConfirmPassText = (EditText)findViewById(R.id.register__confirm_password);
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerLoginBtn = (Button) findViewById(R.id.register_login_btn);
        registerProgress = (ProgressBar)findViewById(R.id.register_progress);

        registerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String registerEmail = registerEmailText.getText().toString();
                String registerPass = registerPassText.getText().toString();
                String registerConfirmPass = registerConfirmPassText.getText().toString();

                if(!TextUtils.isEmpty(registerEmail) && !TextUtils.isEmpty(registerPass))
                {
                    if(registerPass.equals(registerConfirmPass))
                    {
                        registerProgress.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(registerEmail, registerPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(RegisterActivity.this, SetupActivity.class));
                                    finish();
                                }
                                else
                                {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                                registerProgress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Please fill in the information to proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null)
        {
            sendToMain();
        }
    }

    private void sendToMain()
    {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}
