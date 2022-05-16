package com.pk.portkopi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginNew extends AppCompatActivity {
    private EditText emailLogin, passwordLogin;
    private TextView createAcc, btn_forgotpw;
    private Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(LoginNew.this,NavMainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginNew.this, "Welcome Back", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        emailLogin= (EditText) findViewById(R.id.Email);
        passwordLogin= (EditText) findViewById(R.id.password);
        createAcc= (TextView) findViewById(R.id.btn_signup);
        login= (Button) findViewById(R.id.btn_login);
        btn_forgotpw = (TextView) findViewById(R.id.btn_forgotpw);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, ForgotPassword.class));
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, SignUpNew.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();

                if (email.isEmpty()){
                    emailLogin.setError("Email is required");
                    emailLogin.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    passwordLogin.setError("Password is required");
                    passwordLogin.requestFocus();
                    return;
                }
                if (passwordLogin.length()<6){
                    passwordLogin.setError("Min password length should be 6 characters!");
                    passwordLogin.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(mAuth.getCurrentUser().getUid());

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Intent intent = new Intent(LoginNew.this,NavMainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginNew.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(LoginNew.this, "Failed to Log In!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}