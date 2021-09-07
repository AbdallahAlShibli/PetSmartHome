package com.example.petsmarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfilPetActivity extends AppCompatActivity {
    EditText e_clintID,e_petID,e_PetName,e_PetColor,e_PetType;
    Button but_back,but_delete,but_Update;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pet);

        Intent intent = getIntent();
        String pet_id = intent.getStringExtra("ID");
        String pet_name = intent.getStringExtra("NAME");
        String pet_color = intent.getStringExtra("COLOR");
        String pet_type = intent.getStringExtra("TYPE");
        String c_id = intent.getStringExtra("CLIENT_ID");


        e_clintID= (EditText) findViewById(R.id.E_CID);
        e_clintID.setText(c_id);
        e_clintID.setEnabled(false);
        e_petID= (EditText) findViewById(R.id.E_PetID);
        e_petID.setText(pet_id);
        e_petID.setEnabled(false);
        e_PetName= (EditText) findViewById(R.id.E_PETNAME);
        e_PetName.setText(pet_name);

        e_PetColor= (EditText) findViewById(R.id.E_PETCOLOR);
        e_PetColor.setText(pet_color);
        e_PetColor.setEnabled(false);
        e_PetType= (EditText) findViewById(R.id.E_PETTYPE);
        e_PetType.setText(pet_type);
        e_PetType.setEnabled(false);

        but_Update=(Button)findViewById(R.id.Update);
        but_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id= e_petID.getText().toString().trim();
                String N= e_PetName.getText().toString().trim();
                edit_Pet(id,N);

            }
        });

        but_delete=(Button)findViewById(R.id.delete_pet);
        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id= e_petID.getText().toString().trim();
                delete(id);
            }
        });


        but_back=(Button)findViewById(R.id.But_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilPetActivity.this, SearchPetActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void edit_Pet(final String  strID,final String Name) {

        reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
        Query editQuery = reference.orderByChild("PET_ID").equalTo(strID);
        editQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot edtData : dataSnapshot.getChildren()) {
                    edtData.getRef().child("PET_NAME").setValue(Name);
                    Toast.makeText(ProfilPetActivity.this, "Update Name Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private  void delete(String strID){



        reference = FirebaseDatabase.getInstance().getReference("Pet_Details");
        Query applesQuery = reference.orderByChild("PET_ID").equalTo(strID);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Toast.makeText(ProfilPetActivity.this,"Delete Successfully Pet",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProfilPetActivity.this,SearchPetActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
