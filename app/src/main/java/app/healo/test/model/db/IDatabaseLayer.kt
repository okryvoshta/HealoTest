package app.healo.test.model.db

import androidx.core.util.Consumer

interface IDatabaseLayer {
    fun getDataFromDB(onDataLoaded: Consumer<List<Message>>)
    fun saveDataToDB(data: List<Message>)
    fun onDestroy()
}