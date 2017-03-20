package com.avdoshka.android.examplebunch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Ирина on 10.03.2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);


        Button syncEditTextButton = (Button) view.findViewById(R.id.launch_sync_edittext_btn);
        syncEditTextButton.setOnClickListener(this);

        Button weightConverterButton = (Button) view.findViewById(R.id.launch_weight_converter);
        weightConverterButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        Fragment fragment = null;
        switch (viewId) {
            case R.id.launch_sync_edittext_btn:
                fragment = new SyncEditTextFragment();
                break;
            case R.id.launch_weight_converter:
                fragment = new WeightConverterFragment();
                break;
        }

        if (fragment == null) return;

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }
}
