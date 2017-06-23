package com.example.home.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private Cursor cursor3;
    private Cursor cursor4;
    private DBHelper dbHelper;
    TextView spotView, priceView;

    private static String TAG = "GraphActivity";
    private float[] yData;
    private String[] xData;
    PieChart pieChart;
    Button gagebuBtn, graphBtn, setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        xData = new String[5];
        yData = new float[5];
        dbHelper = new DBHelper(this);

        cursor3 = dbHelper.graphgogo();
        cursor4 = dbHelper.graphprice();
        graph(cursor3);
        price(cursor4);

        spotView = (TextView) findViewById(R.id.textView44);
        priceView = (TextView) findViewById(R.id.textView22);

        Log.d(TAG, "onCreate : starting to create chart");
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.setDescription(null);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);

        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("누적 금액 Top 5(%)");
        pieChart.setCenterTextSize(20);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelect: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String sales = e.toString().substring(pos1 + 3);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                sales = sales.replace(".0", "");
                String employee = xData[pos1];
                //Toast.makeText(GraphActivity.this, employee + "\n" + "금액 : " + sales + "원", Toast.LENGTH_LONG).show();
                spotView.setText(employee);
                priceView.setText(sales + "원");
            }
            @Override
            public void onNothingSelected() {
            }
        });

        gagebuBtn = (Button) findViewById(R.id.button);
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
            }
        });

        setBtn = (Button) findViewById(R.id.button4);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    public String[] graph(Cursor cursor){
        startManagingCursor(cursor);
        cursor.moveToLast();
        int length = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length; i++){
            String a = cursor.getString(cursor.getColumnIndex("spot"));
            xData[i] = a;
            cursor.moveToNext();
        }
        return xData;
    }
    public float[] price(Cursor cursor){
        startManagingCursor(cursor);
        cursor.moveToLast();
        int length2 = cursor.getCount();
        cursor.moveToFirst();
        for(int i = 0; i < length2; i++){
            float b = cursor.getFloat(cursor.getColumnIndex("sum"));
            yData[i] = b;
            cursor.moveToNext();
        }
        return yData;
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i< yData.length; i++){
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for(int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "사용장소 누적순위");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(17);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}