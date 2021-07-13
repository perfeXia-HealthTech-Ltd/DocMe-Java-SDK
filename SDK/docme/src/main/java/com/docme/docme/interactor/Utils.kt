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

class Settings {
    companion object {
        var KEY: String = ""
        fun initSDK(api: String) {
            KEY = api
        }
    }
}