package app.healo.test.model

import androidx.core.util.Consumer
import app.healo.test.model.db.IDatabaseLayer
import app.healo.test.model.db.Message
import app.healo.test.model.network.INetworkLayer

class ModelLayer(private var mDatabase: IDatabaseLayer, private var mNetwork: INetworkLayer) : IModelLayer {

    private var onSuccess: Consumer<List<Message>>? = null

    override fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>) {
        this.onSuccess = onSuccess
        mDatabase.getDataFromDB(onSuccess)
        mNetwork.loadDataFromNetwork(Consumer {
            mDatabase.saveDataToDB(it)
        }, Consumer {
            onFailed.accept(it)
        })
    }

    override fun onDestroy() {
        mNetwork.onDestroy()
        mDatabase.onDestroy()
    }
}
