package com.eg.egwallpaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eg.egwallpaper.egwallpaper.EGWallpaperContract;
import com.eg.egwallpaper.egwallpaper.EGWallpaperPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements EGWallpaperContract.View {

    private EGWallpaperPresenter mWallpaperPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mWallpaperPresenter = new EGWallpaperPresenter(this);
        mWallpaperPresenter.setContext(this);
        mWallpaperPresenter.subscribe();
    }

    @Override
    public void setPresenter(EGWallpaperContract.Presenter presenter) {
        mWallpaperPresenter = checkNotNull((EGWallpaperPresenter) presenter);
    }

    @OnClick({R.id.btn_cancelSilence,R.id.btn_setSilence,R.id.btn_setWallpaper,R.id.btn_back})
    void clickDo(View view){
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_cancelSilence:
                mWallpaperPresenter.cancelSilence();
                break;
            case R.id.btn_setSilence:
                mWallpaperPresenter.setSilence();
                break;
            case R.id.btn_setWallpaper:
                mWallpaperPresenter.setWallpaper();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
