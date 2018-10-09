package com.github.evan.common_utils_demo.ui.activity.aboutDataSave;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.db.PersonDbHelper;
import com.github.evan.common_utils.db.dao.PersonV1Dao;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableV1;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableV2;
import com.github.evan.common_utils.event.SQLiteLogEvent;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/17.
 */

public class SQLiteActivity extends BaseLogCatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onReceiveEvent(SQLiteLogEvent event){
        addLog(event.getMessage());
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_sqlite, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_sqlite_insert, R.id.card_sqlite_remove, R.id.card_sqlite_set, R.id.card_sqlite_get, R.id.card_sqlite_view, R.id.card_sqlite_update})
    protected void onClick(View view){
        PersonV1Dao personV1Dao = PersonV1Dao.getInstance(PersonDbHelper.getInstance(this));
        List<PersonTableV1> personTableV1s = PersonV1Dao.getInstance(PersonDbHelper.getInstance(this)).queryAll();
        switch (view.getId()){
            case R.id.card_sqlite_insert:
                PersonTableV1 person = new PersonTableV1();
                person.setName("张三");
                person.setAge(19);
                personV1Dao.insert(person);
                addLog("insert: " + person.toString());
                break;

            case R.id.card_sqlite_remove:
                if(null == personTableV1s || personTableV1s.size() == 0){
                    addLog("There is no data in table 'person'");
                    return;
                }

                PersonTableV1 personTableV1 = personTableV1s.get(personTableV1s.size() - 1);
                personV1Dao.remove(personTableV1.getName());
                addLog("remove: " + personTableV1.toString());
                break;

            case R.id.card_sqlite_set:
                if(null == personTableV1s || personTableV1s.size() == 0){
                    addLog("There is no data in table 'person'");
                    return;
                }

                PersonTableV1 p = personTableV1s.get(personTableV1s.size() - 1);
                PersonTableV1 pp = new PersonTableV1();
                pp.setName("王五");
                pp.setAge(99);
                personV1Dao.update(p.getName(), pp);
                addLog("update: " + p.toString() + "update to " + pp.toString());

                break;

            case R.id.card_sqlite_get:
                PersonTableV1 ppp = personTableV1s.get(personTableV1s.size() - 1);
                addLog("query: " + ppp.toString());
                break;

            case R.id.card_sqlite_view:

                break;

            case R.id.card_sqlite_update:
                PersonDbHelper.getInstance(this).onUpgrade(PersonDbHelper.getInstance(this).getWritableDatabase(), 1, 2);
                break;
        }
    }
}
