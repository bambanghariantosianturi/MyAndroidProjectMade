package com.example.myandroidproject.core.data.source.local

import com.example.myandroidproject.core.data.source.local.entity.DataEntity
import com.example.myandroidproject.core.data.source.local.entity.GenreEntity
import com.example.myandroidproject.core.data.source.local.room.UserDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getAllData(): Flowable<List<DataEntity>> {
        return userDao.getAllData()
    }

    fun getFavoriteMovie(): Flowable<List<DataEntity>> {
        return userDao.getFavoriteMovie()
    }

    fun insertDataMovie(dataEntity: List<DataEntity>): Completable {
        return userDao.insertData(dataEntity.map { it })
    }

    fun setFavoriteMovie(dataEntity: DataEntity, newState: Boolean) {
        dataEntity.isFavorite = newState
        userDao.updateFavoriteMovie(dataEntity)
    }

    fun getGenreMovie(): Flowable<List<GenreEntity>> = userDao.getGenreMovie()

    fun insertGenreData(genreData: List<GenreEntity>) = userDao.insertGenreData(genreData)
}