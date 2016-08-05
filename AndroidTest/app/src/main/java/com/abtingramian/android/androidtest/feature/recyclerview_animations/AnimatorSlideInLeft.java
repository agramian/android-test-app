package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.animation.Interpolator;

public class AnimatorSlideInLeft extends SimpleItemAnimator {

    private Interpolator interpolator;

    public AnimatorSlideInLeft() {

    }

    public AnimatorSlideInLeft(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
        return super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationZ(holder.itemView, holder.getPosition());
        holder.itemView.setTranslationX(-holder.itemView.getWidth());
        holder.itemView.animate()
                .translationX(0)
                .setDuration(getAddDuration())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(holder);
                    }
                })
                .start();
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        dispatchMoveFinished(holder);
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        dispatchChangeFinished(newHolder, false);
        return false;
    }

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationZ(holder.itemView, holder.getAdapterPosition() > 0 ? holder.getAdapterPosition() * -1 : -1);
        holder.itemView.animate()
                .translationX(-holder.itemView.getWidth())
                .setDuration(getRemoveDuration())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchRemoveFinished(holder);
                    }
                })
                .start();
        return true;
    }

}
