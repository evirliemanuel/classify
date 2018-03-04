package io.classify.ui.login

interface LoginInteract {

    interface OnFinishedListener {

        fun onUsernameError()

        fun onPasswordError()

        fun onSuccess()

        fun onFailure()
    }

    fun login(username: String, password: String, listener: OnFinishedListener)
}