package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchPetActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    EditText etSearch;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> pet_id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> color = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> client_id = new ArrayList<>();
    Button but_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);

        firebaseDatabase= FirebaseDatabase.getInstance();

        etSearch=(EditText)findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                arrayAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }


        });

        databaseReference=firebaseDatabase.getReference("Pet_Details");
        listView = (ListView) findViewById(R.id.list);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo,arrayList);
        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.lis_pet,listView,false);

        listView.addHeaderView(header);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    Pet_Details value=ds.getValue(Pet_Details.class);
                    String string = String.valueOf("      "+value.PET_ID +"           "+value.PET_NAME+"        "+value.PET_COLOR);
                    arrayList.add(string);
                   pet_id.add(value.PET_ID);
                   name.add(value.PET_NAME);
                   color.add(value.PET_COLOR);
                   type.add(value.PET_TYPE);
                   client_id.add(value.C_ID);
                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(SearchPetActivity.this, ProfilPetActivity.class);
                i.putExtra("ID", pet_id.get(position-1));
                i.putExtra("NAME", name.get(position-1));
                i.putExtra("COLOR", color.get(position-1));
                i.putExtra("TYPE", type.get(position-1));
                i.putExtra("CLIENT_ID", client_id.get(position-1));
                startActivity(i);
                finish();
            }
        });




        but_back=(Button)findViewById(R.id.back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
