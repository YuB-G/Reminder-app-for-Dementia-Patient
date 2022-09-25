package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Page for users sign up with their own email and password
public class SignUpPage extends AppCompatActivity {
    Button btn_done = null;
    EditText mName,mEmail,mPassword,mRePassword;
    FirebaseAuth fAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        btn_done = (Button) findViewById(R.id.btn_register_done);
        mName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        fAuth = FirebaseAuth.getInstance();
        mRePassword = findViewById(R.id.et_repassword);
        imageView = findViewById(R.id.Back_to_Signin);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SignUpPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // Done button, check if sign up information has correct format
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String Re_password = mRePassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    mName.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Re_password)){
                    mPassword.setError("Please Re-type your password");
                    return;
                }
                if(!TextUtils.equals(password,Re_password)) {
                    mRePassword.setError("Not match with your password");
                    return;
                }
                // Firebase sign up
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpPage.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else{
                            Toast.makeText(SignUpPage.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //sign up dome, go back to login page
                Intent intent = new Intent();
                intent.setClass(SignUpPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}