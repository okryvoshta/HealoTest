package app.healo.test.model

import androidx.core.util.Consumer
import app.healo.test.model.db.Message

interface IModelLayer {
    fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>)
    fun onDestroy()
}