package com.yollpoll.myframework.exoplayer;

import android.content.Context;
import android.net.Uri;

import com.aispeech.medicalcall.net.HttpServiceFactory;
import com.aispeech.medicalcall.net.HttpServiceFactoryKt;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.yollpoll.framework.net.http.RetrofitFactory;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by spq on 2021/6/7
 */
public class MediaUtils {
    public static DefaultHttpDataSource.Factory httpFactory;
    public static OkHttpDataSource.Factory okHttpFactory;
    private static DataSource.Factory cacheDataSourceFactory;

    public static MediaSource buildMediaSource(Context context, Uri... uris) {
        // These factories are used to construct two media sources below
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(context, "exoplayer");
        ProgressiveMediaSource.Factory mediaSourceFactory =
                new ProgressiveMediaSource.Factory(dataSourceFactory);


        MediaSource[] sources = new MediaSource[uris.length];

        for (int i = 0; i < uris.length; i++) {
            // Create a media source using the supplied URI
            MediaSource mediaSource = mediaSourceFactory.createMediaSource(uris[i]);
            sources[i] = mediaSource;
        }

        return new ConcatenatingMediaSource(sources);
    }

    public static SimpleExoPlayer createPlayer(Context context) {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        trackSelector.setParameters(
                trackSelector.buildUponParameters().setMaxVideoSizeSd());
        SimpleExoPlayer.Builder builder = new SimpleExoPlayer.Builder(context);
        builder.setTrackSelector(trackSelector);
        return builder.build();
    }


    /**
     * 配置
     *
     * @return http配置
     */
    public static DefaultHttpDataSource.Factory getHttpFactory() {
        if (null == httpFactory) {
            httpFactory = new DefaultHttpDataSource.Factory();
        }
        return httpFactory;
    }

    /**
     * okhttp
     *
     * @return factory
     */
    public static OkHttpDataSource.Factory getOkHttpDataSourceFactory() {
        if (null == okHttpFactory) {
            okHttpFactory = new OkHttpDataSource.Factory(new Call.Factory() {
                @Override
                public Call newCall(Request request) {
                    //防止okhttp为null
                    HttpServiceFactory.Companion.getInstance();
                    return HttpServiceFactoryKt.getOkHttpClient().newCall(request);
                }
            });
        }
        return okHttpFactory;
    }

    /**
     * 缓存
     *
     * @return
     */
    public static DataSource.Factory getCacheDataSourceFactory(Cache cache) {
        // Create a read-only cache data source factory using the download cache.
        if (null == cacheDataSourceFactory) {
            cacheDataSourceFactory =
                    new CacheDataSource.Factory()
                            .setCache(cache)
                            .setUpstreamDataSourceFactory(getOkHttpDataSourceFactory())
                            .setCacheWriteDataSinkFactory(null) // Disable writing.
                            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        }

        return cacheDataSourceFactory;
    }


}
