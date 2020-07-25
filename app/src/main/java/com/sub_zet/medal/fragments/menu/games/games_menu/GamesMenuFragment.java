package com.sub_zet.medal.fragments.menu.games.games_menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sub_zet.medal.R;
import com.sub_zet.medal.adapters.GamesListRecyclerAdapter;
import com.sub_zet.medal.adapters.PlatformRecyclerAdapter;
import com.sub_zet.medal.api.ApiManager;
import com.sub_zet.medal.databinding.FragmentGamesMenuBinding;
import com.sub_zet.medal.fragments.base_fragment.BaseFragment;
import com.sub_zet.medal.fragments.menu.games.selected_game.SelectedGameFragment;
import com.sub_zet.medal.helpers.LanguageChooser;
import com.sub_zet.medal.helpers.MySavedData;
import com.sub_zet.medal.interfaces.GamesClickListener;
import com.sub_zet.medal.interfaces.PlatformsClickListener;
import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.PlatformsModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamesMenuFragment extends BaseFragment implements PlatformsClickListener, GamesClickListener {

    private FragmentGamesMenuBinding binding;
    private Context context;

    private PlatformRecyclerAdapter platformsAdapter;
    private GamesListRecyclerAdapter gamesAdapter;

    private GamesViewModel gamesViewModel;
    public GamesMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LanguageChooser.changeLanguage(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_games_menu, container, false);
        context = getContext();
        initPlatformsRecyclers();
        initGamesRecycler();
        binding.setLifecycleOwner(this);
        binding.setGetPlatforms(gamesViewModel);
        binding.setGetGames(gamesViewModel);
        updateBackend();
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel.class);
    }

    private void initPlatformsRecyclers() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4,
                GridLayoutManager.VERTICAL, false);
        binding.platformRecycler.setLayoutManager(gridLayoutManager);
        platformsAdapter = new PlatformRecyclerAdapter();
        binding.platformRecycler.setAdapter(platformsAdapter);
        platformsAdapter.setListener(this);
    }

    private void initGamesRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3,
                GridLayoutManager.VERTICAL, false);
        binding.gamesRecycler.setLayoutManager(gridLayoutManager);
        gamesAdapter = new GamesListRecyclerAdapter();
        binding.gamesRecycler.setAdapter(gamesAdapter);
        gamesAdapter.setListener(this);
        binding.loadingPb.setVisibility(View.GONE);
    }

    private void updateBackend(){
        ApiManager.getInstance().updateBackend().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onPlatformItemClick(PlatformsModel item) {
        gamesViewModel.sortGames(item);
        MySavedData.saveChoosenPlatform(item.getPlatformName());
        platformsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGameItemClick(GamesModel item) {
        gamesAdapter.notifyDataSetChanged();
        String gameId = item.getGameId();

        Bundle bundle = new Bundle();
        bundle.putString("game_id", gameId);
        GamesMenuFragment fragment = new GamesMenuFragment();
        fragment.setArguments(bundle);

        Navigation.findNavController(getActivity(), R.id.gamesMenuFragment)
                .navigate(R.id.action_gamesMenuFragment_to_selectedGameFragment,
                        SelectedGameFragment.getBundle(item));
    }
}