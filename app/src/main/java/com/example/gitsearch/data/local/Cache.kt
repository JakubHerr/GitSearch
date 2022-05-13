package com.example.gitsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitsearch.data.local.entities.Branch
import com.example.gitsearch.data.local.entities.Commit
import com.example.gitsearch.data.local.entities.Repo
import com.example.gitsearch.data.local.entities.User

@Database(
    entities = [User::class, Repo::class, Branch::class, Commit::class],
    version = 1,
    exportSchema = false
)
abstract class Cache : RoomDatabase() {
    abstract val cacheDao: CacheDao
}