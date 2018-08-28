package soyouarehere.imwork.speed.view.sliding_card;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import soyouarehere.imwork.speed.R;

/**
 * behavior 实现滑动
 */
public class SlidingCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_card);
        SlidingCardLayout slidingCardLayout1 = findViewById(R.id.view_sliding_card1);
        SlidingCardLayout view_sliding_card2 = findViewById(R.id.view_sliding_card2);
        SlidingCardLayout view_sliding_card3 = findViewById(R.id.view_sliding_card3);
        SlidingCardLayout view_sliding_card4 = findViewById(R.id.view_sliding_card4);

    }
}
