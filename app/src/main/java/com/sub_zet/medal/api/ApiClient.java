package com.sub_zet.medal.api;

import com.google.android.datatransport.Event;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiClient {

    String MAIN_URL = "https://medal.fun/";

    String YANDEX_MONEY = "form/yandexMoney.php";

    String YOUTUBE = "https://www.youtube.com/embed/";

    String PATH_USER_MOBILE = "user_mobile/";
    String PATH_FORM = "form/";

    String PROFILE = "user_profile.php";
    String GAME_LIST = "games.php";
    String SEND_USER_DATA = "game_end.php";
    String GAME_COMPLETE_RESULT = "game_complete_result.php";
    String GAME_RESULT_IMAGE = "send_game_screen.php";
    String SELECTED_GAME_DATA = "choosen_game.php";
    String PLATFORM_LIST = "platform.php";
    String HELP = "help.php";
    String YANDEX = "user_id_form.php";
    String BALANCE = "balance.php";
    String WITHDRAW_MONEY = "withdraw_money.php";
    String LOGIN = "login.php";
    String SIGN_IN_WITH_FIELDS = "login_with_fields.php";
    String BLOCK_STATUS = "block_status.php";
    String UPDATE_BACKEND = "winner_decided_from_menu.php";

    String KEY_GAME_ID = "game_id";
    String KEY_USER_WALLET_NUMBER = "user_wallet_number";
    String KEY_WANTED_AMOUNT = "wanted_amount";
    String KEY_USER_ID = "user_id";
    String KEY_LANG = "lang";
    String KEY_PLAYER1_ID = "player1_id";
    String KEY_PLAYER2_ID = "player2_id";
    String KEY_ROOM_ID = "room_id";
    String KEY_BET_PRICE = "bet_price";
    String KEY_NICK_NAME = "nick_name";
    String KEY_GAME_STATUS = "game_status";
    String KEY_GAME_COMPLAINT = "game_complaint";
    String KEY_NAME = "name";
    String KEY_EMAIL = "email";
    String KEY_IMAGE = "image";
    String KEY_REGISTRATION_STATUS = "status";


    @POST(PATH_USER_MOBILE + UPDATE_BACKEND)
    Call<ResponseBody> updateBackend();

    @Multipart
    @POST(PATH_USER_MOBILE + GAME_RESULT_IMAGE)
    Call<ResponseBody> sendResultImage(
            @Part(KEY_PLAYER1_ID) RequestBody userId,
            @Part(KEY_ROOM_ID) RequestBody roomId,
            @Part() MultipartBody.Part gameScreen
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + "decision_status.php")
    Call<ResponseBody> getOpponentDecision(
            @Field(KEY_GAME_ID) String gameId,
            @Field(KEY_PLAYER1_ID) String player1Id,
            @Field(KEY_PLAYER2_ID) String player2Id,
            @Field(KEY_ROOM_ID) String roomId,
            @Field(KEY_BET_PRICE) String betPrice,
            @Field(KEY_NICK_NAME) String nickName,
            @Field(KEY_GAME_STATUS) String gameStatus,
            @Field(KEY_GAME_COMPLAINT) String gameComplaint
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + SEND_USER_DATA)
    Call<ResponseBody> sendUserDataResponseModel(
            @Field(KEY_GAME_ID) String gameId,
            @Field(KEY_PLAYER1_ID) String player1Id,
            @Field(KEY_PLAYER2_ID) String player2Id,
            @Field(KEY_ROOM_ID) String roomId,
            @Field(KEY_BET_PRICE) String betPrice,
            @Field(KEY_NICK_NAME) String nickName
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + SIGN_IN_WITH_FIELDS)
    Call<ResponseBody> signInWithFields(
            @Field(KEY_EMAIL) String email,
            @Field(KEY_USER_ID) String password,
            @Field(KEY_REGISTRATION_STATUS) String signInStatus
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + SIGN_IN_WITH_FIELDS)
    Call<ResponseBody> registerWithFields(
            @Field(KEY_USER_ID) String id,
            @Field(KEY_EMAIL) String email,
            @Field(KEY_NAME) String name,
            @Field(KEY_REGISTRATION_STATUS) String signInStatus
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + LOGIN)
    Call<ResponseBody> login(
            @Field(KEY_USER_ID) String id,
            @Field(KEY_NAME) String name,
            @Field(KEY_EMAIL) String email,
            @Field(KEY_IMAGE) String userPicture
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + BALANCE)
    Call<ResponseBody> getBalance(
            @Field(KEY_USER_ID) String id
    );

    @FormUrlEncoded
    @POST(PATH_FORM + YANDEX)
    Call<ResponseBody> sendUserId(
            @Field(KEY_USER_ID) String id
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + PROFILE)
    Call<ResponseBody> getProfileData(
            @Field(KEY_USER_ID) String id
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + WITHDRAW_MONEY)
    Call<ResponseBody> cashOut(
            @Field(KEY_USER_ID) String id,
            @Field(KEY_USER_WALLET_NUMBER) String userWalletNumber,
            @Field(KEY_WANTED_AMOUNT) String wantedAmount
    );

    @Multipart
    @POST(PATH_USER_MOBILE + GAME_COMPLETE_RESULT)
    Call<ResponseBody> completeResultWithScreen(
            @Part(KEY_GAME_ID) RequestBody gameId,
            @Part(KEY_PLAYER1_ID) RequestBody player1Id,
            @Part(KEY_PLAYER2_ID) RequestBody player2Id,
            @Part(KEY_ROOM_ID) RequestBody roomId,
            @Part(KEY_BET_PRICE) RequestBody betPrice,
            @Part(KEY_NICK_NAME) RequestBody nickName,
            @Part(KEY_GAME_STATUS) RequestBody gameStatus,
            @Part() MultipartBody.Part gameScreen,
            @Part(KEY_GAME_COMPLAINT) RequestBody gameComplaint
    );
    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + GAME_COMPLETE_RESULT)
    Call<ResponseBody> completeResultWithoutScreen(
            @Field(KEY_GAME_ID) String gameId,
            @Field(KEY_PLAYER1_ID) String player1Id,
            @Field(KEY_PLAYER2_ID) String player2Id,
            @Field(KEY_ROOM_ID) String roomId,
            @Field(KEY_BET_PRICE) String betPrice,
            @Field(KEY_NICK_NAME) String nickName,
            @Field(KEY_GAME_STATUS) String gameStatus,
            @Field(KEY_GAME_COMPLAINT) String gameComplaint
    );


