# Cami项目代码梳理

## 整体结构
MVVM框架结构。采用java+navigation+viewModel+livedata+retrofit+room+dataBinding+cordova的技术栈。
功能调用顺序逻辑为fragment -> viewModel -> repository -> repository在viewModel的回调里设置liveData -> Observer的处理
dataBinding多使用了绑定事件以及通过viewModel里的变量判断view.visible。未使用双向绑定。
cordova用来显示web界面部分。

## 代码逻辑
以navigation作为整体导航结构。
1. 首页: 首页SplashActivity的layout为fragmentContainerView。作为导航容器。设置相应导航地图nav_graph。

2. MainFragment: nav初始页面为mainFragment。这个页面中采用viewpager+bottomNavigation的结构。这里二者没有直接联动。因为viewpager里
有3个界面，而bottomNavigation里有4个导航按钮(其中编辑按钮是单独布局盖在bottomNavigation上的)。这里采用了点击底部按钮手动设置vp的currentItem和直接跳转剪辑视频页的方式。

3. HomeFragment: 首页viewPager的第一个首页为HomeFragment。此页面主要为：顶部标题栏左边跳转摄像，以及右边跳转说明书。标题栏下为使用cordova框架的
mWebCordovaFragment加载web界面展示推荐视频的。

4. DeviceConnectActivity: 点击HomeFragment标题栏左边摄像按钮，会进行检查蓝牙连接情况。如果蓝牙没有打开则请求打开，如果已打开则进行跳转到设备连接界面DeviceConnectActivity
。在此界面layout中，采用NavHostFragment的方式又放置了一个navGraph导航:nav_connect。startDestination为DeviceConectFragment.

5. DeviceConnectFragment: 在initListener方法中，进行一系列监听。先通过viewModel.getScanFailType()进行扫描设备所需条件的状态的监听。
有蓝牙，位置等。接着对扫描状态，连接状态进行监听。mViewModel.getScanningDevice()进行对扫描到的设备进行监听。调用mViewModel.getOperationStatus对要进行的操作状态监听。
在onResume()方法里，通过startScan()方法来进行扫描设备。startScan方法里，通过设备名称进行过滤筛选。接着调用viewModel.startScan()进行实际设备扫描。
在addService()中将扫描到的设备添加到列表中。
在列表rvDeviceList中点击item的连接按钮，调用BleDeviceViewModel.connectDevice()进行连接。

6. 在HomeFragment右上角点击书本icon，跳转到使用cordova加载的界面。展示说明书信息。

7. ExploreFragment:首页点击底部导航探索按钮进入到探索页面。此界面使用一个frameLayout作为容器加载cordovaFragment的。
在此界面首先检查下是否需要展示新手指引。

8. CreationActivity:在首页底部导航里点击编辑按钮，会根据状态判断需要进入发布作品页面还所编辑界面。此界面的layout中
增加了一个navGraph。这个导航的startDestination为creationFragment。

9. CreationFragment:为创作编辑的首页。点击创作按钮，调用onCreate()方法，导航到EditorAddVideoActivity。

10. EditorAddVideoActivity:选择媒体文件界面。此界面与模板页面共用。此界面layout中使用fragment显示。在onCreateTask方法中，
先根据是否所模板来决定是否隐藏Title。然后调用showFragment来显示Fragment。此处为AddVideoFragment。

11. AddVideoFragment: 添加媒体界面。此界面通过MediaType来切换不同类型的媒体列表。在initView中通过调用viewModel.loadMedia方法
进行媒体文件的设置类型过滤然后调用mMediaQueryRepository.startLoading()加载。加载出来的媒体文件，点击选择，放入底部的recycleView中。
在选择了底部的确定按钮，进入编辑界面。调用goEditor方法。进入MediaAddUserCase.goEditor()方法。此方法中，通过EditorMainActivity.startActivity
进入EditorMainActivity。

12. 在EditorMainActivity的layout中，上面播放界面写在界面中，下面编辑功能部分，采用fragment作为navigation容器。主页为EditorMainFragment
这个fragment中有相关编辑的功能操作。点击相关功能，编辑界面中间区域导航到功能界面。比如点击画面按钮，导航到EditorFrameFragment。

13. EditorFrameFragment:通过选中的效果以及滑动滚动条的值来调节效果。滚动条滚动的时候调用seekChange方法。方法里调用viewModel的
的setFrameEffect。方法里调用SDK相关方法进行对效果处理。

14. 在进行效果处理后，点击右上角下一步，调用EditorMainActivity的onRightClick方法。此方法中进行视频时长是否是会员的检测。
条件满足，调用realGoPublish()方法。跳转到publishActivity。

15. PublishActivity: 此界面中，先进入合成界面composeFragment。然后再进入发布界面publishFragment。

16. ComposeFragment: ComposeViewModel.startCompose()开始合成。先进行判断剩余空间是否充足。设置路径，帧率，参数等。然后调用composeUseCase.startCompose开始真正合成。

17. PublishFragment: 合成完成后，点击下一步进入到发布PublishFragment。在initData()中，进行缩略图，定位，标签，定位的设置。
在saveAndPublish进行保存合发布。

18. 在CreationFragment点击一键剪辑，调用onAutoEdit()方法。进入EditorAddVideoActivity。

19. 首页底部点击Me按钮，通过AccountRepository.getInstance().isUserLogin()进行是否登录的判断。如果还没有登录，跳转到登录界面AccountActivity.startActivity。如果已经登录，则显示MeFragment.
登录与注册界面：AccountMainFragment。登录界面:LoginFragment.

20. MeFragment 个人信息界面的展示。在initData()中初始化发布作品信息以及未读的红点状态。监听用户信息。在用户信息回调中
判断是自己还是他人的信息。用户是他人的信息，则判断拉黑状态。并刷新会员的信息，调用updaterPrimerUI()更新UI。如果没有开通
会员，则如果点击开通，调用goPrimeWeb()跳转一个cordova的会员网页界面primeCordovaFragment。其中点击右上角设置icon会
调用goSet()方法进入设置界面setFragment。

21. SetFragment: 设置界面。登出：调用AccountRepository.getInstance().requestLogout()登出账号。

22. SetAccountSecurityFragment： 账号安全。修改密码，绑定账号，注销账号都在这里。注销账号会调用AccountCancellationActivity进入AccountCancellationFragment.




