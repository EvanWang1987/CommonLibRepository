package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.collections.SyncArrayList;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.runnable.HashTableOperateRunnable;
import com.github.evan.common_utils_demo.runnable.Observer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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

public class HashTableActivity extends BaseLogCatActivity implements Observer {
    private List<String> mKeys = new SyncArrayList<>();
    private Hashtable<String, String> mHashTable = new Hashtable<>();
    private AlertDialog mFeaturesDialog;
    private Random mRandom = new Random();
    private HashTableOperateRunnable runnableA;
    private HashTableOperateRunnable runnableB;
    private HashTableOperateRunnable runnableC;
    private HashTableOperateRunnable runnableD;
    private HashTableOperateRunnable runnableE;
    private HashTableOperateRunnable runnableF;

    @Override
    protected void onDestroy() {
        stopMultiThreadTest();
        super.onDestroy();
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_hash_table, null);
        ButterKnife.bind(this, root);
        return root;
    }


    @OnClick({R.id.card_features_hash_table, R.id.card_add_hash_table, R.id.card_remove_hash_table, R.id.card_set_hash_table, R.id.card_get_hash_table, R.id.card_go_through_hash_table, R.id.card_go_through_by_entry_set_hash_table, R.id.card_go_through_by_iterator_hash_table, R.id.card_go_through_by_list_iterator_hash_table, R.id.card_clear_hash_table, R.id.card_to_string_hash_table, R.id.card_sort_hash_table, R.id.card_multi_thread_hash_table, R.id.card_stop_multi_thread_hash_table})
    void onClick(View v) {
        int size = mHashTable.size();
        switch (v.getId()) {

            case R.id.card_features_hash_table:
                if(null == mFeaturesDialog){
                    String title = "HashMap特性";
                    String message = getString(R.string.features_hash_table);
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

            case R.id.card_add_hash_table:
                UUID uuid = UUID.randomUUID();
                String addKey = generateKey();
                mHashTable.put(addKey, uuid.toString());
                mKeys.add(addKey);
                addLog("增加元素, key: " + addKey + ", value: " + uuid.toString());
                break;

            case R.id.card_remove_hash_table:
                if (size <= 0) {
                    addLog("HashMap没有元素!");
                    return;
                }

                String removeKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String removeValue = mHashTable.get(removeKey);
                mHashTable.remove(removeKey);
                addLog("删除元素, key: " + removeKey + ", value: " + removeValue);
                break;

            case R.id.card_set_hash_table:
                if (size <= 0) {
                    addLog("HashMap没有元素!");
                    return;
                }

                UUID putUuid = UUID.randomUUID();
                String putKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String oldValue = mHashTable.put(putKey, putUuid.toString());
                addLog("HashMap更改， key：" + putKey + ", 新value：" + putUuid.toString() + "旧value: " + oldValue);
                break;

            case R.id.card_get_hash_table:
                if (size <= 0) {
                    addLog("HashMap没有元素!");
                    return;
                }

                String getKey = mKeys.get(mRandom.nextInt(mKeys.size()));
                String getValue = mHashTable.get(getKey);
                addLog("HashMap获取，key：" + getKey + ", value：" + getValue);
                break;

            case R.id.card_go_through_by_iterator_hash_table:
                if (size <= 0) {
                    addLog("HashMap没有元素!");
                    return;
                }

                Iterator<String> iterator = mHashTable.keySet().iterator();
                addLog("---开始通过KeySet Iterator遍历---");
                boolean hasNext = iterator.hasNext();
                addLog("Iterator.hasNest: " + hasNext);
                while (hasNext) {
                    String keyByIterator = iterator.next();
                    String valueByIterator = mHashTable.get(keyByIterator);
                    addLog("key: " + keyByIterator + ", value: " + valueByIterator);
                    hasNext = iterator.hasNext();
                    addLog("Iterator.hasNest: " + hasNext);
                }
                addLog("---结束遍历---");

                break;

            case R.id.card_go_through_by_list_iterator_hash_table:
                addLog("HashMap不支持ListIterator");
                break;

            case R.id.card_go_through_hash_table:
                addLog("Set不支持按index遍历");
                break;

            case R.id.card_go_through_by_entry_set_hash_table:
                if (size <= 0) {
                    addLog("HashMap没有元素!");
                    return;
                }

                Set<Map.Entry<String, String>> entries = mHashTable.entrySet();
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

            case R.id.card_clear_hash_table:
                mHashTable.clear();
                mKeys.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_hash_table:
                printToString();
                break;

            case R.id.card_sort_hash_table:
                addLog("HashMap不支持排序");
                break;

            case R.id.card_multi_thread_hash_table:
                startMultiThreadTest();
                break;

            case R.id.card_stop_multi_thread_hash_table:
                stopMultiThreadTest();
                break;
        }
    }

    private void startMultiThreadTest() {
        runnableA = new HashTableOperateRunnable(mHashTable, mKeys);
        runnableB = new HashTableOperateRunnable(mHashTable, mKeys);
        runnableC = new HashTableOperateRunnable(mHashTable, mKeys);
        runnableD = new HashTableOperateRunnable(mHashTable, mKeys);
        runnableE = new HashTableOperateRunnable(mHashTable, mKeys);
        runnableF = new HashTableOperateRunnable(mHashTable, mKeys);

        runnableA.setStop(false);
        runnableB.setStop(false);
        runnableC.setStop(false);
        runnableD.setStop(false);
        runnableE.setStop(false);
        runnableF.setStop(false);

        runnableA.setObserver(this);
        runnableB.setObserver(this);
        runnableC.setObserver(this);
        runnableD.setObserver(this);
        runnableE.setObserver(this);
        runnableF.setObserver(this);

        Thread threadA = new Thread(runnableA);
        Thread threadB = new Thread(runnableB);
        Thread threadC = new Thread(runnableC);
        Thread threadD = new Thread(runnableD);
        Thread threadE = new Thread(runnableE);
        Thread threadF = new Thread(runnableF);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();
        threadF.start();
    }

    private void stopMultiThreadTest(){
        if(null != runnableA){
            runnableA.setObserver(null);
            runnableA.setStop(true);
        }

        if(null != runnableB){
            runnableB.setObserver(null);
            runnableB.setStop(true);
        }

        if(null != runnableC){
            runnableC.setObserver(null);
            runnableC.setStop(true);
        }

        if(null != runnableD){
            runnableD.setObserver(null);
            runnableD.setStop(true);
        }

        if(null != runnableE){
            runnableE.setObserver(null);
            runnableE.setStop(true);
        }

        if(null != runnableF){
            runnableF.setObserver(null);
            runnableF.setStop(true);
        }
    }


    private void printToString(){
        addLog("当前HashTable: " + mHashTable.toString());
    }

    private String generateKey(){
        return DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_SS, Locale.getDefault());
    }

    @Override
    public void onPrintLog(CharSequence log) {
        addLog(log);
    }
}
