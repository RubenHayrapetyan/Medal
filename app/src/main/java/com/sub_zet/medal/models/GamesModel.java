package com.sub_zet.medal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.sub_zet.medal.db.type_convertors.PlatformGamesConvertor;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "games")
//@TypeConverters(PlatformGamesConvertor.class)
public class GamesModel implements Parcelable {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String gameId;
    @SerializedName("url_logo")
    private String gameIconImg;
    @SerializedName("url_video")
    private String gameVideoURL;
    @SerializedName("device")
    private List<String> gamePlatformArray;

    private boolean isSelected;

    @Ignore
    public GamesModel(String gameId, String gameIconImg,
                      ArrayList<String> gamePlatformArray) {
        this.gameId = gameId;
        this.gameIconImg = gameIconImg;
        this.gamePlatformArray = gamePlatformArray;
    }

    @Ignore
    public GamesModel(String gameId, String gameIconImg, String gameVideoURL) {
        this.gameId = gameId;
        this.gameIconImg = gameIconImg;
        this.gameVideoURL = gameVideoURL;
    }

    @Ignore
    protected GamesModel(Parcel in) {
        gameId = in.readString();
        gameIconImg = in.readString();
        gameVideoURL = in.readString();
        gamePlatformArray = in.createStringArrayList();
        isSelected = in.readByte() != 0;
    }

    public GamesModel(){}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameId);
        dest.writeString(gameIconImg);
        dest.writeString(gameVideoURL);
        dest.writeStringList(gamePlatformArray);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GamesModel> CREATOR = new Creator<GamesModel>() {
        @Override
        public GamesModel createFromParcel(Parcel in) {
            return new GamesModel(in);
        }

        @Override
        public GamesModel[] newArray(int size) {
            return new GamesModel[size];
        }
    };

    public String getGameId() {
        return gameId;
    }

    public String getGameIconImg() {
        return gameIconImg;
    }

    public String getGameVideoURL() {
        return gameVideoURL;
    }

    public List<String> getGamePlatformArray() {
        return gamePlatformArray;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameIconImg(String gameIconImg) {
        this.gameIconImg = gameIconImg;
    }

    public void setGameVideoURL(String gameVideoURL) {
        this.gameVideoURL = gameVideoURL;
    }

    public void setGamePlatformArray(List<String> gamePlatformArray) {
        this.gamePlatformArray = gamePlatformArray;
    }
}
