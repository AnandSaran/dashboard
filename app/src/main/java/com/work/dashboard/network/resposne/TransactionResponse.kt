package com.work.dashboard.network.resposne

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.work.dashboard.network.jsonadapter.NullToEmptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TransactionResponse(
    @NullToEmptyString
    val status: String,
    val data: List<Data>
) : Parcelable