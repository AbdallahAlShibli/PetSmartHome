package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class signup_ClientActivity extends AppCompatActivity {
Button but,but_back;
    EditText  txt_cid,txt_cname,txt_cage,txt_caddress,txt_cemail,txt_cphone,txt_cpassword;
    RadioGroup cgender;
    private RadioButton radioSexButton;
TextView En_Password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    boolean isSearching = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__client);

        txt_cid  = (EditText) findViewById(R.id.Edit_C_ID);
         txt_cid.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String enteredString = s.toString();
                if (enteredString.startsWith("0")) {
                    Toast.makeText(signup_ClientActivity.this, "should not starts with zero(0)", Toast.LENGTH_SHORT).show();
                    if (enteredString.length() > 0) {
                        txt_cid.setText(enteredString.substring(1));
                    } else {
                        txt_cid.setText("");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txt_cname  = (EditText) findViewById(R.id.Edit_Full_Name);
        cgender  = (RadioGroup) findViewById(R.id.radioSex);
        txt_cage  = (EditText) findViewById(R.id.Edit_C_AGE);
        txt_caddress  = (EditText) findViewById(R.id.Edit_C_ADDRESS);
        txt_cemail  = (EditText) findViewById(R.id.Edit_C_Email);
        txt_cphone  = (EditText) findViewById(R.id.Edit_C_PHONE);
        txt_cpassword  = (EditText) findViewById(R.id.Edit_Password);
        En_Password = (TextView) findViewById(R.id.en_Pass);

        //validation for ID---------------------------
        txt_cid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {
                    final String p = txt_cid.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();
                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;

                        boolean isPhoneDuplicated = false;
                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("ID"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }
                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "ID already exists!", Toast.LENGTH_LONG).show();
                            isPhoneDuplicated = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {

                    final String p = txt_cid.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();

                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;


                        boolean isPhoneDuplicated = false;

                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("ID"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }

                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "ID already exists!", Toast.LENGTH_LONG).show();

                            isPhoneDuplicated = false;
                            txt_cid.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }


        });
        //------------------------------------------------------
        //validation for ID---------------------------
        txt_cemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {
                    final String p = txt_cemail.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();
                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;

                        boolean isPhoneDuplicated = false;
                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("EMAIL"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }
                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "Email already exists!", Toast.LENGTH_LONG).show();
                            txt_cemail.setText("");
                            isPhoneDuplicated = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {

                    final String p = txt_cemail.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();

                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;


                        boolean isPhoneDuplicated = false;

                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("EMAIL"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }

                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "Email already exists!", Toast.LENGTH_LONG).show();
                            txt_cemail.setText(" ");
                            isPhoneDuplicated = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }


        });
        //------------------------------------------------------

        //validation for Phone---------------------------
        txt_cphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {
                    final String p = txt_cphone.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();
                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;

                        boolean isPhoneDuplicated = false;
                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("PHONE"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }
                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "Phone number already exists!", Toast.LENGTH_LONG).show();
                            txt_cphone.setText(" ");
                            isPhoneDuplicated = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                reference.addValueEventListener(new ValueEventListener() {

                    final String p = txt_cphone.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();

                        if (map == null) {
                            return;
                        }

                        if (!isSearching) return;


                        boolean isPhoneDuplicated = false;

                        for (String Key : map.keySet()) {
                            HashMap<String, Integer> hashMap = map.get(Key);
                            String A = String.valueOf(hashMap.get("PHONE"));

                            if ((A.equals(p))) {
                                isPhoneDuplicated = true;
                                break;
                            }
                        }

                        if (isPhoneDuplicated) {
                            Toast.makeText(signup_ClientActivity.this, "Phone number already exists!", Toast.LENGTH_LONG).show();
                            txt_cphone.setText(" ");
                            isPhoneDuplicated = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }


        });
        //---------------------------------------------------
        but_back=(Button)findViewById(R.id.Button_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_ClientActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




        but=(Button)findViewById(R.id.AddClient);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth = FirebaseAuth.getInstance();

                // get selected radio button from radioGroup
                int selectedId = cgender.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);

                encryption(En_Password.toString());
                final String id = txt_cid.getText().toString();
                final String name = txt_cname.getText().toString();
                final String selected_Id = radioSexButton.getText().toString();
                final String age = txt_cage.getText().toString();
                final String address = txt_caddress.getText().toString();
                final String email = txt_cemail.getText().toString();
                final String phone = txt_cphone.getText().toString();
                final String pass = txt_cpassword.getText().toString();


                final String En = En_Password.getText().toString().trim();

                final int a = Integer.parseInt(txt_cage.getText().toString());

                final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-zA-Z])" + "(?=\\S+$)" + ".{5,}" + "$");
                // client id
                if (id.equals("")) {
                    txt_cid.setError("Enter Client ID");

                    //client name
                } else if (name.equals("")){
                    txt_cname.setError("Enter Client Name");
                    return;
                //client age
                       }else if(age.equals("")){
                    txt_cage.setError("Enter Age");
                    return;

                   }else if (a < 18  || a > 70){
                    txt_cage.setError("above 18 and equal Or less than 70 years");
                    return;
                    //client address
                   }else if (address.equals("")){
                    txt_caddress.setError("Enter address");
                    return;
                    //client email:
                 }else if (email.equals("")){
                    txt_cemail.setError("Enter client Email");
                    return;
                  }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    txt_cemail.setError("Please Enter Valid Email");
                    return;
                    //client phone:
                     }else if (phone.equals("")){
                    txt_cphone.setError("Enter client phone number");
                    return;
                        }else if (!(phone.startsWith("9") || phone.startsWith("7"))){
                    txt_cphone.setError("Phone No should start with 9 or 7");
                    return;
                   }else if (phone.length() != 8){
                    txt_cphone.setError("Phone No should be 8 characters");
                    return;
                //client Pass
                 }else if (pass.length() < 8) {
                    txt_cpassword.setError("ID should not less 8 characters");
                    return;
                }else if (!PASSWORD_PATTERN.matcher(txt_cpassword.getText().toString()).matches()) {
                    txt_cpassword.setError("Password should combine letter,number and symbols");
                    return;
                }else {
                    reference = FirebaseDatabase.getInstance().getReference("Client_Details");
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // String key = reference.push().getKey();
                                Client_Details client = new Client_Details(id, name, selected_Id, age, address, email, phone, En);
                                reference.child(phone).setValue(client);

                                Toast.makeText(signup_ClientActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(signup_ClientActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(signup_ClientActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }

            }
        });

    }
    public void encryption(String pas) {

        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(pas.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }
            En_Password.setText(MD5Hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
