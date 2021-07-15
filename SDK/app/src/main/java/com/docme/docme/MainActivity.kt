package com.docme.test

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.docme.docme.data.Patient
import com.docme.docme.interactor.Docme

const val PERMIT_READ_FILES = 1
const val PICK_VIDEO_FILE = 2

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread { runTest() }.start()

        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), PERMIT_READ_FILES
        )
    }

    private fun runTest() {
        Docme.initSDK("383efded5bmsh5a4cdd9353ec742p176864jsnfcbb6e3aeda0")

        val p = Patient.newPatient()
        val pCopy = Patient.getPatient(p.id)
        Log.d(TAG, "patient id=${p.id}, patient copy id=${pCopy.id}, ${p.id == pCopy.id}")

        try {
            val notExisted = Patient.getPatient("not_existing_id")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        p.deletePatient()
        try {
            val deleted = Patient.getPatient(p.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun runTestWithFile(uri: Uri) {
        val patient = Patient.newPatient()
        Log.d(TAG, "Uri path: ${uri.path}")

        val measurement = patient.newMeasurement(this,228, uri)
        Log.d(TAG, "${measurement.status}")

        val hm3 = patient.getHM3()
        Log.d(TAG, hm3.state)
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
            resultData?.data?.also { uri ->
                Thread {
                    runTestWithFile(uri)
                }.start()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMIT_READ_FILES -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openFile()
                } else {
                    Toast.makeText(this, "Permission denied for reading file",
                        Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
            }
        }
    }
}