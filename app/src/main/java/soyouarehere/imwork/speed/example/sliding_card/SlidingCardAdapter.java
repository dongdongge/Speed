package soyouarehere.imwork.speed.example.sliding_card;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import soyouarehere.imwork.speed.R;

public class SlidingCardAdapter extends RecyclerView.Adapter<SlidingCardAdapter.SlidingCardHolder> {

    public static class SlidingCardHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public SlidingCardHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.view_tv_cardview);
        }
    }

    private final String[] ITEMS = {"小法", "狗头", "光辉", "蛮王", "火女", "狗头", "光辉", "蛮王", "火女", "狗头", "光辉", "蛮王", "火女", "狗头", "光辉", "蛮王", "火女"};

    private final LayoutInflater mLayoutInflater;

    @Override
    public SlidingCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = mLayoutInflater.inflate(R.layout.view_list_item_layout,parent,false);
        return new SlidingCardHolder(view);
    }

    public SlidingCardAdapter(RecyclerView view){
        mLayoutInflater = LayoutInflater.from(view.getContext());
        view.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                final int position = parent.getChildViewHolder(view).getPosition();

                final int offset = parent.getResources().getDimensionPixelOffset(R.dimen.y14);
                outRect.set(offset,
                        position == 0?offset:0,
                        offset,
                        offset);
            }
        });


    }

    @Override
    public void onBindViewHolder(SlidingCardHolder holder, int position) {
        holder.textView.setText(ITEMS[position]);
    }

    @Override
    public int getItemCount() {
        return ITEMS.length;
    }
}
