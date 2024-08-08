package com.example.fina.utils
import com.example.fina.R

object Constant {
    const val BASE_URL_COINS = "https://api.coinranking.com/v2/coins"
    const val BASE_URL_COIN_DETAIL = "https://api.coinranking.com/v2/coin/"
    const val BASE_URL_CURRENCIES = "https://api.coinranking.com/v2/reference-currencies"
    const val USD_UUID = "yhjMzLPhuIDl"
    const val DEFAULT_LIMIT = 50
    const val API_KEY = "coinranking91b6bab1957bb16084164088c92d1e38e63b408ef85cdf33"
}

enum class TimePeriod(private val nameResId: Int, val displayName: String) {
    ONE_HOUR(R.string.ONE_HOUR, "1h"),
    THREE_HOURS(R.string.THREE_HOURS, "3h"),
    TWELVE_HOURS(R.string.TWELVE_HOURS, "12h"),
    TWENTY_FOUR_HOURS(R.string.TWENTY_FOUR_HOURS, "24h"),
    SEVEN_DAYS(R.string.SEVEN_DAYS, "7d"),
    THIRTY_DAYS(R.string.THIRTY_DAYS, "30d"),
    THREE_MONTHS(R.string.THREE_MONTHS, "3m"),
    ONE_YEAR(R.string.ONE_YEAR, "1y"),
    THREE_YEARS(R.string.THREE_YEARS, "3y"),
    FIVE_YEARS(R.string.FIVE_YEARS, "5y"),
    ;

    override fun toString(): String {
        return displayName
    }

    fun getNameResId(): Int {
        return nameResId
    }
}

enum class MarketCapInterval(private val displayName: String) {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    ;

    override fun toString(): String {
        return displayName
    }
}

enum class OrderBy(private val nameResId: Int, private val displayName: String) {
    PRICE(R.string.PRICE, "price"),
    MARKET_CAP(R.string.MARKET_CAP, "marketCap"),
    VOLUME_24H(R.string.VOLUME_24H, "24hVolume"),
    CHANGE(R.string.CHANGE, "change"),
    LISTED_AT(R.string.LISTED_AT, "listedAt"),
    ;

    override fun toString(): String {
        return displayName
    }

    fun getNameResId(): Int {
        return nameResId
    }
}

enum class OrderDirection(private val displayName: String) {
    DESC("desc"),
    ASC("asc"),
    ;

    override fun toString(): String {
        return displayName
    }
}

object CurrencySymbol {
    var symbol: String = "$"
}
