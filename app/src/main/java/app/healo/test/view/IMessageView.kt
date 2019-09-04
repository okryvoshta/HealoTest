package app.healo.test.view

interface IMessageView {
    fun showProgress(visible: Boolean)
    fun showMessage(errorMessage: String)
}