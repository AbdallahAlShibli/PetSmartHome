package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Fan_SystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan__system);


        FirebaseDatabase database5 = FirebaseDatabase.getInstance();

        DatabaseReference myRef5 = database5.getReference();
        myRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value1 = String.valueOf(dataSnapshot.child("Feeding_Pet_Position/MUAU7IQ6HydIo4DfWHg/Fan").getValue());

                if (value1.equals("1")) {
                    String s = "fan is working";
                    notification(s);
                } else {
                    String s = "fan is closed";
                    notification(s);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void notification(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Notification")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Smart Home Pet")
                    .setContentText(s)
                    .setAutoCancel(true)
                    .setContentInfo("Info");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(new Random().nextInt(), notificationBuilder.build());


        }

    }
}