package com.example.home.finalproject;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SingerLayout extends LinearLayout {
    Context mContext;
    LayoutInflater inflater;

    TextView nameTextView;
    TextView spotTextView;
    TextView priceTextView;
    TextView dateTextView;

    public SingerLayout(Context context) {
        super(context);
        mContext = context;

        init();
    }

    public SingerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();
    }

    private void init() {
        inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this, true);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        spotTextView = (TextView) findViewById(R.id.spotTextView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

    }

    public void setNameText(String name) {
        nameTextView.setText(name);
    }

    public void setCompany(String company) {
        spotTextView.setText(company);
    }

    public void setSong(String song) {
        priceTextView.setText(song);
    }

    public void setdate(String date) {
        dateTextView.setText(date);
    }

}