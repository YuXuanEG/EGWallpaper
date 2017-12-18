package com.eg.egwallpaper.egwallpaper;

import android.content.Context;

import com.eg.egwallpaper.base.BasePresenter;
import com.eg.egwallpaper.base.BaseView;

/**
 * Created by EG on 2017/12/18.
 */

public interface EGWallpaperContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter{
        void setContext(Context context);
        void setWallpaper();
        void setSilence();
        void cancelSilence();
    }
}
