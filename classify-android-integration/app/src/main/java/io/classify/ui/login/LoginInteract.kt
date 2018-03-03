package io.classify.ui.login

interface LoginInteract {

    interface OnLoginFinishedListener {

        fun onUsernameError()

        fun onPasswordError()

        fun onSuccess()

        fun onFailed()
    }

    fun login(username: String, password: String, listener: OnLoginFinishedListener)
}