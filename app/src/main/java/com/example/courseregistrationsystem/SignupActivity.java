package com.example.courseregistrationsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
     EditText et_signup_stuID, et_signup_name, et_signup_pass, et_signup_email, et_signup_phone;
     Button btn_signup_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        et_signup_stuID = findViewById(R.id.et_signup_stuid);
        et_signup_name = findViewById(R.id.et_signup_name);
        et_signup_pass = findViewById(R.id.et_signup_pass);
        et_signup_email = findViewById(R.id.et_signup_email);
        et_signup_phone = findViewById(R.id.et_signup_phone);
        btn_signup_submit = findViewById(R.id.btn_signup_submit);
        btn_signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuID = et_signup_stuID.getText().toString();
                String name = et_signup_name.getText().toString();
                String pass = et_signup_pass.getText().toString();
                String email = et_signup_email.getText().toString();
                String phone = et_signup_phone.getText().toString();
                if (stuID.isEmpty() || name.isEmpty() || pass.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbHelper = new DBHelper(SignupActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("student_id", stuID);
                values.put("name", name);
                values.put("password", pass);
                values.put("email", email);
                values.put("phone", phone);
                long result = db.insert("students", null, values);
                if (result != -1) {
                    Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pass_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(SignupActivity.this,MainActivity.class);
        startActivity(intent);
    }
}