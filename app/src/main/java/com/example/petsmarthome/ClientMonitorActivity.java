package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientMonitorActivity extends AppCompatActivity {
    String ClientID;
    ListView listView_1;
    TextView TName ,num,Meg;
    Button but_bac;
    public static final String EXTRA_MESSAGE = "";
    ArrayList<String> array = new ArrayList<>();
    ArrayList<String> pet_id = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_monitor);


        SharedPreferences sharedPreferences = getSharedPreferences("GetData", Context.MODE_PRIVATE);
        final String n = sharedPreferences.getString("Number", EXTRA_MESSAGE);
        final String Name = sharedPreferences.getString("ClientName", EXTRA_MESSAGE);


        Meg=(TextView)findViewById(R.id.message);
        TName = (TextView) findViewById(R.id.T_name);
        TName.setText(Name);
        this.ClientID =n;
        listView_1=(ListView) findViewById(R.id.list_data);
        but_bac=(Button)findViewById(R.id.but_back);
        but_bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientMonitorActivity.this, ClientHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Query reference = FirebaseDatabase.getInstance().getReference("Pet_Details").orderByChild("C_ID").equalTo(ClientID);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, array);
        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.lis_pet_id,listView_1,false);

        listView_1.addHeaderView(header);
        reference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Pet_Details pet_details=ds.getValue(Pet_Details.class);
                    String string = String.valueOf(pet_details.PET_ID);
                    array.add(string);
                    pet_id.add(pet_details.PET_ID);
                }
                listView_1.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error !!!!", Toast.LENGTH_LONG).show();
            }
        });


        listView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Intent i = new Intent(ClientMonitorActivity.this, CollarDetailsActivity.class);
                Intent i = new Intent(ClientMonitorActivity.this, Devices_StatuesActivity.class);
              //  i.putExtra("ID", pet_id.get(position-1));
                SharedPreferences sharedPreferences = getSharedPreferences("Pet_ID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ID", pet_id.get(position-1));
                editor.commit();

                startActivity(i);
                finish();


            }
        });
    }

}
