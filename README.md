UninstallMonitor
To monitor the apk self been uninstalled by forking a child process in native code
android应用程序自身被卸载监听，简要思路是：在jni中fork一个child 进程，监听/data/data/you_package_name目录是否被删除，然后再判断/data/app/your_apk，是否被真正删除了，本程序对reference进行的优化也在此处，可以避免清除数据被认为被卸载的情况。更简单的方式就是直接轮询监视apk是否被删除，逻辑更加简单，有兴趣可以自己实现。


reference：
https://github.com/sevenler/Uninstall_Statics

