package com.sub_zet.medal.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    private String gameId;
    private String userId;
    private String userPicture;
    private String userNickname;
    private String betPrice;
    private String rating;
    private String roomId;
    private float balance;

    public UserData() {}

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setBetPrice(String betPrice) {
        this.betPrice = betPrice;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    protected UserData(Parcel in) {
        gameId = in.readString();
        userId = in.readString();
        userPicture = in.readString();
        userNickname = in.readString();
        betPrice = in.readString();
        rating = in.readString();
        roomId = in.readString();
        balance = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameId);
        dest.writeString(userId);
        dest.writeString(userPicture);
        dest.writeString(userNickname);
        dest.writeString(betPrice);
        dest.writeString(rating);
        dest.writeString(roomId);
        dest.writeFloat(balance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}


