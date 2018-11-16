package com.medandevelop.kotlinsimplescreenrecording

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.Surface
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private var screenDensity:Int = 0;
    private var projectManager:MediaProjectionManager?=null
    private var mediaProjection:MediaProjection?=null
    private var virtualDisplay:VirtualDisplay?=null
    private var mediaProjectionCallback:MediaProjectionCallback?=null
    private var mediaRecorder:MediaRecorder?=null

    internal var videoUri:String=""
    companion object {
        private val REQUEST_CODE = 1000
        private val REQUEST_PERMISSION = 1001
        private var DISPLAY_WIDTH = 700
        private var DISPLAY_HEIGHT = 1280
        private val ORIENTATIONS = SparseIntArray()
        init {
            ORIENTATIONS.append(Surface.ROTATION_0,90)
            ORIENTATIONS.append(Surface.ROTATION_90,0)
            ORIENTATIONS.append(Surface.ROTATION_180,270)
            ORIENTATIONS.append(Surface.ROTATION_270,180)
        }
    }
    inner class MediaProjectionCallback:MediaProjection.Callback() {
        override fun onStop() {
            if(toggleBtn.isChecked){
                toggleBtn.isChecked = false
                mediaRecorder!!.stop()
                mediaRecorder!!.reset()
            }
            mediaProjection = null
            stopScreenRecord()
        }
    }

    private fun stopScreenRecord() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        screenDensity = metrics.densityDpi
        mediaRecorder = MediaRecorder()
        projectManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        DISPLAY_HEIGHT = metrics.heightPixels
        DISPLAY_WIDTH = metrics.widthPixels
        //event
        toggleBtn.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this@MainActivity,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                    ContextCompat.checkSelfPermission(this@MainActivity,
                            android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(
                                this@MainActivity,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(
                                this@MainActivity,
                                android.Manifest.permission.RECORD_AUDIO
                        ))
                {
                    toggleBtn.isChecked = false
                    Snackbar.make(rootLayout,"Permissions",Snackbar.LENGTH_INDEFINITE)
                        .setAction("ENABLE",{
                            ActivityCompat.requestPermissions(this@MainActivity,
                                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.RECORD_AUDIO), REQUEST_PERMISSION)
                        }).show()
                }else{

                }
            }else{
                startRecording()
            }
        }
    }

    private fun startRecording() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


