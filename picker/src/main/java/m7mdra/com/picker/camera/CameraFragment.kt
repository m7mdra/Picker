package m7mdra.com.picker.camera


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.wonderkiln.camerakit.*
import kotlinx.android.synthetic.main.fragment_camera.*
import m7mdra.com.picker.ImagePickerActivity
import m7mdra.com.picker.R
import m7mdra.com.picker.saveImage
import okio.BufferedSink
import okio.Okio
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CameraFragment : Fragment() {
    private lateinit var ctx: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val iv = flash.getChildAt(0) as ImageView
        flash.setOnClickListener {
            val height = flash.height
            iv.animate().translationY(height.toFloat()).setDuration(100).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    iv.translationY = (-(height / 2)).toFloat()
                    when (camera.flash) {
                        CameraKit.Constants.FLASH_ON -> {
                            iv.setImageResource(R.drawable.ic_flash_auto_white_24dp)
                            camera.flash = CameraKit.Constants.FLASH_AUTO
                        }
                        CameraKit.Constants.FLASH_AUTO -> {
                            iv.setImageResource(R.drawable.ic_flash_off_white_24dp)
                            camera.flash = CameraKit.Constants.FLASH_OFF
                        }
                        else -> {
                            iv.setImageResource(R.drawable.ic_flash_on_white_24dp)
                            camera.flash = CameraKit.Constants.FLASH_ON
                        }
                    }

                    iv.animate().translationY(0f).setDuration(50).setListener(null).start()
                }
            }).start()
        }
        clickme.setOnClickListener { camera.captureImage() }
        camera.addCameraKitListener(object : CameraKitEventListener {
            override fun onEvent(cameraKitEvent: CameraKitEvent) {

            }

            override fun onError(cameraKitError: CameraKitError) {

            }

            override fun onImage(cameraKitImage: CameraKitImage) {
                MediaPlayer.create(activity, R.raw.camera_shutter).apply {
                    start()
                }
                val jpeg = cameraKitImage.jpeg
                try {
                    val image = saveImage(ctx, jpeg)
                    if (image != null) {
                        val data = Intent()
                        data.putExtra(ImagePickerActivity.IMAGE_URI, image.path)

                        fragmentManager?.beginTransaction()?.apply {
                            replace(R.id.camera_layout, ImagePreviewFragment.newInstance(image.absolutePath))
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            addToBackStack("")
                            commit()
                        }
                    } else {
                        Toast.makeText(activity, "Failed to capture Image", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: FileNotFoundException) {
                    Toast.makeText(activity, "Failed to capture Image", Toast.LENGTH_SHORT).show()

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed to capture Image", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onVideo(cameraKitVideo: CameraKitVideo) {

            }
        })
        front.setOnClickListener {
            val oa1 = ObjectAnimator.ofFloat(front, "scaleX", 1f, 0f).setDuration(150)
            val oa2 = ObjectAnimator.ofFloat(front, "scaleX", 0f, 1f).setDuration(150)
            oa1.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    front!!.setImageResource(R.drawable.ic_photo_camera_white_24dp)
                    oa2.start()
                }
            })
            oa1.start()
            camera.facing =
                    if (camera.facing == CameraKit.Constants.FACING_FRONT)
                        CameraKit.Constants.FACING_BACK
                    else CameraKit.Constants.FACING_FRONT
        }
    }


    override fun onStart() {
        camera.start()
        super.onStart()
    }

    override fun onStop() {
        camera.stop()
        super.onStop()
    }

}
