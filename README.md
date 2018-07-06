
# Picker

A small library that allows you to pick/capture image from inside your application with 5 lines of code .

  ##	demo
  ![enter image description here](https://github.com/m7mdra/Picker/raw/master/art/demo.gif)

# Usage

  

1. add this line to your manifest to allow the android os to recognize

our activity.

  

<activity android:name="m7mdra.com.picker.ImagePickerActivity"/>

2. invoke the static method `ImagePickerActivity#startCameraMode` to start camera mode or `ImagePickerActivity#startImagePickMode`to start image picking mode.

`ImagePickerActivity#startCameraMode`and `ImagePickerActivity#startImagePickMode`basically a `startActivityForResult` method with extra parameters (Extras)

val pickImge=findViewByid(R.id.pickImage) as Button

val captureImage=findViewById(R.id.captureImage) as Button

pickImage.setOnClickListener {

ImagePickerActivity.startImagePickMode(this, 132)

}

captureImage.setOnClickListener {

ImagePickerActivity.startCameraMode(this, 132)

}

  

3. override onActivityResult

  

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

super.onActivityResult(requestCode, resultCode, data)

if (requestCode == 132 && resultCode == Activity.RESULT_OK) {

// URI to the captured/picked Image

val uri = data?.getStringExtra(ImagePickerActivity.IMAGE_URI)

}

}

  

thats it.

  

Dependencies:

this library depends on the [CameraKit-Android](https://android-arsenal.com/details/1/5383)

  

# Todo

  

- allow to pick multiple images in the same intent.

- add support to pick videos and audio files.

- improve the user interface.