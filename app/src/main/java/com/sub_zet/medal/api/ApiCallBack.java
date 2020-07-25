package com.sub_zet.medal.api;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallBack<T> implements Callback<ResponseBody> {

    private TypeToken<T> mTypeToken;

    public ApiCallBack(TypeToken<T> mTypeToken) {
        this.mTypeToken = mTypeToken;
    }

    protected abstract void onSuccess(Call<ResponseBody> call, T response);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    String responseString = response.body().string();

                    JsonElement jsonElement = JsonParser.parseString(responseString);
                    onSuccess(call, new Gson()
                            .fromJson(jsonElement, mTypeToken.getType()));
                }

            } else {
                onFailure(response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onFailure(String error) {
        Log.i("erroRR", error);
    }
}
