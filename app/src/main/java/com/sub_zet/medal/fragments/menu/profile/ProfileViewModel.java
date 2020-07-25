package com.sub_zet.medal.fragments.menu.profile;

import com.sub_zet.medal.models.ProfileModel;
import com.sub_zet.medal.repository.ProfileRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel implements Observer<ProfileModel> {

    private MutableLiveData<ProfileModel> profileModelLiveDataM = new MutableLiveData<>();
    public final LiveData<ProfileModel> profileModelLiveData = profileModelLiveDataM;

    public ProfileViewModel() {
        ProfileRepository.getInstance().getProfileModel().observeForever(this);
    }

    void getData(String userId) {
        ProfileRepository.getInstance().getProfileData(userId);
    }

    @Override
    public void onChanged(ProfileModel profileModel) {
        if (profileModel != null) {
            profileModelLiveDataM.setValue(profileModel);
        }
    }
}