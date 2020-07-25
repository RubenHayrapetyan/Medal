package com.sub_zet.medal.fragments.menu.games.selected_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import kotlin.Lazy;

import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sub_zet.medal.R;
import com.sub_zet.medal.adapters.BetPriceRecyclerAdapter;
import com.sub_zet.medal.api.Resource;
import com.sub_zet.medal.databinding.FragmentSelectedGameBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.helpers.LanguageChooser;
import com.sub_zet.medal.helpers.MyAnimation;
import com.sub_zet.medal.helpers.MyBalanceChecker;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.helpers.MyUniqueID;
import com.sub_zet.medal.interfaces.BetPriceClickListener;
import com.sub_zet.medal.models.BetPriceModel;
import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.SelectedGameModel;
import com.sub_zet.medal.models.UserData;

import static org.koin.java.KoinJavaComponent.inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedGameFragment extends BaseFragment implements BetPriceClickListener {

    private FragmentSelectedGameBinding binding;
    private Context context;

    private MyAnimation createAnimation;

    private final static String MODEL_KEY = "games_model";
    private GamesModel gameModel;

    private Lazy<MyUniqueID> myUniqueIDLazy = inject(MyUniqueID.class);

    private SelectedGameViewModel selectedGameViewModel;

    public SelectedGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameModel = getArguments().getParcelable(MODEL_KEY);
        Log.i("gameIDD", gameModel.getGameId());
    }

    public static Bundle getBundle(GamesModel model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL_KEY, model);
        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_game, container, false);
        selectedGameViewModel = ViewModelProviders.of(this).get(SelectedGameViewModel.class);
        selectedGameViewModel.gamesModelLiveData.observe(this,
                (Resource<SelectedGameModel> selectedGameModelResource) -> {
            switch (selectedGameModelResource.getStatus()) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (selectedGameModelResource.getData() != null) {
                        binding.setViewModel(selectedGameViewModel);
                        setBetPriceClickListener();
                        binding.searchBtn.setEnabled(true);
                    }
                    if (!selectedGameModelResource.getData().getSelectedGameBetPriceArray().isEmpty())
                        showView(selectedGameModelResource.getData().getSelectedGameBetPriceArray().get(0).getPrice());
                    break;
                case ERROR:
                    Log.i("responseError", "Error from server data");
                    break;
            }
        });
        binding.setLifecycleOwner(this);

        selectedGameViewModel.getData(gameModel.getGameId(), myUniqueIDLazy.getValue().loadUniqueId());
        context = getContext();
        createAnimation = new MyAnimation();

        makeDescriptionScrollable();

        clickListeners();
        getUserGameNickname();
        return binding.getRoot();
    }

    private void getUserGameNickname(){
        binding.setUserNickname(MySavedData.getNickName());
        Log.i("getUserNickname", MySavedData.getNickName());
    }

    private void saveUserGameNickname(String nickname){
        MySavedData.saveGameNickname(nickname);
    }

    private void makeDescriptionScrollable() {
        binding.selectedGameDescriptionTxt.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setBetPriceClickListener() {
        BetPriceRecyclerAdapter betPriceAdapter = (BetPriceRecyclerAdapter) binding.betPriceRecycler.getAdapter();
        if (betPriceAdapter != null) {
            betPriceAdapter.setListener(this);
        }
    }

    private boolean isNicknameFieldEmpty(String nickname) {
        return nickname.isEmpty();
    }

    private void clickListeners() {
        binding.searchBtn.setOnClickListener(v -> {

            float userBalance = Float.parseFloat(selectedGameViewModel.gamesModelLiveData.getValue()
                    .getData().getBalance());
            float choosenBalance = Float.parseFloat(binding.betPriceBtn.getText().toString()
                    .replace("\u0584", ""));
            if (MyBalanceChecker.checkBalance(userBalance, choosenBalance)) {
                String userNickname = binding.userNicknameEdt.getText().toString();
                if (!isNicknameFieldEmpty(userNickname)) {
                    saveUserGameNickname(userNickname);
                } else {
                    Toast.makeText(context, R.string.nickname_cannot_be_empty_text, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, getString(R.string.not_enough_cash), Toast.LENGTH_LONG).show();
            }
        });
        binding.betPriceBtn.setOnClickListener(v -> {
            openAndCloseBetPrices();
        });
    }

    private void openAndCloseBetPrices() {
        if (binding.betPriceRecycler.getVisibility() == View.GONE) {
            binding.toggleArrowImg.setImageResource(R.drawable.toggle_arrow_opened_icon);

            createAnimation.myAnimation(context, R.anim.rotate_from_down_to_up, binding.toggleArrowImg);
            createAnimation.myAnimation(context, R.anim.from_up_to_down, binding.betPriceRecycler);

            binding.betPriceRecycler.setVisibility(View.VISIBLE);

        } else {
            binding.toggleArrowImg.setImageResource(R.drawable.toggle_arrow_closed_icon);

            createAnimation.myAnimation(getContext(), R.anim.rotate_from_up_to_down, binding.toggleArrowImg);
            createAnimation.myAnimation(context, R.anim.from_down_to_up, binding.betPriceRecycler);

            new Handler().postDelayed(() -> {
                binding.betPriceRecycler.setVisibility(View.GONE);
            }, 300);
        }
    }

    private void showView(String price) {
        binding.betPriceBtn.setText(price.concat("\u0584"));
        binding.loadingPb.setVisibility(View.GONE);
        binding.searchFormConstraint.setVisibility(View.VISIBLE);
    }

    @Override
    public void priceClickListener(BetPriceModel priceItem) {
        binding.betPriceBtn.setText(priceItem.getPrice() + "\u0584");
        openAndCloseBetPrices();
    }
}