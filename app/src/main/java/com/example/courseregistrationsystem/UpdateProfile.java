package com.example.courseregistrationsystem;

import android.content.Intent;
import android.database.Cursor;
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

public class UpdateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText et_UP_name,et_UP_email,et_UP_phone,et_UP_password;
        Button btn_UP_Update;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        et_UP_name=findViewById(R.id.et_UP_name);
        et_UP_email=findViewById(R.id.et_UP_email);
        et_UP_phone=findViewById(R.id.et_UP_phone);
        et_UP_password=findViewById(R.id.et_UP_password);
        btn_UP_Update=findViewById(R.id.btn_UP_Update);
        String student_id=getIntent().getStringExtra("student_id");
        DBHelper dbHelper = new DBHelper(UpdateProfile.this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select name,email,phone from students where student_id=?",new String[]{student_id});
        if(cursor.moveToFirst()){
            et_UP_name.setText(cursor.getString(0));
            et_UP_email.setText(cursor.getString(1));
            et_UP_phone.setText(cursor.getString(2));
        }
        cursor.close();
        db.close();
        btn_UP_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_UP_name.getText().toString();
                String email=et_UP_email.getText().toString();
                String phone=et_UP_phone.getText().toString();
                String password=et_UP_password.getText().toString();
                if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()){
                    Toast.makeText(UpdateProfile.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbHelper1 = new DBHelper(UpdateProfile.this);
                SQLiteDatabase db=dbHelper1.getReadableDatabase();
                Cursor cursor1=db.rawQuery("Select password from students where student_id=?",new String[]{student_id});
                if (cursor1.moveToFirst()) {
                    String orignial_pass = cursor1.getString(0);
                    if (!password.equals(orignial_pass)) {
                        Toast.makeText(UpdateProfile.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        update_info(name,email,phone,student_id);
                    }
                }
            }
        });
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pass_admin), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

    }
    private void update_info(String name,String email, String phone,String student_id){
        DBHelper dbHelper=new DBHelper(UpdateProfile.this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL("Update students set name=?,email=?,phone=? where student_id=?",new String[]{name,email,phone,student_id});
        Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(UpdateProfile.this,Profile.class);
        intent.putExtra("student_id",student_id);
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent=new Intent(UpdateProfile.this,Profile.class);
        startActivity(intent);
    }
}