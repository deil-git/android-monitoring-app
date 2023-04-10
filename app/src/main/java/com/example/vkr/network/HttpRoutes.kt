package com.example.vkr.network

object HttpRoutes {

    private const val BASE_URL = "https://web.foodrus.ru/api/"
    private const val INDICATIONS = "indications/"
    private const val PROM_TIME_START = "?start="
    private const val PROM_TIME_END = "&end="
    private const val CONFIG = "config"

    const val Indications = "$BASE_URL$INDICATIONS"
    const val PromTimeStart = "$PROM_TIME_START"
    const val PromTimeEnd = "$PROM_TIME_END"

    const val Config = "$BASE_URL$CONFIG"

}