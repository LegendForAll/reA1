package com.example.hothaingoc.chemicallight;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChemicalNote extends AppCompatActivity {

    final String DATABASE_NAME = "note.sqlite";
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    ArrayList<data_Note> arrayList;
    adapter_Note dataB;

    EditText editText_topic, editText_main;
    Button insert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_note);

        initView();
        readData();
        actionButton();
    }

    private void actionButton() {
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNote();
            }
        });
    }

    private void insertNote() {
        sqLiteDatabase= Database.initDatabase(this,DATABASE_NAME);

        String topicT = editText_topic.getText().toString();
        String mainT = editText_main.getText().toString();
        String currentDate;

        ContentValues contentValues = new ContentValues();
        currentDate = getCurrentTime();

        contentValues.put("Topic",topicT);
        contentValues.put("TimeNote",currentDate);
        contentValues.put("MainNote",mainT);


        sqLiteDatabase.insert("NOTEBOOK",null,contentValues);
        Toast.makeText(this, "Inserted...", Toast.LENGTH_SHORT).show();
        readData();
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =mdformat.format(calendar.getTime());
        return strDate;
    }

    private void readData() {
        sqLiteDatabase = Database.initDatabase(this,DATABASE_NAME);
        String countQuery = "SELECT  * FROM " + "NOTEBOOK";
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);
        arrayList.clear();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            int idn = cursor.getInt(0);
            String topicn = cursor.getString(1);
            String daten = cursor.getString(2);
            String main = cursor.getString(3);

            arrayList.add(new data_Note(idn,topicn,daten,main));
        }

        dataB.notifyDataSetChanged();
    }

    private void initView() {
        editText_topic = (EditText) findViewById(R.id.edit_topicIns);
        editText_main = (EditText) findViewById(R.id.edit_MainIns);
        insert = (Button) findViewById(R.id.btn_insertnote);

        recyclerView = (RecyclerView) findViewById(R.id.rv_notebook);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator( new DefaultItemAnimator());
        arrayList = new ArrayList<>();
        dataB = new adapter_Note(arrayList,this);
        recyclerView.setAdapter(dataB);
    }
}
