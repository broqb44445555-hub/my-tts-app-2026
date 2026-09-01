package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TtsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TtsDao {
    @Query("SELECT * FROM tts_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<TtsItem>>

    @Query("SELECT * FROM tts_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavorites(): Flow<List<TtsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TtsItem): Long

    @Update
    suspend fun update(item: TtsItem)

    @Delete
    suspend fun delete(item: TtsItem)

    @Query("DELETE FROM tts_history WHERE isFavorite = 0")
    suspend fun clearNonFavorites()

    @Query("DELETE FROM tts_history")
    suspend fun clearAll()

    @Query("UPDATE tts_history SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)
}
