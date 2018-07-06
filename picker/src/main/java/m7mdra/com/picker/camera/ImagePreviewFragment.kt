package m7mdra.com.picker.camera


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_preview.*
import m7mdra.com.picker.ImagePickerActivity
import m7mdra.com.picker.R

import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class ImagePreviewFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val img = arguments!!.getString("img", null)
        Picasso.with(activity).load(File(img))
                .into(imageView)

        accept_image_button.setOnClickListener {
            val data = Intent()
            data.putExtra(ImagePickerActivity.IMAGE_URI, img)
            activity?.setResult(Activity.RESULT_OK, data)
            activity?.finish()
        }

        reject_image_button.setOnClickListener { activity?.onBackPressed() }
    }

    companion object {

        fun newInstance(imageUri: String): ImagePreviewFragment {
            val args = Bundle()
            args.putString("img", imageUri)
            val fragment = ImagePreviewFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor