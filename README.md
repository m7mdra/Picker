

# Picker

A small library that allows you to pick/capture image from inside your application with 5 lines of code .

what dose this library do exactly?

 - Query the system for all images 
 - Handle Run-time Permissions , permissions includes (`WRITE_EXTERNAL_STORAGE`,`READ_EXTERNAL_STORAGE`,`CAMERA`)
 - Ability you to pick multiple images in one shot
 - Ability to capture Image from camera

 ### demo can be found [here](https://www.youtube.com/watch?v=spbX644P8Xg)

## Usage


1. add this line to your manifest to allow the android os to recognize our activity.

delcare these permissions:
  
	<uses-permission android:name="android.permission.CAMERA"/>  
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

then add this line to let the android os recognize our activity :

	<activity android:name="m7mdra.com.picker.ImagePickerActivity"
	android:theme="@style/PickerAppTheme"
	/>

 `android:theme="@style/PickerAppTheme"` add this line to use the style defined by the library otherwise it will inhert it from the application theme
 
2. invoke the static method `ImagePickerActivity#startCameraMode` to start camera mode or `ImagePickerActivity#startImagePickMode`to start image picking mode.

`ImagePickerActivity#startCameraMode`and `ImagePickerActivity#startImagePickMode`basically a `startActivityForResult` method with extra parameters (Extras)

	val pickImge=findViewByid(R.id.pickImage) as Button
	val captureImage=findViewById(R.id.captureImage) as Button
	
	pickImage.setOnClickListener {
					// pass true to allow multiple picks
		ImagePickerActivity.startImagePickMode(this, 132, false)
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
				//if you passed true to startImagePickMode use the line below
				//val uriList:ArrayList<Image>? = data?.getParcelableArrayListExtra(ImagePickerActivity.SELECTED_IMAGE_URIS)			}
			
		}

  



thats it.

  ### Add to your project? 
  Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2.**  Add the dependency

	dependencies {
	        implementation 'com.github.m7mdra:picker:0.22.1'
	}


### Dependencies:

this library depends on the [CameraKit-Android](https://android-arsenal.com/details/1/5383)

  

# Todo

  

- ~~Allow to pick multiple images in the same intent.~~

- Add support to capture/pick other media type like: audio,video etc...

- ~~Improve the user interface.~~
