package m7mdra.com.picker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.view.MenuItem

import m7mdra.com.picker.camera.CameraFragment
import m7mdra.com.picker.picker.AlbumFragment


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
            val multiplePicks = intent.getBooleanExtra(PICK_COUNT, false)
            log("Multiple picks mode: passed to fragment $multiplePicks")

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout,
                            AlbumFragment.newInstance(multiplePicks))
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
        supportActionBar?.subtitle = "$count ${getString(R.string.images)}"
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
        internal const val PICK_COUNT = "pickCount"
        const val SELECTED_IMAGE_URIS = "SelectedImageUris"
        const val IMAGE_URI = "imageUri"
        fun startCameraMode(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_CAMERA)
            activity.startActivityForResult(intent, requestCode)
        }

        fun startImagePickMode(activity: Activity, requestCode: Int, multiplePicks: Boolean) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_PICK)
            intent.putExtra(PICK_COUNT, multiplePicks)
            log("Multiple picks mode: $multiplePicks")
            activity.startActivityForResult(intent, requestCode)
        }
    }
}