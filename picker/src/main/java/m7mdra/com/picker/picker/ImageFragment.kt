package m7mdra.com.picker.picker


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_images.*
import m7mdra.com.picker.ImagePickerActivity
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.log
import m7mdra.com.picker.model.Album
import m7mdra.com.picker.picker.adapter.ImageAdapter


/**
 * A simple [Fragment] subclass.
 */
class ImageFragment : Fragment(), ItemClickListener<String>, ActionMode.Callback {
    private var supportActionMode: ActionMode? = null
    private lateinit var imageAdapter: ImageAdapter

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        activity?.apply {
            val intent = Intent()
            intent.putParcelableArrayListExtra(ImagePickerActivity.SELECTED_IMAGE_URIS, imageAdapter.getSelected())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {

        mode?.menuInflater?.inflate(R.menu.menu_picker, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true

    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = arguments?.getParcelable<Album>(IMAGES) as Album
        val multiplePicks: Boolean = arguments?.getBoolean(ImagePickerActivity.PICK_COUNT)!!

        if (multiplePicks) {
            supportActionMode = (activity as ImagePickerActivity).startActionMode(this)
            supportActionMode?.title = getString(R.string._0_selected)
            supportActionMode?.subtitle = images.folderName
        } else {
            (activity as ImagePickerActivity).setActionBarTitle(images.folderName, images.pathList.size)
        }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        imageAdapter = ImageAdapter(images, Picasso.get(), this, multiplePicks)
        { _, i -> supportActionMode?.title = String.format("%d %s", i, getString(R.string.selected)) }
        recyclerView.adapter = imageAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as ImagePickerActivity).resetActionBarTitle()
    }


    override fun onClick(view: View, uri: String, position: Int) {
          val data = Intent()
          data.putExtra(ImagePickerActivity.IMAGE_URI, uri)
          activity?.apply {
              setResult(Activity.RESULT_OK, data)
              finish()
          }
       /* fragmentManager?.apply {
            beginTransactio()
                    .replace(R.id.fragment_layout, CropFragment.newInstance(uri))
                    .addToBackStack("")
                    .commit()

        }*/
    }

    companion object {


        const val IMAGES = "images"

        fun newInstance(images: Album, multiplePicks: Boolean = false): ImageFragment {
            val args = Bundle()
            args.putParcelable(IMAGES, images)
            args.putBoolean(ImagePickerActivity.PICK_COUNT, multiplePicks)
            log("Multiple picks mode: in pick fragment $multiplePicks")
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
