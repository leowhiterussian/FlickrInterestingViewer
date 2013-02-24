#android-remoteimageview

This is a fork of the RemoteImageView class and support code from the great
ignition project, which can be found at 
[http://github.com/kaeppler/ignition](http://github.com/kaeppler/ignition).

## Features

* Graceful OOM prediction, waits until it seems like there is enough memory
  to decode the bitmap.
* Catches OOM exceptions and retries (The latter statement about detection 
  refers to avoiding OOM exceptions outright, but if they occur, they are
  handled)
* Build with ant. No need to depend on the entire ignition project or use
  maven.

When OOMs are caught or predicted, the GC will be hinted to run and all threads
will sleep at a random interval until trying again. The range for this can be
adjusted in `RemoteImageLoaderJob.java` and the magic constants that control
OOM detection can be changed in `MemoryHelper.java`. Using random intervals
for the sleep means that all the threads won't retry opening the bitmap
simultaneously (a recipe for OOM if let to happen)

## How-To

1. Follow the steps you would normally use for set up a library project
   with your IDE of choice, or add an android.library.reference.<n> line in
   project.properties.

2. Create an XML layout similar to the following [1]
  
      
      <?xml version="1.0" encoding="utf-8"?>
      <RelativeLayout 
          xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:ignition="http://github.com/ignition/schema"
          android:layout_width="match_parent"
          android:layout_height="match_parent" 
          >

         <com.tomdignan.remoteimage.RemoteImageView
            android:id="@+id/image"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            android:scaleType="centerCrop"
            ignition:autoLoad="false"
          />

      </RelativeLayout>

3. In your java code, do something like this:

      
      RemoteImageView image = (RemoteImageView) view.findViewById(R.id.image);
      image.setImageUrl("http://i.imgur.com/JQdTG.png");
      image.loadImage();

That's it! Asynchronous image loading & it's cached too.

##Author

Original code by [Matthias Kaeppler](http://github.com/kaeppler)

OOM-proofed and refactored for use with ant by Tom Dignan <tom@tomdignan.com>

##Footnotes

1. The above example is nested because the author of this documentation assumes this
   will be easier to understand for most developers.

   It is possible to use the RemoteImageView class without a container layout as follows:

<com.tomdignan.remoteimage.RemoteImageView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:ignition="http://github.com/ignition/schema"
    android:id="@+id/image"
    android:layout_width="match_parent"
    android:layout_height="300dp"
    android:scaleType="centerCrop"
    ignition:autoLoad="false"
/>


