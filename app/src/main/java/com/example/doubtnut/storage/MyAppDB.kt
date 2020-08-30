package com.example.doubtnut.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doubtnut.model.Article

const val DATABASE_NAME = "my_app_db"

@Database(entities = [Article::class], version = 1)
abstract class MyAppDB : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO
}