package app.healo.test.presenter

import androidx.core.util.Consumer
import app.healo.test.model.IModelLayer
import app.healo.test.model.db.Message
import app.healo.test.view.IMessageView

class Presenter(private var mViewCallback: IMessageView, private var mDataLayer: IModelLayer) : IPresenter {

    override fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>) {

        mViewCallback.showProgress(true)
        mDataLayer.getData (Consumer {
            mViewCallback.showProgress(false)
            onSuccess.accept(it)
        }, Consumer {
            mViewCallback.showProgress(false)
            onFailed.accept(it)
        })
    }

    override fun onDestroy() {
        mDataLayer.onDestroy()
    }
}


