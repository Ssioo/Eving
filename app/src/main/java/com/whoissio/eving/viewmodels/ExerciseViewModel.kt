package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.LineData
import com.orhanobut.logger.Logger
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.models.Sensor
import com.whoissio.eving.networks.services.ExerciseService
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.objects.SingleEvent
import io.reactivex.rxjava3.core.Single

class ExerciseViewModel : BaseViewModel() {

    var exerciseId: Int = -1
    val newTitle: MutableLiveData<String> = MutableLiveData("")

    val totalSet: MutableLiveData<Int> = MutableLiveData(1)
    val fullAccSensorData: ArrayList<List<Sensor>> = ArrayList()
    val fullGyroSensorData: ArrayList<List<Sensor>> = ArrayList()
    val fullTiltSensorData: ArrayList<List<Sensor>> = ArrayList()
    val fullMagSensorData: ArrayList<List<Sensor>> = ArrayList()
    val fullChartData: ArrayList<LineData> = ArrayList()

    val canFinish: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

    fun trySaveExercise() {
        networkEvent.startLoading()
        val avgAcc = listOf(0, 1, 2).map { item -> fullAccSensorData.map { it.map { it.data[item] }.average()}}
        val avgGyro = listOf(0, 1, 2).map { item -> fullGyroSensorData.map { it.map { it.data[item] }.average()}}
        val avgTilt = listOf(0).map { item -> fullTiltSensorData.map { it.map { it.data[item] }.average()}}
        Logger.d("IS SAME? ${fullAccSensorData.all { it.first() == fullAccSensorData[0].first() }}")
        val sensors = fullAccSensorData.mapIndexed { idx, item ->
            item.mapIndexed { idx2, item2 ->
                item2.data
                    .plus(fullGyroSensorData.getOrNull(idx)?.getOrNull(idx2)?.data ?: listOf(0f, 0f, 0f))
                    .plus(fullTiltSensorData.getOrNull(idx)?.getOrNull(idx2)?.data ?: listOf(0f))
                    .plus(fullMagSensorData.getOrNull(idx)?.getOrNull(idx2)?.data ?: listOf(0f, 0f, 0f))
            }}
        Single.zip(
            ExerciseService().registerExercise(exerciseId, sensors, avgAcc, avgGyro , avgTilt),
            ExerciseService().setExerciseTitle(newTitle.value!!, exerciseId)
        ) { a, b -> listOf(a, b) }
            .toDisposal(rxDisposable, {
                networkEvent.handleResponse(it[0])
                toastEvent.value = SingleEvent(data = R.string.exercise_upload_success)
                canFinish.value  = SingleEvent(data = true)
            }, {
                doOnNetworkError(it)
            })
    }

    fun createExercise() {
        networkEvent.startLoading()
        ExerciseService().createExercise().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    alertEvent.value = SingleEvent(data = R.string.network_error)
                    return@let
                }
                exerciseId = it.data?.exerciseId ?: -1
            }
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        createExercise()
    }
}