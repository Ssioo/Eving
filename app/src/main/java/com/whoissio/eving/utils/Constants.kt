package com.whoissio.eving.utils

import java.util.*

object Constants {
    const val BASE_URL = "http://18.224.20.121:3000"
    const val CONNECT_TIMEOUT = 6000L

    const val SP_TAG = "EVING"
    const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

    const val ACTIVITY_SIGN_IN = "SIGN_IN"
    const val ACTIVITY_SIGN_UP = "SIGN_UP"
    const val ACTIVITY_MAIN = "MAIN"
    const val ACTIVITY_IOT = "IOT"

    const val FRAGMENT_CONTENTS = "CONTENTS"
    const val FRAGMENT_MYPAGE = "MYPAGE"

    const val STATE_DISCONNECTED = 0
    const val STATE_CONNECTING = 1
    const val STATE_CONNECTED = 2
    const val ACTION_GATT_CONNECTED = "com.whoissio.eving.ACTION_GATT_CONNECTED"
    const val ACTION_GATT_DISCONNECTED = "com.whoissio.eving.ACTION_GATT_DISCONNECTED"
    const val ACTION_GATT_SERVICES_DISCOVERED =
        "com.whoissio.eving.ACTION_GATT_SERVICES_DISCOVERED"
    const val ACTION_DATA_AVAILABLE = "com.whoissio.eving.ACTION_DATA_AVAILABLE"
    const val EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA"
    val UUID_DEVICE = UUID.fromString("")
}