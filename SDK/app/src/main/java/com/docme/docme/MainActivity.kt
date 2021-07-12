package com.docme.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.docme.docme.Api.Api
import com.docme.docme.Api.Data.Measurement
import com.docme.docme.Api.Data.Patient
import com.docme.docme.Api.Data.Patient.Companion.newPatient
import com.docme.docme.Interactor.Interactor
import com.docme.docme.Interactor.createRetrofitApi
import com.docme.docme.Interactor.timeInSeconds
import com.docme.test.R

import java.io.File



class MainActivity : AppCompatActivity() {
    private fun createAppFolderIfNotExist() {
        Log.d("folder_", this.externalCacheDir?.absolutePath!!)
        val dir = File(this.externalCacheDir?.absolutePath!!)
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAppFolderIfNotExist()

        val tmp: File = File(this.externalCacheDir?.absolutePath!!)
        Environment.getDownloadCacheDirectory()
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val t = HandlerThread("new_thread")
        t.start()
        Handler(t.looper).post {
            Log.d("request", newPatient().toString())
            val Bob: Patient = newPatient()
            val measurementTimestamp: Long = 1624224079
            val fileVideo = tmp.path + "/video1.mp4"

            val duration = timeInSeconds(fileVideo)
            Log.d("duration", duration.toString())

            val result: Measurement =  Bob.newMeasurement(measurementTimestamp, File(fileVideo))

            
        }

    }

//    fun onClickStart(v: View) {
//        startService(Intent(this, MyService::class.java))
//    }
//
//    fun onClickStop(v: View) {
//        stopService(Intent(this, MyService::class.java))
//    }
}
// сервер не доступен, мы падаем
// не быть интернета, в измерении еррор
// если айдишник пустой, то падаем throw exeption

