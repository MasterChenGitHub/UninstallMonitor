卸载（uninstall） 安卓（android） 应用 监听，弹出调查问卷网页。

UninstallMonitor

To monitor the apk self been uninstalled by forking a child process in native code
android应用程序自身被卸载监听，简要思路是：在jni中fork一个child 进程，监听/data/data/you_package_name目录是否被删除，然后再判断/data/app/your_apk，是否被真正删除了，本程序对reference进行的优化也在此处，可以避免清除数据被认为被卸载的情况。更简单的方式就是直接轮询监视apk是否被删除，逻辑更加简单，有兴趣可以自己实现。
（已修正清除应用数据不会触发弹网页）

Technology Background：

Monitoring File Events

Some applications need to be able to monitor files or directories in order to determine
whether events have occurred for the monitored objects. For example, a
graphical file manager needs to be able to determine when files are added or
removed from the directory that is currently being displayed, or a daemon may
want to monitor its configuration file in order to know if the file has been changed.
Starting with kernel 2.6.13, Linux provides the inotify mechanism, which allows
an application to monitor file events.

API：
The key steps in the use of the inotify API are as follows:
1. The application uses inotify_init() to create an inotify instance. This system call
returns a file descriptor that is used to refer to the inotify instance in later
operations.

2. The application informs the kernel about which files are of interest by using
inotify_add_watch() to add items to the watch list of the inotify instance created
in the previous step. Each watch item consists of a pathname and an associated
bit mask. The bit mask specifies the set of events to be monitored for the pathname.
As its function result, inotify_add_watch() returns a watch descriptor, which
is used to refer to the watch in later operations. (The inotify_rm_watch() system
call performs the converse task, removing a watch that was previously added to
an inotify instance.)

3. In order to obtain event notifications, the application performs read() operations
on the inotify file descriptor. Each successful read() returns one or more
inotify_event structures, each containing information about an event that
occurred on one of the pathnames being watched via this inotify instance.
If no events have occurred so far, then read() blocks until an event occurs (unless
the O_NONBLOCK status flag has been set for the file descriptor, in which case the read()
fails immediately with the error EAGAIN if no events are available).


4. When the application has finished monitoring, it closes the inotify file descriptor.
This automatically removes all watch items associated with the inotify instance.
The inotify mechanism can be used to monitor files or directories. When monitoring
a directory, the application will be informed about events for the directory
itself and for files inside the directory.
The inotify monitoring mechanism is not recursive. If an application wants to
monitor events within an entire directory subtree, it must issue inotify_add_watch()
calls for each directory in the tree.


reference：

1、https://github.com/sevenler/Uninstall_Statics

2、LinuxUNIX系统编程手册(英文版)

