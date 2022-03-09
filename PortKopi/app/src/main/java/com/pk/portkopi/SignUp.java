package com.pk.portkopi;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //Variables
    private FirebaseAuth mAuth;

    TextInputLayout regName, regUsername, regEmail, regPassword;
    EditText name, username, editTextEmail, editTextPassword;

    MaterialButton signUp;
    MaterialTextView callSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // get the reference of View's

        regName = (TextInputLayout) findViewById(R.id.reg_name);
        regUsername = (TextInputLayout) findViewById(R.id.reg_username);
        regEmail = (TextInputLayout) findViewById(R.id.reg_email);
        regPassword = (TextInputLayout) findViewById(R.id.reg_password);

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        signUp = (MaterialButton) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        callSignIn = (MaterialTextView) findViewById(R.id.signIn);
        callSignIn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.signUp:
                signUp();
                break;
        }
    }

    private void signUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String regName = name.getText().toString().trim();
        String regUsername = username.getText().toString().trim();

        if(regName.isEmpty()) {
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }

        if(regUsername.isEmpty()) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        }

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        User user = new User(regName, regUsername, email, password);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}