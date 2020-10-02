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

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("pwd") val pwd: String,
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
    @SerializedName("name") val name: String,
    @SerializedName("pwd") val pwd: String,
)

data class UserTokenIssueParam(
    @SerializedName("email") val name: String,
    @SerializedName("pwd") val pwd: String,
)