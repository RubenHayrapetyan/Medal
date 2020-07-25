package com.sub_zet.medal.fragments.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import kotlin.Lazy;
import okhttp3.ResponseBody;
import retrofit2.Call;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.R;
import com.sub_zet.medal.activities.WatchTrialVideoActivity;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.databinding.FragmentRegisterBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.helpers.MyFragmentTransaction;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.helpers.MyUniqueID;
import com.sub_zet.medal.models.LoginModel;

import static org.koin.java.KoinJavaComponent.inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment {

    private FragmentRegisterBinding binding;
    private Context context;
    private Lazy<MyUniqueID> myUniqueIDLazy = inject(MyUniqueID.class);

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        context = getContext();

        clickLisetener();

        return binding.getRoot();
    }

    private void goYoutubeActivityToWatchTutorial(String videoTutorialUrl) {
        Intent intent = new Intent(context, WatchTrialVideoActivity.class);
        intent.putExtra("video_tutorial_url", videoTutorialUrl);
        startActivity(intent);
        getActivity().finish();
    }

    private void registerWithFields(String id, String email, String name) {
        ApiManager.getInstance().registerWithFields(id, email, name, "registration")
                .enqueue(new ApiCallBack<LoginModel>(new TypeToken<LoginModel>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, LoginModel response) {
                        if (response != null) {
                            if (response.getStatus().equals("registration")) {
                                myUniqueIDLazy.getValue().saveUniqueID(response.getUniqueId());
                                FirebaseMessaging.getInstance().subscribeToTopic(response.getUniqueId());

                                MySavedData.setLoginState(true);
                                goYoutubeActivityToWatchTutorial(response.getVideoTutorial());
                            } else {
                                Toast.makeText(context, response.getStatus(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(context, getString(R.string.server_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void clickLisetener() {
        binding.returnImg.setOnClickListener(v -> {
            MyFragmentTransaction.replaceFragment(getFragmentManager(), new LoginFragment(), R.id.login_main_container);
        });
        binding.registerBtn.setOnClickListener(v -> {

            String name = binding.userNameEdt.getText().toString();
            String email = binding.userEmailEdt.getText().toString();
            String password = binding.userPasswordEdt.getText().toString();

            if (validateFields(name, email, password)){

                registerWithFields(password, email, name);
            }
        });
    }

    private boolean validateFields(String name, String email, String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(name)){
            binding.userNameEdt.setError("Is empty");
            valid = false;
        }
        else if (TextUtils.isEmpty(email) || !(email.contains("@")) || !(email.contains(".")) || email.length() < 8){
            binding.userEmailEdt.setError("Invalid email");
            valid = false;
        }
        else if (TextUtils.isEmpty(password) || password.length() < 8){
            binding.userPasswordEdt.setError("Is empty");
            valid = false;
        }
        return valid;
    }

}
