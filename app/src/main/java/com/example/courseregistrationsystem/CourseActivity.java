package com.example.courseregistrationsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

public class CourseActivity extends AppCompatActivity {
    private CheckBox cb_course_python, cb_course_c, cb_course_cp, cb_course_java, cb_course_dld, cb_course_math, cb_course_dbms, cb_course_android;
    private Button btn_course_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        cb_course_python = findViewById(R.id.cb_course_python);
        cb_course_c = findViewById(R.id.cb_course_c);
        cb_course_cp = findViewById(R.id.cb_course_cp);
        cb_course_java = findViewById(R.id.cb_course_java);
        cb_course_dld = findViewById(R.id.cb_course_dld);
        cb_course_math = findViewById(R.id.cb_course_math);
        cb_course_dbms = findViewById(R.id.cb_course_dbms);
        cb_course_android = findViewById(R.id.checkBox8);
        btn_course_submit = findViewById(R.id.btn_course_submit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.et_course_reg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String student_id = getIntent().getStringExtra("student_id");
        findViewById(R.id.btn_course_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, CourseInfo.class);
                intent.putExtra("student_id", student_id);
                startActivity(intent);
            }
        });
        ArrayList<String> enrolled_courses = getCoursesFromDatabase(student_id);
        for(String courses:enrolled_courses){
            if(courses.equals("Introduction to Python")) cb_course_python.setChecked(true);
            if (courses.equals("C Programming")) cb_course_c.setChecked(true);
            if (courses.equals("Competitive Programming")) cb_course_cp.setChecked(true);
            if (courses.equals("Object Oriented Programming")) cb_course_java.setChecked(true);
            if (courses.equals("Digital Logic Design")) cb_course_dld.setChecked(true);
            if (courses.equals("Number Theory")) cb_course_math.setChecked(true);
            if (courses.equals("Database Management System")) cb_course_dbms.setChecked(true);
            if (courses.equals("Android App Development")) cb_course_android.setChecked(true);
        }
        btn_course_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selected_courses = new ArrayList<>();
                ArrayList<String>unselected_courses = new ArrayList<>();
                if (cb_course_python.isChecked()) selected_courses.add("Introduction to Python");
                else unselected_courses.add("Introduction to Python");

                if (cb_course_c.isChecked()) selected_courses.add("C Programming");
                else unselected_courses.add("C Programming");

                if (cb_course_cp.isChecked()) selected_courses.add("Competitive Programming");
                else unselected_courses.add("Competitive Programming");

                if (cb_course_java.isChecked()) selected_courses.add("Object Oriented Programming");
                else unselected_courses.add("Object Oriented Programming");

                if (cb_course_dld.isChecked()) selected_courses.add("Digital Logic Design");
                else unselected_courses.add("Digital Logic Design");

                if (cb_course_math.isChecked()) selected_courses.add("Number Theory");
                else unselected_courses.add("Number Theory");

                if (cb_course_dbms.isChecked()) selected_courses.add("Database Management System");
                else unselected_courses.add("Database Management System");

                if (cb_course_android.isChecked()) selected_courses.add("Android App Development");
                else unselected_courses.add("Android App Development");

                if (!selected_courses.isEmpty()) {
                    saveCoursesToDatabase(student_id, selected_courses);
                    Toast.makeText(CourseActivity.this, "Courses added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CourseActivity.this, Profile.class);
                    intent.putExtra("student_id", student_id);
                    startActivity(intent);
                }
                if (!unselected_courses.isEmpty()){
                    removeCoursesFromDatabase(student_id,unselected_courses);
                    Toast.makeText(CourseActivity.this, "Courses removed successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CourseActivity.this, Profile.class);
                    intent.putExtra("student_id", student_id);
                    startActivity(intent);
                }
            }
        });
    }
    void saveCoursesToDatabase(String student_id, ArrayList<String> courses) {
        DBHelper dbHelper = new DBHelper(CourseActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String course : courses) {
            ContentValues values = new ContentValues();
            values.put("student_id", student_id);
            values.put("courses_enrolled", course);
            db.insert("enrolled", null, values);
        }
    }

    void removeCoursesFromDatabase(String student_id, ArrayList<String> courses) {
        DBHelper dbHelper = new DBHelper(CourseActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (String course : courses) {
            db.delete("enrolled", "student_id = ? AND courses_enrolled = ?", new String[]{student_id, course});
        }
    }
    ArrayList<String> getCoursesFromDatabase(String student_id) {
        DBHelper dbHelper = new DBHelper(CourseActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> getCourses = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select courses_enrolled from enrolled where student_id=?", new String[]{student_id});
        if (cursor.moveToFirst()) {
            do {
                String course = cursor.getString(cursor.getColumnIndex("courses_enrolled"));
                getCourses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return getCourses;
    }
    public void onBackPressed(){
        String student_id = getIntent().getStringExtra("student_id");
        Intent intent=new Intent(CourseActivity.this,Profile.class);
        intent.putExtra("student_id",student_id);
        startActivity(intent);
    }
}