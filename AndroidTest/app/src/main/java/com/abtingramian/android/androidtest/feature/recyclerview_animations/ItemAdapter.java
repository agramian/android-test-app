package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Random;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private RecyclerViewAnimations mainActivity;
    private ArrayList<Integer> colors = new ArrayList<>();
    private int itemWidth;
    private int itemHeight;

    public ItemAdapter(RecyclerViewAnimations mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        final ViewHolder colorViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onItemClicked(v);
            }
        });
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int color = colors.get(position);
        holder.itemView.setLayoutParams(new FrameLayout.LayoutParams(itemWidth, itemHeight));
        holder.itemView.setBackgroundColor(color);
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
        colors.add(position, getRandomColor());
        notifyItemInserted(position);
    }

    public void changeItemAtPosition(int position) {
        colors.set(position, getRandomColor());
        notifyItemChanged(position);
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
