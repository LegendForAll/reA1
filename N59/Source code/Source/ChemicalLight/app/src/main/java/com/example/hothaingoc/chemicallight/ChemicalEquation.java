package com.example.hothaingoc.chemicallight;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ChemicalEquation extends AppCompatActivity {


    final String DATABASE_NAME = "arenadata.sqlite";
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    ArrayList<data_Equation> arrayList;
    adapter_Equation dataEqua;

    EditText editText_TG;
    Button button_Search;

    Dialog equaDialog, reactDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_equation);

        initView();
        //readData();
        actionSearch();

        //Test dialog
        equaDialog = new Dialog(this);
        reactDialog = new Dialog(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {

                //get position item recyclerView
                int pos_dragged = dragged.getAdapterPosition();
                int pos_target  = target.getAdapterPosition();

                Collections.swap(arrayList,pos_dragged,pos_target);
                dataEqua.notifyItemMoved(pos_dragged,pos_target);

                Toast.makeText(ChemicalEquation.this, "OKItem", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void ShowReactDialog(View v){
        Button button_close_re;
        reactDialog.setContentView(R.layout.custom_reactivity_series);
        button_close_re = (Button) reactDialog.findViewById(R.id.btn_close_reactivity);

        button_close_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reactDialog.dismiss();
            }
        });

        reactDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        reactDialog.show();
    }

    public void ShowEquaDialog(View v){
        Button button_close;
        equaDialog.setContentView(R.layout.custom_equation);
        button_close = (Button) equaDialog.findViewById(R.id.btn_close_eque);

        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equaDialog.dismiss();
            }
        });

        equaDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        equaDialog.show();
    }

    private void actionSearch() {

        button_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fTG = String.valueOf(editText_TG.getText());
                findData(fTG);
            }
        });
    }

    private void findData(String a){

        sqLiteDatabase = Database.initDatabase(this,DATABASE_NAME);
        String countQuery = "SELECT * FROM "+"Equation"+" WHERE "+"Ingredient"+" like '%"+a+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);
        arrayList.clear();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            String ctg = cursor.getString(0);
            String csp = cursor.getString(1);
            arrayList.add(new data_Equation(ctg,csp,"Normal"));
        }
        dataEqua.notifyDataSetChanged();
    }

    private void readData() {
        sqLiteDatabase = Database.initDatabase(this,DATABASE_NAME);
        //String countQuery = "SELECT  * FROM " + "PHUONGTRINH";
        String countQuery = "SELECT  * FROM " + "Equation";
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);
        arrayList.clear();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            String ctg = cursor.getString(0);
            String csp = cursor.getString(1);
            arrayList.add(new data_Equation(ctg,csp,"Normal"));
        }

        dataEqua.notifyDataSetChanged();

    }

    private void initView() {

        editText_TG = (EditText) findViewById(R.id.edt_inCTG);
        button_Search = (Button) findViewById(R.id.btn_searchPT);

        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rvPT);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        arrayList = new ArrayList<>();
        dataEqua = new adapter_Equation(arrayList,this);
        recyclerView.setAdapter(dataEqua);
    }
}
