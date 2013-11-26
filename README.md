### OverlayImageView
OverlayImageView is a simple extension of Android's ImageView that allows drawing an overlay on top of it. 

### Usage
Import OverlayImageView as an Android library project and reference it in your project. 
Then simply add OverlayImageView views programmatically or in your xml layouts like this:
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:roboto="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <com.eevoskos.overlayimageview.OverlayImageView
        android:layout_width="96dp"
        android:layout_height="96dp"
        android:layout_gravity="center"
        android:src="#aaaaaa"
        app:overlay_gravity="center"
        app:overlay_padding="8dp"
        app:overlay_src="@drawable/ic_overlay" />

</FrameLayout>
```

### Sample
The library includes a sample application (in the ```/sample``` folder) that demonstrates how the OverlayImageView looks like:

![Example image from the OverlayImageView Sample][1]

### Developed By
Stratos Theodorou - <eeVoskos@gmail.com>

### License

    Copyright 2013 Stratos Theodorou

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: /art/screenshot.png
