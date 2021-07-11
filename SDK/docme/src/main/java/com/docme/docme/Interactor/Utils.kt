package com.docme.docme.Interactor

/**
 * Overloaded exception if smth goes wrong with server
 */
class DocMeServerException(private val messageFromServer: String): Exception("Server response: $messageFromServer")