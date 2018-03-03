package io.classify.ui.login

interface LoginView {

    fun showProgress()

    fun hideProgress()

    fun setUsernameError()

    fun setPasswordError()

    fun setLoginError();

    fun navigateToHome()
}