package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static final int NUM_AVATAR_THRESHOLD = 4;

    private RecyclerViewAnimations mainActivity;
    private ArrayList<Integer> colors = new ArrayList<>();
    private int itemWidth;
    private int itemHeight;

    public ItemAdapter(RecyclerViewAnimations mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
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
        holder.itemView.setBackgroundColor(position != NUM_AVATAR_THRESHOLD - 1 || getTotalCount() <= NUM_AVATAR_THRESHOLD ? color : Color.parseColor("#FF0000"));
        ((TextView) holder.itemView).setText(position != NUM_AVATAR_THRESHOLD - 1 || getTotalCount() <= NUM_AVATAR_THRESHOLD ? "" : "+" + String.valueOf(getTotalCount() - NUM_AVATAR_THRESHOLD));
    }

    @Override
    public int getItemCount() {
        return Math.min(colors.size(), NUM_AVATAR_THRESHOLD);
    }

    public int getTotalCount() {
        return colors.size();
    }

    public void removeItemAtPosition(int position) {
        if (getItemCount() >= NUM_AVATAR_THRESHOLD) {
            notifyItemChanged(NUM_AVATAR_THRESHOLD - 1);
        }
        colors.remove(position);
        notifyItemRemoved(position);
    }

    public void addItemAtPosition(int position) {
        int color = getRandomColor();
        colors.add(position, color);
        if (getTotalCount() <= NUM_AVATAR_THRESHOLD) {
            notifyItemInserted(position);
        } else if (position >= NUM_AVATAR_THRESHOLD){
            notifyItemChanged(NUM_AVATAR_THRESHOLD - 1);
        } else {
            notifyItemRemoved(NUM_AVATAR_THRESHOLD - 1);
            notifyItemInserted(position);
            notifyItemChanged(NUM_AVATAR_THRESHOLD - 1);
        }
    }

    public void changeItemAtPosition(int position) {
        int color = getRandomColor();
        colors.set(position, color);
        notifyItemChanged(position);
    }

    public void moveItems(int startPosition, int count, int offset) {
        for (int i = startPosition; i < count; i++) {
            notifyItemMoved(startPosition, startPosition + offset);
        }
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
