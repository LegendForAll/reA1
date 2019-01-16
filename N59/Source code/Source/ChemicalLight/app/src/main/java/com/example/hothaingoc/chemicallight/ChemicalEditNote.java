package com.example.hothaingoc.chemicallight;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChemicalEditNote extends AppCompatActivity {

    TextView textView_ms, textView_nd;
    Button button_de, button_up;
    EditText editText;

    final String DATABASE_NAME = "note.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_edit_note);

        initView();
        initUI();
        actionButton();
    }

    private void actionButton() {
        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
                //goBack();
            }
        });
        button_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleleNote();
                //goBack();
            }
        });
    }

    private void goBack() {
        Intent intent = new Intent(ChemicalEditNote.this,ChemicalNote.class);
        startActivity(intent);
    }

    private void deleleNote() {

        sqLiteDatabase= Database.initDatabase(this,DATABASE_NAME);
        String idUp = textView_ms.getText().toString();
        sqLiteDatabase.delete("NOTEBOOK","Id=?",new String[]{idUp});
        Toast.makeText(this, "Deleted...", Toast.LENGTH_SHORT).show();
        initUI();

    }

    private void updateNote() {
        sqLiteDatabase= Database.initDatabase(this,DATABASE_NAME);

        String textUpdate = editText.getText().toString();
        String idUp = textView_ms.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("MainNote",textUpdate);

        sqLiteDatabase.update("NOTEBOOK",contentValues,"Id=?",new String[]{idUp});
        Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show();
        initUI();
    }

    private void initUI() {

        Intent intent = getIntent();
        String IDn = intent.getStringExtra("ID");
        sqLiteDatabase = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NOTEBOOK WHERE Id = ? ",new String[]{IDn});
        cursor.moveToFirst();

        String textNote = cursor.getString(3);

        textView_ms.setText(IDn);
        textView_nd.setText(textNote);

    }

    private void initView() {
        textView_ms = (TextView) findViewById(R.id.txt_MS);
        textView_nd = (TextView) findViewById(R.id.txt_MainText);
        button_de = (Button) findViewById(R.id.btn_delete);
        button_up = (Button) findViewById(R.id.btn_update);
        editText = (EditText) findViewById(R.id.edt_uptext);
    }
}
