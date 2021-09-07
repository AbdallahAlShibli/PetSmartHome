package com.example.petsmarthome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AdminFeedbackActivity extends AppCompatActivity {
   ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference reference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();


        listView = (ListView) findViewById(R.id.list);

        reference = FirebaseDatabase.getInstance().getReference("Feedback_User");
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo,arrayList);
        LayoutInflater layoutinflater = getLayoutInflater();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Feedback value=ds.getValue(Feedback.class);
                    String row = String.valueOf(value.getSuggestion());
                    arrayList.add(row);

                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        final RatingBar simpleRatingBar1 = (RatingBar) findViewById(R.id.simpleRatingBar1);
        reference = FirebaseDatabase.getInstance().getReference("Feedback_User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float total = 0;
                float Result=0;
                for( DataSnapshot ds :dataSnapshot.getChildren()) {
                    Feedback feedback = ds.getValue(Feedback.class);
                    float rating = Float.valueOf(feedback.getQuestion_1());
                    total = total + rating;
                    Result = total /dataSnapshot.getChildrenCount();
                    simpleRatingBar1.setRating(Result);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        final RatingBar simpleRatingBar2 = (RatingBar) findViewById(R.id.simpleRatingBar2);
        reference = FirebaseDatabase.getInstance().getReference("Feedback_User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float total = 0;
                float Result=0;
                for( DataSnapshot ds :dataSnapshot.getChildren()) {
                    Feedback feedback = ds.getValue(Feedback.class);
                    float rating = Float.valueOf(feedback.getQuestion_2());
                    total = total + rating;
                    Result = total /dataSnapshot.getChildrenCount();
                    simpleRatingBar2.setRating(Result);

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        Button back=(Button)findViewById(R.id.but_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminFeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button pdf =(Button)findViewById(R.id.but_pdf);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }
        });

    }

}
