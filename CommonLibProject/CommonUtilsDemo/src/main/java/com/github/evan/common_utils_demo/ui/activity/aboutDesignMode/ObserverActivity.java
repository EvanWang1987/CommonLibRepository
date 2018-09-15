package com.github.evan.common_utils_demo.ui.activity.aboutDesignMode;

import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.designMode.observer.DataManager;
import com.github.evan.common_utils_demo.designMode.observer.IObserver;
import com.github.evan.common_utils_demo.designMode.observer.Person;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/15.
 */
public class ObserverActivity extends BaseLogCatActivity {



    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_observer, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_observer_features, R.id.card_observer_test, R.id.card_observer_stop_test})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_observer_features:
                showMessageDialog(getString(R.string.observer_mode_features_title), getString(R.string.observer_mode_features), getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_observer_test:
                DataManager.getInstance().clearAllObservers();
                for (int i = 0; i < 5; i++) {
                    IObserver<List<Person>> observer = new IObserver<List<Person>>() {
                        @Override
                        public void onUpdate(List<Person> persons) {
                            addLog("观察者-" + this.hashCode() + ", 收到观察事件, 数据persons: " + persons.toString());
                        }
                    };
                    DataManager.getInstance().addObserver(observer);
                    addLog("添加一个观察者: " + observer.hashCode());
                }

                for (int i = 0; i < 3; i++) {
                    Person person = new Person("张三" + i, i);
                    DataManager.getInstance().addPerson(person);
                    addLog("添加一个数据: " + person.toString());
                }
                break;

            case R.id.card_observer_stop_test:
                DataManager.getInstance().clearAllObservers();
                break;
        }
    }
}
