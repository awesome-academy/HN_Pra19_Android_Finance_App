package com.example.fina.screen.home

import com.example.fina.data.model.CoinStatsAndListCoins
import com.example.fina.data.model.Currency
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.OnResultListener
import com.example.fina.utils.CurrencyParam
import com.example.fina.utils.ExtraParams
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.TimePeriod

class HomePresenter internal constructor(private val mCoinRepository: CoinRepository) :
    HomeContract.Presenter {
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

    fun updateParamsWithCurrency(currency: Currency) {
        this.currentParams.updateReferenceCurrency(currency.uuid)
        count--
        if (count <= 0) {
            getStatsAndCoins()
        }
    }

    fun updateParamsWithTimePeriod(timePeriod: TimePeriod) {
        this.currentParams.updateTimePeriod(timePeriod)
        count--
        if (count <= 0) {
            getStatsAndCoins()
        }
    }

    fun updateOrderProperties(orderProperties: OrderProperties) {
        this.currentOrderProperties = orderProperties
        count--
        if (count <= 0) {
            getStatsAndCoins()
        }
    }

    override fun getCurrencies() {
        val params = CurrencyParam()
        mCoinRepository.getCurrencies(
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

    override fun getStatsAndCoins() {
        mCoinRepository.getCoinStatsAndListCoins(
            currentParams,
            currentOrderProperties,
            object : OnResultListener<CoinStatsAndListCoins> {
                override fun onSuccess(data: CoinStatsAndListCoins) {
                    mView?.onGetCoinsAndStatsSuccess(data)
                }

                override fun onFailure(exception: Exception?) {
                    mView?.onError(exception)
                }
            },
        )
    }
}
