package com.work.dashboard.network.resposne

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.work.dashboard.network.jsonadapter.NullToEmptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    @NullToEmptyString
    val transactionId: String,
    val amount: Double,
    @NullToEmptyString
    val transactionDate: String,
    @NullToEmptyString
    val description: String,
    @NullToEmptyString
    val transactionType: String,
    val receipient: Receipient
) : Parcelable