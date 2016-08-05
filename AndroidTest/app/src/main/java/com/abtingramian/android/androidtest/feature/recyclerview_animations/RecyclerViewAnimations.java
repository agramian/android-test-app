package com.abtingramian.android.androidtest.feature.recyclerview_animations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.example.gramian.androidtest.R;

public class RecyclerViewAnimations extends AppCompatActivity {

    RecyclerView rvColors;
    RadioGroup rgOperations;
    CheckBox cbPredictive;
    Button button;

    private ColorsAdapter colorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_animations);
        //setupColorsList();
        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorsAdapter.addItemAtPosition(colorsAdapter.getItemCount());
            }
        });*/
    }

    private void setupColorsList() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return cbPredictive.isChecked();
            }
        };
        /*
        rvColors.setLayoutManager(layoutManager);

        colorsAdapter = new ColorsAdapter(this);
        rvColors.setAdapter(colorsAdapter);

        setupRecyclerViewAnimator();*/
    }

    public void onColorsListItemClicked(View view) {
        int itemAdapterPosition = rvColors.getChildAdapterPosition(view);
        if (itemAdapterPosition == RecyclerView.NO_POSITION) {
            return;
        }

        int checkedRadioButton = rgOperations.getCheckedRadioButtonId();
        if (checkedRadioButton == R.id.itemDelete) {
            colorsAdapter.removeItemAtPosition(itemAdapterPosition);
        } else if (checkedRadioButton == R.id.itemChange) {
            colorsAdapter.changeItemAtPosition(itemAdapterPosition);
        }
    }

    private void setupRecyclerViewAnimator() {
        rvColors.setItemAnimator(new AnimatorSlideInLeft());
    }
}
