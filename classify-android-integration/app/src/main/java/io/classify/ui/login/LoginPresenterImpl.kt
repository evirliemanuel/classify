package io.classify.ui.login

class LoginPresenterImpl(private var loginView: LoginView,
                         private val loginInteract: LoginInteract)
    : LoginPresenter, LoginInteract.OnFinishedListener {

    override fun validateCredentials(username: String, password: String) {
        loginView.showProgress()
        loginInteract.login(username, password, this)
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUsernameError() {
        loginView.setUsernameError()
        loginView.hideProgress()
    }

    override fun onPasswordError() {
        loginView.setPasswordError()
        loginView.hideProgress()
    }

    override fun onSuccess() {
        loginView.navigateToHome()
    }

    override fun onFailure() {
        loginView.setLoginError()
        loginView.hideProgress()
    }
}