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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class CollarDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.petsmarthome";
    TextView txt,txt1,txt2,txt3,txt4;
     Button B_back;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
     ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> array1 = new ArrayList<>();
    ArrayList<Double> array2 = new ArrayList<>();
    ArrayList<String> array3 = new ArrayList<>();
    ArrayList<Double> array4 = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase= FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_collar_details);

        SharedPreferences sharedPreferences = getSharedPreferences("Pet_ID", Context.MODE_PRIVATE);
        final String pet_id = sharedPreferences.getString("ID", EXTRA_MESSAGE);

       // Intent intent = getIntent();
      //  int pet_id = Integer.parseInt(intent.getStringExtra("ID"));
        txt=(TextView) findViewById(R.id.txtTitle);
        txt.setText(""+pet_id);


        B_back=(Button)findViewById(R.id.But_back);



        txt1=(TextView) findViewById(R.id.Col_Material);
        txt2=(TextView) findViewById(R.id.Col_NeckWidth);
        txt3=(TextView) findViewById(R.id.Col_Color);
        txt4=(TextView) findViewById(R.id.Col_Price);



        Query classrooms_reference = FirebaseDatabase.getInstance().getReference("Collar_Details").orderByChild("Pet_ID").equalTo(pet_id);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, arrayList);

        classrooms_reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.clear();

                if(dataSnapshot!=null && dataSnapshot.exists())
                {
                    for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        CollarDetails value = snapshot.getValue(CollarDetails.class);
                        txt1.setText(value.Collar_Material);
                        txt2.setText(""+value.Neck_Width);
                        txt3.setText(value.Collar_Color);
                        txt4.setText(""+ value.Price);
                    }
                }
                else {


                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });




        B_back.setOnClickListener(new View.OnClickListener() {
             @Override
        public void onClick(View v) {
         Intent i = new Intent(CollarDetailsActivity.this, ClientMonitorActivity.class);
         startActivity(i);
          }
      });
    }
}
