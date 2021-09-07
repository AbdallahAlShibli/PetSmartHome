package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;

public class signup_PetActivity extends AppCompatActivity {
EditText e_petID,e_PetName,e_PetColor,e_PetType;
Button but_save,but_back;
Spinner spinner1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    boolean isSearching = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__pet);

        spinner1 = (Spinner) findViewById(R.id.E_CID);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Client_Details");
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        arrayAdapter.clear();
        arrayAdapter.add("--Select Item--");


        reference.addValueEventListener(new ValueEventListener() {
            //if (spinner1.getSelectedItem() == null) return;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, HashMap<String, String>> map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                    for (String Key : map.keySet()) {
                        HashMap<String, String> hashMap = map.get(Key);
                        Pet_Details value = new Pet_Details();
                        String desc = String.valueOf(hashMap.get("ID"));
                        arrayList.add(desc);
                        spinner1.setAdapter(arrayAdapter);
                    }


                } else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        but_save = (Button) findViewById(R.id.add_save);


        e_petID = (EditText) findViewById(R.id.E_PetID);
//check validation can't  start zero pet id--------------------------------------------
        e_petID.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String enteredString = s.toString();
                if (enteredString.startsWith("0")) {
                    Toast.makeText(signup_PetActivity.this, "should not starts with zero(0)", Toast.LENGTH_SHORT).show();
                    if (enteredString.length() > 0) {
                        e_petID.setText(enteredString.substring(1));
                    } else {
                        e_petID.setText("");
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
//check validation can't  start zero pet id--------------------------------------------

//check validation pet id unique--------------------------------------------
        e_petID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
                reference.addValueEventListener(new ValueEventListener() {

                    final String p = e_petID.getText().toString().trim();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) dataSnapshot.getValue();

                            if (map == null) {
                           //     return;
                            }

                        //    if (!isSearching) return;


                            boolean isPhoneDuplicated = false;

                            for (String Key : map.keySet()) {
                                HashMap<String, Integer> hashMap = map.get(Key);
                                String A = String.valueOf(hashMap.get("PET_ID"));

                                if ((A.equals(p))) {
                                    isPhoneDuplicated = true;
                                    break;
                                }
                            }

                            if (isPhoneDuplicated) {
                                isPhoneDuplicated = false;
                                e_petID.setError("ID Pet already exists!");
                             //   Toast.makeText(signup_PetActivity.this, "ID Pet already exists!", Toast.LENGTH_SHORT).show();
                            }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //  Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }


        });
//check validation pet id unique--------------------------------------------


        e_PetName = (EditText) findViewById(R.id.E_PETNAME);
        e_PetColor = (EditText) findViewById(R.id.E_PETCOLOR);
        e_PetType = (EditText) findViewById(R.id.E_PETTYPE);


        but_back = (Button) findViewById(R.id.But_back);

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_PetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String c_id = spinner1.getSelectedItem().toString();
                final String pet_id = e_petID.getText().toString().trim();
                final String pet_name = e_PetName.getText().toString().trim();
                final String pet_color = e_PetColor.getText().toString().trim();
                final String pet_type = e_PetType.getText().toString().trim();


                if (c_id == "--Select Item--") {
                    Toast.makeText(getBaseContext(), "Select client id!", Toast.LENGTH_LONG).show();

                    return;
                } else if (TextUtils.isEmpty(pet_id)) {
                    Toast.makeText(getBaseContext(), "Enter pet id!", Toast.LENGTH_LONG).show();
                    e_petID.setError("Enter pet id");
                    return;
                } else if (TextUtils.isEmpty(pet_name)) {
                    Toast.makeText(getBaseContext(), "Enter name!", Toast.LENGTH_LONG).show();
                    e_PetName.setError("Enter name");
                    return;
                } else if (TextUtils.isEmpty(pet_color)) {
                    Toast.makeText(getBaseContext(), "Enter pet color!", Toast.LENGTH_LONG).show();
                    e_PetColor.setError("Enter pet color");
                    return;
                } else if (TextUtils.isEmpty(pet_type)) {
                    Toast.makeText(getBaseContext(), "Enter pet type!", Toast.LENGTH_LONG).show();
                    e_PetType.setError("Enter pet type");
                    return;
                }
            reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
              //  reference = FirebaseDatabase.getInstance().getReference("Feeding_Food");
                Pet_Details pet_details = new Pet_Details(c_id, pet_id, pet_name, pet_color, pet_type);
                reference.push().setValue(pet_details);
                Toast.makeText(signup_PetActivity.this, "Add successful", Toast.LENGTH_LONG).show();
              e_PetName.setText("");
              e_PetColor.setText("");
              e_PetType.setText("");

            }
        });

    }
}
