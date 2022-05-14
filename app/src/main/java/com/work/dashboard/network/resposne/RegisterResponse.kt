package com.work.dashboard.network.resposne

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.work.dashboard.network.jsonadapter.NullToEmptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @NullToEmptyString
    val status: String,
    @NullToEmptyString
    val token: String,
) : Parcelable