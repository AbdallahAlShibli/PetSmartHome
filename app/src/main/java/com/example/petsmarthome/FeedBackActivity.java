package com.example.petsmarthome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        final RatingBar simpleRatingBar1 = (RatingBar) findViewById(R.id.simpleRatingBar1);
        final RatingBar simpleRatingBar2 = (RatingBar) findViewById(R.id.simpleRatingBar2);
        final EditText E_Suggestion=(EditText)findViewById(R.id.edit_suggestion);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating_1 =  simpleRatingBar1.getRating();
                float rating_2 = simpleRatingBar2.getRating();

                String text= E_Suggestion.getText().toString().trim();

                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(getBaseContext(), "Enter Suggestion!!", Toast.LENGTH_LONG).show();
                    return;
                }

                final DatabaseReference Reference = FirebaseDatabase.getInstance().getReference().child("Feedback_User");
                Feedback feedback = new Feedback(rating_1,rating_2,text);
                Reference.push().setValue(feedback);

                Toast.makeText(FeedBackActivity.this, "Added Successfully...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FeedBackActivity.this, ClientHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}