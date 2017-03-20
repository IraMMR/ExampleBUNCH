package com.avdoshka.android.examplebunch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Ирина on 10.03.2017.
 */

public class SyncEditTextFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sync_edittexts, container, false);


        EditText editText1 = (EditText) view.findViewById(R.id.edit_text_1);
        EditText editText2 = (EditText) view.findViewById(R.id.edit_text_2);
        EditText editText3 = (EditText) view.findViewById(R.id.edit_text_3);
        EditText editText4 = (EditText) view.findViewById(R.id.edit_text_4);


        final EditText [] editTexts = new EditText[] {editText1, editText2, editText3, editText4};

        final TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                for (EditText editText : editTexts) {
                    if (!editText.hasFocus()) {
                        editText.setText(s);
                    }
                }
            }
        };



        for (EditText editText : editTexts) {

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    EditText chosenEditText = (EditText)view;
                    if (b) {
                        chosenEditText.addTextChangedListener(textWatcher);
                    }
                    else {
                        chosenEditText.removeTextChangedListener(textWatcher);
                    }
                }
            });
        }



        initToolbar();


        return view;


    }

    private void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar toolbar =  activity.getSupportActionBar();
        toolbar.setTitle(getString(R.string.launch_sync_edittext_button_text));

    }


}
