package com.example.petsmarthome;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
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

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info, textView, textView1,textView2,TextView3;
    private Button Login,back, forget;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    DatabaseReference reference;
    TextView txt1;
    public static final String EXTRA_MESSAGE = "com.example.smartattendance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forget = (Button) findViewById(R.id.ForgotPassword);
        Intent intent = getIntent();
        String a = intent.getStringExtra(SelectUserActivity.EXTRA_MESSAGE);
        txt1 = (TextView) findViewById(R.id.txtTitle);
        txt1.setText(a);

        if (a.equals("Admin Login")) {
            forget.setVisibility(View.GONE);
        }

        textView = (TextView) findViewById(R.id.No);
        textView.setVisibility(View.GONE);

        textView1 = (TextView) findViewById(R.id.U_Name);
        textView1.setVisibility(View.GONE);

        textView2 = (TextView) findViewById(R.id.Tec_Name);
        textView2.setVisibility(View.GONE);

        TextView3 = (TextView) findViewById(R.id.student_name);
        TextView3.setVisibility(View.GONE);


        Name = (EditText) findViewById(R.id.etName);
        Name.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase();
                    }
                }
        });
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        Info.setText("No of attempts remaining: 5");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        back=(Button)findViewById(R.id.btn_back);
        back.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SelectUserActivity.class));
            }
        });

  final FirebaseUser user = firebaseAuth.getCurrentUser();

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String user = Name.getText().toString().trim();
                final String pass = Password.getText().toString().trim();


                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(getBaseContext(), "Enter your Email!", Toast.LENGTH_LONG).show();

                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getBaseContext(), "Enter your password!", Toast.LENGTH_LONG).show();
                    return;
                }

                String a = (String) txt1.getText().toString();
                if (a.equals("Admin Login")) {

                        validate_Admin(Name.getText().toString(), Password.getText().toString());
                } else if (a.equals("Client Login")) {
                    validate_Client(Name.getText().toString(), Password.getText().toString());
                }


            }
        });


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, forgetPassword.class));
                finish();
            }
        });

    }


    private void validate_Admin(String userName, String userPassword) {

        progressDialog.setMessage("...");
        progressDialog.show();
        final String user = Name.getText().toString().trim();
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        reference = FirebaseDatabase.getInstance().getReference().child("Admin");

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Toast.makeText(LoginActivity.this, "Login Successful !!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(LoginActivity.this, "Login Failed !!!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        counter--;
                        Info.setText("No of attempts remaining: " + counter);
                        progressDialog.dismiss();
                        if (counter == 0) {
                            Login.setEnabled(false);
                            Name.setEnabled(false);
                            Password.setEnabled(false);
                            back.setVisibility(View.VISIBLE);
                            startActivity(new Intent(LoginActivity.this, SelectUserActivity.class));
                            finish();
                        }

                    }

                }


            });


    }

    private void validate_Client(String userName, String userPassword) {

        progressDialog.setMessage("...");
        progressDialog.show();

        final String user = Name.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reference = FirebaseDatabase.getInstance().getReference().child("Client_Details");

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    HashMap<String, HashMap<String, String>> mapHashMap = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                                    for (String key : mapHashMap.keySet()) {
                                        HashMap<String, String> map = mapHashMap.get(key);
                                        String Email = map.get("EMAIL");
                                        String ID = map.get("ID");
                                        String phone = map.get("PHONE");
                                        String name = map.get("NAME");

                                        if (Email.equals(user)) {
                                            textView.setText(Email);
                                            textView1.setText(ID);
                                            TextView3.setText(name);
                                            progressDialog.dismiss();

                                            SharedPreferences sharedPreferences = getSharedPreferences("GetData", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("Number", textView1.getText().toString().trim());
                                            editor.putString("Email", textView.getText().toString().trim());
                                            editor.putString("ClientName", TextView3.getText().toString().trim());
                                            editor.commit();

                                            startActivity(new Intent(LoginActivity.this, ClientHomeActivity.class));
                                            finish();
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    progressDialog.dismiss();
                                    counter--;
                                    Info.setText("No of attempts remaining: " + counter);
                                    Toast.makeText(LoginActivity.this, "User Name Or Password Not Correct !!", Toast.LENGTH_LONG).show();
                                    if (counter == 0) {
                                        Login.setEnabled(false);
                                        Name.setEnabled(false);
                                        Password.setEnabled(false);
                                        back.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            counter--;
                            Info.setText("No of attempts remaining: " + counter);
                            Toast.makeText(LoginActivity.this, "User Name Or Password Not Correct !!", Toast.LENGTH_LONG).show();
                            if (counter == 0) {
                                Login.setEnabled(false);
                                Name.setEnabled(false);
                                Password.setEnabled(false);
                                back.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });


    }

}
