package com.sub_zet.medal.repository;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.interfaces.BetPriceClickListener;
import com.sub_zet.medal.models.BetPriceModel;
import com.sub_zet.medal.models.SelectedGameModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class SelectedGameRepository {

    private static SelectedGameRepository instance;
    public static SelectedGameRepository getInstance(){
        if(instance == null)
            instance = new SelectedGameRepository();
        return instance;
    }
    private MutableLiveData<SelectedGameModel> selectedGameModel = new MutableLiveData<>();

    public void getSelectedGameRetrofit(String selectedGameId, String userId) {
        Log.e("selected game id" , selectedGameId + "<<<<<<<<<<<");
        ApiManager.getInstance().getSelectedGameData(selectedGameId, userId, MySavedData.loadLanguage())
                .enqueue(new ApiCallBack<SelectedGameModel>(new TypeToken<SelectedGameModel>(){}) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, SelectedGameModel response) {
                        selectedGameModel.setValue(response);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<SelectedGameModel> getSelectedGameModel() {
        return selectedGameModel;
    }
}