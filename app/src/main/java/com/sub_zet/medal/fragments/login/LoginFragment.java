package com.sub_zet.medal.fragments.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.reflect.TypeToken;
import com.sub_zet.medal.R;
import com.sub_zet.medal.activities.MenuActivity;
import com.sub_zet.medal.activities.WatchTrialVideoActivity;
import com.sub_zet.medal.api.ApiCallBack;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.databinding.FragmentLoginBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.helpers.LanguageChooser;
import com.sub_zet.medal.helpers.MyFragmentTransaction;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.helpers.MyUniqueID;
import com.sub_zet.medal.models.LoginModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import kotlin.Lazy;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static org.koin.java.KoinJavaComponent.inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {

    private String imageURL = "";

    private static final int REQ_CODE = 1698;

    private Activity activity;

    private FragmentLoginBinding binding;

    private GoogleApiClient googleApiClient;

    private Lazy<MyUniqueID> myUniqueIDLazy = inject(MyUniqueID.class);

    private Context context;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        context = getContext();
        activity = getActivity();
        createGoogleSignIn();
        clickListeners();
        getLanguage();
        switchLanguages();
        return binding.getRoot();
    }

    private void getLanguage(){
        // Show english flag selected
        if (MySavedData.loadLanguage().equals("en")){
            binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
            binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
        }
        else{
            // Show russian language flag selected
            binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
            binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
        }
    }

    private void switchLanguages(){
        binding.secondLanguageImg.setOnClickListener(v->{

            // Change second flag from russian -> english
            if (binding.secondLanguageImg.getDrawable().getConstantState() ==
                    ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon).getConstantState()){
                binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
                binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
                MySavedData.saveLanguage("ru");
            }else {
                // Change second flag from english -> russian
                binding.firstLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.english_flag_icon));
                binding.secondLanguageImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.russian_flag_icon));
                MySavedData.saveLanguage("en");
            }

            refreshFragment();
        });
    }

    private void refreshFragment(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    private void clickListeners() {
        binding.googleSignInBtn.setOnClickListener(v -> {

            signIn();
        });
        binding.signInBtn.setOnClickListener(v -> {
            validateSignIn();
        });
        binding.registerBtn.setOnClickListener(v -> {
            MyFragmentTransaction.replaceFragment(getFragmentManager(), new RegisterFragment(), R.id.login_main_container);
        });
    }

    private void createGoogleSignIn() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient!= null && googleApiClient.isConnected()){
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    private boolean validateFields(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            binding.emailEdt.setError("Required");
            valid = false;
        }
        if (password.length() < 8) {
            binding.passwordEdt.setError("Invalid password");
            valid = false;
        }
        return valid;
    }

    private void validateSignIn() {
        String email = binding.emailEdt.getText().toString();
        String password = binding.passwordEdt.getText().toString();
        if (validateFields(email, password)) {
            loginWithFields(password, email);
        }
    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    private void handleResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            if (account != null) {
                String id = account.getId();
                String name = account.getDisplayName();
                String email = account.getEmail();

                try {
                    if (account.getPhotoUrl() != null) {
                        imageURL = account.getPhotoUrl().toString();
                    }
                } catch (NullPointerException ignored) {

                }
                registerUser(id, name, email, imageURL);
            } else {
                Toast.makeText(context, getString(R.string.please_try_with_another_acc), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void goToMenuActivity() {
        startActivity(new Intent(context, MenuActivity.class));
        getActivity().finish();
    }

    private void loginWithFields(String id, String email) {
        ApiManager.getInstance().signInWithFields(email, id, "login")
                .enqueue(new ApiCallBack<LoginModel>(new TypeToken<LoginModel>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, LoginModel response) {
                        if (response != null) {

                            if (response.getStatus().equals("login")) {
                                myUniqueIDLazy.getValue().saveUniqueID(response.getUniqueId());
                                FirebaseMessaging.getInstance().subscribeToTopic(response.getUniqueId());
                                goToMenuActivity();
                                MySavedData.setLoginState(true);
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

    private void registerUser(String id, String name, String email, String image) {
        ApiManager.getInstance().login(id, name, email, image)
                .enqueue(new ApiCallBack<LoginModel>(new TypeToken<LoginModel>() {
                }) {
                    @Override
                    protected void onSuccess(Call<ResponseBody> call, LoginModel response) {
                        if (response != null) {

                            myUniqueIDLazy.getValue().saveUniqueID(response.getUniqueId());
                            FirebaseMessaging.getInstance().subscribeToTopic(response.getUniqueId());

                            if (response.getStatus().equals("registration")) {
                                goYoutubeActivityToWatchTutorial(response.getVideoTutorial());
                            } else {
                                goToMenuActivity();
                            }
                            MySavedData.setLoginState(true);
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

    private void goYoutubeActivityToWatchTutorial(String videoTutorialUrl) {
        Intent intent = new Intent(context, WatchTrialVideoActivity.class);
        intent.putExtra("video_tutorial_url", videoTutorialUrl);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
