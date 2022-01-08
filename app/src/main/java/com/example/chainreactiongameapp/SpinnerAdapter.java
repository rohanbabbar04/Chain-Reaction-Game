package com.example.chainreactiongameapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private int[] colorImages;
    private String[] colorNames;
    private ImageView colorImage;
    private TextView colorText;

    public SpinnerAdapter(Context context,int[] colorImages,String[] colorNames) {
        this.context = context;
        this.colorImages = colorImages;
        this.colorNames = colorNames;
    }
    @Override
    public int getCount() {
        return colorImages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.spinner_choose_color,viewGroup,false);
        colorImage = view.findViewById(R.id.colorImageSpinner);
        colorText = view.findViewById(R.id.colorTextSpinner);
        colorImage.setImageResource(colorImages[i]);
        colorText.setText(colorNames[i]);
        return view;
    }
}
