package com.work.dashboard.network.request

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.work.dashboard.util.constants.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RegisterRequest(
    val username: String,
    val password: String = EMPTY_STRING
) : Parcelable