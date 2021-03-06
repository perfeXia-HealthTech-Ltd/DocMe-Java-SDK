package com.docme.docme.interactor

/**
 * Overloaded exception if something goes wrong with server
 */
class DocMeServerException(private val messageFromServer: String): Exception("Server response: $messageFromServer")

/**
 * Overloaded exception if not available format of video
 */
class NotAvailableFormatException(): Exception("DocMe response: Not available format")

/**
 * Overloaded exception if not appropriate duration of video
 */
class NotAppropriateDurationException(): Exception("DocMe response: Not available duration of video")

/**
 * Overloaded exception if not appropriate size of video
 */
class NotAppropriateSizeException(): Exception("DocMe response: Not appropriate size of video, max size 20 Mb")

/**
 * Setting to authorize the user with API key
 */
class DocMe {
    companion object {
        private var KEY: String = ""

        /**
         * Getting API key
         * @return API key
         */
        fun getKey(): String {
            return KEY
        }

        /**
         * Initiating of API key
         * @param api API key
         */
        fun initSDK(api: String) {
            KEY = api
        }
    }
}