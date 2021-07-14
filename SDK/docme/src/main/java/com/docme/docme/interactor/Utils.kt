package com.docme.docme.interactor

/**
 * Overloaded exception if something goes wrong with server
 */
class DocMeServerException(private val messageFromServer: String): Exception("Server response: $messageFromServer")

/**
 * Overloaded exception if not available format of video
 */
class NotAvailableFormat(): Exception("DocMe response: Not available format")

/**
 * Overloaded exception if not appropriate duration of video
 */
class NotAppropriateDuration(): Exception("DocMe response: Not available duration of video")

/**
 * Overloaded exception if not appropriate size of video
 */
class NotAppropriateSize(): Exception("DocMe response: Not appropriate size of video, max size 20 Mb")

/**
 * Setting to authorize the user with API key
 */
class Docme {
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