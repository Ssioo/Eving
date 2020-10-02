package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

enum class UserRole(val roleName: String) {
    COMMON("common"),
    ADMIN("admin")
}

enum class UserStatus(val status: String) {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BANNED("BANNED")
}

enum class UserGender(val gender: Int) {
    MALE(1),
    FEMALE(2)
}

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("pwd") val pwd: String,
    @SerializedName("gender") val gender: UserGender,
    @SerializedName("role") val role: UserRole,
    @SerializedName("status") val status: UserStatus,
    @SerializedName("created_at") val createAt: String,
    @SerializedName("deleted_at") val deletedAt: String?,
)

data class Jwt(
    @SerializedName("token") val token: String
)

data class UserRegisterParam(
    @SerializedName("email") val email: String,
    @SerializedName("pwd") val pwd: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("gender") val gender: UserGender,
)

data class UserTokenIssueParam(
    @SerializedName("email") val name: String,
    @SerializedName("pwd") val pwd: String,
)