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
import static com.example.petsmarthome.SelectUserActivity.EXTRA_MESSAGE;
public class SearchClientActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    EditText etSearch;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> age = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> gender = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();

Button but_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

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

        databaseReference=firebaseDatabase.getReference("Client_Details");
        listView = (ListView) findViewById(R.id.list);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo,arrayList);
        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.lis_client,listView,false);

        listView.addHeaderView(header);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Client_Details client_details=ds.getValue(Client_Details.class);
                    String string = String.valueOf("      "+client_details.ID +"           "+client_details.NAME+"        "+client_details.GENDER);
                    arrayList.add(string);

                      id.add(client_details.ID);
                      name.add(client_details.NAME);
                      address.add(client_details.ADDRESS);
                      age.add(client_details.AGE);
                      email.add(client_details.EMAIL);
                      gender.add(client_details.GENDER);
                      phone.add(client_details.PHONE);


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
                Intent i = new Intent(SearchClientActivity.this, ProfilClientActivity.class);
                i.putExtra("ID", id.get(position-1));
                i.putExtra("NAME", name.get(position-1));
                i.putExtra("ADDRESS", address.get(position-1));
                i.putExtra("AGE", age.get(position-1));
                i.putExtra("EMAIL", email.get(position-1));
                i.putExtra("GENDER", gender.get(position-1));
                i.putExtra("PHONE", phone.get(position-1));
                startActivity(i);
                finish();
            }
        });

        but_back=(Button)findViewById(R.id.back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchClientActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
