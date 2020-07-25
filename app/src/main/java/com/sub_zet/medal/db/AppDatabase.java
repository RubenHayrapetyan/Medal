package com.sub_zet.medal.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sub_zet.medal.db.type_convertors.PlatformGamesConvertor;
import com.sub_zet.medal.models.GamesModel;
import com.sub_zet.medal.models.PlatformsModel;

@Database(entities = {PlatformsModel.class , GamesModel.class}, version = 1, exportSchema = false)
@TypeConverters({PlatformGamesConvertor.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlatformsDao platformsDao();
    public abstract GamesDao gamesDao();
}
