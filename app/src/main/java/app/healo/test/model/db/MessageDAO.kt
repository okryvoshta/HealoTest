package app.healo.test.model.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MessageDAO {

    @Query("SELECT * FROM message ORDER BY id")
    fun getAll(): Flowable<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>?): Completable

    @Query("DELETE FROM message")
    fun clean()
}