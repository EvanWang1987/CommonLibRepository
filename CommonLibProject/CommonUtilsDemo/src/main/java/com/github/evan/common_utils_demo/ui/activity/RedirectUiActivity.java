package com.github.evan.common_utils_demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.utils.SchemeJumpUtils;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.ArrayListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.HashMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.HashSetActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.TreeMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.TreeSetActivity;
import com.github.evan.common_utils_demo.utils.RedirectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Evan on 2018/9/11.
 */
public class RedirectUiActivity extends BaseActivity {
    public static final String EXTRAS_REDIRECT_CODE = "EXTRAS_REDIRECT_CODE";
    private static final Map<String, Class<? extends Activity>> mPathMap = new HashMap<>();

    static {
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_ARRAY_LIST_UI, ArrayListActivity.class);
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_LINKED_LIST_UI, LinkedListActivity.class);
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_HASH_SET_UI, HashSetActivity.class);
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_TREE_SET_UI, TreeSetActivity.class);
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_HASH_MAP_UI, HashMapActivity.class);
        mPathMap.put(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_PATH_TREE_MAP_UI, TreeMapActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redirect();
        finish();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_redirect_ui;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    private void redirect(){
        String stringExtra = getIntent().getStringExtra(EXTRAS_REDIRECT_CODE);
        if(StringUtil.isEmpty(stringExtra)){
            ToastUtil.showToastWithShortDuration("空的跳转码");
            finish();
            return;
        }
        Uri data = Uri.parse(stringExtra);
        if(null == data){
            ToastUtil.showToastWithShortDuration(getString(R.string.invalid_scheme_jump_code));
            finish();
            return;
        }

        String scheme = data.getScheme();
        String host = data.getHost();
        String path = data.getPath().substring(1, data.getPath().length());
        Set<String> queryParameterNames = data.getQueryParameterNames();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Iterator<String> iterator = queryParameterNames.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = data.getQueryParameter(key);
            keys.add(key);
            values.add(value);
        }

        if(StringUtil.isEmpty(scheme) || !scheme.equals(SchemeJumpUtils.COMMON_UTILS_SCHEME_JUMP_PREFIX)){
            ToastUtil.showToastWithShortDuration(getString(R.string.invalid_scheme_jump_prefix));
            finish();
            return;
        }

        if(StringUtil.isEmpty(host) || !host.equals(SchemeJumpUtils.COMMON_UTILS_SCHEME_JUMP_HOST_REDIRECT_UI)){
            ToastUtil.showToastWithShortDuration(getString(R.string.invalid_scheme_jump_host));
            finish();
            return;
        }

        if(StringUtil.isEmpty(path) || !mPathMap.containsKey(path)){
            ToastUtil.showToastWithShortDuration(getString(R.string.invalid_scheme_jump_path));
            finish();
            return;
        }

        if(keys.size() != values.size()){
            ToastUtil.showToastWithShortDuration(getString(R.string.invalid_scheme_jump_args));
            finish();
            return;
        }

        if(host.equals(RedirectUtils.COMMON_UTILS_SCHEME_JUMP_HOST_REDIRECT_UI)){
            //跳转UI
            Bundle bundle = new Bundle();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = values.get(i);
                bundle.putString(key, value);
            }
            Intent intent = new Intent(this, mPathMap.get(path));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
