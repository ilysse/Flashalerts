// FlashToggleActivity.kt
package com.example.flashcall
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class FlashToggleActivity : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    private lateinit var flashButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        flashButton = findViewById(R.id.flashButton)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            setupFlashButton()
        }
    }

    private fun setupFlashButton() {
        var isFlashOn = false

        flashButton.setOnClickListener {
            toggleFlash(isFlashOn)
            isFlashOn = !isFlashOn
        }
    }

    private fun toggleFlash(isOn: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, isOn)
            Toast.makeText(this, if (isOn) "Flash On" else "Flash Off", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 123
    }
}