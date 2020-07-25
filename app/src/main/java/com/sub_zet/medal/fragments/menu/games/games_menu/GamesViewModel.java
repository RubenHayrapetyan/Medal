package com.sub_zet.medal.fragments.menu.games.games_menu;

import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.PlatformsModel;
import com.sub_zet.medal.repository.GamesRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class GamesViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(false);
    public LiveData<ArrayList<PlatformsModel>> platformsModelLiveData;
    public LiveData<ArrayList<GamesModel>> gamesModelLiveData;

    public GamesViewModel(){
        getPlatforms();
        getGames();
    }

    private void getPlatforms(){
        if(platformsModelLiveData == null)
          loadingLiveData.setValue(true);
        platformsModelLiveData = Transformations.map(GamesRepository.getInstance().getPlatformsRetrofit() , list -> {
            if(list.size() > 0)
              loadingLiveData.setValue(false);
           return list;
        });
    }

    private void getGames(){
        gamesModelLiveData = Transformations.map(GamesRepository.getInstance().getGames(), gamesList -> gamesList);
    }

    void sortGames(PlatformsModel platformModel){
        GamesRepository.getInstance().sortGamesByPlatform(platformModel.getPlatformName());
    }
}
