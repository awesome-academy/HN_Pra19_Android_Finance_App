package com.example.fina.screen.home

import com.example.fina.data.model.Coin
import com.example.fina.data.model.CoinStats
import com.example.fina.data.model.Currency
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.CurrencyParam
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.TimePeriod

class HomePresenter internal constructor(private val mCoinRepository: CoinRepository?) : HomeContract.Presenter {
    private var mView: HomeContract.View? = null
    private var currentParams: ExtraParams = ExtraParams()
    private var currentOrderProperties: OrderProperties = OrderProperties()

    private var count: Int = 3

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: HomeContract.View?) {
        this.mView = view
    }

    override fun getCoins() {
        mCoinRepository?.getCoins(
            currentParams,
            currentOrderProperties,
            object : OnResultListener<List<Coin>> {
                override fun onSuccess(data: List<Coin>) {
                    println("currentParams $currentParams")
                    println("orderProperties $currentOrderProperties")
                    mView?.onGetCoinsSuccess(data.toMutableList())
                }

                override fun onFailure(exception: Exception?) {
                    mView?.onError(exception)
                }
            },
        )
    }

    fun updateParamsWithCurrency(currency: Currency) {
        this.currentParams.updateReferenceCurrency(currency.uuid)
        count--
        if (count <= 0) {
            getCoins()
        }
    }

    fun updateParamsWithTimePeriod(timePeriod: TimePeriod) {
        this.currentParams.updateTimePeriod(timePeriod)
        count--
        if (count <= 0) {
            getCoins()
        }
    }

    fun updateOrderProperties(orderProperties: OrderProperties) {
        this.currentOrderProperties = orderProperties
        count--
        if (count <= 0) {
            getCoins()
        }
    }

    override fun getCoinStats() {
        mCoinRepository?.getCoinStats(
            "yhjMzLPhuIDl",
            object : OnResultListener<CoinStats> {
                override fun onSuccess(data: CoinStats) {
                    mView?.onGetCoinStatsSuccess(data)
                }

                override fun onFailure(exception: Exception?) {
                    mView?.onError(exception)
                }
            },
        )
    }

    override fun getCurrencies() {
        val params = CurrencyParam()
        mCoinRepository?.getCurrencies(
            params,
            object : OnResultListener<List<Currency>> {
                override fun onSuccess(data: List<Currency>) {
                    mView?.onGetCurrenciesSuccess(data.toMutableList())
                }

                override fun onFailure(exception: Exception?) {
                    mView?.onError(exception)
                }
            },
        )
    }
}
