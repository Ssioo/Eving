package com.whoissio.eving.networks.apis

import com.whoissio.eving.models.Exercise
import com.whoissio.eving.models.ExerciseId
import com.whoissio.eving.models.ExerciseSensorDataParam
import com.whoissio.eving.models.SensorData
import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExerciseApi {
    @POST("/eving/exercises/data/{exerciseId}")
    fun saveExerciseSensorData(@Path("exerciseId") exerciseId: Int, @Body param: ExerciseSensorDataParam): Single<BaseResponse<Any>>

    @POST("/eving/exercises")
    fun createNewExercise(): Single<BaseResponse<ExerciseId>>

    @GET("/eving/exercises")
    fun getAllExercises(): Single<BaseResponse<ArrayList<Exercise?>>>

    @GET("/eving/exercises/data/{exerciseId}")
    fun getExerciseSensorData(@Path("exerciseId") exerciseId: Int): Single<BaseResponse<ArrayList<SensorData?>>>
}