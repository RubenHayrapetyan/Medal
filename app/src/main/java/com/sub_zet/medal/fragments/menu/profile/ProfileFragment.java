package com.sub_zet.medal.fragments.menu.profile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.R;
import com.sub_zet.medal.databinding.FragmentProfileBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.helpers.LanguageChooser;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.helpers.MyUniqueID;
import com.sub_zet.medal.models.ProfileModel;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import kotlin.Lazy;

import static org.koin.java.KoinJavaComponent.inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    private Lazy<MyUniqueID> myUniqueIDLazy = inject(MyUniqueID.class);
    private Activity activity;
    private ProfileViewModel profileViewModel;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.profileModelLiveData.observe(this, new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
               if (profileModel != null)
                   binding.setProfileViewModel(profileViewModel);
            }
        });
        binding.setLifecycleOwner(this);
        profileViewModel.getData(myUniqueIDLazy.getValue().loadUniqueId());

        activity = getActivity();
        getLanguage();
        switchLanguages();
        Log.i("menuCreating", "Profile");
        return binding.getRoot();
    }

    private void getLanguage(){
             // Show english flag is selected
        if (MySavedData.loadLanguage().equals("en")){
            binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
            binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
        } else{
            // Show russian language is flag selected
            binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
            binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
        }
    }

    private void switchLanguages(){
        binding.secondLanguageImg.setOnClickListener(v->{

            // Change second flag from russian -> english
            if (binding.secondLanguageImg.getDrawable().getConstantState() ==
                    ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon).getConstantState()){
                binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
                binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
                MySavedData.saveLanguage("ru");
            }else {
                // Change second flag from english -> russian
                binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
                binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
                MySavedData.saveLanguage("en");
            }

            refreshFragment();
        });
    }

    private void refreshFragment(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
