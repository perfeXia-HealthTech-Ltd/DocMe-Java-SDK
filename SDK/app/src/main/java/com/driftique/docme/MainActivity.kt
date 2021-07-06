package com.driftique.docme

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.provider.DocumentsContract
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import com.driftique.docme.Api.Api
import com.driftique.docme.Api.Data.Measurement
import com.driftique.docme.Api.Data.Patient
import com.driftique.docme.Interactor.Interactor
import com.driftique.docme.Interactor.createRetrofitApi
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val video: VideoView = findViewById(R.id.video_view)
//        val videoPath: String = "android.resource://" + getPackageName() + "/" + R.raw.video
//        val uri: Uri = Uri.parse(videoPath)
//        video.setVideoURI(uri)
//        val mediaController: MediaController = MediaController(this)
//        video.setMediaController(mediaController)
//        mediaController.setAnchorView(video)

        val PICK_PDF_FILE = 1

        fun openFile(pickerInitialUri: Uri) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "video/mp4"

                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }

            startActivityForResult(intent, PICK_PDF_FILE)
        }



        val tmp: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Environment.getDownloadCacheDirectory()
        val api: Api = createRetrofitApi()
        val interactor =  Interactor(api)
        val t = HandlerThread("new_thread")
        t.start()
        Handler(t.looper).post {
            Log.d("request", interactor.newPatient().toString())
            val Bob: Patient = interactor.newPatient()
            val measurementTimestamp: Long = 1624224079
            val fileVideo = tmp.path + "/video.mp4"

         //   openFile(Uri.parse(fileVideo))
            val result: Measurement = Bob.newMeasurement(measurementTimestamp, File(fileVideo), "video.mp4")
            Log.d("request", "kek")

            val a = 435
        }
    }
}