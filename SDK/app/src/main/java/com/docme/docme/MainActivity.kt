package com.docme.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.docme.docme.api.Api
import com.docme.docme.data.Measurement
import com.docme.docme.data.Patient
import com.docme.docme.data.Patient.Companion.newPatient
import com.docme.docme.interactor.Docme
import com.docme.docme.interactor.Interactor
import com.docme.docme.interactor.createRetrofitApi
import com.docme.docme.interactor.timeInSeconds

import java.io.File
import java.util.jar.Manifest


val PERMIT_READ_FILES = 1
val PICK_VIDEO_FILE = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), PERMIT_READ_FILES
        )

        Thread {
            runTest()
        }.start()

        openFile()
    }

    private fun runTest() {
        Docme.initSDK("383efded5bmsh5a4cdd9353ec742p176864jsnfcbb6e3aeda0")
        val p = Patient.newPatient()
        val pCopy = Patient.getPatient(p.id)
        Log.d("Docme test", "patient id=${p.id}, patient copy id=${pCopy.id}")

        try {
            val another = Patient.getPatient("not_existing_id")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        p.deletePatient()
        try {
            val another = Patient.getPatient(p.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "video/mp4"
        }

        startActivityForResult(intent, PICK_VIDEO_FILE)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_VIDEO_FILE
            && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also { uri ->
                Thread {
                    val p = Patient.newPatient()
                    Log.d("Docme TEST", "Path: $uri")
                    val m = p.newMeasurement(this, 228,uri)
                    val hm3 = p.getHM3()
                    Log.d("Docme TEST", hm3.state)
                    val a = 30
                }.start()
            }
        }
    }
}





