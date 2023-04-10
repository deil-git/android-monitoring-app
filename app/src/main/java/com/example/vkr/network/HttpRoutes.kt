package com.example.vkr.network

object HttpRoutes {

    private const val REAL_TIME_BASE_URL = "https://web.foodrus.ru/api/indications/"
    private const val PROM_TIME_START = "?start="
    private const val PROM_TIME_END = "&end="

    const val BaseURL = "$REAL_TIME_BASE_URL"
    const val PromTimeStart = "$PROM_TIME_START"
    const val PromTimeEnd = "$PROM_TIME_END"

}