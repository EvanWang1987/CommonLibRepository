package com.github.evan.common_utils_demo.ui.activity.aboutDesignMode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.event.SingletonEvent;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.SinglePhotoActivity;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.designMode.Singleton;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/15.
 */
public class SingletonActivity extends BaseLogCatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.card_singleton_features, R.id.card_singleton_lazy_mode, R.id.card_singleton_hungry_mode, R.id.card_singleton_test, R.id.card_singleton_stop_test})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_singleton_features:
                showMessageDialog(getString(R.string.singleton_mode_features_title), getString(R.string.singleton_mode_features), getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_singleton_lazy_mode:
                Bundle bundle = new Bundle();
                bundle.putInt(SinglePhotoActivity.PHOTO_RES_ID, R.mipmap.lazy);
                loadActivity(SinglePhotoActivity.class, bundle, false, -1);
                break;

            case R.id.card_singleton_hungry_mode:
                Bundle hBundle = new Bundle();
                hBundle.putInt(SinglePhotoActivity.PHOTO_RES_ID, R.mipmap.hungry);
                loadActivity(SinglePhotoActivity.class, hBundle, false, -1);
                break;

            case R.id.card_singleton_test:
                for (int i = 0; i < 20; i++) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Singleton.HungrySingleton hungrySingleton = Singleton.HungrySingleton.getInstance();
                        }
                    };

                    Thread thread = new Thread(runnable);
                    thread.start();
                }
                break;

            case R.id.card_singleton_stop_test:
                Singleton.HungrySingleton.clearInstance();
                break;
        }
    }

    @Subscribe
    public void receiveSingletonEvent(SingletonEvent event){
        addLog(event.getmMessage());
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_singleton, null);
        return root;
    }
}
