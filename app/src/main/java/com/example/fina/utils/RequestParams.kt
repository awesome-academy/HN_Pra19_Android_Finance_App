package com.example.fina.utils

class ExtraParams(
    private var referenceCurrencyUuid: String = Constant.USD_UUID,
    private var timePeriod: TimePeriod = TimePeriod.TWENTY_FOUR_HOURS,
) {
    override fun toString(): String {
        return "referenceCurrencyUuid=$referenceCurrencyUuid&timePeriod=$timePeriod"
    }

    fun updateTimePeriod(timePeriod: TimePeriod) {
        this.timePeriod = timePeriod
    }

    fun updateReferenceCurrency(referenceCurrencyUuid: String) {
        this.referenceCurrencyUuid = referenceCurrencyUuid
    }
}

class OrderProperties(
    private val orderBy: OrderBy = OrderBy.MARKET_CAP,
    private val orderDirection: OrderDirection = OrderDirection.DESC,
    private val limit: Int = Constant.DEFAULT_LIMIT,
    private val offset: Int = 0,
) {
    override fun toString(): String {
        return "orderBy=$orderBy&orderDirection=$orderDirection&limit=$limit&offset=$offset"
    }
}

class CurrencyParam(
    private val limit: Int = Constant.DEFAULT_LIMIT,
    private val offset: Int = 0,
) {
    override fun toString(): String {
        return "limit=$limit&offset=$offset"
    }
}
