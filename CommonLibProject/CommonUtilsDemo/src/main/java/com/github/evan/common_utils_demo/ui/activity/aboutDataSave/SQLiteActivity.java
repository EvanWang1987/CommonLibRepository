package com.github.evan.common_utils_demo.ui.activity.aboutDataSave;

import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/17.
 */

public class SQLiteActivity extends BaseLogCatActivity {

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
        switch (view.getId()){
            case R.id.card_sqlite_insert:

                break;

            case R.id.card_sqlite_remove:

                break;

            case R.id.card_sqlite_set:

                break;

            case R.id.card_sqlite_get:

                break;

            case R.id.card_sqlite_view:

                break;

            case R.id.card_sqlite_update:

                break;
        }
    }
}
