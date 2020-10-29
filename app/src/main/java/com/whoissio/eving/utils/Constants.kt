package com.whoissio.eving.utils

import android.graphics.Color
import java.text.SimpleDateFormat
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

    val UUID_DEVICE2 = UUID.fromString("0000FFE1-0000-1000-8000-00805f9b34fb")

    val COLOR_SET = listOf(Color.WHITE, Color.GRAY, Color.YELLOW, Color.RED, Color.LTGRAY)

    val ALL_SENSORTYPES = listOf(
        SensorType.ACC_X,
        SensorType.ACC_Y,
        SensorType.ACC_Z,
        SensorType.GYRO_X,
        SensorType.GYRO_Y,
        SensorType.GYRO_Z,
        SensorType.TILT
    )

    val DATETIME_FORMAT: SimpleDateFormat = SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
    val DATETIME_FORMAT_SIMPLE = SimpleDateFormat("YYYY.MM.dd HH:mm", Locale.KOREA)
}