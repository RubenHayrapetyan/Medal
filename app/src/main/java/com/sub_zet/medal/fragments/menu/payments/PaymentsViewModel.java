package com.sub_zet.medal.fragments.menu.payments;

import android.util.Log;

import com.sub_zet.medal.api.Resource;
import com.sub_zet.medal.models.BetPriceModel;
import com.sub_zet.medal.models.PaymentModel;
import com.sub_zet.medal.repository.PaymentsRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class PaymentsViewModel extends ViewModel implements Observer<PaymentModel>{

    private MutableLiveData<Resource<PaymentModel>> paymentModelLiveDataM = new MutableLiveData<>(Resource.loading(null));
    final LiveData<Resource<PaymentModel>> paymentModelLiveData = paymentModelLiveDataM; // <<< info lsox

    public PaymentsViewModel(){
        PaymentsRepository.getInstance().getPaymentModel().observeForever(this);
    }
    // mer tvyalnery update anelu hamara/ datan vercnelu hamara
    void getPaymentData(String userId){
        PaymentsRepository.getInstance().getUserBalance(userId);
    }

    @Override
    public void onChanged(PaymentModel paymentModel) {
        if (paymentModel != null){
            Log.i("myViewModel", paymentModel.getBalance());
            paymentModelLiveDataM.setValue(Resource.success(paymentModel));
        }
    }
}