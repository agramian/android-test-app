package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.gramian.androidtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import static butterknife.OnItemSelected.Callback.NOTHING_SELECTED;

public class RecyclerViewAnimations extends LinearLayout {

    private static final int ITEM_WIDTH_HORIZONTAL_DP = 48;
    private static final int ITEM_HEIGHT_VERTICAL_DP = 48;

    @BindView(R.id.recyclerViewAnimations)
    RecyclerView rv;
    @BindView(R.id.animationsSpinner)
    Spinner spinnerAnimation;
    @BindView(R.id.durationSpinner)
    Spinner spinnerDuration;
    String animation;
    int animationDuration;
    @BindView(R.id.itemClickActionRadioGroup)
    RadioGroup rgItemClickAction;
    @BindView(R.id.orientationRadioGroup)
    RadioGroup rgViewOrientation;
    @BindView(R.id.buttonAddStart)
    Button buttonAddStart;
    @BindView(R.id.buttonAddEnd)
    Button buttonAddEnd;
    ItemAdapter itemAdapter;
    LinearLayoutManager layoutManager;

    public RecyclerViewAnimations(Context context) {
        super(context);
    }

    public RecyclerViewAnimations(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        rgViewOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                createLayoutManager();
                rv.setLayoutManager(layoutManager);
                itemAdapter.notifyDataSetChanged();
            }
        });
        itemAdapter = new ItemAdapter(this);
        createLayoutManager();
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(itemAdapter);
    }

    public void onItemClicked(View view) {
        int itemAdapterPosition = rv.getChildAdapterPosition(view);
        if (itemAdapterPosition == RecyclerView.NO_POSITION) {
            return;
        }
        int checkedRadioButton = rgItemClickAction.getCheckedRadioButtonId();
        if (checkedRadioButton == R.id.itemDelete) {
            itemAdapter.removeItemAtPosition(itemAdapterPosition);
        } else if (checkedRadioButton == R.id.itemChange) {
            itemAdapter.changeItemAtPosition(itemAdapterPosition);
        }
    }

    private void createLayoutManager() {
        layoutManager = new LinearLayoutManager(getContext(),
                rgViewOrientation.getCheckedRadioButtonId() == R.id.orientationHorizontal
                        ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL,
                false) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
        itemAdapter.setItemWidth(rgViewOrientation.getCheckedRadioButtonId() == R.id.orientationHorizontal
                ? dpToPx(ITEM_WIDTH_HORIZONTAL_DP)
                : FrameLayout.LayoutParams.WRAP_CONTENT);
        itemAdapter.setItemHeight(rgViewOrientation.getCheckedRadioButtonId() == R.id.orientationHorizontal
                ? FrameLayout.LayoutParams.MATCH_PARENT
                : dpToPx(ITEM_HEIGHT_VERTICAL_DP));
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @OnItemSelected(R.id.animationsSpinner)
    void spinnerAnimationItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent != null && parent.getItemAtPosition(position) != null) {
            animation = parent.getItemAtPosition(position).toString();
            if (animation.equals(getContext().getString(R.string.animation_default))) {
                rv.setItemAnimator(new DefaultItemAnimator());
            } else if (animation.equals(getContext().getString(R.string.animation_slide_in_left))) {
                rv.setItemAnimator(new AnimatorSlideInLeft());
            }
        }
    }

    @OnItemSelected(value = R.id.animationsSpinner, callback = NOTHING_SELECTED)
    void spinnerAnimationNothingSelected() {
    }

    @OnItemSelected(R.id.durationSpinner)
    void spinnerDurationItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent != null && parent.getItemAtPosition(position) != null) {
            animationDuration = Integer.parseInt(parent.getItemAtPosition(position).toString());
            rv.getItemAnimator().setAddDuration(animationDuration);
            rv.getItemAnimator().setRemoveDuration(animationDuration);
            rv.getItemAnimator().setMoveDuration(animationDuration);
            rv.getItemAnimator().setChangeDuration(animationDuration);
        }
    }

    @OnClick(R.id.buttonAddStart)
    void addStart() {
        itemAdapter.addItemAtPosition(0);
    }

    @OnClick(R.id.buttonAddEnd)
    void addEnd() {
        itemAdapter.addItemAtPosition(itemAdapter.getItemCount());
    }

}
