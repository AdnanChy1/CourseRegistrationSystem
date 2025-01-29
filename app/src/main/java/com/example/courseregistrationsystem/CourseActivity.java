package com.example.courseregistrationsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private CheckBox cb_course_python,cb_course_c,cb_course_cp,cb_course_java,cb_course_dld,cb_course_math,cb_course_dbms,cb_course_android;
    private Button btn_course_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        cb_course_python=findViewById(R.id.cb_course_python);
        cb_course_c=findViewById(R.id.cb_course_c);
        cb_course_cp=findViewById(R.id.cb_course_cp);
        cb_course_java=findViewById(R.id.cb_course_java);
        cb_course_dld=findViewById(R.id.cb_course_dld);
        cb_course_math=findViewById(R.id.cb_course_math);
        cb_course_dbms=findViewById(R.id.cb_course_dbms);
        cb_course_android=findViewById(R.id.checkBox8);
        btn_course_submit=findViewById(R.id.btn_course_submit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_course_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, CourseInfo.class);
                startActivity(intent);
            }
        });
        btn_course_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selected_courses = new ArrayList<>();
                if (cb_course_python.isChecked()) {
                    selected_courses.add("Python");
                }
                if (cb_course_c.isChecked()) {
                    selected_courses.add("C");
                }
                if (cb_course_cp.isChecked()) {
                    selected_courses.add("Competitive Programming");
                }
                if (cb_course_java.isChecked()) {
                    selected_courses.add("Java");
                }
                if (cb_course_dld.isChecked()) {
                    selected_courses.add("Digital Logic Design");
                }
                if (cb_course_math.isChecked()) {
                    selected_courses.add("Number Theory");
                }
                if (cb_course_dbms.isChecked()) {
                    selected_courses.add("Database Management System");
                }
                if (cb_course_android.isChecked()) {
                    selected_courses.add("Android App Development");
                }
                String student_id= getIntent().getStringExtra("student_id");
                if (!selected_courses.isEmpty()){
                    saveCoursesToDatabase(student_id,selected_courses);
                    Toast.makeText(CourseActivity.this,"Courses added successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CourseActivity.this,Profile.class);
                    intent.putExtra("student_id",student_id);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CourseActivity.this,"Please select at least one course",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void saveCoursesToDatabase(String student_id,ArrayList<String>courses){
        DBHelper dbHelper = new DBHelper(CourseActivity.this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        for (String course:courses){
            values.put("student_id",student_id);
            values.put("courses_enrolled",course);
            db.insert("enrolled",null,values);
        }
    }
}