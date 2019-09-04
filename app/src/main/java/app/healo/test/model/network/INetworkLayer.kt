package app.healo.test.model.network

import androidx.core.util.Consumer
import app.healo.test.model.db.Message

interface INetworkLayer {
    fun loadDataFromNetwork(data: Consumer<List<Message>>, onFailed: Consumer<String>)
    fun onDestroy()
}