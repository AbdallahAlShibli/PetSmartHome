package com.example.petsmarthome;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class forgetPassword extends AppCompatActivity {
    EditText mail;
    Button send_email;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        mail = (EditText) findViewById(R.id.inputEmail);
        send_email = (Button) findViewById(R.id.send_email);

        Button button=(Button)findViewById(R.id.send_email);
        Button but_back=(Button)findViewById(R.id.back);

        auth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();

                    return;
                }


                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplication(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplication(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgetPassword.this, SelectUserActivity.class));
                finish();
            }
        });
        return ;
    }



}
