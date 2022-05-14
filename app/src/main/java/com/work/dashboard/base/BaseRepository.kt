package com.work.dashboard.base

import android.util.Log
import com.work.dashboard.util.constants.EMPTY_STRING
import com.work.dashboard.util.log.LogUtil
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): BaseResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("BaseRepository", "getResult: body is null ${body == null}")
                if (body != null) return BaseResult.success(body)
            }
            return error(generateError(response.errorBody()))
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun generateError(responseErrorBody: ResponseBody?): String {
        val errorBody: String? = responseErrorBody?.string()
        var error = EMPTY_STRING
        if (errorBody != null) {
            val bodyObj = JSONObject(errorBody.toString())
            error = bodyObj.optString("error")
        }
        return error
    }

    private fun <T> error(message: String): BaseResult<T> {
        LogUtil.printError("BaseDataSource", message)
        return BaseResult.error(message)
    }
}