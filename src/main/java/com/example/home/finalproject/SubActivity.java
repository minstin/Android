package com.example.home.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView listView;
    private Cursor cursor;
    private Cursor cursor2;
    private Cursor cursor3;


    private SimpleCursorAdapter mAdapter;
    private Context context = this;
    Button gagebuBtn, graphBtn, settingBtn;
    TextView jaja;
    TextView oneday;

    private String[] columns;

    private int[] to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.listView);//리스트 뷰
        jaja = (TextView) findViewById(R.id.jaja);
        oneday = (TextView) findViewById(R.id.oneday);

        try {
            cursor = dbHelper.CursorQuery();
            listViewSetAdapter(cursor);

            cursor2 = dbHelper.totalgogo();
            total(cursor2);
            cursor3 = dbHelper.onegogo();
            one(cursor3);

        }catch (Exception e) {
            Toast.makeText(getApplication(), "표시할 목록이 없어요." + e,
                    Toast.LENGTH_LONG).show();
        }


        gagebuBtn = (Button) findViewById(R.id.button);
        gagebuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    cursor = dbHelper.CursorQuery();
                    listViewSetAdapter(cursor);

                }catch (Exception e) {
                    Toast.makeText(getApplication(), "표시할 목록이 없어요." + e,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        graphBtn = (Button) findViewById(R.id.button2);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphintent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(graphintent);
                finish();
            }
        });

        settingBtn = (Button) findViewById(R.id.button4);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setintent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(setintent);
                finish();
            }
        });

    }

    public void total(Cursor cursor){
        startManagingCursor(cursor);
        cursor.moveToFirst();
        int result= 0;
        result = cursor.getInt(0);
        String result2 = String.valueOf(result);
        jaja.setText(result2);

    }

    public void one(Cursor cursor){
        startManagingCursor(cursor);
        cursor.moveToFirst();
        int result= 0;
        result = cursor.getInt(0);
        String result2 = String.valueOf(result);
        oneday.setText(result2);

    }


    public void listViewSetAdapter(Cursor cursor) {

        startManagingCursor(cursor);

        columns = new String[] {"name", "spot", "price", "date"};

        to = new int[] { R.id.nameTextView, R.id.spotTextView, R.id.priceTextView, R.id.dateTextView};

        mAdapter = new SimpleCursorAdapter(context,R.layout.singer_item, cursor, columns, to);

        listView.setAdapter(mAdapter);
    }
}
