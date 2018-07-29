package soyouarehere.imwork.speed.app.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import soyouarehere.imwork.speed.R;

public class NetErrorView extends LinearLayout {

    ImageView imgError;
    TextView tvMessage;

    private NetErrorListener netErrorListener;

    public NetErrorView(Context context) {
        this(context, null);
        initView(context);
    }

    public NetErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public NetErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View netView = layoutInflater.inflate(R.layout.base_layout_net_error, this);
        imgError = netView.findViewById(R.id.img_error);
        tvMessage = netView.findViewById(R.id.tv_message);
        imgError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (netErrorListener != null) {
                    netErrorListener.netError();
                }
            }
        });
    }

    public void setMessage(String message) {
        if (tvMessage != null) {
            tvMessage.setText(message);
        }
    }

    public void setImgrBackGround(int res) {
        if (imgError != null) {
            imgError.setImageResource(res);
        }
    }

    public void setNetErrorListener(NetErrorListener netErrorListener) {
        this.netErrorListener = netErrorListener;
    }

    public interface NetErrorListener {
        void netError();
    }
}
