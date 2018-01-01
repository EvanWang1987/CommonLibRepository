package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */

public class LinkedHashMapActivity extends BaseLogCatActivity {
    private List<String> mKeys = new ArrayList<>();
    private LinkedHashMap<String, String> mLinkedHashMap = new LinkedHashMap<>();
    private AlertDialog mFeaturesDialog;
    private Random mRandom = new Random();


    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_linked_hash_map, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_features_linked_hash_map, R.id.card_add_linked_hash_map, R.id.card_remove_linked_hash_map, R.id.card_set_linked_hash_map, R.id.card_get_linked_hash_map, R.id.card_go_through_linked_hash_map, R.id.card_go_through_by_entry_set_linked_hash_map, R.id.card_go_through_by_iterator_linked_hash_map, R.id.card_go_through_by_list_iterator_linked_hash_map, R.id.card_clear_linked_hash_map, R.id.card_to_string_linked_hash_map, R.id.card_sort_linked_hash_map, R.id.card_multi_thread_linked_hash_map})
    void onClick(View v) {
        int size = mLinkedHashMap.size();
        switch (v.getId()) {

            case R.id.card_features_linked_hash_map:
                if(null == mFeaturesDialog){
                    String title = "LinkedHashMap特性";
                    String message = getString(R.string.features_linked_hash_map);
                    AlertDialog alertDialog = DialogFactory.createDesignMessageDialog(this, -1, title, message);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mFeaturesDialog = alertDialog;
                }
                if(!mFeaturesDialog.isShowing()){
                    mFeaturesDialog.show();
                }
                break;

            case R.id.card_add_linked_hash_map:
                UUID uuid = UUID.randomUUID();
                String addKey = generateKey();
                mLinkedHashMap.put(addKey, uuid.toString());
                mKeys.add(addKey);
                addLog("增加元素, key: " + addKey + ", value: " + uuid.toString());
                break;

            case R.id.card_remove_linked_hash_map:
                if (size <= 0) {
                    addLog("LinkedHashMap没有元素!");
                    return;
                }

                String removeKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String removeValue = mLinkedHashMap.get(removeKey);
                mLinkedHashMap.remove(removeKey);
                addLog("删除元素, key: " + removeKey + ", value: " + removeValue);
                break;

            case R.id.card_set_linked_hash_map:
                if (size <= 0) {
                    addLog("LinkedHashMap没有元素!");
                    return;
                }

                UUID putUuid = UUID.randomUUID();
                String putKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String oldValue = mLinkedHashMap.put(putKey, putUuid.toString());
                addLog("LinkedHashMap更改， key：" + putKey + ", 新value：" + putUuid.toString() + "旧value: " + oldValue);
                break;

            case R.id.card_get_linked_hash_map:
                if (size <= 0) {
                    addLog("LinkedHashMap没有元素!");
                    return;
                }

                String getKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String getValue = mLinkedHashMap.get(getKey);
                addLog("LinkedHashMap获取，key：" + getKey + ", value：" + getValue);
                break;

            case R.id.card_go_through_by_iterator_linked_hash_map:
                if (size <= 0) {
                    addLog("LinkedHashMap没有元素!");
                    return;
                }

                Iterator<String> iterator = mLinkedHashMap.keySet().iterator();
                addLog("---开始通过KeySet Iterator遍历---");
                boolean hasNext = iterator.hasNext();
                addLog("Iterator.hasNest: " + hasNext);
                while (hasNext) {
                    String keyByIterator = iterator.next();
                    String valueByIterator = mLinkedHashMap.get(keyByIterator);
                    addLog("key: " + keyByIterator + ", value: " + valueByIterator);
                    hasNext = iterator.hasNext();
                    addLog("Iterator.hasNest: " + hasNext);
                }
                addLog("---结束遍历---");

                break;

            case R.id.card_go_through_by_list_iterator_linked_hash_map:
                addLog("LinkedHashMap不支持ListIterator");
                break;

            case R.id.card_go_through_linked_hash_map:
                addLog("Set不支持按index遍历");
                break;

            case R.id.card_go_through_by_entry_set_linked_hash_map:
                if (size <= 0) {
                    addLog("LinkedHashMap没有元素!");
                    return;
                }

                Set<Map.Entry<String, String>> entries = mLinkedHashMap.entrySet();
                addLog("开始通过EntrySet遍历");
                Iterator<Map.Entry<String, String>> entrySetIterator = entries.iterator();
                boolean entrySetIteratorHasNext = entrySetIterator.hasNext();
                addLog("entrySetIteratorHasNext: " + entrySetIteratorHasNext);
                while (entrySetIteratorHasNext){
                    Map.Entry<String, String> next = entrySetIterator.next();
                    addLog("entry: " + next);
                    entrySetIteratorHasNext = entrySetIterator.hasNext();
                    addLog("entrySetIteratorHasNext: " + entrySetIteratorHasNext);
                }
                break;

            case R.id.card_clear_linked_hash_map:
                mLinkedHashMap.clear();
                mKeys.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_linked_hash_map:
                printToString();
                break;

            case R.id.card_sort_linked_hash_map:
                addLog("LinkedHashMap不支持排序");
                break;

            case R.id.card_multi_thread_linked_hash_map:
                addLog("LinkedHashMap不同步，不支持多线程读写操作!");
                break;
        }
    }


    private void printToString(){
        addLog("当前LinkedHashMap: " + mLinkedHashMap.toString());
    }

    private String generateKey(){
        return DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_SS, Locale.getDefault());
    }
}
