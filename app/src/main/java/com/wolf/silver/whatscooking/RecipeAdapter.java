package com.wolf.silver.whatscooking;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarabesh on 12/23/2017.
 */

public class RecipeAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Recipe> RItems;

    public RecipeAdapter(Activity activity, List<Recipe> Items) {
        this.activity = activity;
        this.RItems = Items;
    }

    @Override
    public int getCount() {
        return RItems.size();
    }

    @Override
    public Object getItem(int i) {
        return RItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.listie, null);

        TextView name=view.findViewById(R.id.nam);


        //Button dia=view.findViewById(R.id.button4);
        final Recipe r=RItems.get(i);

        name.setText(r.getDish());



        return view;
    }

}

