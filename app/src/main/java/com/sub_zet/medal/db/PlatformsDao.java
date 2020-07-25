package com.sub_zet.medal.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.sub_zet.medal.db.type_convertors.PlatformGamesConvertor;
import com.sub_zet.medal.models.PlatformsModel;

import java.util.ArrayList;
import java.util.List;

@Dao
//@TypeConverters({PlatformGamesConvertor.class})
public interface PlatformsDao {

    @Query("SELECT * FROM platforms")
    public List<PlatformsModel> getAllPlatforms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addPlatform(PlatformsModel platformsModels);

    public static void addAllPlatforms(PlatformsDao platformsDao , ArrayList<PlatformsModel> platformsModels){
        for (PlatformsModel p : platformsModels)
        {
            platformsDao.addPlatform(p);
        }
    }
}
