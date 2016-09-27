package com.abtingramian.android.androidtest.feature.alphabet_indexer;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gramian.androidtest.R;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.LocaleData;
import com.ibm.icu.util.ULocale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlphabetIndexer extends LinearLayout implements View.OnClickListener {

    Map<String, Integer> mapIndex;
    @BindView(R.id.list_fruits)
    ListView fruitList;
    @BindView(R.id.side_index)
    LinearLayout indexLayout;

    public AlphabetIndexer(Context context) {
        super(context);
    }

    public AlphabetIndexer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        String[] fruits = getResources().getStringArray(R.array.fruits_array);

        Arrays.asList(fruits);

        fruitList.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, fruits));

        getIndexList(fruits);

        displayIndex();
    }

    private void getIndexList(String[] fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.length; i++) {
            String fruit = fruits[i];
            String index = fruit.substring(0, 1).toUpperCase();
            if (TextUtils.isDigitsOnly(index)) {
                mapIndex.put("#", i);
            } else if (mapIndex.get(index) == null) {
                mapIndex.put(index, i);
            }
        }
    }

    private void displayIndex() {
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        UnicodeSet localAlphabet =  LocaleData.getExemplarSet(ULocale.forLocale(getResources().getConfiguration().locale), 0);
        Iterator<String> it = localAlphabet.iterator();
        while(it.hasNext()) {
            String letter = it.next().toUpperCase();
            if (indexList.contains(letter)) {
                textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.content_side_index_item, null);
                textView.setText(letter);
                textView.setOnClickListener(this);
                indexLayout.addView(textView);
            }
        }
        if (indexList.contains("#")) {
            textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.content_side_index_item, null);
            textView.setText("#");
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        fruitList.setSelection(mapIndex.get(selectedIndex.getText()));
    }

}
