package com.example.chainreactiongameapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlayerFragment extends DialogFragment {

    private Spinner colorChooseSpinner;
    private EditText editTextName;
    private Button saveButton;
    private Integer[] colorsImages = new Integer[]{R.color.black,R.color.blue,R.color.red,R.color.yellow};
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
        saveButton = view.findViewById(R.id.saveButton);
        Bundle bundle = getArguments();
        assert bundle != null;
        String updateName = bundle.getString("updateName");
        int updateID = bundle.getInt("updateColorID");
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),colorsImages,colorNames);
        colorChooseSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (updateName!=null) {
            editTextName.setText(new SpannableString(updateName));
            for (int i=0;i<colorsImages.length;i++) {
                if (colorsImages[i] == updateID) {
                    colorChooseSpinner.setSelection(i);
                }
            }
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getDialog()).dismiss();
                String name = "";
                Log.d("message", editTextName.getText().toString());
                if (editTextName.getText() != null) {
                    name = editTextName.getText().toString();
                }
                int colorID = colorChooseSpinner.getSelectedItemPosition();
                PlayFriends friends = (PlayFriends) getActivity();
                assert friends != null;
                Player player = new Player(name, colorNames[colorID], colorsImages[colorID]);
                if (updateName != null) {
                    int currentPos = bundle.getInt("currentPos");
                    Log.d("currentPos",String.valueOf(currentPos));
                    friends.updateItem(currentPos, player);

                } else {
                    friends.addItems(player);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }
}