package soyouarehere.imwork.speed.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.view.custom.chart.LineChart;

public class CustomViewActivity extends AppCompatActivity {
    LineChart mSimpleLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        mSimpleLineChart = findViewById(R.id.simpleLineChart);

    }
}
