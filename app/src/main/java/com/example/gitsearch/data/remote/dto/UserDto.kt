package com.example.gitsearch.data.remote.dto

import com.example.gitsearch.data.local.entities.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val login: String,
    val id: Long,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)

fun UserDto.toEntity(): User = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    location = location,
    email = email,
    bio = bio,
    followers = followers,
    following = following
)