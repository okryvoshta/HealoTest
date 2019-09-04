package app.healo.test.model.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.healo.test.helper.App
import app.healo.test.helper.Config

@Database(entities = [Message::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDAO

    companion object {

        private var INSTANCE: MyDatabase? = null

        fun getInstance(): MyDatabase =
            INSTANCE ?: buildDatabase().also { INSTANCE = it }

        private fun buildDatabase() =
            Room.databaseBuilder(
                App.getInstance().applicationContext,
                MyDatabase::class.java, Config.DB_NAME
            ).build()
    }
}