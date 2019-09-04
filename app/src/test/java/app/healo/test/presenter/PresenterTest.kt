package app.healo.test.presenter

import androidx.core.util.Consumer
import app.healo.test.TestData
import app.healo.test.model.IModelLayer
import app.healo.test.model.db.Message
import app.healo.test.view.IMessageView
import org.junit.Assert.*
import org.junit.Test

class PresenterTest {

    @Test
    fun getDataSuccess() {
        var progressStatus: Boolean? = null

        val presenter = Presenter(object: IMessageView {
            override fun showProgress(visible: Boolean) {
                progressStatus = visible
            }

            override fun showMessage(errorMessage: String) {
                fail("Error for success call")
            }

        }, object : IModelLayer {
            override fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>) {
                assertTrue("Progress bar is not shown", progressStatus!!)
                onSuccess.accept(TestData.messagesList)
            }
            override fun onDestroy() {
                fail("Destroy is called")
            }

        })
        presenter.getData(Consumer {
            assertSame(it, TestData.messagesList)
            assertFalse("Progress bar is not shown", progressStatus!!)
        }, Consumer {
            fail("Failed for success call")
        })
    }

    @Test
    fun getDataFail() {
        val errorString = "ErrorString"
        var progressStatus: Boolean? = null

        val presenter = Presenter(object: IMessageView {
            override fun showProgress(visible: Boolean) {
                progressStatus = visible
            }

            override fun showMessage(errorMessage: String) {
                assertEquals(errorMessage, errorString)
            }

        }, object : IModelLayer {
            override fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>) {
                assertTrue("Progress bar is not shown", progressStatus!!)
                onFailed.accept(errorString)
            }
            override fun onDestroy() {
                fail("Destroy is called")
            }

        })
        presenter.getData(Consumer {
            fail("Success for failed call")
        }, Consumer {
            assertSame(it, errorString)
            assertFalse("Progress bar is not shown", progressStatus!!)
        })
    }

    @Test
    fun onDestroy() {

        var isDestroyCalled = false
        val presenter = Presenter(object : IMessageView {
            override fun showProgress(visible: Boolean) {
                fail("Updating progress state in onDestroy method")
            }

            override fun showMessage(errorMessage: String) {
                fail("Showing message in onDestroy method")
            }
        }, object : IModelLayer {
            override fun getData(onSuccess: Consumer<List<Message>>, onFailed: Consumer<String>) {
                fail("Getting data in onDestroy method")
            }

            override fun onDestroy() {
                isDestroyCalled = true
            }
        })

        presenter.onDestroy()
        assertTrue("onDestroy is not called", isDestroyCalled)
    }

}
