package io.classify.ui.login

interface LoginPresenter {

    fun validateCredentials(username: String, password: String)

    fun onDestroy()
}