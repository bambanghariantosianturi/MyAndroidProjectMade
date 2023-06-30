package com.example.myandroidproject.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myandroidproject.core.data.source.local.entity.DataEntity
import com.example.myandroidproject.core.data.source.local.entity.GenreEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllData(): Flowable<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataEntity>): Completable

    @Query("SELECT * FROM user where isFavorite = 1")
    fun getFavoriteMovie(): Flowable<List<DataEntity>>

    @Update
    fun updateFavoriteMovie(dataEntity: DataEntity)

    @Query("SELECT * FROM user")
    fun getDetailMovie(): Flowable<DataEntity>

    @Query("SELECT * FROM genre")
    fun getGenreMovie(): Flowable<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreData(genreData: List<GenreEntity>)
}