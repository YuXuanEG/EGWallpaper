package com.eg.egwallpaper.egwallpaper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.egvideowallpaper.EGVideoWallpaperService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by EG on 2017/12/18.
 */

public class EGWallpaperPresenter implements EGWallpaperContract.Presenter {
    private final EGWallpaperContract.View mWallpaperView;
    private Context mContext;
    private EGVideoWallpaperService mVideoWallpaper;
    private File mFile1 ;
    private File mFile2;
    private File mFile3;
    private static final String TAG = "EGWallpaper";
    public final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String IS_VIDEO1 = "is_video1";
    @SuppressLint("RestrictedApi")
    public EGWallpaperPresenter(@NonNull EGWallpaperContract.View mWallpaperView) {
        this.mWallpaperView = checkNotNull(mWallpaperView,"mWallpaper cannot be null");
        mWallpaperView.setPresenter(this);
    }
    @Override
    public void subscribe() {
        mVideoWallpaper = new EGVideoWallpaperService();
        //动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "checkSelfPermission=" + mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
            if (mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 申请WRITE_EXTERNAL_STORAGE权限
                    ((Activity)mContext).requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                } else {
                    // 申请WRITE_EXTERNAL_STORAGE权限
                    ((Activity)mContext).requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

            }
        }
        initFile();
    }
    private void initFile() {
        AssetManager asset = mContext.getAssets();
        mFile1 = new File(Environment.getExternalStorageDirectory()+ "/video1.mp4");
        if (!mFile1.exists()) {
            try {
                mFile1.createNewFile();
                InputStream is = asset.open("video1.mp4");
                writeMp4ToNative(mFile1,is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mFile2 = new File(Environment.getExternalStorageDirectory()+ "/video2.mp4");
        if (!mFile2.exists()) {
            try {
                mFile2.createNewFile();
                InputStream is = asset.open("video2.mp4");
                writeMp4ToNative(mFile2,is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mFile3 = new File(Environment.getExternalStorageDirectory()+ "/video3.mp4");
        if (!mFile3.exists()) {
            try {
                mFile3.createNewFile();
                InputStream is = asset.open("video3.mp4");
                writeMp4ToNative(mFile3,is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void setWallpaper() {
        if (SPUtil.get(mContext,IS_VIDEO1,true)){
            SPUtil.put(mContext,IS_VIDEO1,false);
            mVideoWallpaper.setToWallPaper(mContext,mFile3.getAbsolutePath());
        }else {
            SPUtil.put(mContext,IS_VIDEO1,true);
            mVideoWallpaper.setToWallPaper(mContext,mFile2.getAbsolutePath());
        }
    }

    @Override
    public void setSilence() {
        EGVideoWallpaperService.setVoiceSilence(mContext);
    }

    @Override
    public void cancelSilence() {
        EGVideoWallpaperService.setVoiceNormal(mContext);
    }
    private void writeMp4ToNative(File file,InputStream is) {

        try {
            FileOutputStream os = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer))!=-1){
                os.write(buffer,0,buffer.length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
