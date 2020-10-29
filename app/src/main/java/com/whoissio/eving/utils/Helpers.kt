package com.whoissio.eving.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.whoissio.eving.utils.Constants.DATETIME_FORMAT
import com.whoissio.eving.utils.Constants.DATETIME_FORMAT_SIMPLE
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import java.text.SimpleDateFormat
import java.util.*

object Helpers {
    fun <T> Single<T>.toDisposal(
        disposable: CompositeDisposable,
        onSuccess: Consumer<in T>,
        onError: Consumer<in Throwable>
    ) {
        disposable.add(this.subscribe(onSuccess, onError))
    }

    fun Float.toPx(context: Context): Float =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
        )

    fun Int.toPx(context: Context): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()

    fun LineChart.initChart(data: LineData) {
        this.data = data
        moveViewToX(data.entryCount.toFloat())

        description.text = "시간"
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.textColor = Color.WHITE
        isDragEnabled = true
        xAxis.gridLineWidth = 0f
        axisLeft.textColor = Color.WHITE
        axisLeft.gridLineWidth = 0f
        axisRight.gridLineWidth = 0f
        legend.textColor = Color.WHITE
        axisRight.textColor = Color.WHITE
        setDrawGridBackground(false)
        isAutoScaleMinMaxEnabled = true
        setDrawGridBackground(false)
        axisRight.axisMaximum = 360f
        axisRight.axisMinimum = 0f
        setVisibleXRangeMaximum(50f)
        isHorizontalScrollBarEnabled = true
        invalidate()
    }

    @JvmStatic
    fun toHourMin(src: Int?): String {
        if (src == null) return ""
        return String.format("%02d:%02d", src.div(60), src % 60)
    }

    @JvmStatic
    fun toSimpleDate(src: String?): String {
        if (src == null) return ""
        return DATETIME_FORMAT.parse(src)
            ?.let { DATETIME_FORMAT_SIMPLE.format(it) }
            ?: "----.--.-- --:--"
    }
}