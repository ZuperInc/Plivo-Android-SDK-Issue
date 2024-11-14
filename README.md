# Android Integration Issue: Mapbox, OpenCV, and Plivo SDK

## 📋 Overview

This repository demonstrates a conflict issue when integrating three Android SDKs:
- **Mapbox SDK** (Map background view)
- **OpenCV SDK** (Image processing)
- **Plivo SDK** (VoIP functionalities)

The app includes:
- A Mapbox map view as the background.
- A "Plivo login" button at the bottom center.

## ❌ Problem

The integration fails during the build process due to a conflict in the shared C++ library (`libc++_shared.so`) included by all three SDKs:

1. **Mapbox SDK** (`11.3.0`)
2. **OpenCV SDK** (`4.6.0.0`)
3. **Plivo SDK** (`3.2.2-beta`)

### ⚠️ Build Error

The error message observed during the build process:

```
3 files found with path 'lib/arm64-v8a/libc++_shared.so' from inputs:
/Users/shrikanthravi/.gradle/caches/transforms-4/bb825cf84525505d5b6e95c6278320b4/transformed/common-24.3.1/jni/arm64-v8a/libc++_shared.so
/Users/shrikanthravi/.gradle/caches/transforms-4/251c19cef47b75bfdaedbdbf383b843b/transformed/opencv-4.6.0.0/jni/arm64-v8a/libc++_shared.so
/Users/shrikanthravi/.gradle/caches/transforms-4/397c3ffc119254687f1a575528024c5a/transformed/endpoint-3.2.2-beta/jni/arm64-v8a/libc++_shared.so
```

## 🛠️ Current Workaround

We tried resolving the conflict using the `pickFirst` configuration in `build.gradle`:

```gradle
jniLibs.pickFirsts += listOf("lib/**/libc++_shared.so")
```

### 🚨 Issue with Workaround

This workaround forces Gradle to pick the first occurrence of libc++_shared.so, which is typically from the Mapbox SDK. As a result, when the app calls endpoint.login() from the Plivo SDK, it crashes due to missing native methods.

## 🐛 Steps to Reproduce the Crash

1. Clone the repository
2. Open the project in Android Studio.
3. Provide a USERNAME and PASSSWORD for plivo login
4. Build and run the app on a physical Android device.
5. Observe the Mapbox map view as the background.
6. Click the "Plivo login" button at the bottom center.
7. The app crashes due to missing native methods from the Plivo SDK.

## 🤔 Expected Behavior

The app should successfully call the endpoint.login() method and proceed with the Plivo login flow without any crashes.

## 💥 Actual Behavior

