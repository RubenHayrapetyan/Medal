package com.sub_zet.medal.helpers;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sub_zet.medal.adapters.BetPriceRecyclerAdapter;
import com.sub_zet.medal.adapters.GameRatingRecyclerAdapter;
import com.sub_zet.medal.adapters.GamesListRecyclerAdapter;
import com.sub_zet.medal.adapters.HelpRecyclerAdapter;
import com.sub_zet.medal.adapters.PlatformRecyclerAdapter;
import com.sub_zet.medal.api.ApiClient;
import com.sub_zet.medal.models.BetPriceModel;
import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.HelpModel;
import com.sub_zet.medal.models.PlatformsModel;
import com.sub_zet.medal.models.ProfileGameRatingModel;

import java.util.ArrayList;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapters {
    @BindingAdapter("android:load_image")
    public static void loadImage(ImageView imageView, String iconUrl) {
        Glide.with(imageView.getContext()).load(iconUrl).into(imageView);
    }

    @BindingAdapter("android:platform_models")
    public static void showPlatforms(RecyclerView recyclerView, ArrayList<PlatformsModel> platformsModels) {
        if (recyclerView.getAdapter() != null) {
            PlatformRecyclerAdapter adapter = (PlatformRecyclerAdapter) recyclerView.getAdapter();
            adapter.setData(platformsModels);
        } else {
            PlatformRecyclerAdapter adapter = new PlatformRecyclerAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1,
                    GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setData(platformsModels);
        }
    }

    @BindingAdapter("android:games_model")
    public static void showGames(RecyclerView recyclerView, ArrayList<GamesModel> gamesModel) {
        if (recyclerView.getAdapter() != null) {
            GamesListRecyclerAdapter adapter = (GamesListRecyclerAdapter) recyclerView.getAdapter();
            adapter.setData(gamesModel);
        } else {
            GamesListRecyclerAdapter adapter = new GamesListRecyclerAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setData(gamesModel);
        }
    }

    @BindingAdapter("android:selected_game_bet_price")
    public static void selected_game_bet_price(RecyclerView recyclerView, ArrayList<BetPriceModel> betPriceModels) {
        if (betPriceModels == null)
            betPriceModels = new ArrayList<>();
        if (recyclerView.getAdapter() != null) {
            BetPriceRecyclerAdapter adapter = (BetPriceRecyclerAdapter) recyclerView.getAdapter();
            adapter.setData(betPriceModels);
        } else {
            BetPriceRecyclerAdapter adapter = new BetPriceRecyclerAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setData(betPriceModels);
        }
    }

    @BindingAdapter("android:get_help_data")
    public static void getHelpData(RecyclerView recyclerView, ArrayList<HelpModel> helpModels) {
        if (helpModels == null)
            helpModels = new ArrayList<>();
        if (recyclerView.getAdapter() != null) {
            HelpRecyclerAdapter adapter = (HelpRecyclerAdapter) recyclerView.getAdapter();
            adapter.setData(helpModels);
        } else {
            HelpRecyclerAdapter adapter = new HelpRecyclerAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setData(helpModels);
        }
    }

    @BindingAdapter("android:games_rating")
    public static void showGamesRating(RecyclerView recyclerView, ArrayList<ProfileGameRatingModel> profileGameRatingModels) {
        //   Log.i("profileGameRaTING", profileGameRatingModels.get(0).getGameName() + " ======");
        if (profileGameRatingModels == null)
            return;
        if (recyclerView.getAdapter() != null) {
            GameRatingRecyclerAdapter adapter = (GameRatingRecyclerAdapter) recyclerView.getAdapter();
            adapter.setData(profileGameRatingModels);
        } else {
            GameRatingRecyclerAdapter adapter = new GameRatingRecyclerAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setData(profileGameRatingModels);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("android:my_webview")
    public static void initWebView(WebView webView, String url) {
        if (url != null && url.isEmpty()) {
            return;
        }

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ApiClient.YOUTUBE + url);
    }

}
