package com.sk.metofficeweather.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.sk.metofficeweather.database.MyAppDbHelper

/*
 * Created by Sambhaji Karad on 08-3-2019
*/

class MyApplicationClass : Application() {

    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()

        MyApplicationClass.appContext = applicationContext!!
        MyAppDbHelper(applicationContext!!)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }
        })
    }
}
