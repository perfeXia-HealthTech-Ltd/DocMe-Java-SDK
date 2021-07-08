package com.driftique.docme

import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.driftique.docme.Api.Api
import com.driftique.docme.Api.Data.Measurement
import com.driftique.docme.Api.Data.Patient
import com.driftique.docme.Interactor.Interactor
import com.driftique.docme.Interactor.createRetrofitApi
import com.driftique.docme.Interactor.timeInSeconds

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.driftique.docme.test", appContext.packageName)
    }
}
