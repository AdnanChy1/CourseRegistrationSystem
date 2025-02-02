package com.example.courseregistrationsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        EditText et_admin_stuID,et_admin_courseName,et_admin_courseCredit,et_admin_courseGPA,et_admin_courseGrade;
        Button btn_admin_addCourse;
        et_admin_stuID=findViewById(R.id.et_admin_stuID);
        et_admin_courseName=findViewById(R.id.et_admin_courseName);
        et_admin_courseCredit=findViewById(R.id.et_admin_courseCredit);
        et_admin_courseGPA=findViewById(R.id.et_admin_courseGPA);
        et_admin_courseGrade=findViewById(R.id.et_admin_courseGrade);
        btn_admin_addCourse=findViewById(R.id.btn_admin_addCourse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.et_admin_pass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_admin_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = ((EditText) findViewById(R.id.et_admin_pass)).getText().toString();
                String admin_pass=getIntent().getStringExtra("admin_pass");
                if (admin_pass.equals(pass)){
                    et_admin_stuID.setVisibility(View.VISIBLE);
                    et_admin_courseName.setVisibility(View.VISIBLE);
                    et_admin_courseCredit.setVisibility(View.VISIBLE);
                    et_admin_courseGPA.setVisibility(View.VISIBLE);
                    et_admin_courseGrade.setVisibility(View.VISIBLE);
                    btn_admin_addCourse.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(AdminActivity.this,"Wrong Password!",Toast.LENGTH_SHORT).show();
                }
            }
        });
            btn_admin_addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student_id=et_admin_stuID.getText().toString();
                String course_name=et_admin_courseName.getText().toString();
                String course_credit=et_admin_courseCredit.getText().toString();
                String course_gpa=et_admin_courseGPA.getText().toString();
                String course_grade=et_admin_courseGrade.getText().toString();
                if (student_id.isEmpty()||course_name.isEmpty()||course_credit.isEmpty()||course_gpa.isEmpty()||course_grade.isEmpty()){
                  Toast.makeText(AdminActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                DBHelper dbHelper=new DBHelper(AdminActivity.this);
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.execSQL("PRAGMA foreign_keys = ON;");
                ContentValues values=new ContentValues();
                values.put("student_id",student_id);
                values.put("course_name",course_name);
                values.put("course_credit",course_credit);
                values.put("gpa",course_gpa);
                values.put("grade",course_grade);
                long result=db.insert("results",null,values);
                if (result!=-1){
                    Toast.makeText(AdminActivity.this,"Results Added Successfully!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AdminActivity.this,"No Student found with this ID!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(AdminActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}