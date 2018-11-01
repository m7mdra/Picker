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
import m7mdra.com.picker.*
import m7mdra.com.picker.model.Album
import m7mdra.com.picker.picker.adapter.AlbumAdapter


class AlbumFragment : Fragment(), ItemClickListener<Album> {
    override fun onClick(view: View, item: Album, position: Int) {
        fragmentManager?.beginTransaction()?.apply {
            val multiplePicks = arguments?.getBoolean(ImagePickerActivity.PICK_COUNT)
            replace(R.id.fragment_layout, ImageFragment.newInstance(item, multiplePicks!!))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
                        .setTitle(getString(R.string.write_permission_dialog_title))
                        .setMessage(getString(R.string.write_permission_dialog_message))
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                                    123)
                        }.setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            activity.finish()
                        }
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
        recyclerView.adapter = AlbumAdapter(images, picasso, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            bindDataToList()
        // TODO: 26/06/18 else
    }

    companion object {
        fun newInstance(multiplePicks: Boolean): Fragment {
            val bundle = Bundle()
            bundle.putBoolean(ImagePickerActivity.PICK_COUNT, multiplePicks)
            val photoPickerFragment = AlbumFragment()
            photoPickerFragment.arguments = bundle

            return photoPickerFragment
        }
    }

}
