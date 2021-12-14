package com.yollpoll.myframework.exoplayer.down;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.ui.DownloadNotificationHelper;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.yollpoll.arch.threadpool.ThreadPool;
import com.yollpoll.myframework.exoplayer.MediaUtils;

import java.io.File;
import java.util.concurrent.Executor;


/**
 * Created by spq on 2021/6/9
 */
public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    public static final String DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel";
    public static ExoDatabaseProvider databaseProvider;
    private static SimpleCache downloadCache;
    private static DownloadManager downloadManager;
    private static File downloadDirectory;
    private static DownloadNotificationHelper downloadNotificationHelper;

    public static void down(Context context, String url) {
        DownloadRequest downloadRequest =
                new DownloadRequest.Builder(url, Uri.parse(url)).build();

        DownloadService.sendAddDownload(context, ExoDownService.class, downloadRequest, true);
    }

    /**
     * 移除下载
     *
     * @param context
     * @param url
     */
    public static void removeDownload(Context context, String url) {
        DownloadService.sendRemoveDownload(context, ExoDownService.class, url, false);
    }

    /**
     * databaseProvider
     *
     * @param context
     * @return
     */
    public static synchronized ExoDatabaseProvider getDatabaseProvider(Context context) {
        if (null == databaseProvider) {
            synchronized (DownloadUtils.class) {
                if (null == databaseProvider) {
                    databaseProvider = new ExoDatabaseProvider(context);
                }
            }
        }
        return databaseProvider;
    }

    /**
     * 下载目录
     *
     * @param context
     * @return
     */
    private static synchronized File getDownloadDirectory(Context context) {
        if (downloadDirectory == null) {
            downloadDirectory = context.getExternalFilesDir(/* type= */ null);
            if (downloadDirectory == null) {
                downloadDirectory = context.getFilesDir();
            }
        }
        return downloadDirectory;
    }

    /**
     * 缓存
     * 单例
     *
     * @param context
     * @return
     */
    public synchronized static SimpleCache getDownloadCache(Context context) {
        if (null == downloadCache) {
            File downloadContentDirectory = new File(getDownloadDirectory(context),
                    DOWNLOAD_CONTENT_DIRECTORY);

            downloadCache = new SimpleCache(
                    downloadContentDirectory,
                    new NoOpCacheEvictor(),
                    databaseProvider);
        }
        return downloadCache;
    }

    /**
     * 获取下载管理
     * 单例
     *
     * @param context
     * @return
     */
    public static synchronized DownloadManager getDownloadManager(Context context) {
        if (null == downloadManager) {
            // Choose an executor for downloading data. Using Runnable::run will cause each download task to
            // download data on its own thread. Passing an executor that uses multiple threads will speed up
            // download tasks that can be split into smaller parts for parallel execution. Applications that
            // already have an executor for background downloads may wish to reuse their existing executor.
            Executor downloadExecutor = Runnable::run;

            // Create the download manager.
            downloadManager = new DownloadManager(
                    context,
                    getDatabaseProvider(context),
                    getDownloadCache(context),
                    MediaUtils.getHttpFactory(),
                    ThreadPool.getThreadPoolExecutor());


            // Optionally, setters can be called to configure the download manager.
            downloadManager.setRequirements(new Requirements(Requirements.NETWORK));
            downloadManager.setMaxParallelDownloads(3);
//            downloadManager.getDownloadIndex().getDownload()
            downloadManager.addListener(new DownloadManager.Listener() {
                @Override
                public void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception finalException) {
                    Log.d(TAG, "onDownloadChanged: " + download.request.uri.toString()+"/"+download.getPercentDownloaded());
                }

                @Override
                public void onInitialized(DownloadManager downloadManager) {
                    Log.d(TAG, "onInitialized: ");
                }

                @Override
                public void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int notMetRequirements) {
                    Log.d(TAG, "onRequirementsStateChanged: ");
                }

                @Override
                public void onDownloadsPausedChanged(DownloadManager downloadManager, boolean downloadsPaused) {
                    Log.d(TAG, "onDownloadsPausedChanged: ");
                }

                @Override
                public void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean waitingForRequirements) {
                    Log.d(TAG, "onWaitingForRequirementsChanged: ");
                }

                @Override
                public void onDownloadRemoved(DownloadManager downloadManager, Download download) {
                    Log.d(TAG, "onDownloadRemoved: ");
                }

                @Override
                public void onIdle(DownloadManager downloadManager) {
                    Log.d(TAG, "onIdle: ");
                }
            });
        }
        return downloadManager;
    }

    public static synchronized DownloadNotificationHelper getDownloadNotificationHelper(
            Context context) {
        if (downloadNotificationHelper == null) {
            downloadNotificationHelper =
                    new DownloadNotificationHelper(context, DOWNLOAD_NOTIFICATION_CHANNEL_ID);
        }
        return downloadNotificationHelper;
    }

}
