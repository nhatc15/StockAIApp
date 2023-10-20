package com.example.stockaiapp.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String? {
    try {
        val file = context.assets.open(path)

        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }

        return stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
