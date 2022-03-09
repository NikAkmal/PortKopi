package com.pk.portkopi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    MaterialTextView callSignUp;

    TextInputLayout loginEmail, login_password;
    EditText editTextEmail, editTextPassword;

    MaterialButton logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // get the reference of View's
        loginEmail = (TextInputLayout) findViewById(R.id.loginEmail);
        login_password = (TextInputLayout) findViewById(R.id.login_password);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        callSignUp = (MaterialTextView) findViewById(R.id.signUp);
        callSignUp.setOnClickListener(this);

        logIn = (MaterialButton) findViewById(R.id.loginBTN);
        logIn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.loginBTN:
                userlogIn();
                break;

        }
    }

    private void userlogIn() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(editTextPassword.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //redirect to homepage
                    startActivity(new Intent(Login.this, HomePage.class));

                } else{
                    Toast.makeText(Login.this, "Failed to Log In!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

}