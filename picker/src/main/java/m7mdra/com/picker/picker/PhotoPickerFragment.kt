package m7mdra.com.picker.picker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo_picker.*
import m7mdra.com.picker.ImageProvider
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.model.Album


class PhotoPickerFragment : Fragment(), ItemClickListener<Album> {
    override fun onClick(view: View, t: Album, position: Int) {
        fragmentManager?.beginTransaction()?.apply {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                replace(R.id.fragment_layout, ImagesFragment.newInstance(t))
                addToBackStack("")
                commit()
        }
    }

    private lateinit var activity: Activity
    private lateinit var imageProvider: ImageProvider
    private lateinit var picasso: Picasso
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageProvider = ImageProvider(activity.contentResolver)
        picasso = Picasso.with(activity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindPickerView()
    }

    private fun bindPickerView() {
        if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(activity)
                        .setTitle("Read and Write permission Are Required.")
                        .setMessage("We use these permission to allow you pick/capture images")
                        .setCancelable(false)
                        .setPositiveButton("ok", { _, _ ->
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                                    123)
                        }).setNegativeButton("Cancel", { dialogInterface, _ -> dialogInterface.dismiss() })
                        .create().show()
            } else {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                        123)
            }
        } else {
            bindDataToList()
        }
    }

    private fun bindDataToList() {
        val images = imageProvider.loadImages()
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.setHasFixedSize(true)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerView.adapter = ImagesFolderAdapter(images, picasso, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            bindDataToList()
        // TODO: 26/06/18 else
    }

}
