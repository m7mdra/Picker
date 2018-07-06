package m7mdra.com.picker.picker


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_images.*
import m7mdra.com.picker.ImagePickerActivity
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.model.Album


/**
 * A simple [Fragment] subclass.
 */
class ImagesFragment : Fragment(), ItemClickListener<String> {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = arguments?.getSerializable(IMAGES) as Album
        (activity as ImagePickerActivity).setActionBarTitle(images.folderName, images.pathList.size)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.setHasFixedSize(true)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerView.adapter = ImagesAdapter(images, Picasso.with(activity), this)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as ImagePickerActivity).resetActionBarTitle()
    }



    override fun onClick(view: View, s: String, position: Int) {
        Log.d("MEGA", "selected Image Uri:  $s")
        val data = Intent()
        data.putExtra(ImagePickerActivity.IMAGE_URI, s)
        activity?.apply {
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }

    companion object {


        const val IMAGES = "images"

        fun newInstance(images: Album): ImagesFragment {
            val args = Bundle()
            args.putSerializable(IMAGES, images)
            val fragment = ImagesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}