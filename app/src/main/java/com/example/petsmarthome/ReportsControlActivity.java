package com.example.petsmarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportsControlActivity extends AppCompatActivity {
Button b_1,b_2,b_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_control);

        b_1=(Button)findViewById(R.id.but_one);
        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsControlActivity.this, ReportFeedbackActivity.class);
                startActivity(intent);
                finish();
            }
        });
        b_2=(Button)findViewById(R.id.but_two);
        b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsControlActivity.this, ReportClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
        b_3=(Button)findViewById(R.id.but_three);
        b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsControlActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
