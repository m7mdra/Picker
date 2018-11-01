package m7mdra.com.imagepicker


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import m7mdra.com.picker.ImagePickerActivity
import m7mdra.com.picker.log
import m7mdra.com.picker.model.Image
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickImage.setOnClickListener {
            ImagePickerActivity.startImagePickMode(this, 132, false)

        }
        captureImage.setOnClickListener {
            ImagePickerActivity.startCameraMode(this, 132, packageName)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 132 && resultCode == Activity.RESULT_OK) {
            val uri: String? = data?.getStringExtra(ImagePickerActivity.IMAGE_URI)
            if (uri != null)
                Picasso.with(this).load(File(uri)).into(imageView)
        }
    }

}
