package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

data class Contents(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("video") val video: String?,
    @SerializedName("video_time") val videoTime: Int?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("author_id") val authorId: Int,
    @SerializedName("author_email") val authorEmail: String,
    @SerializedName("created_at") val createdAt: String,
)

data class Ads(
    @SerializedName("id") val id: Int,
    @SerializedName("img") val img: String,
    @SerializedName("link") val link: String?,
)