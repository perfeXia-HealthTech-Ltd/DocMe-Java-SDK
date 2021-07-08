package com.docme.docme.Interactor

class DocMeServerException(private val messageFromServer: String): Exception("Server response: $messageFromServer")