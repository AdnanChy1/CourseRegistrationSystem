package com.example.courseregistrationsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    TextView stuID,stuName,stuEmail,stuPhone,course_enrolled;
    DBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        stuID=findViewById(R.id.tv_profile_stuID);
        stuName=findViewById(R.id.tv_profile_stuName);
        stuEmail=findViewById(R.id.tv_profile_stuEmail);
        stuPhone=findViewById(R.id.tv_profile_stuPhone);
        dbhelper=new DBHelper(Profile.this);
        String student_id=getIntent().getStringExtra("student_id");
        String stu_Name =displayStudentInfo(student_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pass_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_profile_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_profile_addwithdrawCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,CourseActivity.class);
                intent.putExtra("student_id",student_id);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_profile_updateProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,UpdateProfile.class);
                intent.putExtra("student_id",student_id);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_profile_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Result.class);
                intent.putExtra("student_id",student_id);
                intent.putExtra("stu_Name",stu_Name);
                startActivity(intent);
            }
        });
    }
    String displayStudentInfo(String student_id){
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE student_id = ?", new String[]{student_id});
        String n="";
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            stuID.setText("Student ID: "+student_id);
            stuName.setText("Name: "+name);
            stuEmail.setText("Email: "+email);
            stuPhone.setText("Phone: "+phone);
            displayCoursesEnrolled(student_id);
            n=name;
        }
        else{
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return n;
    }
    void displayCoursesEnrolled(String student_id){
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM enrolled WHERE student_id = ?", new String[]{student_id});
        ArrayList<String>courses=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String course = cursor.getString(cursor.getColumnIndex("courses_enrolled"));
                courses.add(course);
            } while (cursor.moveToNext());
            int i=1;
            TableLayout tableLayout=findViewById(R.id.tableLayout_profile);
            for(String course:courses){
                TableRow tableRow=new TableRow(this);
                TextView textView1=new TextView(this);
                textView1.setText(i+". "+course);
                textView1.setTextSize(20);
                textView1.setPadding(10,10,10,10);
                textView1.setTextColor(Color.parseColor("#FFD700"));
                textView1.setTypeface(null, Typeface.ITALIC);
                textView1.setGravity(Gravity.CENTER);
                tableRow.addView(textView1);
                tableLayout.addView(tableRow);
                i++;
            }
        cursor.close();
    }
}
    public void onBackPressed(){
        Intent intent=new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
    }
}