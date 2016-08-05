package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gramian.androidtest.R;

import java.util.ArrayList;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

    private RecyclerViewAnimations mainActivity;
    private ArrayList<Integer> colors = new ArrayList<>();

    public ColorsAdapter(RecyclerViewAnimations mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onColorsListItemClicked(v);
            }
        });
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        int color = colors.get(position);
        holder.itemView.setBackgroundColor(color);
        holder.tvColor.setText("#" + Integer.toHexString(color));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public void removeItemAtPosition(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    public void addItemAtPosition(int position) {
        colors.add(position, ColorsHelper.getRandomColor());
        notifyItemInserted(position);
    }

    public void changeItemAtPosition(int position) {
        colors.set(position, ColorsHelper.getRandomColor());
        notifyItemChanged(position);
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder {

        TextView tvColor;

        public ColorViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
        }
    }
}
