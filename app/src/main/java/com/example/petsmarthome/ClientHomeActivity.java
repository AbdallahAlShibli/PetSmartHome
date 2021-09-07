package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class ClientHomeActivity extends AppCompatActivity {
    Button but_1, but_2,but_3,but_4,but_5,but_feed;
    public static final String EXTRA_MESSAGE = "com.example.petsmarthome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);


        but_1 = (Button) findViewById(R.id.but_one);
        but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomeActivity.this, ClientMonitorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        but_2 = (Button) findViewById(R.id.but_two);
        but_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomeActivity.this, Res_PassActivity.class);
                startActivity(intent);

            }
        });
        but_3 = (Button) findViewById(R.id.but_three);
        but_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomeActivity.this, SelectUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_4 = (Button) findViewById(R.id.but_Update);
        but_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomeActivity.this, UpdateClientActivity.class);
                startActivity(intent);
                finish();
            }
        });




        but_feed= (Button) findViewById(R.id.but_feedback);
        but_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientHomeActivity.this, FeedBackActivity.class);
                startActivity(intent);
                finish();
            }
        });

        }

}
