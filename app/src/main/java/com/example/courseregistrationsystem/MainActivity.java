package com.example.courseregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_submit_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_submit_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuID = ((EditText) findViewById(R.id.et_login_stuID)).getText().toString();
                String pass =   ((EditText)findViewById(R.id.et_login_pass)).getText().toString();
                if (stuID.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbHelper=new DBHelper(MainActivity.this);
                boolean isValid=dbHelper.validateUser(stuID,pass);
                if (isValid){
                    Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Profile.class);
                    intent.putExtra("student_id",stuID);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"Enter valid ID and Password!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
        startActivity(intent);
    }
}