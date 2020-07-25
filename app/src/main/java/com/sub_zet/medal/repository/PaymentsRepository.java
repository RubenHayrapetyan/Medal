package com.sub_zet.medal.repository;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.models.PaymentModel;

import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PaymentsRepository {
    private static PaymentsRepository instance;
    public static PaymentsRepository getInstance(){
        if(instance == null)
            instance = new PaymentsRepository();
        return instance;
    }

    private MutableLiveData<PaymentModel> paymentModel = new MutableLiveData<>();

    public void getUserBalance(String userId) {
        ApiManager.getInstance().getBalance(userId)
                .enqueue(new ApiCallBack<PaymentModel>(new TypeToken<PaymentModel>() {}) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, PaymentModel response) {
                        Log.i("resPonse", response.getBalance());
                        paymentModel.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public MutableLiveData<PaymentModel> getPaymentModel() {
        return paymentModel;
    }
}
