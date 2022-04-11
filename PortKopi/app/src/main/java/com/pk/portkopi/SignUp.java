//package com.pk.portkopi;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textview.MaterialTextView;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//import com.pk.portkopi.Model.User;
//
//public class SignUp extends AppCompatActivity implements View.OnClickListener {
//
//    //Variables
//    private FirebaseAuth mAuth;
//
//
//    //TextInputLayout regName, regUsername, regEmail, regPassword;
//    EditText name, username, editTextEmail, editTextPassword;
//
//    MaterialButton signUp;
//    MaterialTextView callSignIn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // get the reference of View's
//
//        //regName = (TextInputLayout) findViewById(R.id.reg_name);
//        //regUsername = (TextInputLayout) findViewById(R.id.reg_username);
//        //regEmail = (TextInputLayout) findViewById(R.id.reg_email);
//        //regPassword = (TextInputLayout) findViewById(R.id.reg_password);
//
//        name = (EditText) findViewById(R.id.name);
//        username = (EditText) findViewById(R.id.username);
//        editTextEmail = (EditText) findViewById(R.id.email);
//        editTextPassword = (EditText) findViewById(R.id.password);
//
//        signUp = findViewById(R.id.signUp);
//        signUp.setOnClickListener(this);
//
//        callSignIn = findViewById(R.id.signIn);
//        callSignIn.setOnClickListener(this);
//
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), Login.class));
//            finish();
//        }
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.signIn:
//                startActivity(new Intent(this, Login.class));
//                break;
//            case R.id.signUp:
//                signUp();
//                break;
//        }
//    }
//
//    private void signUp() {
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//        String regname = name.getText().toString().trim();
//        String regusername = username.getText().toString().trim();
//
//
//        if(regname.isEmpty()) {
//            name.setError("Full name is required");
//            name.requestFocus();
//            return;
//        }
//
//        if(regusername.isEmpty()) {
//            username.setError("Username is required");
//            username.requestFocus();
//            return;
//        }
//
//        if(email.isEmpty()) {
//            editTextEmail.setError("Email is required");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            editTextEmail.setError("Email is not valid");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if(password.isEmpty()) {
//            editTextPassword.setError("Password is required");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        if(editTextPassword.length() < 6) {
//            editTextPassword.setError("Minimum password length should be 6 characters!");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//
//                    String id = mAuth.getCurrentUser().getUid();
//                    User user = new User(regname, regusername, email, id);
//
//                    FirebaseDatabase.getInstance().getReference("Users")
//                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful()) {
//                                Toast.makeText(SignUp.this, "Registration success", Toast.LENGTH_LONG).show();
//
//                            } else {
//                                Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//}