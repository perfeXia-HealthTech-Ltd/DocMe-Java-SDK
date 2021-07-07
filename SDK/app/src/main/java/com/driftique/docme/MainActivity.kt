package com.driftique.docme

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.driftique.docme.Api.Api
import com.driftique.docme.Api.Data.Measurement
import com.driftique.docme.Api.Data.Patient
import com.driftique.docme.Interactor.Interactor
import com.driftique.docme.Interactor.createRetrofitApi
import com.driftique.docme.Interactor.timeInSeconds
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
            Log.d("request", interactor.newPatient().toString())
            val Bob: Patient = interactor.newPatient()
            val measurementTimestamp: Long = 1624224079
            val fileVideo = tmp.path + "/video1.mp4"

            val duration = timeInSeconds(fileVideo)
            Log.d("duration", duration.toString())
            if (10 <= duration && duration <= 20){
                val result: Measurement =
                    Bob.newMeasurement(measurementTimestamp, File(fileVideo), "video1.mp4")
            } else {
                throw Exception()
            }
            
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
//

// mov на if
// delete в метод пациента