package com.example.chainreactiongameapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.Objects;

public class AddPlayerFragment extends DialogFragment {

    private Spinner colorChooseSpinner;
    private EditText editTextName;
    private int[] colorsImages = new int[]{R.color.black,R.color.blue,R.color.red,R.color.yellow};
    private String[] colorNames = new String[] {"Black","Blue","Red","Yellow"};

    public AddPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_player, container, false);
        editTextName = view.findViewById(R.id.EnterNameEditText);
        colorChooseSpinner = view.findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),colorsImages,colorNames);
        colorChooseSpinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}