package com.example.fina.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fina.R
import com.example.fina.data.model.Coin
import com.example.fina.data.model.CoinStats
import com.example.fina.data.model.Currency
import com.example.fina.data.repository.CoinRepository
import com.example.fina.data.repository.source.local.CoinLocalDataSource
import com.example.fina.data.repository.source.local.SharedPreferencesHelper
import com.example.fina.data.repository.source.remote.CoinRemoteDataSource
import com.example.fina.databinding.FragmentHomeBinding
import com.example.fina.screen.home.adapter.CoinsAdapter
import com.example.fina.screen.home.adapter.CurrencyAdapter
import com.example.fina.screen.home.adapter.OrderByAdapter
import com.example.fina.screen.home.adapter.TimePeriodAdapter
import com.example.fina.utils.Constant
import com.example.fina.utils.CurrencySymbol
import com.example.fina.utils.OnItemRecyclerViewClickListener
import com.example.fina.utils.OrderBy
import com.example.fina.utils.OrderDirection
import com.example.fina.utils.OrderProperties
import com.example.fina.utils.TimePeriod
import com.example.fina.utils.base.BaseFragment
import com.example.fina.utils.ext.round
import com.example.fina.utils.ext.roundOneDecimal
import java.lang.Exception

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(),
    HomeContract.View,
    OnItemRecyclerViewClickListener<Coin> {
    private lateinit var mHomePresenter: HomePresenter
    private val mCoinAdapter: CoinsAdapter by lazy { CoinsAdapter() }
    private val mCurrencyAdapter: CurrencyAdapter by lazy {
        CurrencyAdapter(requireContext(), arrayListOf())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initView() {
        viewBinding.recyclerViewCoin.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCoinAdapter
        }
        mCoinAdapter.registerItemRecyclerViewClickListener(this)
        setupSpinners()
        setupBottomNavigation()
    }

    override fun initData() {
        mHomePresenter =
            HomePresenter(
                CoinRepository.getInstance(
                    CoinRemoteDataSource.getInstance(),
                    CoinLocalDataSource.getInstance(
                        SharedPreferencesHelper.getInstance(requireContext()),
                    ),
                ),
            )
        mHomePresenter.setView(this)
        mHomePresenter.getCurrencies()
        mHomePresenter.getCoins()
        mHomePresenter.getCoinStats()
    }

    override fun onGetCoinsSuccess(coins: MutableList<Coin>) {
        mCoinAdapter.updateData(coins)
        viewBinding.currencySymbolVol24hValue.text = CurrencySymbol.symbol
        viewBinding.currencySymbolTotalMarketCapValue.text = CurrencySymbol.symbol
    }

    override fun onGetCurrenciesSuccess(currencies: MutableList<Currency>) {
        val listCurrencies = ArrayList(currencies)
        mCurrencyAdapter.updateData(listCurrencies)
    }

    override fun onGetCoinStatsSuccess(coinStats: CoinStats) {
        viewBinding.tvTotalCoinsValue.text = coinStats.totalCoins.round()
        viewBinding.tvTotalMarketplaceValue.text = coinStats.totalMarkets.round()
        viewBinding.tvTotalMarketCapValue.text = coinStats.totalMarketCap.toDouble().roundOneDecimal()
        viewBinding.tvVol24hValue.text = coinStats.total24hVolume.toDouble().roundOneDecimal()
    }

    private fun setupSpinners() {
        viewBinding.spinnerCurrency.adapter = mCurrencyAdapter
        viewBinding.spinnerCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long,
                ) {
                    val selectedCurrency = p0?.getItemAtPosition(p2) as Currency
                    if (selectedCurrency.sign != null) {
                        CurrencySymbol.symbol = selectedCurrency.sign.toString()
                    } else {
                        CurrencySymbol.symbol = selectedCurrency.symbol
                    }

                    mHomePresenter.updateParamsWithCurrency(selectedCurrency)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        val listTimePeriod = TimePeriod.values().toList()
        val timePeriodAdapter = TimePeriodAdapter(requireContext(), ArrayList(listTimePeriod))
        viewBinding.spinnerTimePeriod.adapter = timePeriodAdapter
        val defaultTimePeriodPosition = TimePeriod.values().indexOf(TimePeriod.TWENTY_FOUR_HOURS)
        viewBinding.spinnerTimePeriod.setSelection(defaultTimePeriodPosition)
        viewBinding.spinnerTimePeriod.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long,
                ) {
                    val selectedTimePeriod = p0?.getItemAtPosition(p2) as TimePeriod
                    Toast.makeText(
                        requireContext(),
                        "Selected Time Period: ${context?.getString(selectedTimePeriod.getNameResId())}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    mHomePresenter.updateParamsWithTimePeriod(selectedTimePeriod)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        val listOrder = OrderBy.values().toList()
        val orderByAdapter = OrderByAdapter(requireContext(), ArrayList(listOrder))
        viewBinding.spinnerOrderBy.adapter = orderByAdapter
        val defaultOrderByPosition = OrderBy.values().indexOf(OrderBy.MARKET_CAP)
        viewBinding.spinnerOrderBy.setSelection(defaultOrderByPosition)
        viewBinding.spinnerOrderBy.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long,
                ) {
                    val selectedOrderBy = p0?.getItemAtPosition(p2) as OrderBy
                    Toast.makeText(
                        requireContext(),
                        "Selected Order By: ${context?.getString(selectedOrderBy.getNameResId())}",
                        Toast.LENGTH_SHORT,
                    ).show()

                    val newOrderProperties =
                        OrderProperties(
                            orderBy = selectedOrderBy,
                            orderDirection = OrderDirection.DESC,
                            limit = Constant.DEFAULT_LIMIT,
                            offset = 0,
                        )
                    mHomePresenter.updateOrderProperties(newOrderProperties)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: Coin?) {
        if (item != null) {
            val selectedTimePeriod = viewBinding.spinnerTimePeriod.selectedItem as? TimePeriod
            val bundle =
                Bundle().apply {
                    putParcelable("COIN", item)
                    putSerializable("TIME_PERIOD", selectedTimePeriod)
                }
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.layoutContainer, DetailFragment.newInstance(item, selectedTimePeriod))
//                .addToBackStack(null)
//                .commit()
        } else {
            Toast.makeText(requireContext(), "Selected item is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        viewBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.favourite -> {
                    Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search -> {
                    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
