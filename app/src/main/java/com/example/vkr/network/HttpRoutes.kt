package com.example.vkr.network

object HttpRoutes {
    private const val BASE_URL = "https://web.foodrus.ru/api/"
    private const val INDICATIONS = "indications/"
    private const val PROM_TIME_START = "?start="
    private const val PROM_TIME_END = "&end="
    private const val REPLACE = "/replace"
    private const val CONFIG = "config"
    private const val DEVICES = "devices"
    private const val LOG = "logs"
    private const val BOX = "boxes"
    private const val TOKEN = "tokens"
    private const val FCMTOKEN = "fcmtoken"

    const val Indications = "$BASE_URL$INDICATIONS"
    const val PromTimeStart = "$PROM_TIME_START"
    const val PromTimeEnd = "$PROM_TIME_END"
    const val Replace = "$BASE_URL$CONFIG$REPLACE"

    const val Config = "$BASE_URL$CONFIG"
    const val Devices = "$BASE_URL$DEVICES"
    const val Logs = "$BASE_URL$LOG"
    const val Boxes = "$BASE_URL$BOX"
    const val Token = "$BASE_URL$TOKEN"
    const val FCMtoken = "$BASE_URL$FCMTOKEN"
}