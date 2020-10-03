package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

data class Contents(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("video") val video: String?,
    @SerializedName("videoTime") val videoTime: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("author_id") val authorId: Int,
    @SerializedName("author_email") val authorEmail: String,
    @SerializedName("created_at") val createdAt: String,
)