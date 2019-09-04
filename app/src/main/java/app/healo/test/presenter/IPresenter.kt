package app.healo.test.presenter

import androidx.core.util.Consumer
import app.healo.test.model.db.Message

interface IPresenter {
    fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>)
    fun onDestroy()
}