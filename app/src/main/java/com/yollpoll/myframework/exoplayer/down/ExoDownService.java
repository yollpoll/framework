package com.yollpoll.myframework.exoplayer.down;

import android.app.Notification;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ext.workmanager.WorkManagerScheduler;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.yollpoll.myframework.R;

import java.util.List;


/**
 * Created by spq on 2021/6/10
 */
public class ExoDownService extends DownloadService {
    public static final int NOTIFY_ID = 123;
    public static final String WORK_NAME = "down";
    private static final int JOB_ID = 1;
    private static final int FOREGROUND_NOTIFICATION_ID = 1;


    public ExoDownService() {
        super(
                FOREGROUND_NOTIFICATION_ID,
                DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
                DownloadUtils.DOWNLOAD_NOTIFICATION_CHANNEL_ID,
                R.string.exo_download_notification_channel_name,
                /* channelDescriptionResourceId= */ 0);
    }


    @Override
    protected DownloadManager getDownloadManager() {
        return DownloadUtils.getDownloadManager(this);
    }

    @Nullable
    @Override
    protected Scheduler getScheduler() {
        return new WorkManagerScheduler(this, WORK_NAME);
//        return Util.SDK_INT >= 21 ? new PlatformScheduler(this, JOB_ID) : null;
    }

    @Override
    protected Notification getForegroundNotification(List<Download> downloads) {
        return DownloadUtils.getDownloadNotificationHelper(this).buildProgressNotification(
                this, R.drawable.ic_launcher_foreground, null, null, downloads
        );
//        return DownloadUtils.getDownloadNotificationHelper(this).buildDownloadCompletedNotification(
//                this, R.drawable.icon, null, null);
    }

}
