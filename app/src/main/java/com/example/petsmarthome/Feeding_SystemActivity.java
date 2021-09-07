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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class Feeding_SystemActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.petsmarthome";
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter_2;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList_2 = new ArrayList<>();
   ListView listView_1,listView_2;
   Button _back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding__system);
        SharedPreferences sharedPreferences = getSharedPreferences("Pet_ID", Context.MODE_PRIVATE);
        final String pet_id = sharedPreferences.getString("ID", EXTRA_MESSAGE);

        listView_1 = (ListView) findViewById(R.id.list_1);
        listView_2 = (ListView) findViewById(R.id.list_2);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        Query classrooms_reference = FirebaseDatabase.getInstance().getReference("Feeding Water").orderByChild("PET_ID").equalTo(pet_id);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, arrayList);

        classrooms_reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.clear();

                if(dataSnapshot!=null && dataSnapshot.exists())
                {
                    for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        C_Class value = snapshot.getValue(C_Class.class);
                        if(value.Low==0){
                            arrayList.add(" Water below the required Level");
                        }else{
                            arrayList.add(" Water filling");
                        }

                        if(value.hight==0){
                            arrayList.add(" Water below the required Level");
                        }else{
                            arrayList.add(" Water filling");
                        }
                        listView_1.setAdapter(arrayAdapter);
                        LineGraphSeries <DataPoint> series = new LineGraphSeries< >(new DataPoint[] {
                                new DataPoint(0, 1),
                                new DataPoint(Integer.valueOf(value.Low), Integer.valueOf(value.hight))

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

      Query classrooms_reference1 = FirebaseDatabase.getInstance().getReference("Feeding_Food").orderByChild("PET_ID").equalTo(pet_id);
      arrayAdapter_2 = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, arrayList_2);

       classrooms_reference1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter_2.clear();

                if(dataSnapshot!=null && dataSnapshot.exists())
                {
                    for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        food_Class value_1 = snapshot.getValue(food_Class.class);
                        if(value_1.Food==0){
                            arrayList_2.add("Food is Empty");
                        }else{
                            arrayList_2.add("Food is Available");
                        }
                        listView_2.setAdapter(arrayAdapter_2);

                        listView_1.setAdapter(arrayAdapter);


                    }
                }
                else {


                    arrayAdapter_2.notifyDataSetChanged();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        _back=(Button)findViewById(R.id.back_12);
        _back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feeding_SystemActivity.this, Devices_StatuesActivity.class);
                startActivity(intent);
                finish();
            }
        });


        FirebaseDatabase database5 = FirebaseDatabase.getInstance();

        DatabaseReference myRef5 = database5.getReference();
        myRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value1 = String.valueOf(dataSnapshot.child("Feeding_Food/-MUAU7IQ62uOjGvDfWHg/Food").getValue());

                if (value1.equals("1")) {
                    String s = "Food is filled";
                    notification(s);
                }else {

                    String s = "Food is not filled";
                    notification(s);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        FirebaseDatabase database6 = FirebaseDatabase.getInstance();

        DatabaseReference myRef6 = database6.getReference();
        myRef6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value0 = String.valueOf(dataSnapshot.child("Feeding Water/-MUA21rcWVh1heqwC-6d/Low").getValue());


                if (value0.equals("1")) {
                    String s0 = "Level of water in the require level";
                    notification(s0);
                }else {

                    String s0 = "The water level below the required level";
                    notification(s0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void notification(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"Notification")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Smart Home Pet")
                    .setContentText(s)
                    .setAutoCancel(true)
                    .setContentInfo("Info");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(new Random().nextInt(),notificationBuilder.build());


        }

    }
}