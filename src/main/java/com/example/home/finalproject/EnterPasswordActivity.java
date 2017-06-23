package com.example.home.finalproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Vibrator mVibe;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        final Intent intent2 = getIntent();
        final String cash = intent2.getStringExtra("authNumber");
        String spot = intent2.getStringExtra("spot");
        String cardname = intent2.getStringExtra("cardname");
        String date = intent2.getStringExtra("receivedDate");
        final Intent Intent3 = new Intent(getApplicationContext(), InsertActivity.class);
        Intent3.putExtra("authNumber", cash);
        Intent3.putExtra("spot", spot);
        Intent3.putExtra("cardname", cardname);
        Intent3.putExtra("date", date);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.equals(password) && Intent3.getStringExtra("spot") != null){
                    startActivity(Intent3);
                    finish();
                    Intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }else if(text.equals(password)){
                    Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(EnterPasswordActivity.this, "wrong password!", Toast.LENGTH_SHORT).show();
                    mVibe.vibrate(800);
                }
            }
        });
    }
}
