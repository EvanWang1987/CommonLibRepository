package com.github.evan.common_utils_demo.ui.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.db.PersonDbHelper;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutDataSave.ContentProviderActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutDataSave.FileActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutDataSave.SQLiteActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutDataSave.SharedPreferenceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/17.
 */
public class AboutDataSaveFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_data_save, null);
        ButterKnife.bind(this, root);
        SQLiteDatabase writableDatabase = PersonDbHelper.getInstance(getContext()).getWritableDatabase();
        writableDatabase.isOpen();
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_shared_preference, R.id.card_sqlite, R.id.card_content_provider, R.id.card_file})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_shared_preference:
                loadActivity(SharedPreferenceActivity.class);
                break;

            case R.id.card_sqlite:
                loadActivity(SQLiteActivity.class);
                break;

            case R.id.card_content_provider:
                loadActivity(ContentProviderActivity.class);
                break;

            case R.id.card_file:
                loadActivity(FileActivity.class);
                break;
        }
    }
}
