package com.sub_zet.medal.fragments.menu.games.selected_game;

import com.sub_zet.medal.api.Resource;
import com.sub_zet.medal.models.SelectedGameModel;
import com.sub_zet.medal.repository.SelectedGameRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class SelectedGameViewModel  extends ViewModel implements Observer<SelectedGameModel> {
    public MutableLiveData<Boolean> showVideo = new MutableLiveData<>(false);
    private   MutableLiveData<Resource<SelectedGameModel>> gamesModelLiveDataM = new MutableLiveData<>(Resource.loading(null));
    public final LiveData<Resource<SelectedGameModel>> gamesModelLiveData = gamesModelLiveDataM;

    public SelectedGameViewModel(){
        SelectedGameRepository.getInstance().getSelectedGameModel().observeForever(this);
    }

    void getData(String gameID, String userID){
        SelectedGameRepository.getInstance().getSelectedGameRetrofit(gameID, userID);
    }

    @Override
    public void onChanged(SelectedGameModel selectedGameModel) {
        if (selectedGameModel != null){
            gamesModelLiveDataM.setValue(Resource.success(selectedGameModel));
        }
    }
}
