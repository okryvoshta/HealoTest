package app.healo.test.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.healo.test.R
import app.healo.test.dependency.DependencyRegistry
import app.healo.test.helper.App
import app.healo.test.presenter.IPresenter

class MainActivity : AppCompatActivity(), IMessageView {
    private lateinit var mListView: RecyclerView
    private lateinit var mProgressBar: ProgressBar

    private lateinit var mPresenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        DependencyRegistry.inject(this)
    }

    private fun initUI() {
        mListView = findViewById(R.id.list)
        mListView.layoutManager = LinearLayoutManager(this)
        mListView.addItemDecoration(MarginItemDecoration())

        mProgressBar = findViewById(R.id.progressBar)
    }

    fun configure(presenter: IPresenter) {
        mPresenter = presenter
        mPresenter.getData(
            Consumer { mListView.adapter = MessageAdapter(it) },
            Consumer { showMessage(App.getInstance().getString(R.string.network_load_error,it))}
        )
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress(visible: Boolean) {
        mProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

}
