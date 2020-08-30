package com.example.doubtnut.di.module

import androidx.room.Room
import com.example.doubtnut.NewsApplication
import com.example.doubtnut.di.scope.AppScope
import com.example.doubtnut.storage.ArticleDAO
import com.example.doubtnut.storage.DATABASE_NAME
import com.example.doubtnut.storage.MyAppDB
import dagger.Module
import dagger.Provides


@Module
class AppDbModule {

    @AppScope
    @Provides
    fun provideAppDatabase(application: NewsApplication): MyAppDB {
        return Room.databaseBuilder(application, MyAppDB::class.java, DATABASE_NAME).build()
    }

    @AppScope
    @Provides
    fun provideFavMovieDao(db: MyAppDB): ArticleDAO {
        return db.getArticleDao()
    }
}