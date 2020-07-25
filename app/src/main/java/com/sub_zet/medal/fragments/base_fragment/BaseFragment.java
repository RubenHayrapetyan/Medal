package com.sub_zet.medal.fragments.base_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.R;
import com.sub_zet.medal.helpers.LanguageChooser;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());

        return inflater.inflate(R.layout.fragment_base, container, false);
    }
}
