package com.sub_zet.medal.db.type_convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PlatformGamesConvertor {
    @TypeConverter
    public String fromGamePlatformArray(List<String> gamesList) {
        if (gamesList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(gamesList, type);
        return json;
    }

    @TypeConverter
    public  List<String> toGamePlatformArray(String gamesListString) {
        if (gamesListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> countryLangList = gson.fromJson(gamesListString, type);
        return countryLangList;
    }
}
