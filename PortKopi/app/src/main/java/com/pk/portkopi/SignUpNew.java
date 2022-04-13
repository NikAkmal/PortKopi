package com.pk.portkopi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpNew extends AppCompatActivity {
    //declare variable
    private EditText fullname, username, email, password;
    private TextView log_in;
    private Button sign_up;
    private ImageView img1, img2, img3;
    private TextView atleast6, upper, number, symbol;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_new);

        fullname=(EditText) findViewById(R.id.fullname_up);
        username=(EditText) findViewById(R.id.username_up);
        email=(EditText) findViewById(R.id.email_up);
        password=(EditText) findViewById(R.id.password_up);
        log_in=(TextView) findViewById(R.id.btn_loginAcc);
        sign_up=(Button) findViewById(R.id.btn_signupAcc);

        atleast6 = findViewById(R.id.atleast6);
        upper = findViewById(R.id.upper);
        number = findViewById(R.id.number);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpNew.this, LoginNew.class));
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 6 characters
                if (hasLength(charSequence)) {
                    img1.setImageResource(R.drawable.checkbox);

                } else {
                    img1.setImageResource(R.drawable.uncheck);
                }

                // uppercase
                if (hasUpper(charSequence)) {
                    img2.setImageResource(R.drawable.checkbox);

                } else {
                    img2.setImageResource(R.drawable.uncheck);
                }

                // number
                if (hasNumber(charSequence)) {
                    img3.setImageResource(R.drawable.checkbox);

                } else {
                    img3.setImageResource(R.drawable.uncheck);
                }

                if (hasLength(charSequence) && hasUpper(charSequence) && hasNumber(charSequence) ) {
                    sign_up.setEnabled(true);
                } else {
                    sign_up.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignUpNew.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();

                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)
                        || TextUtils.isEmpty(str_username)|| TextUtils.isEmpty(str_fullname)){
                    Toast.makeText(SignUpNew.this, " All Filed are required", Toast.LENGTH_SHORT).show();

                }else if (str_password.length()<6){
                    Toast.makeText(SignUpNew.this, " Min password length should be 6 characters!", Toast.LENGTH_SHORT).show();

                }else{

                    signUp(str_email, str_password, str_username, str_fullname);
                }
            }
        });
    }

    public boolean hasLength(CharSequence value) {
        return String.valueOf(value).length() >=6;
    }

    public boolean hasNumber(CharSequence value) {
        return String.valueOf(value).matches("(.*[0-9].*)");
    }

    public boolean hasUpper(CharSequence value) {
        String s = String.valueOf(value);
        return  !s.equals(s.toLowerCase());
    }

    private void signUp(String email, String password, String username, String fullname){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpNew.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("fullname", fullname);
                            hashMap.put("bio", "");
                            hashMap.put("email", email);
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/portkopi-fbd40.appspot.com/o/default-profile-icon-24.jpg?alt=media&token=256a3215-a12b-4095-8250-dfceaeaa4ff9");

//                            Toast.makeText(SignUp.this, "Registration success", Toast.LENGTH_SHORT).show();
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUpNew.this, LoginNew.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(SignUpNew.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}