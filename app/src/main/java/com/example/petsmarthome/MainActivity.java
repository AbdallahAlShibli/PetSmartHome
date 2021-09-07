package com.example.petsmarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button but_1, but_2,but_3,but_4,but_5,but_6,but_7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_1 = (Button) findViewById(R.id.but_one);
        but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup_ClientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        but_2 = (Button) findViewById(R.id.but_two);
        but_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup_PetActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_3 = (Button) findViewById(R.id.but_three);
        but_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_4 = (Button) findViewById(R.id.but_four);
        but_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterCollarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_5 = (Button) findViewById(R.id.but_Client);
        but_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_6 = (Button) findViewById(R.id.but_pet);
        but_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPetActivity.class);
                startActivity(intent);
                finish();
            }
        });



        but_7 = (Button) findViewById(R.id.but_rep);
        but_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportsControlActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
