package m7mdra.com.picker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import m7mdra.com.picker.picker.AlbumFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImagePickerActivity : AppCompatActivity() {


    private val isCameraMode: Boolean
        get() = intent.hasExtra(MODE) && intent.getStringExtra(MODE) == MODE_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.write_permission_dialog_title))
                        .setMessage(getString(R.string.write_permission_dialog_message))
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                                    123)
                        }.setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            finish()
                        }
                        .create().show()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                        123)
            }
        } else {
            bindUi()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            bindUi()
        else
            finish()
    }

    private fun bindUi() {
        if (!isCameraMode) {
            supportActionBar?.title = getString(R.string.pick_image)
            val multiplePicks = intent.getBooleanExtra(PICK_COUNT, false)
            log("Multiple picks mode: passed to fragment $multiplePicks")

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout,
                            AlbumFragment.newInstance(multiplePicks))
                    .commit()
        } else {
            dispatchTakePictureIntent()
        }
    }


    private lateinit var capturedImagePath: String

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "image_${timeStamp}_", ".jpg", storageDir).apply {
            capturedImagePath = absolutePath
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also { _ ->
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    toast("Unable to find default Camera application")
                    finish()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "m7mdra.com.imagepicker.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            CropImage.activity(Uri.fromFile(File(capturedImagePath)))
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAllowRotation(true)
                    .setAllowCounterRotation(true)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(70)
                    .setOutputUri(Uri.fromFile(File(capturedImagePath)))
                    .start(this)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            when (resultCode) {
                RESULT_OK -> {
                    val resultUri = result.uri.path
                    log(resultUri)
                    val intent = Intent()
                    intent.putExtra(ImagePickerActivity.IMAGE_URI, resultUri)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    val error = result.error
                    toast("failed to capture image. ${error.message}")
                    finish()
                }
                else -> finish()
            }
        } else finish()
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
        internal const val PICK_COUNT = "pickCount"
        const val SELECTED_IMAGE_URIS = "SelectedImageUris"
        const val IMAGE_URI = "imageUri"
        private const val REQUEST_TAKE_PHOTO = 1

        @JvmStatic
        fun startCameraMode(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_CAMERA)
            activity.startActivityForResult(intent, requestCode)
        }

        @JvmStatic
        fun startImagePickMode(activity: Activity, requestCode: Int, multiplePicks: Boolean) {
            val intent = Intent(activity, ImagePickerActivity::class.java)
            intent.putExtra(MODE, MODE_PICK)
            intent.putExtra(PICK_COUNT, multiplePicks)
            log("Multiple picks mode: $multiplePicks")
            activity.startActivityForResult(intent, requestCode)
        }
    }
}