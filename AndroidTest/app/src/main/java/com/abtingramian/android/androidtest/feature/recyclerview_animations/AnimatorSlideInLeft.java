package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class AnimatorSlideInLeft extends BaseItemAnimator {

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mRemoveAnimations.add(holder);
        ViewCompat.setTranslationZ(holder.itemView, -holder.itemView.getX());
        animation.setDuration(getRemoveDuration())
                .translationX(-holder.itemView.getWidth()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                dispatchRemoveStarting(holder);
            }

            @Override
            public void onAnimationEnd(View view) {
                animation.setListener(null);
                ViewCompat.setTranslationX(view, -holder.itemView.getWidth());
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        this.resetAnimation(holder);
        ViewCompat.setTranslationX(holder.itemView, -holder.itemView.getWidth());
        this.mPendingAdditions.add(holder);
        return true;
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        ViewCompat.setTranslationZ(holder.itemView, holder.getAdapterPosition());
        mAddAnimations.add(holder);
        animation.translationX(0).setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        dispatchAddStarting(holder);
                    }
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationX(view, 0);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        animation.setListener(null);
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

}
