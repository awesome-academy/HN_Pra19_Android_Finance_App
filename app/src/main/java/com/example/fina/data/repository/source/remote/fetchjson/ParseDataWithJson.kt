package com.example.fina.data.repository.source.remote.fetchjson

import android.util.Log
import com.example.fina.utils.ResponseEntry
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ParseDataWithJson {
    fun parseJsonToData(
        jsonObject: JSONObject?,
        keyEntity: String,
    ): Any? {
        val jsonDataObject = jsonObject?.getJSONObject(ResponseEntry.DATA)
        return when (keyEntity) {
            ResponseEntry.COINS ->
                parseJsonToListObject(
                    jsonDataObject?.optJSONArray(ResponseEntry.COINS),
                    keyEntity,
                )

            ResponseEntry.PRICE_HISTORY ->
                parseJsonToListObject(
                    jsonDataObject?.optJSONArray(ResponseEntry.PRICE_HISTORY),
                    keyEntity,
                )

            ResponseEntry.STATS ->
                parseJsonToObject(
                    jsonDataObject?.optJSONObject(ResponseEntry.STATS),
                    keyEntity,
                )

            ResponseEntry.CURRENCIES ->
                parseJsonToListObject(
                    jsonDataObject?.optJSONArray(ResponseEntry.CURRENCIES),
                    keyEntity,
                )

            ResponseEntry.DATA ->
                parseJsonToObject(
                    jsonDataObject,
                    keyEntity,
                )

            else -> Any()
        }
    }

    private fun parseJsonToListObject(
        jsonArray: JSONArray?,
        keyEntity: String,
    ): MutableList<Any> {
        val data = mutableListOf<Any>()
        try {
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val item = parseJsonToObject(jsonArray?.getJSONObject(i), keyEntity)
                item?.let {
                    data.add(it)
                }
            }
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "parseJsonToData: ", e)
        }
        return data
    }

    private fun parseJsonToObject(
        jsonObject: JSONObject?,
        keyEntity: String,
    ): Any? {
        try {
            jsonObject?.let {
                return when (keyEntity) {
                    ResponseEntry.COINS -> ParseJson.coinParseJson(it)
                    ResponseEntry.PRICE_HISTORY -> ParseJson.priceRecordParseJson(it)
                    ResponseEntry.STATS -> ParseJson.coinStatsParseJson(it)
                    ResponseEntry.CURRENCIES -> ParseJson.currencyParseJson(it)
                    ResponseEntry.DATA -> ParseJson.coinStatsAndListCoinsParseJson(it)
                    else -> null
                }
            }
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "parseJsonToData: ", e)
        }
        return null
    }
}
