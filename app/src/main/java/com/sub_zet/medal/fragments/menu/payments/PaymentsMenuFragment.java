package com.sub_zet.medal.fragments.menu.payments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import kotlin.Lazy;
import okhttp3.ResponseBody;
import retrofit2.Call;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.R;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiClient;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.databinding.FragmentPaymentsMenuBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.helpers.LanguageChooser;
import com.sub_zet.medal.helpers.MyBalanceChecker;
import com.sub_zet.medal.helpers.MyUniqueID;
import com.sub_zet.medal.models.GetResultResponseModel;
import com.sub_zet.medal.models.StatusResponseModel;

import static org.koin.java.KoinJavaComponent.bind;
import static org.koin.java.KoinJavaComponent.inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentsMenuFragment extends BaseFragment {

    private FragmentPaymentsMenuBinding binding;
    private PaymentsViewModel paymentsViewModel;
    private Lazy<MyUniqueID> myUniqueIDLazy = inject(MyUniqueID.class);
    private Context context;

    private Dialog cashOutDialog;

    public PaymentsMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payments_menu, container, false);
        cashOutDialog = new Dialog(getContext());
        getBalance();
        clickListeners();
        context = getContext();
        Log.i("menuCreating", "Payments");
        return binding.getRoot();
    }

    private void clickListeners() {
        binding.cashInBtn.setOnClickListener(v -> {
            Log.i("userID", myUniqueIDLazy.getValue().loadUniqueId() + "");
            sendUserId(myUniqueIDLazy.getValue().loadUniqueId());
            //  paymentsViewModel.sendUserId(myUniqueIDLazy.getValue().loadUniqueId());
        });

        binding.cashOutBtn.setOnClickListener(v -> {
            cashOutDialog();
        });
    }

    private void cashOutDialog() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_cash_withdrawal, null);

        TextInputEditText valletEdt = layout.findViewById(R.id.vallet_number_text_Input_Edt);
        TextInputEditText amountEdt = layout.findViewById(R.id.amount_text_input_edt);

        layout.findViewById(R.id.cash_out_confirm_btn).setOnClickListener(v -> {
            String vallet = valletEdt.getText().toString();
            String amount = amountEdt.getText().toString();
            float userBalance = Float.parseFloat(paymentsViewModel.paymentModelLiveData.getValue().getData().getBalance());
            Log.i("sdasdas", userBalance + "");
            if (inputsAreEmpty(amount, vallet)) {
                Toast.makeText(context, getString(R.string.input_is_empty), Toast.LENGTH_SHORT).show();
            } else if (!(MyBalanceChecker.canUserCashOut(userBalance, Float.parseFloat(amount)))) {
                Log.i("amount", amount + "");
                Toast.makeText(context, getString(R.string.chosen_amount),
                        Toast.LENGTH_LONG).show();
            } else {
                cashOut(vallet, amount);
            }

        });

        cashOutDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cashOutDialog.setContentView(layout);
        cashOutDialog.setCancelable(true);
        cashOutDialog.show();
    }

    private void getBalance() {
        paymentsViewModel = ViewModelProviders.of(this).get(PaymentsViewModel.class);
        binding.setLifecycleOwner(this);
        paymentsViewModel.getPaymentData(myUniqueIDLazy.getValue().loadUniqueId());
        paymentsViewModel.paymentModelLiveData.observe(this, paymentModelResource -> {
            switch (paymentModelResource.getStatus()) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (paymentModelResource.getData() != null) {
                        Log.i("mtavvVV", paymentModelResource.getData().getBalance());
                        //   binding.userBalanceTxt.setText(paymentModelResource.getData().getBalance());
                        binding.setModel(paymentModelResource.getData());
                    }
                    binding.loadingPb.setVisibility(View.GONE);
                    break;
                case ERROR:
                    Log.i("responseError", "Error from server data");
                    break;
            }
        });
    }

    private boolean inputsAreEmpty(String sum, String walletNumber) {
        return sum.isEmpty() || walletNumber.isEmpty();
    }

    private void cashOut(String walletNumber, String sum) {
        ApiManager.getInstance().cashOut(myUniqueIDLazy.getValue().loadUniqueId(), walletNumber, sum)
                .enqueue(new ApiCallBack<GetResultResponseModel>(new TypeToken<GetResultResponseModel>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, GetResultResponseModel response) {
                        if (response != null) {
                            if (response.getStatus()) {
                                cashOutDialog.dismiss();
                                paymentsViewModel.getPaymentData(myUniqueIDLazy.getValue().loadUniqueId());
                                Toast.makeText(context, getString(R.string.your_transfer_will_be),
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void sendUserId(String userId) {
        binding.loadingPopolnitPb.setVisibility(View.VISIBLE);
        ApiManager.getInstance().sendUserId(userId)
                .enqueue(new ApiCallBack<StatusResponseModel>(new TypeToken<StatusResponseModel>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, StatusResponseModel response) {

                        if (response.getStatus().equals("true")) {

                            initYandexMoneyWebView();
                            binding.cashInBtn.setVisibility(View.GONE);
                            binding.cashOutBtn.setVisibility(View.GONE);
                            binding.loadingPopolnitPb.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initYandexMoneyWebView() {
        binding.yandexMoneyWebview.setVisibility(View.VISIBLE);
        binding.yandexMoneyWebview.getSettings().setJavaScriptEnabled(true);
        binding.yandexMoneyWebview.setWebViewClient(new WebViewClient()); // senc chi gnum browser
        binding.yandexMoneyWebview.loadUrl(ApiClient.MAIN_URL.concat(ApiClient.YANDEX_MONEY));
    }
}