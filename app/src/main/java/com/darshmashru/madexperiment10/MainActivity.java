package com.darshmashru.madexperiment10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText rollNo, name, marks;
    Button add, remove, update, view;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollNo = findViewById(R.id.studentRollNo);
        name = findViewById(R.id.studentName);
        marks = findViewById(R.id.studentMarks);

        add = findViewById(R.id.addStudent);
        remove = findViewById(R.id.removeStudent);
        update = findViewById(R.id.updateStudent);
        view = findViewById(R.id.viewStudent);

        db = new DatabaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = rollNo.getText().toString();
                String n = name.getText().toString();
                String m = marks.getText().toString();

                Boolean verifyAdd = db.addStudentData(r, n, m);
                if(verifyAdd == true)
                    Toast.makeText(MainActivity.this, "New Student Added to DB", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error! - New Student NOT Added to DB", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = rollNo.getText().toString();
                String n = name.getText().toString();
                String m = marks.getText().toString();

                Boolean verifyUpdate = db.updateStudentData(r, n, m);
                if(verifyUpdate == true)
                    Toast.makeText(MainActivity.this, "Student Record Updated in DB", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error! - Student Record NOT Updated in DB", Toast.LENGTH_SHORT).show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = rollNo.getText().toString();

                Boolean verifyRemove = db.removeStudentData(r);
                if(verifyRemove == true)
                    Toast.makeText(MainActivity.this, "Student Record Removed from DB", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error! - Student Record NOT Removed from DB", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = db.fetchStudentData();
                if(result.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(result.moveToNext()) {
                    buffer.append("Roll Number: " + result.getString(0) + "\n");
                    buffer.append("Name: " + result.getString(1) + "\n");
                    buffer.append("Marks: " + result.getString(2) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Existing Student Records");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}