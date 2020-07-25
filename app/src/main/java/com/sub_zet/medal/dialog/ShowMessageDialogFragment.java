package com.sub_zet.medal.dialog;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.R;
import com.sub_zet.medal.databinding.FragmentShowMessageDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMessageDialogFragment extends DialogFragment {

    private FragmentShowMessageDialogBinding binding;

    public ShowMessageDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_message_dialog, container, false);
        showMessage();
        return binding.getRoot();
    }

    private void showMessage(){
        Bundle bundle = getArguments();
        String message = "";
        if (bundle != null) {
            message = bundle.getString("message","");
        }
        binding.dialogMessage.setText(message);
    }

}
