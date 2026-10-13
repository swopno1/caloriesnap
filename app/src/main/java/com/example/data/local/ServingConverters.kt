package com.example.data.local

import androidx.room.TypeConverter
import com.example.model.ServingOption
import org.json.JSONArray
import org.json.JSONObject

/**
 * Type converters for serializing List<ServingOption> to/from String format
 * without requiring external reflection libraries.
 */
object ServingConverters {

    @TypeConverter
    @JvmStatic
    fun serializeServingOptions(options: List<ServingOption>): String {
        val array = JSONArray()
        for (opt in options) {
            val obj = JSONObject().apply {
                put("labelEn", opt.labelEn)
                put("labelBn", opt.labelBn)
                put("gramsOrMl", opt.gramsOrMl)
                put("calories", opt.calories)
                put("isDefault", opt.isDefault)
            }
            array.put(obj)
        }
        return array.toString()
    }

    @TypeConverter
    @JvmStatic
    fun deserializeServingOptions(jsonString: String?): List<ServingOption> {
        if (jsonString.isNullOrBlank()) return emptyList()
        val list = mutableListOf<ServingOption>()
        try {
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    ServingOption(
                        labelEn = obj.optString("labelEn", ""),
                        labelBn = obj.optString("labelBn", ""),
                        gramsOrMl = obj.optInt("gramsOrMl", 100),
                        calories = obj.optInt("calories", 0),
                        isDefault = obj.optBoolean("isDefault", false)
                    )
                )
            }
        } catch (_: Exception) {
            // Return empty list on parse failure
        }
        return list
    }
}
