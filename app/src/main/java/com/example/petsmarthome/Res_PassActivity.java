package com.example.petsmarthome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class Res_PassActivity extends AppCompatActivity {
    TextView textView;
    EditText editText1, editText2,Old_pass;
    Button but,back;
    FirebaseAuth Auth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    public static final String EXTRA_MESSAGE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res__pass);


        Intent intent = getIntent();
        String a = intent.getStringExtra(SelectUserActivity.EXTRA_MESSAGE);


            SharedPreferences sharedPreferences = getSharedPreferences("GetData", Context.MODE_PRIVATE);
            final String email = sharedPreferences.getString("Email", EXTRA_MESSAGE);
            textView = (TextView) findViewById(R.id.Email);
            textView.setVisibility(View.GONE);
            textView.setText(email);



        progressDialog = new ProgressDialog(this);


        Old_pass= (EditText) findViewById(R.id.etPassword);
        editText1 = (EditText) findViewById(R.id.editPass);
        editText2 = (EditText) findViewById(R.id.editCon_Pass);
        Auth = FirebaseAuth.getInstance();


        but = (Button) findViewById(R.id.but_Save);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change password
                Change_pass(textView.getText().toString(), Old_pass.getText().toString());

            }
        });

        back= (Button) findViewById(R.id.but_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void Change_pass(String userName, String userPassword) {
        progressDialog.setMessage("...");
        progressDialog.show();
       // reference = FirebaseDatabase.getInstance().getReference().child("students");
        Auth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    change();
                    progressDialog.dismiss();
                    Toast.makeText(Res_PassActivity.this, "change password ", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(Res_PassActivity.this, "Old password Not Correct", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    }

                }



        });


    }



    public void change() {
        final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-zA-Z])" + "(?=\\S+$)" + ".{7,}" + "$");
        String New_Pass = editText1.getText().toString();
        String Con_Pass = editText2.getText().toString();

        if (New_Pass.isEmpty()) {
            Toast.makeText(Res_PassActivity.this, "Enter new password.", Toast.LENGTH_LONG).show();
            editText1.setError("Enter new password");
            return;
        } else if (Con_Pass.isEmpty()) {
            Toast.makeText(Res_PassActivity.this, "Enter Confirm password.", Toast.LENGTH_LONG).show();
            editText2.setError("Enter Confirm password");
            return;
        } else if (!PASSWORD_PATTERN.matcher(editText1.getText().toString()).matches()) {
            Toast.makeText(Res_PassActivity.this, "Password should combines letters and numbers and length not less than 7 digits.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!New_Pass.equals(Con_Pass)) {
            Toast.makeText(Res_PassActivity.this, "New Pass and confirm not matched.", Toast.LENGTH_LONG).show();
            return;
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            progressDialog.setMessage("Changing Password please waite !!!! ");
            progressDialog.show();

            user.updatePassword(editText1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Your Password has Change !", Toast.LENGTH_LONG).show();
                        Auth.signOut();
                        finish();
                        startActivity(new Intent(Res_PassActivity.this, SelectUserActivity.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Password could not be Change !", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

    }


}
