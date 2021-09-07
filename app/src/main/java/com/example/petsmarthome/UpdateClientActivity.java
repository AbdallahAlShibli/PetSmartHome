package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateClientActivity extends AppCompatActivity {
    Button but,but_back;
    EditText  txt_cage,txt_caddress,txt_cphone;
    private RadioButton radioSexButton;
    TextView txt_cid,txt_cname,cgender,txt_cemail;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    boolean isSearching = true;
Button but1,but2;
    public static final String EXTRA_MESSAGE = "";
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> sex = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> age = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> gender = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        SharedPreferences sharedPreferences = getSharedPreferences("GetData", Context.MODE_PRIVATE);
        final String ClientNo = sharedPreferences.getString("Number", EXTRA_MESSAGE);
        txt_cid  = (TextView) findViewById(R.id.Edit_C_ID);
        txt_cname  = (TextView) findViewById(R.id.Edit_Full_Name);
        cgender  = (TextView) findViewById(R.id.sex);
        txt_cage  = (EditText) findViewById(R.id.Edit_C_AGE);
        txt_caddress  = (EditText) findViewById(R.id.Edit_C_ADDRESS);
        txt_cemail  = (TextView) findViewById(R.id.Edit_C_Email);
        txt_cphone  = (EditText) findViewById(R.id.Edit_C_PHONE);
        but1=(Button) findViewById(R.id.updateClient);
        but2=(Button) findViewById(R.id.Button_back);


        Query reference = FirebaseDatabase.getInstance().getReference("Client_Details").orderByChild("ID").equalTo(ClientNo);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, arrayList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayAdapter.clear();

                HashMap<String, HashMap<String, String>> mapHashMap = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                for (String key : mapHashMap.keySet()) {
                    HashMap<String, String> map = mapHashMap.get(key);
                    String ID = map.get("ID");
                    String name = map.get("NAME");
                    String Email = map.get("EMAIL");
                    String Gender = map.get("GENDER");

                    String ade = map.get("AGE");
                    String address = map.get("ADDRESS");
                    String phone = map.get("PHONE");

                    txt_cid.setText(ID);
                    txt_cname.setText(name);
                    txt_cemail.setText(Email);
                    cgender.setText(Gender);

                    txt_cage.setText(ade);
                    txt_caddress.setText(address);
                    txt_cphone.setText(phone);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error !!!!", Toast.LENGTH_LONG).show();
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= txt_cid.getText().toString().trim();
            String a=txt_cage.getText().toString().trim();
                String b=txt_caddress.getText().toString().trim();
               String c=txt_cphone.getText().toString().trim();

                edit_Client(id,a,b,c);
            }
        });



        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateClientActivity.this, ClientHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void edit_Client(final String  strID,final String age,final String address,final String phone) {

        reference = FirebaseDatabase.getInstance().getReference("Client_Details");
        Query editQuery = reference.orderByChild("ID").equalTo(strID);
        editQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot edtData : dataSnapshot.getChildren()) {
                    edtData.getRef().child("AGE").setValue(age);
                    edtData.getRef().child("ADDRESS").setValue(address);
                    edtData.getRef().child("PHONE").setValue(phone);
                    Toast.makeText(UpdateClientActivity.this, "Update Client Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