The app crashes when the Plivo login button is clicked. Please find the logs below.
```
---------------------------- PROCESS ENDED (5869) for package com.example.plivoandroidsdkissue ----------------------------
---------------------------- PROCESS STARTED (5971) for package com.example.plivoandroidsdkissue ----------------------------
11:09:22.249  W  Unable to open '/data/data/com.example.plivoandroidsdkissue/code_cache/.overlay/base.apk/classes3.dm': No such file or directory
11:09:22.250  W  Unable to open '/data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.dm': No such file or directory
11:09:22.250  W  Unable to open '/data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.dm': No such file or directory
11:09:22.432  D  Configuring clns-6 for other apk /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk. target_sdk_version=35, uses_libraries=, library_path=/data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/lib/arm64:/data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!/lib/arm64-v8a, permitted_path=/data:/mnt/expand:/data/user/0/com.example.plivoandroidsdkissue
11:09:22.443  V  Currently set values for:
11:09:22.443  V    angle_gl_driver_selection_pkgs=[]
11:09:22.443  V    angle_gl_driver_selection_values=[]
11:09:22.444  V  ANGLE GameManagerService for com.example.plivoandroidsdkissue: false
11:09:22.444  V  com.example.plivoandroidsdkissue is not listed in per-application setting
11:09:22.444  V  Neither updatable production driver nor prerelease driver is supported.
11:09:22.457  I  MapboxMapsInitializer create() is called
11:09:22.457  I  MapboxInitializer started MapboxMapsInitializerImpl initialization, attempt 1
11:09:22.469  I  [common]: Using Mapbox Common SDK v24.3.1(0b57c207b)
11:09:22.498  I  [maps-core]: Using Mapbox Core Maps SDK v11.3.0(36ab08e5ed)
11:09:22.500  I  [MapboxReachability]: Application permission for ACCESS_NETWORK_STATE granted
11:09:22.508  I  Initialized MapboxMapsInitializerImpl successfully
11:09:22.511  I  MapboxSDKCommonInitializer create() is called
11:09:22.511  I  Already initialized MapboxSDKCommonInitializerImpl before successfully
11:09:22.517  D  loaded /vendor/lib64/egl/libEGL_emulation.so
11:09:22.520  D  loaded /vendor/lib64/egl/libGLESv1_CM_emulation.so
11:09:22.521  D  loaded /vendor/lib64/egl/libGLESv2_emulation.so
11:09:22.555  D  Compat change id reported: 237531167; UID 10196; state: DISABLED
11:09:22.556  W  Unknown dataspace 0
11:09:22.566  I  [LifecycleUtils]: Task: TaskInfo{userId=0 taskId=418 displayId=0 isRunning=true baseIntent=Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=com.example.plivoandroidsdkissue/.MainActivity } baseActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} topActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} origActivity=null realActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} numActivities=1 lastActiveTime=1619759 supportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defaultMinSize=220 token=WCT{android.window.IWindowContainerToken$Stub$Proxy@21fb280} topActivityType=1 pictureInPictureParams=null shouldDockBigOverlays=false launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=null topActivityInfo=ActivityInfo{ee230b9 com.example.plivoandroidsdkissue.MainActivity} launchCookies=[] positionInParent=Point(0, 0) parentTaskId=-1 isFocused=true isVisible=true isVisibleRequested=true isSleeping=false topActivityInSizeCompat=false topActivityEligibleForLetterboxEducation= false topActivityLetterboxed= false isFromDoubleTap= false topActivityLetterboxVerticalPosition= -1 topActivityLetterboxHorizontalPosition= -1 topActivityLetterboxWidth=-1 topActivityLetterboxHeight=-1 locusId=null displayAreaFeatureId=1 cameraCompatControlState=hidden}
11:09:22.662  W  Accessing hidden field Landroid/graphics/Typeface;->sSystemFontMap:Ljava/util/Map; (unsupported, reflection, allowed)
11:09:22.662  I  Couldn't map font family for local ideograph, using sans-serif instead
11:09:22.680  I  [maps-android\ThreadChecker]: No boolean metadata found for key com.mapbox.maps.ThreadChecker Attempt to invoke virtual method 'boolean android.os.Bundle.containsKey(java.lang.String)' on a null object reference
11:09:22.680  I  [maps-android\ThreadChecker]: Unable to lookup build config of application. com.example.plivoandroidsdkissue.BuildConfig
11:09:22.692  W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(boolean, kotlin.jvm.functions.Function1) failed lock verification and will run slower.
                 Common causes for lock verification issues are non-optimized dex code
                 and incorrect proguard optimizations.
11:09:22.692  W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate$default(androidx.compose.runtime.snapshots.SnapshotStateList, boolean, kotlin.jvm.functions.Function1, int, java.lang.Object) failed lock verification and will run slower.
11:09:22.692  W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateList.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
11:09:22.692  W  Method void androidx.compose.runtime.snapshots.SnapshotStateList.update(boolean, kotlin.jvm.functions.Function1) failed lock verification and will run slower.
11:09:22.693  W  Method void androidx.compose.runtime.snapshots.SnapshotStateList.update$default(androidx.compose.runtime.snapshots.SnapshotStateList, boolean, kotlin.jvm.functions.Function1, int, java.lang.Object) failed lock verification and will run slower.
11:09:22.734  I  [maps-android\Mbgl-RenderThread]: Renderer resumed, renderThreadPrepared=false, surface.isValid=null
11:09:22.735  I  [maps-android\Mbgl-RenderThread]: renderThreadPrepared=false and Android surface is not valid (isValid=null). Waiting for new one.
11:09:22.735  D  Compat change id reported: 193247900; UID 10196; state: ENABLED
11:09:22.763  I  [maps-android\Mbgl-RenderThread]: renderThreadPrepared=false and Android surface is not valid (isValid=null). Waiting for new one.
11:09:22.765  W  Failed to choose config with EGL_SWAP_BEHAVIOR_PRESERVED, retrying without...
11:09:22.765  W  Failed to initialize 101010-2 format, error = EGL_SUCCESS
11:09:22.772  I  [maps-android\Mbgl-RenderThread]: onSurfaceCreated
11:09:22.772  I  [maps-android\Mbgl-RenderThread]: onSurfaceCreated: waiting Android surface to be processed...
11:09:22.772  I  [maps-android\Mbgl-RenderThread]: Setting up render thread, flags: creatingSurface=true, nativeRenderCreated=false, eglContextMadeCurrent=false, eglContextCreated=false, paused=false
11:09:22.775  I  [maps-android\Mbgl-EGLConfigChooser]: In emulator: false
11:09:22.779  I  mapper 4.x is not supported
11:09:22.781  I  [maps-android\Mbgl-EglCore]: EGLContext created, client version 3
11:09:22.784  E  Unable to match the desired swap behavior.
11:09:22.786  I  [maps-core]: Using OpenGL render backend
11:09:22.787  I  [maps-android\Mbgl-RenderThread]: Native renderer created.
11:09:22.788  I  [maps-android\Mbgl-RenderThread]: onSurfaceCreated: Android surface was processed.
11:09:22.833  I  [LifecycleUtils]: Task: TaskInfo{userId=0 taskId=418 displayId=0 isRunning=true baseIntent=Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=com.example.plivoandroidsdkissue/.MainActivity } baseActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} topActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} origActivity=null realActivity=ComponentInfo{com.example.plivoandroidsdkissue/com.example.plivoandroidsdkissue.MainActivity} numActivities=1 lastActiveTime=1619759 supportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defaultMinSize=220 token=WCT{android.window.IWindowContainerToken$Stub$Proxy@aa66212} topActivityType=1 pictureInPictureParams=null shouldDockBigOverlays=false launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=null topActivityInfo=ActivityInfo{6a56de3 com.example.plivoandroidsdkissue.MainActivity} launchCookies=[] positionInParent=Point(0, 0) parentTaskId=-1 isFocused=true isVisible=true isVisibleRequested=true isSleeping=false topActivityInSizeCompat=false topActivityEligibleForLetterboxEducation= false topActivityLetterboxed= false isFromDoubleTap= false topActivityLetterboxVerticalPosition= -1 topActivityLetterboxHorizontalPosition= -1 topActivityLetterboxWidth=-1 topActivityLetterboxHeight=-1 locusId=null displayAreaFeatureId=1 cameraCompatControlState=hidden}
11:09:22.843  V  JNI_OnLoad
11:09:22.846  D  Compat change id reported: 289878283; UID 10196; state: ENABLED
11:09:22.846  D  [endpoint]librtcsip loaded
11:09:22.848  E  device/generic/goldfish-opengl/system/GLESv2_enc/GL2Encoder.cpp:s_glGetStringi:4973 GL error 0x501
11:09:22.848  D  [endpoint]Starting module..
11:09:22.853  D  Audio Toggle listener registered successfully.
11:09:22.861  D  Plivo SDK initializing with options:  {} failed due to [android.os.Parcel.createExceptionOrNull(Parcel.java:3057), android.os.Parcel.createException(Parcel.java:3041), android.os.Parcel.readException(Parcel.java:3024), android.os.Parcel.readException(Parcel.java:2966), android.app.IActivityManager$Stub$Proxy.registerReceiverWithFeature(IActivityManager.java:5684), android.app.ContextImpl.registerReceiverInternal(ContextImpl.java:1852), android.app.ContextImpl.registerReceiver(ContextImpl.java:1792), android.app.ContextImpl.registerReceiver(ContextImpl.java:1780), android.content.ContextWrapper.registerReceiver(ContextWrapper.java:755), com.plivo.endpoint.Endpoint.registerAudioOutputDeviceReceiver(Endpoint.java:296), com.plivo.endpoint.Endpoint.registerReceivers(Endpoint.java:277), com.plivo.endpoint.Endpoint.initLib(Endpoint.java:458), com.plivo.endpoint.Endpoint.<init>(Endpoint.java:107), com.plivo.endpoint.Endpoint.newInstance(Endpoint.java:120), com.example.plivoandroidsdkissue.MainActivityKt$MapView$1.invokeSuspend(MainActivity.kt:59), kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33), kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:108), androidx.compose.ui.platform.AndroidUiDispatcher.performTrampolineDispatch(AndroidUiDispatcher.android.kt:81), androidx.compose.ui.platform.AndroidUiDispatcher.access$performTrampolineDispatch(AndroidUiDispatcher.android.kt:41), androidx.compose.ui.platform.AndroidUiDispatcher$dispatchCallback$1.run(AndroidUiDispatcher.android.kt:57), android.os.Handler.handleCallback(Handler.java:958), android.os.Handler.dispatchMessage(Handler.java:99), android.os.Looper.loopOnce(Looper.java:205), android.os.Looper.loop(Looper.java:294), android.app.ActivityThread.main(ActivityThread.java:8177), java.lang.reflect.Method.invoke(Native Method), com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:552), com.android.internal.os.ZygoteInit.main(ZygoteInit.java:971)]
11:09:22.867  D  newInstance true
11:09:22.867  D  Plivo SDK initialized successfully in true mode
11:09:22.910  D  Projection(value=) projection  applied
11:09:22.953  D  NetworkChangeReceiver Network changed
11:09:22.994  D  tagSocket(136) with statsTag=0xffffffff, statsUid=-1
11:09:23.009  D  tagSocket(140) with statsTag=0xffffffff, statsUid=-1
11:09:23.923  D  app_time_stats: avg=34.15ms min=10.85ms max=435.76ms count=32
11:09:27.676  I  [tile_store]: Searching for stray files to clean up
11:09:27.677  I  [tile_store]: Cleanup of stray partial downloads complete
11:09:27.678  I  [tile_store]: Searching for stale partial downloads to clean up
11:09:27.678  I  [tile_store]: Cleanup of stray partial downloads complete
11:09:27.954  D  Installing profile for com.example.plivoandroidsdkissue
11:09:30.677  D  app_time_stats: avg=2251.03ms min=16.79ms max=6697.93ms count=3
11:09:30.724  D  Login initiated with username:- Sriram712881884819549799377481 deviceToken:  certificateId: 
11:09:30.729  D   registerTimeOut() m_reg_timeout 2592000
11:09:30.729  D  clearRegistrationRetry
11:09:30.729  D  registerUser: uri with (username/password/token/certid): <sip:Sriram712881884819549799377481@phone.plivo.com;transport=tls>
11:09:30.729  D  setupTransport: url: address: <sip:Sriram712881884819549799377481@phone.plivo.com;transport=tls> contact_url: <sip:Sriram712881884819549799377481@phone.plivo.com;transport=tls>>
11:09:30.729  D  inside m_stack
11:09:30.729  D   BaseSecurity::BaseSecurity
11:09:30.730  D   unable to load DH parameters (required for PFS): TlsDHParamsFilename not specified
11:09:30.730  D   ECDH initialized
11:09:30.730  D   unable to load DH parameters (required for PFS): TlsDHParamsFilename not specified
11:09:30.730  D   ECDH initialized
11:09:30.730  D   DNS initialization: found  1 name servers
11:09:30.731  D    name server: 8.8.8.8
11:09:30.731  D   COMPRESSION SUPPORT NOT COMPILED IN
11:09:30.731  D   Compression configuration object created; algorithm = 0
11:09:30.731  D   No compression library available
11:09:30.732  D   local hostname does not contain a domain part localhost
11:09:30.733  D   0xb4000075fec4fcb0 IN
11:09:30.735  D   0xb4000074eecb84d0 PEM_CERT
11:09:30.736  D   0xb4000074eec725b0 PEM_CERT
11:09:30.736  D   0xb4000074eec86890 PEM_CERT
11:09:30.736  D   0xb4000074eec701d0 PEM_CERT
11:09:30.737  D   0xb4000074eec70470 PEM_CERT
11:09:30.737  D   0xb4000074eec68750 PEM_CERT
11:09:30.737  D   0xb4000074eec6fe50 PEM_CERT
11:09:30.738  D   0xb4000074eecb6010 PEM_CERT
11:09:30.738  D   0xb4000074eecb9d50 PEM_CERT
11:09:30.738  D   0xb4000074eec70010 PEM_CERT
11:09:30.739  D   0xb4000074eec71510 PEM_CERT
11:09:30.739  D   0xb4000074eec710b0 PEM_CERT
11:09:30.739  D   0xb4000074eec71cf0 PEM_CERT
11:09:30.740  D   0xb4000074eec72850 PEM_CERT
11:09:30.740  D   0xb4000074eec6d6f0 PEM_CERT
11:09:30.740  D   0xb4000074eec84c90 PEM_CERT
11:09:30.740  D   0xb4000074eec6fc90 PEM_CERT
11:09:30.740  D   0xb4000075fec4fcb0 IN
11:09:30.740  D   0xb4000074eec71970 PEM_CERT
11:09:30.740  D   0xb4000074eec71270 PEM_CERT
11:09:30.740  D   0xb4000074eecb77b0 PEM_CERT
11:09:30.741  D   0xb4000074eecb6550 PEM_CERT
11:09:30.741  D   0xb4000074eecb76d0 PEM_CERT
11:09:30.741  D   0xb4000074eecb8770 PEM_CERT
11:09:30.741  D   0xb4000074eecbb170 PEM_CERT
11:09:30.742  D   0xb4000074eecbaa70 PEM_CERT
11:09:30.742  D   0xb4000074eecba610 PEM_CERT
11:09:30.742  D   0xb4000074eecb6a90 PEM_CERT
11:09:30.743  D   0xb4000074eecb8e70 PEM_CERT
11:09:30.743  D   0xb4000074eecba450 PEM_CERT
11:09:30.743  D   0xb4000074eecb9810 PEM_CERT
11:09:30.743  D   0xb4000074eecba290 PEM_CERT
11:09:30.743  D   0xb4000074eecbaed0 PEM_CERT
11:09:30.743  D   0xb4000074eecb9f10 PEM_CERT
11:09:30.744  D   0xb4000074eecb9ab0 PEM_CERT
11:09:30.744  D   ConnectionBase::ConnectionBase, who: [ V4 0.0.0.0:0 UNKNOWN_TRANSPORT ] 0xb40000759ec9a798
11:09:30.744  D   No compression library available: 0xb40000759ec9a798
11:09:30.744  D   Connection::Connection: new connection created to who: [ V4 0.0.0.0:0 UNKNOWN_TRANSPORT ], is server = 0
11:09:30.744  D   ConnectionManager::ConnectionManager() called 
11:09:30.744  D   Creating fd=162 V4/TCP
11:09:30.744  D   Binding to 0.0.0.0
11:09:30.744  D   Some other error (98): Address already in use
11:09:30.744  D   [ V4 0.0.0.0:5061 TLS ] already in use 
11:09:30.744  D   BaseException at /home/ubuntu/workspace/vo_plivo-android-sdk-webrtc_beta/PlivoEndpoint/src/main/cpp/resip/stack/InternalTransport.cxx:143 port already in use
11:09:30.744  D   Shutting down [ V4 0.0.0.0:5061 TLS ]
11:09:30.744  D   ConnectionBase::~ConnectionBase 0xb40000759ec9a798
11:09:30.748  A  Fatal signal 11 (SIGSEGV), code 2 (SEGV_ACCERR), fault addr 0x7fc777fd20 in tid 5971 (androidsdkissue), pid 5971 (androidsdkissue)
11:09:31.002  A  Cmdline: com.example.plivoandroidsdkissue
11:09:31.002  A  pid: 5971, tid: 5971, name: androidsdkissue  >>> com.example.plivoandroidsdkissue <<<
11:09:31.002  A        #01 pc 00000000000e8238  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!libc++_shared.so (offset 0x8000) (BuildId: 25b13e08a3a359bf0ac835176dfbc89a7b30c1c4)
11:09:31.002  A        #02 pc 00000000000e7e6c  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!libc++_shared.so (offset 0x8000) (BuildId: 25b13e08a3a359bf0ac835176dfbc89a7b30c1c4)
11:09:31.002  A        #03 pc 00000000000e3f50  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!libc++_shared.so (offset 0x8000) (BuildId: 25b13e08a3a359bf0ac835176dfbc89a7b30c1c4)
11:09:31.002  A        #04 pc 00000000000e3da4  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!libc++_shared.so (offset 0x8000) (__gxx_personality_v0+224) (BuildId: 25b13e08a3a359bf0ac835176dfbc89a7b30c1c4)
11:09:31.002  A        #05 pc 000000000043bb44  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #06 pc 000000000043c04c  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (_Unwind_Resume+116) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #07 pc 00000000002a03ac  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (resip::TlsBaseTransport::TlsBaseTransport(resip::Fifo<resip::TransactionMessage>&, int, resip::IpVersion, resip::Data const&, resip::Security&, resip::Data const&, resip::SecurityTypes::SSLType, resip::TransportType, void (*)(int, int, char const*, int), resip::Compression&, unsigned int, resip::SecurityTypes::TlsClientVerificationMode, bool, resip::Data const&, resip::Data const&, resip::Data const&)+684) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #08 pc 000000000029f6cc  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (resip::TlsTransport::TlsTransport(resip::Fifo<resip::TransactionMessage>&, int, resip::IpVersion, resip::Data const&, resip::Security&, resip::Data const&, resip::SecurityTypes::SSLType, void (*)(int, int, char const*, int), resip::Compression&, unsigned int, resip::SecurityTypes::TlsClientVerificationMode, bool, resip::Data const&, resip::Data const&, resip::Data const&)+136) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #09 pc 0000000000307840  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (resip::SipStack::addTransport(resip::TransportType, int, resip::IpVersion, resip::StunSetting, resip::Data const&, resip::Data const&, resip::Data const&, resip::SecurityTypes::SSLType, unsigned int, resip::Data const&, resip::Data const&, resip::SecurityTypes::TlsClientVerificationMode, bool, resip::SharedPtr<resip::WsConnectionValidator>, resip::SharedPtr<resip::WsCookieContextFactory>, resip::Data const&)+944) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #10 pc 0000000000335020  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (rtcsip::SipControllerCore::getTransport()+200) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #11 pc 000000000033627c  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (rtcsip::SipControllerCore::setupTransport(std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> > const&, std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> > const&)+884) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #12 pc 0000000000336d1c  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (rtcsip::SipControllerCore::registerUser(std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> > const&, std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> >, std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> > const&, std::__ndk1::basic_string<char, std::__ndk1::char_traits<char>, std::__ndk1::allocator<char> > const&)+212) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #13 pc 000000000032c7f8  /data/app/~~Difzec62HgfUbWpFZxFxXw==/com.example.plivoandroidsdkissue-PIxFdQfNDFiWG51dywR_Tg==/base.apk!librtcsip_jni.so (offset 0x1ec000) (Java_com_plivo_endpoint_SipController_registerUserToken+236) (BuildId: f5ae346a02c6b1875eb1bd63027616732c8678ef)
11:09:31.002  A        #39 pc 0000000000001fc0  /data/data/com.example.plivoandroidsdkissue/code_cache/.overlay/base.apk/classes3.dex (com.example.plivoandroidsdkissue.MainActivityKt$MapView$2$1$1.invoke+0)
11:09:31.003  A        #44 pc 0000000000001f84  /data/data/com.example.plivoandroidsdkissue/code_cache/.overlay/base.apk/classes3.dex (com.example.plivoandroidsdkissue.MainActivityKt$MapView$2$1$1.invoke+0)
---------------------------- PROCESS ENDED (5971) for package com.example.plivoandroidsdkissue ----------------------------

```

