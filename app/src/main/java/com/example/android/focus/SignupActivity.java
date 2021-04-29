package com.example.android.focus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class SignupActivity extends AppCompatActivity {
    private Button mRegistration;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private TextView mAlready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent= new Intent(SignupActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mAuth=FirebaseAuth.getInstance();
        mRegistration=findViewById(R.id.registration);
        mEmail=findViewById(R.id.emails);
        mPassword=findViewById(R.id.passwords);
        mAlready=findViewById(R.id.textView);

        mAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=mEmail.getText().toString();
                final String password=mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Executor) SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(!task.isSuccessful()){
                            Toast.makeText(getApplication(),"Sign in error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}