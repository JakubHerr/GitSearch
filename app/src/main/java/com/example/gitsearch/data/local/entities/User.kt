package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.UserDto

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)

fun User.toDto(): UserDto = UserDto(
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
