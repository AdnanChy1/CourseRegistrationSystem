package com.example.courseregistrationsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        TextView tv_result_name = findViewById(R.id.tv_result_name);
        TextView tv_result_studentID = findViewById(R.id.tv_result_studentID);
        TextView tv_result_creditsCompleted = findViewById(R.id.tv_result_creditsCompleted);
        TextView tv_result_CGPA = findViewById(R.id.tv_result_CGPA);
        String student_id = getIntent().getStringExtra("student_id");
        String stu_Name = getIntent().getStringExtra("stu_Name");
        ArrayList<Float> values = totalCreditsCompleted(student_id);
        String totalCredits = String.valueOf(values.get(0));
        float totalGpa = values.get(1);
        float cgpa = cgpa(values);
        cgpa=Math.round(cgpa*100.0)/100.0f;
        tv_result_name.setText("Name: " + stu_Name);
        tv_result_studentID.setText("Student ID: " + student_id);
        tv_result_creditsCompleted.setText("Credits Completed: " + totalCredits);
        tv_result_CGPA.setText("CGPA: " + cgpa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pass_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        ArrayList<String> courseNames = new ArrayList<>();
        ArrayList<String> courseCredit = new ArrayList<>();
        ArrayList<String> courseGPA = new ArrayList<>();
        ArrayList<String> courseGrade = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(Result.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT course_name,course_credit,gpa,grade FROM results WHERE student_id = ?", new String[]{student_id});
        if (cursor.moveToFirst()) {
            do {
                courseNames.add(cursor.getString(cursor.getColumnIndex("course_name")));
                courseGPA.add(cursor.getString(cursor.getColumnIndex("gpa")));
                courseGrade.add(cursor.getString(cursor.getColumnIndex("grade")));
                courseCredit.add(cursor.getString(cursor.getColumnIndex("course_credit")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        for (int i = 0; i < courseNames.size(); i++) {
            TableRow tableRow=new TableRow(this);
            TextView textView1=new TextView(this);
            textView1.setText(courseNames.get(i));
            textView1.setTextSize(8);
            textView1.setPadding(10,10,10,10);
            tableRow.addView(textView1);
            TextView textView2=new TextView(this);
            textView2.setText(courseCredit.get(i));
            textView2.setTextSize(16);
            textView2.setPadding(10,10,10,10);
            tableRow.addView(textView2);
            TextView textView3=new TextView(this);
            textView3.setText(courseGPA.get(i));
            textView3.setTextSize(16);
            textView3.setPadding(10,10,10,10);
            tableRow.addView(textView3);
            TextView textView4=new TextView(this);
            textView4.setText(courseGrade.get(i));
            textView4.setTextSize(16);
            textView4.setPadding(10,10,10,10);
            tableRow.addView(textView4);
            tableLayout.addView(tableRow);
        }
    }
    ArrayList<Float>totalCreditsCompleted(String student_id){
        float totalCredits=0;
        float totalGpa=0;
        ArrayList<Float>credits=new ArrayList<>();
        ArrayList<Float>gpa=new ArrayList<>();
        ArrayList<Float>values=new ArrayList<>();
        DBHelper dbHelper=new DBHelper(Result.this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT course_credit,gpa FROM results WHERE student_id = ?", new String[]{student_id});
        if (cursor.moveToFirst()) {
            do {
                float credit = cursor.getFloat(cursor.getColumnIndex("course_credit"));
                credits.add(credit);
                float gpaValue = cursor.getFloat(cursor.getColumnIndex("gpa"));
                gpa.add(gpaValue*credit);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
    }
        for(float credit:credits){
            totalCredits+=credit;
        }
        for (float gpaValue:gpa){
            totalGpa+=gpaValue;
        }
        values.add(totalCredits);
        values.add(totalGpa);
        return values;
    }
    float cgpa(ArrayList<Float>values) {
        float totalCredits=values.get(0);
        float totalGpa=values.get(1);
        float cgpa=totalGpa/totalCredits;
        return cgpa;
    }
    public void onBackPressed(){
        Intent intent=new Intent(Result.this,Profile.class);
        startActivity(intent);
    }
}