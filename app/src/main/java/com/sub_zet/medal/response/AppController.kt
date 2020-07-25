package com.sub_zet.medal.response

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import androidx.multidex.MultiDex
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.sub_zet.medal.data_injection.appModule
import com.sub_zet.medal.helpers.NetworkChangeReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppController : Application() {
    val CHANNEL_1_ID = "channel1"
    val CHANNEL_2_ID = "channel2"

    val TAG = AppController::class.java.simpleName

    var mNetworkReceiver: BroadcastReceiver? = null

    private var mRequestQueue: RequestQueue? = null
    private var mInstance: AppController? = null

    override fun onCreate() {
        super.onCreate()
        //Fabric.with(this, new Crashlytics());

        MultiDex.install(this)
        mInstance = this
        mNetworkReceiver = NetworkChangeReceiver()
        registerNetworkBroadcastForNougat()
        createNotificationChannels()
        initialize()
    }

//    companion object {
//        lateinit var instance: AppController
//            private set
//    }

    fun registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    private fun initialize() {
        startKoin {
            androidContext(this@AppController)
            modules(listOf(appModule))
        }
    }

        @Synchronized
        fun getInstance(): AppController? {
            return mInstance
        }

    fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }
        return mRequestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()!!.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()!!.add(req)
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is chanel 1"
            val channel2 = NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description = "This is chanel 2"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
    }
}