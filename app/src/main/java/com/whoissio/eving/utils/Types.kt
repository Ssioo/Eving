package com.whoissio.eving.utils

import androidx.annotation.ColorRes
import com.whoissio.eving.R

enum class SensorType(val label: String, val index: Int, val chartIdx: Int, val type: Int, @ColorRes val color: Int) {
    ACC_X("X축 가속도", 0, 0, 0, R.color.chartColor1),
    ACC_Y("Y축 가속도", 1, 1, 0, R.color.chartColor2),
    ACC_Z("Z축 가속도", 2, 2, 0, R.color.chartColor3),
    GYRO_X("X축 회전각속도", 0, 3, 1, R.color.chartColor4),
    GYRO_Y("Y축 회전각속도", 1, 4, 1, R.color.chartColor5),
    GYRO_Z("Z축 회전각속도", 2, 5, 1, R.color.chartColor6),
    TILT("수평 회전각", 0, 6, 2, R.color.chartColor7)
}