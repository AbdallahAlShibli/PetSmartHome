package com.example.petsmarthome;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectUserActivity extends AppCompatActivity {
    Button but_1, but_2;

    public static final String EXTRA_MESSAGE = "com.example.petsmarthome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        SharedPreferences sharedPreferences = getSharedPreferences("Data1", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        but_1 = (Button) findViewById(R.id.but_one);
        but_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectUserActivity.this, LoginActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Admin Login").getData();
                startActivity(intent);


            }
        });

        but_2 = (Button) findViewById(R.id.but_two);
        but_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserActivity.this, LoginActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Client Login").getData();
                startActivity(intent);

            }
        });


    }
}
