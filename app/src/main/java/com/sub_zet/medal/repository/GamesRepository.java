package com.sub_zet.medal.repository;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.db.AppDatabase;
import com.sub_zet.medal.db.PlatformsDao;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.PlatformsModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import kotlin.Lazy;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static org.koin.java.KoinJavaComponent.inject;

public class GamesRepository {

    private static GamesRepository instance;
    private Lazy<AppDatabase> appDatabase = inject(AppDatabase.class);
    private MutableLiveData<ArrayList<PlatformsModel>> platformModels = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<ArrayList<GamesModel>> filteredGamesModels = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<ArrayList<GamesModel>> gamesModels = new MutableLiveData<>(new ArrayList<>());

    private GamesRepository() {}

    public static GamesRepository getInstance() {
        if (instance == null)
            instance = new GamesRepository();
        return instance;
    }

    public MutableLiveData<ArrayList<PlatformsModel>> getPlatformsRetrofit() {
        ArrayList<PlatformsModel> platformsModels = new ArrayList<>(appDatabase.getValue().platformsDao().getAllPlatforms());
        platformModels.setValue(platformsModels);
        ApiManager.getInstance().getPlatforms()
                .enqueue(new ApiCallBack<ArrayList<PlatformsModel>>(new TypeToken<ArrayList<PlatformsModel>>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, ArrayList<PlatformsModel> response) {
                        platformModels.setValue(response);
                        sortGamesByPlatform(MySavedData.getChoosenPlatform());
                        PlatformsDao.addAllPlatforms(appDatabase.getValue().platformsDao() , response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
        return platformModels;
    }

    public MutableLiveData<ArrayList<GamesModel>> getGames() {
        ArrayList<GamesModel> gameModles = new ArrayList<>(appDatabase.getValue().gamesDao().getAllGames());
        filteredGamesModels.setValue(gameModles);
        gamesModels.setValue(gameModles);
        ApiManager.getInstance().getGames()
                .enqueue(new ApiCallBack<ArrayList<GamesModel>>(new TypeToken<ArrayList<GamesModel>>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, ArrayList<GamesModel> response) {
                        gamesModels.setValue(response);
                      //  GamesDao.addAllGames(appDatabase.getValue().gamesDao(), response);
                      //  filteredGamesModels.setValue(response);
                        Log.i("responSSE", "response = " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("responSSE", "Fail = " + t.getMessage());
                    }
                });
        return filteredGamesModels;
    }

    public void sortGamesByPlatform(String platformName) {
        Log.i("platformName", platformName);
        ArrayList<GamesModel> sortedGames = new ArrayList<>();
        ArrayList<GamesModel> gamesArray = gamesModels.getValue();
        if(gamesArray == null)
            return;

        for (int i = 0; i < gamesArray.size(); i++) {
            if (gamesArray.get(i).getGamePlatformArray().contains(platformName)) {

                String id = gamesArray.get(i).getGameId();
                String iconURL = gamesArray.get(i).getGameIconImg();
                String videoURL = gamesArray.get(i).getGameVideoURL();

                sortedGames.add(new GamesModel(id, iconURL, videoURL));
                Log.i("thatGame", gamesArray.get(i).getGameId());
            }
        }
        filteredGamesModels.setValue(sortedGames);
    }
}
