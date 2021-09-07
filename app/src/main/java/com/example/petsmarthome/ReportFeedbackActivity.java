package com.example.petsmarthome;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportFeedbackActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_feedback);

        firebaseDatabase= FirebaseDatabase.getInstance();

        TextView title = findViewById(R.id.feedback_Title);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        final String current_Date_Time = sdf.format(new Date());

        title.setText("Report Feedback User"+"     "+ current_Date_Time);

        Query query = firebaseDatabase.getReference("Feedback_User");
        listView = (ListView) findViewById(R.id.list);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo,arrayList);
        LayoutInflater layoutinflater = getLayoutInflater();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Feedback value=ds.getValue(Feedback.class);
                    String row = String.valueOf("Q1-"+value.question_1 +" | Q2-"+ value.question_2 +"  | "+value.suggestion);
                    arrayList.add(row);



                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button Button = findViewById(R.id.feedback_Button);
       Button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                }

                createPdf("Report Feedback User"+"     "+ current_Date_Time, arrayList);
            }
        });

        Button but_back=(Button)findViewById(R.id.back_f);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportFeedbackActivity.this, ReportsControlActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(String title, ArrayList<String> sometext){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.RED);
        canvas.drawText(title, 20, 20, paint);

        paint.setColor(Color.BLACK);
        for (int i = 0; i< sometext.size(); i++){
            canvas.drawText(sometext.get(i).toString(), 20, 50+i*30, paint);
        }
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Pet/";
        File file = new File(directory_path);
        if(file.isFile()){
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+title+".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();



        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"PERMISSION not GRANTED",Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }
}