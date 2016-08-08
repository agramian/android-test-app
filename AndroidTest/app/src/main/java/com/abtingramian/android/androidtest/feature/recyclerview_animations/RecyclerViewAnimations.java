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

public class RecyclerViewAnimations extends LinearLayout {

    private static final int ITEM_WIDTH_HORIZONTAL_DP = 48;
    private static final int ITEM_HEIGHT_VERTICAL_DP = 48;

    private RecyclerView rv;
    private Spinner spinnerAnimation;
    private Spinner spinnerDuration;
    private String animation;
    private int animationDuration;
    private RadioGroup rgItemClickAction;
    private RadioGroup rgViewOrientation;
    private Button buttonAddStart;
    private Button buttonAddEnd;
    private ItemAdapter itemAdapter;
    private LinearLayoutManager layoutManager;

    public RecyclerViewAnimations(Context context) {
        super(context);
    }

    public RecyclerViewAnimations(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rv = (RecyclerView) findViewById(R.id.recyclerViewAnimations);
        spinnerAnimation = (Spinner) findViewById(R.id.animationsSpinner);
        spinnerAnimation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent != null && parent.getItemAtPosition(position) != null) {
                    animation = parent.getItemAtPosition(position).toString();
                    if (animation.equals(getContext().getString(R.string.animation_default))) {
                        rv.setItemAnimator(new DefaultItemAnimator());
                    } else if (animation.equals(getContext().getString(R.string.animation_slide_in_left))) {
                        rv.setItemAnimator(new AnimatorSlideInLeft());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDuration = (Spinner) findViewById(R.id.durationSpinner);
        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent != null && parent.getItemAtPosition(position) != null) {
                    animationDuration = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    rv.getItemAnimator().setAddDuration(animationDuration);
                    rv.getItemAnimator().setRemoveDuration(animationDuration);
                    rv.getItemAnimator().setMoveDuration(animationDuration);
                    rv.getItemAnimator().setChangeDuration(animationDuration);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rgItemClickAction = (RadioGroup) findViewById(R.id.itemClickActionRadioGroup);
        rgViewOrientation = (RadioGroup) findViewById(R.id.orientationRadioGroup);
        rgViewOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                createLayoutManager();
                rv.setLayoutManager(layoutManager);
                itemAdapter.notifyDataSetChanged();
            }
        });
        buttonAddStart = (Button) findViewById(R.id.buttonAddStart);
        buttonAddEnd= (Button) findViewById(R.id.buttonAddEnd);
        itemAdapter = new ItemAdapter(this);
        createLayoutManager();
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(itemAdapter);
        buttonAddStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAdapter.addItemAtPosition(0);
            }
        });
        buttonAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAdapter.addItemAtPosition(itemAdapter.getItemCount());
            }
        });
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

}