//    @FormUrlEncoded
//    @POST(PATH_USER_MOBILE + GAME_COMPLETE_RESULT)
//    Call<ResponseBody> completeResultWithScreen(
//            @Field(KEY_GAME_ID) String gameId,
//            @Field(KEY_PLAYER1_ID) String player1Id,
//            @Field(KEY_PLAYER2_ID) String player2Id,
//            @Field(KEY_ROOM_ID) String roomId,
//            @Field(KEY_BET_PRICE) String betPrice,
//            @Field(KEY_NICK_NAME) String nickName,
//            @Field(KEY_GAME_STATUS) String gameStatus,
//            @Field(KEY_GAME_SCREEN) String gameScreen,
//            @Field(KEY_GAME_COMPLAINT) String gameComplaint
//    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + SELECTED_GAME_DATA)
    Call<ResponseBody> getSelectedGameData(
            @Field(KEY_GAME_ID) String gameID,
            @Field(KEY_USER_ID) String userID,
            @Field(KEY_LANG) String language
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + BLOCK_STATUS)
    Call<ResponseBody> getUserBlockStatus(
            @Field(KEY_USER_ID) String userID
    );

    @FormUrlEncoded
    @POST(PATH_USER_MOBILE + HELP)
    Call<ResponseBody> getHelp(
            @Field(KEY_LANG) String language
    );

    @GET(PATH_USER_MOBILE + PLATFORM_LIST)
    Call<ResponseBody> getPlatforms();

    @GET(PATH_USER_MOBILE + GAME_LIST)
    Call<ResponseBody> getGames();
}
