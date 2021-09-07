package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class Light_SystemActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.petsmarthome";
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ListView listView_1;
    Button _back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light__system);

        SharedPreferences sharedPreferences = getSharedPreferences("Pet_ID", Context.MODE_PRIVATE);
        final String pet_id = sharedPreferences.getString("ID", EXTRA_MESSAGE);

        listView_1 = (ListView) findViewById(R.id.list_1);

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        Query classrooms_reference = FirebaseDatabase.getInstance().getReference("light").orderByChild("PET_ID").equalTo(pet_id);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, arrayList);

        classrooms_reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.clear();

                if(dataSnapshot!=null && dataSnapshot.exists())
                {
                    for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        L_Class value = snapshot.getValue(L_Class.class);
                        if(value.Low==0){
                            arrayList.add("light is closed");

                        }else{
                            arrayList.add("light is working");
                        }

                        if(value.high==0){
                            arrayList.add("light is closed");
                        }else{
                            arrayList.add("light is working");

                        }
                        listView_1.setAdapter(arrayAdapter);
                        LineGraphSeries<DataPoint> series = new LineGraphSeries< >(new DataPoint[] {
                                new DataPoint(0, 1),
                                new DataPoint(Integer.valueOf(value.Low), Integer.valueOf(value.high))

                        });
                        graph.addSeries(series);
                    }
                }
                else {


                    arrayAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });



        _back1=(Button)findViewById(R.id.back_12);
        _back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Light_SystemActivity.this, Devices_StatuesActivity.class);
                startActivity(intent);
                finish();
            }
        });





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef6 = database.getReference();
        myRef6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value1 = String.valueOf(dataSnapshot.child("Feeding Water/-MUA21rcWVh1heqwC-6d/hight").getValue());
                if (value1.equals("1")) {
                    String s = "light is working";
                    notification(s);
                } else {

                    String s = "light is closed";
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