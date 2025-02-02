package com.example.courseregistrationsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS="students";
    private static final String TABLE_ENROLLED="enrolled";
    private static final String TABLE_RESULTS="results";
    public static final String COLUMN_ID = "student_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COURSE_ENROLLED = "course_enrolled";
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + "(" +
            COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PHONE + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";
    private static final String CREATE_TABLE_ENROLLED =
            "CREATE TABLE " + TABLE_ENROLLED + " (" +
                    "student_id TEXT NOT NULL, " +
                    "courses_enrolled TEXT NOT NULL, " +
                    "FOREIGN KEY (student_id) REFERENCES Students(student_id), " +
                    "PRIMARY KEY (student_id, courses_enrolled));";
    private static final String CREATE_TABLE_RESULTS = "CREATE TABLE "+ TABLE_RESULTS + "("
            + "student_id TEXT, "
            + "course_name TEXT, "
            + "course_credit FLOAT, "
            + "gpa FLOAT, "
            + "grade TEXT, "
            + "PRIMARY KEY (student_id, course_name), "
            + "FOREIGN KEY (student_id) REFERENCES Students(student_id));";
    public DBHelper(Context context){
        super(context,"university.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_ENROLLED);
        db.execSQL(CREATE_TABLE_RESULTS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // I skipped these steps as it is not required for this project
    }
    public boolean validateUser(String studentId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ID + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{studentId, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }
}
