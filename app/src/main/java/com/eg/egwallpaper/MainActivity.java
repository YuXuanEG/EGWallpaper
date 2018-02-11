package com.eg.egwallpaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eg.egwallpaper.egwallpaper.BuilderManager;
import com.eg.egwallpaper.egwallpaper.EGWallpaperContract;
import com.eg.egwallpaper.egwallpaper.EGWallpaperPresenter;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements EGWallpaperContract.View {

    private EGWallpaperPresenter mWallpaperPresenter;
    @BindView(R.id.bmb)
    public BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mWallpaperPresenter = new EGWallpaperPresenter(this);
        mWallpaperPresenter.setContext(this);
        mWallpaperPresenter.subscribe();

        initView();
    }

    private void initView() {
        List wallpaperItemNames = new ArrayList<Integer>();
        wallpaperItemNames.add(R.string.set_wallpaper);
        wallpaperItemNames.add(R.string.set_silence);
        wallpaperItemNames.add(R.string.cancel_silence);
        wallpaperItemNames.add(R.string.back);

        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_2);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_2);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder()
                    .normalTextRes((int) wallpaperItemNames.get(i))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0://设置壁纸
                                    mWallpaperPresenter.setWallpaper();
                                    break;
                                case 1://设置静音
                                    mWallpaperPresenter.setSilence();
                                    mWallpaperPresenter.setWallpaper();
                                    break;
                                case 2://取消静音
                                    mWallpaperPresenter.cancelSilence();
                                    mWallpaperPresenter.setWallpaper();
                                    break;
                                case 3://退出
                                    finish();
                                    break;
                            }
                            Toast.makeText(getApplicationContext(), "click" + index, Toast.LENGTH_SHORT).show();
                        }
                    }));
        }
    }

    @Override
    public void setPresenter(EGWallpaperContract.Presenter presenter) {
        mWallpaperPresenter = checkNotNull((EGWallpaperPresenter) presenter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
