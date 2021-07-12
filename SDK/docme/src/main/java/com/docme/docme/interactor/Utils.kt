package com.docme.docme.interactor

/**
 * Overloaded exception if smth goes wrong with server
 */
class DocMeServerException(private val messageFromServer: String): Exception("Server response: $messageFromServer")

class Settings {
    companion object {
        var KEY: String = ""
        fun initSDK(api: String) {
            KEY = api
        }
    }
}