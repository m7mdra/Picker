

# Picker

A small library that allows you to pick/capture image from inside your application with 5 lines of code .

what dose this library do exactly?

 - Query the system for all images 
 - Handle Run-time Permissions , permissions includes (`WRITE_EXTERNAL_STORAGE`,`READ_EXTERNAL_STORAGE`)
 - Ability you to pick multiple images in one shot
 - Ability to capture Image from camera

 ### demo can be found [here](https://www.youtube.com/watch?v=spbX644P8Xg)

## Usage


1. add this line to your manifest to allow the android os to recognize our activity.

delcare these permissions:

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

then add this line to let the android os recognize our activity :

```xml
<activity android:name="m7mdra.com.picker.ImagePickerActivity"
android:theme="@style/PickerAppTheme"
/>
```

and if you want to crop image after capturing add this too:

```xml
<activity android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
    android:theme="@style/PickerAppTheme"/>
  
```

add `FileProvider` so we can get path of captured image from camera

```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths"></meta-data>
</provider>
```

 `android:theme="@style/PickerAppTheme"` add this line to use the style defined by the library otherwise it will inherit it from the application theme

2. invoke the static method `ImagePickerActivity#startCameraMode` to start camera mode or `ImagePickerActivity#startImagePickMode`to start image picking mode.

`ImagePickerActivity#startCameraMode`and `ImagePickerActivity#startImagePickMode`basically a `startActivityForResult` method with extra parameters (Extras)

```kotlin
val pickImge=findViewByid(R.id.pickImage) as Button
val captureImage=findViewById(R.id.captureImage) as Button

pickImage.setOnClickListener {
				// pass true to allow multiple picks
   //activity: Activity, requestCode: Int, multiplePicks: Boolean
	ImagePickerActivity.startImagePickMode(this, 132, false)
}
captureImage.setOnClickListener {
	//activity: Activity, requestCode: Int, packageName: String, cropImage: Boolean
	ImagePickerActivity.startCameraMode(this, 132,true)
}
```

  


3. override onActivityResult
    ​		

    ```
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    	super.onActivityResult(requestCode, resultCode, data)
    		if (requestCode == 132 && resultCode == Activity.RESULT_OK) {
    			// URI to the captured/picked Image
    			val uri = data?.getStringExtra(ImagePickerActivity.IMAGE_URI)
    			//if you passed true to startImagePickMode use the line below
    			//val uriList:ArrayList<Image>? = data?.getParcelableArrayListExtra(ImagePickerActivity.SELECTED_IMAGE_URIS)			}
    }
    ```

  



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
	        implementation 'com.github.m7mdra:Picker:0.24'
		}

