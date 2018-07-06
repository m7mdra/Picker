package m7mdra.com.picker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.view.MenuItem

import m7mdra.com.picker.camera.CameraFragment
import m7mdra.com.picker.picker.PhotoPickerFragment


class ImagePickerActivity : AppCompatActivity() {


    private val isCameraMode: Boolean
        get() = intent.hasExtra(MODE) && intent.getStringExtra(MODE) == MODE_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        if (!isCameraMode) {
            supportActionBar?.title = getString(R.string.pick_image)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout, PhotoPickerFragment())
                    .commit()
        } else {
            supportActionBar?.title = getString(R.string.capture_image)
            supportActionBar?.elevation = 0.0f
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout, CameraFragment())
                    .commit()

        }
    }

    fun setActionBarTitle(title: String, count: Int) {
        supportActionBar?.title = title
        supportActionBar?.subtitle = count.toString() + getString(R.string.images)
    }

    fun resetActionBarTitle() {
        supportActionBar?.title = getString(R.string.pick_image)
        supportActionBar?.subtitle = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }


    companion object {
        private const val MODE = "mode"
        private const val MODE_CAMERA = "modeCamera"
        private const val MODE_PICK = "modePick"
        private const val REQUEST_CODE = "request_code"
        const val IMAGE_URI = "imageUri"

        private val TAG = "ImagePickerActivity"

        fun startCameraMode(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_CAMERA)
            activity.startActivityForResult(intent, requestCode)
        }

        fun startImagePickMode(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_PICK)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}