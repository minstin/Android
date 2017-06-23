package com.example.home.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by home on 2017-06-06.
 */

public class InsertActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Cursor cursor;
    TextView cardnameView;
    TextView usenameView;
    TextView priceView;
    TextView dateView;
    Button btnInsert;
    Button btncancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);

        cardnameView = (TextView) findViewById(R.id.cardname);
        usenameView = (TextView) findViewById(R.id.usename);
        priceView = (TextView) findViewById(R.id.price);
        dateView = (TextView) findViewById(R.id.date);

        Intent passedIntent = getIntent();
        processIntent(passedIntent);

        dbHelper = new DBHelper(this);
        btnInsert = (Button) findViewById(R.id.insert_btn);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = cardnameView.getText().toString();
                    String spot = usenameView.getText().toString();
                    String price = priceView.getText().toString();
                    Integer pricesum = Integer.parseInt(price);
                    String date = dateView.getText().toString();

                    dbHelper.insert(name, spot, price, pricesum, date);
                    dbHelper.insert2(pricesum);

                    Intent goMain = new Intent(InsertActivity.this, SubActivity.class);
                    startActivity(goMain);
                    finish();

                }catch (Exception e) {
                    Toast.makeText(getApplication(), "insert error" + e,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        btncancel = (Button) findViewById(R.id.cancel_btn);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onNewIntent(Intent intent){
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent != null){
            String cash = intent.getStringExtra("authNumber");
            String spot = intent.getStringExtra("spot");
            String cardname = intent.getStringExtra("cardname");
            String date = intent.getStringExtra("date");
            try{
                cardnameView.setText(cardname);
                usenameView.setText(spot);
                priceView.setText(cash);
                dateView.setText(date);

            }catch(Exception e){
                Toast.makeText(getApplication(), "문자메세지 읽기 불가능", Toast.LENGTH_LONG).show();
            }
        }
    }

}
