package com.sub_zet.medal.api;

import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiManager {

    private static final String BASE_URL = "https://medal.fun/";

    private static ApiClient sApiClient;

    public static ApiClient getInstance() {
        if (sApiClient == null)
            sApiClient = createApiClient();
        return sApiClient;
    }

    @NonNull
    private static ApiClient createApiClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .client(createClient())
                .build()
                .create(ApiClient.class);
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
               // .addInterceptor(chain -> chain.proceed(chain.request().newBuilder().build()));
        return okHttpClient.build();
    }
}
