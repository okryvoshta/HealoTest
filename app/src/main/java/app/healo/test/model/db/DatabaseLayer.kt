package app.healo.test.model.db

import androidx.core.util.Consumer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DatabaseLayer : IDatabaseLayer {
    private var mGetDisposable: Disposable? = null
    private var mInsertDisposable: Disposable? = null
    private var mDAO: MessageDAO = MyDatabase.getInstance().messageDao()

    override fun getDataFromDB(onDataLoaded: Consumer<List<Message>>) {
        mGetDisposable?.dispose()
        mGetDisposable = mDAO.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onDataLoaded.accept(it)
            }
    }

    override fun saveDataToDB(data: List<Message>) {
        mInsertDisposable = mDAO.insertAll(data).subscribeOn(Schedulers.io()).subscribe{
            println("Data saved")
        }
    }

    override fun onDestroy() {
        mGetDisposable?.dispose()
        mInsertDisposable?.dispose()

    }
}