## NDK analysis
We are not sure if error is due to NDK conflicts, but we were able to find the NDK versions of other dependencies that we depend on

### Mapbox NDK versions
- Using `llvm-readelf`, we found that the current version of mapbox that we are using uses NDK 23

```
> llvm-readelf -p .note.android.ident libc++_shared.so
String dump of section '.note.android.ident':
[     0] .
[     4] .
[     8] .
[     c] Android
[    14] .
[    18] r23c
[    58] 8568313

> llvm-readelf -p .note.android.ident libmapbox-common.so
String dump of section '.note.android.ident':
[     0] .
[     4] .
[     8] .
[     c] Android
[    14] .
[    18] r23c
[    58] 8568313
```

### Plivo
- For plivo, we are not able to determine this from our end since the information is stipped off

```
```
>llvm-readelf -p .note.android.ident libc++_shared.so
llvm-readelf: warning: 'libc++_shared.so': could not find section '.note.android.ident'

>llvm-readelf -p .note.android.ident libjingle_peerconnection_so.so
String dump of section '.note.android.ident':
[     0] .
[     4] .
[     8] .
[     c] Android
[    14] .
[    18] r23
[    58] 7599858

>llvm-readelf -p .note.android.ident librtcsip_jni.so
llvm-readelf: warning: 'librtcsip_jni.so': could not find section '.note.android.ident'
```