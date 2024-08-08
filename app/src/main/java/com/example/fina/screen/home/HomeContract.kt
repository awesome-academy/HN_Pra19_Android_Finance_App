package com.example.fina.screen.home

import com.example.fina.data.model.CoinStatsAndListCoins
import com.example.fina.data.model.Currency
import com.example.fina.utils.base.BasePresenter
import java.lang.Exception

class HomeContract {
    interface View {
        fun onGetCoinsAndStatsSuccess(data: CoinStatsAndListCoins)

        fun onGetCurrenciesSuccess(currencies: MutableList<Currency>)

        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getCurrencies()

        fun getStatsAndCoins()
    }
}
