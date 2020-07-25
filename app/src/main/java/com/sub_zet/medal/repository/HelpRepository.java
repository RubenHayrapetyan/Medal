package com.sub_zet.medal.repository;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.models.HelpModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class HelpRepository {

    private static HelpRepository instance;
    public static HelpRepository getInstance(){
        if(instance == null)
            instance = new HelpRepository();
        return instance;
    }

    private MutableLiveData<ArrayList<HelpModel>> helpModel = new MutableLiveData<>();

    public void getHelpData() {
        Log.i("loadLang", MySavedData.loadLanguage());
        ApiManager.getInstance().getHelp(MySavedData.loadLanguage())
                .enqueue(new ApiCallBack<ArrayList<HelpModel>>(new TypeToken<ArrayList<HelpModel>>() {}) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, ArrayList<HelpModel> response) {
                          helpModel.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public MutableLiveData<ArrayList<HelpModel>> getHelpModel() {
        return helpModel;
    }
}
