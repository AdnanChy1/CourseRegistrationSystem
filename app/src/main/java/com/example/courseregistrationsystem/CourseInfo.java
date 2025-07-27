package com.example.courseregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourseInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pass_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_courseinfo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student_id = getIntent().getStringExtra("student_id");
                Intent intent = new Intent(CourseInfo.this, CourseActivity.class);
                intent.putExtra("student_id", student_id);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(CourseInfo.this,CourseActivity.class);
        String student_id = getIntent().getStringExtra("student_id");
        intent.putExtra("student_id",student_id);
        startActivity(intent);
    }
}