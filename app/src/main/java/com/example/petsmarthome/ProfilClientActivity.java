package com.example.petsmarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfilClientActivity extends AppCompatActivity {
    EditText  txt_cid,txt_cname,txt_cage,txt_caddress,txt_cemail,txt_cphone;
    RadioGroup cgender;
    Button but_back,but_Delete;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);

        Intent intent = getIntent();
            String Client_ID = intent.getStringExtra("ID");
            String Client_Name = intent.getStringExtra("NAME");
            String Client_Email = intent.getStringExtra("EMAIL");
            String Client_phone = intent.getStringExtra("PHONE");
            String Client_Age = intent.getStringExtra("AGE");
            String Client_Address = intent.getStringExtra("ADDRESS");
           // String Client_Gender = intent.getStringExtra("GENDER");

        txt_cid  = (EditText) findViewById(R.id.Edit_C_ID);
        txt_cid.setText(Client_ID);
        txt_cid.setEnabled(false);

        txt_cname  = (EditText) findViewById(R.id.Edit_Full_Name);
        txt_cname.setText(Client_Name);
        txt_cname.setEnabled(false);

        txt_cage  = (EditText) findViewById(R.id.Edit_C_AGE);
        txt_cage.setText(Client_Age);
        txt_cage.setEnabled(false);

        txt_caddress  = (EditText) findViewById(R.id.Edit_C_ADDRESS);
        txt_caddress.setText(Client_Address);
        txt_caddress.setEnabled(false);

        txt_cemail  = (EditText) findViewById(R.id.Edit_C_Email);
        txt_cemail.setText(Client_Email);
        txt_cemail.setEnabled(false);

        txt_cphone  = (EditText) findViewById(R.id.Edit_C_PHONE);
        txt_cphone.setText(Client_phone);
        txt_cphone.setEnabled(false);


        but_Delete=(Button)findViewById(R.id.deleteClient);
        but_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= txt_cid.getText().toString().trim();
                delete(id);
            }
        });

        but_back=(Button)findViewById(R.id.Button_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilClientActivity.this, SearchClientActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private  void delete(String ID){

        reference = FirebaseDatabase.getInstance().getReference("Client_Details");
        Query applesQuery = reference.orderByChild("ID").equalTo(ID);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    appleSnapshot.getRef().removeValue();
                    Toast.makeText(ProfilClientActivity.this,"Delete Successfully Client",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProfilClientActivity.this,SearchClientActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
        Query Query = reference.orderByChild("C_ID").equalTo(ID);

        Query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    appleSnapshot.getRef().removeValue();
                    Toast.makeText(ProfilClientActivity.this,"Delete Successfully Pet",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProfilClientActivity.this,SearchClientActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


    }
}
