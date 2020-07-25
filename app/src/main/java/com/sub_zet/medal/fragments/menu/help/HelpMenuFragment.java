package com.sub_zet.medal.fragments.menu.help;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.R;
import com.sub_zet.medal.databinding.FragmentHelpMenuBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpMenuFragment extends BaseFragment {

    private FragmentHelpMenuBinding binding;

    public HelpMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_menu, container, false);

        HelpViewModel helpViewModel = ViewModelProviders.of(this).get(HelpViewModel.class);
        helpViewModel.helpModelLiveData.observe(this, helpModelResource -> {
            switch (helpModelResource.getStatus()) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (helpModelResource.getData() != null) {
                        binding.setValues(helpModelResource.getData());
                    }
                    break;
                case ERROR:
                    Log.i("responseError", "Error from server data");
                    break;
            }
        });
        binding.setLifecycleOwner(this);
        helpViewModel.getHelpData();
        Log.i("menuCreating", "Help");

        return binding.getRoot();
    }
}
