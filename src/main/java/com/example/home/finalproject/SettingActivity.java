package com.example.home.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/**
 * Created by home on 2017-06-11.
 */
public class SettingActivity extends AppCompatActivity{

    private DBHelper dbHelper;
    Button gagebuBtn, graphBtn, setBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dbHelper = new DBHelper(this);
        gagebuBtn = (Button) findViewById(R.id.button1);
        gagebuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gagebuintent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(gagebuintent);
                finish();
            }
        });
        graphBtn = (Button) findViewById(R.id.button2);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        setBtn = (Button) findViewById(R.id.button4);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void passwordClick(View v){
        new AlertDialog.Builder(this).
                setTitle("비밀번호 변경").
                setMessage("현재 비밀번호를 바꾸시겠습니까?").
                setPositiveButton("확인", t).
                setNegativeButton("취소", t).
                show();
    }
    DialogInterface.OnClickListener t = new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface di, int w){
            if(DialogInterface.BUTTON_POSITIVE == w){
                Intent intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
                startActivity(intent);
                finish();
            }
            if(DialogInterface.BUTTON_NEGATIVE == w){

            }
        }
    };
    public void clearClick(View v){
        new AlertDialog.Builder(this).
                setTitle("DB 초기화").
                setMessage("현재 저장된 목록을 초기화하시겠습니까?").
                setPositiveButton("확인", a).
                setNegativeButton("취소", a).
                show();
    }
    DialogInterface.OnClickListener a = new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface di, int w){
            if(DialogInterface.BUTTON_POSITIVE == w){
                dbHelper.drop();
                dbHelper.drop2();
                Intent intent2 = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent2);
                finish();
            }
            if(DialogInterface.BUTTON_NEGATIVE == w){

            }
        }
    };
}