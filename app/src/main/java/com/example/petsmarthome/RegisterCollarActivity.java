package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterCollarActivity extends AppCompatActivity {
EditText editText_1,editText_2,editText_3,editText_4,editText_5,editText_6;
    TextView t_n;
    Button but_Register,but_back;
     FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ArrayList<String> arrayList_1 = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter_1;
    Spinner spinner1,Spinner2,Spinner3;
    boolean isSearching = true;
    String[] items_Color = new String[]{ "--Select Item--","Black", "Red", "Green", "White"};
    String[] items = new String[]{"--Select Item--", "Neoprene", "Chain", "Nylon", "Faux beather"};
    int[] items_value = new int[]{0,4, 8, 3, 12};

    double a,b,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_collar);
         t_n=(TextView)findViewById(R.id.No);
        t_n.setVisibility(View.GONE);
        spinner1= (Spinner) findViewById(R.id.Col_PetID);
        editText_3= (EditText) findViewById(R.id.Col_NeckWidth);
        editText_3.setEnabled(false);
        editText_6= (EditText) findViewById(R.id.Col_Price);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
        arrayAdapter_1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayList_1);
        arrayAdapter_1.clear();
        arrayAdapter_1.add("--Select Item--");


        reference.addValueEventListener(new ValueEventListener() {
   //if (spinner1.getSelectedItem() == null) return;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, HashMap<String, String>> map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                    for (String Key : map.keySet()) {
                        HashMap<String, String> hashMap = map.get(Key);
                        Pet_Details value = new Pet_Details();
                        String desc = String.valueOf(hashMap.get("PET_ID"));
                        arrayList_1.add(desc);
                        spinner1.setAdapter(arrayAdapter_1);
                    }


                } else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner2= (Spinner) findViewById(R.id.Col_Material);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        Spinner2.setAdapter(adapter);

        Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String Sp = Spinner2.getSelectedItem().toString();

                if(Sp=="Neoprene"){
                    t_n.setText("4.5");
                    editText_3.setText("6.0");
                    editText_3.setEnabled(true);
                }else if(Sp=="Chain"){
                    t_n.setText("8.5");
                    editText_3.setText("6.0");
                    editText_3.setEnabled(true);
                }else if(Sp=="Nylon"){
                    t_n.setText("3.5");
                    editText_3.setText("6.0");
                    editText_3.setEnabled(true);
                }else if(Sp== "Faux beather"){
                    t_n.setText("12.0");
                    editText_3.setText("6.0");
                    editText_3.setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        editText_3.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                editText_6.setEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if (s.length() != 0);

                    Double X = Double.parseDouble(t_n.getText().toString());
                    Double Y = Double.parseDouble(editText_3.getText().toString());
                    Double Z = 0.0;
                    Z = X * Y;
                    editText_6.setText("" + Z);
            }
        });
        Spinner3= (Spinner) findViewById(R.id.Col_Color);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_Color);
        Spinner3.setAdapter(adapter1);



                editText_6.setEnabled(false);


        but_Register=(Button)findViewById(R.id.register);
        but_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth = FirebaseAuth.getInstance();


                final int pet_id = Integer.parseInt(spinner1.getSelectedItem().toString());

                final double N_Width = Double.parseDouble(editText_3.getText().toString());
                final String Col_M = Spinner2.getSelectedItem().toString();
                final String Col_C = Spinner3.getSelectedItem().toString();
                final double price = Double.parseDouble(editText_6.getText().toString());

                // client id
                if (pet_id == 0) {
                    editText_1.setError("Enter Pet ID");

                    //client age

                }else if (N_Width==0.0){
                    editText_3.setError("Enter Neck-Width");
                    return;
                }else if (N_Width < 6 || N_Width > 18){
                    editText_3.setError("Enter between 6 -18 inches Neck-Width");
                    return;
                    //client email:
                }else if (Col_M.equals("")){
                    editText_4.setError("Enter Collar-Material");
                    return;
                }else if (Col_C.equals("")){
                    editText_5.setError("Enter Collar-Color");
                    return;
                    //client phone:
                }else if (price==0.0){
                    editText_6.setError("Enter Price");
                    return;
                }else {
                    reference = FirebaseDatabase.getInstance().getReference("Collar_Details");
                    CollarDetails collarDetails = new CollarDetails(pet_id, N_Width, Col_M, Col_C, price);
                    reference.push().setValue(collarDetails);
                    Toast.makeText(RegisterCollarActivity.this, "Registration successful", Toast.LENGTH_LONG).show();

                }

            }
        });












        but_back=(Button)findViewById(R.id.But_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCollarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });






    }
    public void cul(){

        Double S = 0.0;
        if (Spinner2.getSelectedItem() == "Neoprene") {
            S=4.5;
        } else if (Spinner2.getSelectedItem() == "Chain") {
            S=8.5;
        }else if (Spinner2.getSelectedItem() == "Nylon") {
            S=3.5;
        }else if (Spinner2.getSelectedItem() == "Faux beather") {
            S=12.0;
        }


        if (editText_3.getText().toString()== ""){
            editText_3.setText("");
        }else {
             double Y= Double.parseDouble(editText_3.getText().toString());
            b=Y;
            c = S;
            a = c* b;
            editText_6.setText(""+a);
        }
    }
}
