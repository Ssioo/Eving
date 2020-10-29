package com.whoissio.eving.networks.services

import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.ApplicationClass.Companion.retrofit
import com.whoissio.eving.models.*
import com.whoissio.eving.networks.BaseResponse
import com.whoissio.eving.networks.apis.ExerciseApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ExerciseService {

    fun registerExercise(
        exerciseId: Int,
        sensors: List<List<List<Float>>>,
        avgAcc: List<List<Double>>,
        avgGyro: List<List<Double>>,
        avgTilt: List<List<Double>>
    ): Single<BaseResponse<Any>> {
        return retrofit.create(ExerciseApi::class.java)
            .saveExerciseSensorData(
                exerciseId,
                ExerciseSensorDataParam(
                    sensors = sensors,
                    avgAcc = avgAcc,
                    avgGyro = avgGyro,
                    tilt = avgTilt
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createExercise(): Single<BaseResponse<ExerciseId>> {
        return retrofit.create(ExerciseApi::class.java)
            .createNewExercise()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllExercises(): Single<BaseResponse<ArrayList<Exercise?>>> {
        return retrofit.create(ExerciseApi::class.java)
            .getAllExercises()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getExerciseSensorData(exerciseId: Int): Single<BaseResponse<ArrayList<SensorData?>>> {
        return retrofit.create(ExerciseApi::class.java)
            .getExerciseSensorData(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setExerciseTitle(title: String, exerciseId: Int): Single<BaseResponse<Any>> {
        return retrofit.create(ExerciseApi::class.java)
            .setTitleOfExercise(exerciseId, ExerciseTitle(title))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteExercise(exerciseId: Int): Single<BaseResponse<Any>> {
        return retrofit.create(ExerciseApi::class.java)
            .deleteExercise(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}