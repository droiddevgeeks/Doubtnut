package com.example.doubtnut.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doubtnut.model.Article

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM news_article")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articlesList: List<Article>)
}