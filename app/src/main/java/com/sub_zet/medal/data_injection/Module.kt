package com.sub_zet.medal.data_injection

import android.content.Context
import androidx.room.Room
import com.sub_zet.medal.db.AppDatabase
import com.sub_zet.medal.helpers.MyUniqueID
import com.sub_zet.medal.helpers.SocketIOInstance
import com.sub_zet.medal.response.AppController
import org.koin.dsl.module

val appModule = module {
    single {
        MyUniqueID(get())
    }
    single {provideRoom(get())}
    single {SocketIOInstance()}
    single {AppController().getInstance()}
}

fun provideRoom(context: Context) : AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "medal_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
}