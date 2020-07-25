package com.sub_zet.medal.fragments.menu.help;

import com.sub_zet.medal.api.Resource;
import com.sub_zet.medal.models.HelpModel;
import com.sub_zet.medal.repository.HelpRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel implements Observer<ArrayList<HelpModel>> {

    private MutableLiveData<Resource<ArrayList<HelpModel>>> helpModelLiveDataM = new MutableLiveData<>(Resource.loading(null));
    public final LiveData<Resource<ArrayList<HelpModel>>> helpModelLiveData = helpModelLiveDataM;

    public HelpViewModel(){
        HelpRepository.getInstance().getHelpModel().observeForever(this);
    }

    void getHelpData(){
        HelpRepository.getInstance().getHelpData();
    }

    @Override
    public void onChanged(ArrayList<HelpModel> helpModel) {
        if (helpModel != null){
            helpModelLiveDataM.setValue(Resource.success(helpModel));
        }
    }
}
