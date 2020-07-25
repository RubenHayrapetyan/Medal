package com.sub_zet.medal.repository;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.models.ProfileModel;

import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ProfileRepository {
    private static ProfileRepository instance;

    public static ProfileRepository getInstance() {
        if (instance == null)
            instance = new ProfileRepository();
        return instance;
    }

    private final MutableLiveData<ProfileModel> profileModel = new MutableLiveData<>();

    public void getProfileData(String userId) {
        ApiManager.getInstance().getProfileData(userId)
                .enqueue(new ApiCallBack<ProfileModel>(new TypeToken<ProfileModel>(){}) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, ProfileModel response) {
                        Log.i("gamesRatingStatus", "Mtav");
                        Log.i("gamesRatingStatus", response.getGamesRatingArray().get(0).getGameName());
                        Log.i("gamesRatingStatus", response.getImageURL() + " = image");
                        profileModel.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public MutableLiveData<ProfileModel> getProfileModel() {
        return profileModel;
    }
}
