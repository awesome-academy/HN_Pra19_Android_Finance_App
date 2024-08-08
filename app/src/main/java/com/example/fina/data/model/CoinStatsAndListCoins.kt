package com.example.fina.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinStatsAndListCoins(
    var stats: CoinStats = CoinStats(),
    var coins: List<Coin> = emptyList(),
) : Parcelable
