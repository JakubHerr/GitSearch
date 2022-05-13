package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.UserDto

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var login: String = "",
    var avatarUrl: String = "",
    var name: String? = "",
    var company: String? = "",
    var location: String? = "",
    var email: String? = "",
    var bio: String? = "",
    var followers: Int = 0,
    var following: Int = 0
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
