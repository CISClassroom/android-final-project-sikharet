package com.example.applicationdomain1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import java.util.Date;

import static com.example.applicationdomain1.Constants.TABLE_NAME;
import static com.example.applicationdomain1.Constants._ID;
import  static com.example.applicationdomain1.Constants.CONTENT;
import static com.example.applicationdomain1.Constants.TIME;

public class applicationdomain2 extends AppCompatActivity {

    private Button add_butt;
    private EditText add_box;
    private TextView add_view;
    private NoteHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationdomain2);

        Button next = (Button)findViewById(R.id.bt2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(applicationdomain2.this,MainActivity.class);  //คำสั่่งเมื่อกดปุ่นbuttonให้จากหน้าapplicationdomain2ไปยังMainActivity
                startActivity(i);
                Toast.makeText(getApplicationContext(),"ออกจากระบบ",Toast.LENGTH_LONG).show(); //คำสั่งให้showว่า ออกจากระบบ
            }
        });
        init();

        try{
            Cursor cursor = getAllNotes();
            showNotes(cursor);
        }finally{
            helper.close();
        }

        add_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    addNote(add_box.getText().toString());
                    Cursor cursor = getAllNotes();
                    showNotes(cursor);
                    add_box.setText(null);                      //ตั้งข้อมูลให้มันเพิ่มจากadd_box
                }finally{
                    helper.close();
                }
            }
        });
    }

    private void addNote(String str){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(CONTENT, str);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private static String[] COLUMNS = {_ID, TIME, CONTENT};
    private static String ORDER_BY = TIME + " DESC";

    private Cursor getAllNotes(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, ORDER_BY);

        return cursor;
    }

    private void showNotes(Cursor cursor){
        StringBuilder builder = new StringBuilder("ข้อความที่บันทึกไว้:\n\n");

        while (cursor.moveToNext()){
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String content = cursor.getString(2);

            builder.append("ลำดับ ").append(id).append(": ");

            String strDate = (String)DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date(time));
            builder.append(strDate).append("\n");
            builder.append("\t").append(content).append("\n\n");
        }

        add_view.setText(builder);
    }

    private void init(){
        add_butt = (Button)findViewById(R.id.add_butt);
        add_box = (EditText)findViewById(R.id.add_box);
        add_view = (TextView)findViewById(R.id.add_view);
        helper = new NoteHelper(this);

    }
}
