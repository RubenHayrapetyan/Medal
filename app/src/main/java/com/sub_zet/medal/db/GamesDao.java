package com.sub_zet.medal.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sub_zet.medal.models.GamesModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GamesDao {
    @Query("SELECT * FROM games")
    public List<GamesModel> getAllGames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addGame(GamesModel platformsModels);

    public static void addAllGames(GamesDao gamesDao , ArrayList<GamesModel> gameModels){
        for (GamesModel g : gameModels) {
            gamesDao.addGame(g);
        }
    }
}
