package com.whoissio.eving.networks.apis

import com.whoissio.eving.models.*
import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ExerciseApi {
    @POST("/eving/exercises/data/{exerciseId}")
    fun saveExerciseSensorData(@Path("exerciseId") exerciseId: Int, @Body param: ExerciseSensorDataParam): Single<BaseResponse<Any>>

    @POST("/eving/exercises")
    fun createNewExercise(): Single<BaseResponse<ExerciseId>>

    @GET("/eving/exercises")
    fun getAllExercises(): Single<BaseResponse<ArrayList<Exercise?>>>

    @GET("/eving/exercises/data/{exerciseId}")
    fun getExerciseSensorData(@Path("exerciseId") exerciseId: Int): Single<BaseResponse<ArrayList<SensorData?>>>

    @DELETE("/eving/exercises/{exerciseId}")
    fun deleteExercise(@Path("exerciseId") exerciseId: Int): Single<BaseResponse<Any>>

    @PUT("/eving/exercises/title/{exerciseId}")
    fun setTitleOfExercise(@Path("exerciseId") exerciseId: Int, @Body title: ExerciseTitle): Single<BaseResponse<Any>>
}