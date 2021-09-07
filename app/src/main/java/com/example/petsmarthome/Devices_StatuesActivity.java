package com.example.petsmarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Devices_StatuesActivity extends AppCompatActivity {
    Button but_1, but_2,but_3,but_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices__statues);


        but_1 = (Button) findViewById(R.id.but_one);
        but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Devices_StatuesActivity.this, CollarDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        but_2 = (Button) findViewById(R.id.but_Two);
        but_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Devices_StatuesActivity.this, Feeding_SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_3 = (Button) findViewById(R.id.but_Three);
        but_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Devices_StatuesActivity.this, Light_SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but_4 = (Button) findViewById(R.id.but_Four);
        but_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Devices_StatuesActivity.this, Fan_SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}