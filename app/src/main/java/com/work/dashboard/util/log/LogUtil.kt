package com.work.dashboard.util.log

import android.util.Log
import com.work.dashboard.BuildConfig

object LogUtil {
    fun print(tag: String, text: String) {
        if (BuildConfig.BUILD_TYPE.contentEquals("debug")) {
            Log.i(tag, "PRINT:$text")
        }
    }

    fun printException(e: Exception) {
        e.printStackTrace()
    }

    fun printThrowable(throwable: Throwable?) {
        throwable?.printStackTrace()
    }

    fun printError(tag: String, string: String) {
        if (BuildConfig.BUILD_TYPE.contentEquals("debug")) {
            Log.e(tag, "ERROR:$string")
        }
    }

    fun printDebug(tag: String, string: String) {
        if (BuildConfig.BUILD_TYPE.contentEquals("debug")) {
            Log.d(tag, "DEBUG:$string")
        }
    }
}