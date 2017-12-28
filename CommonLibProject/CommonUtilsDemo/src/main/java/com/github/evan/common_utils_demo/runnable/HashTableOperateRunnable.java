package com.github.evan.common_utils_demo.runnable;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.github.evan.common_utils.collections.SyncArrayList;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;

import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Evan on 2017/12/28.
 */
public class HashTableOperateRunnable implements Runnable {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private Hashtable<String, String> mHashTable;
    private List<String> mKeys;
    private Observer mObserver;
    private boolean mIsStop;
    private Random mRandom = new Random();

    public HashTableOperateRunnable(Hashtable<String, String> mHashTable, List<String> mKeys) {
        this.mHashTable = mHashTable;
        this.mKeys = mKeys;
    }

    public Observer getObserver() {
        return mObserver;
    }

    public void setObserver(Observer observer) {
        this.mObserver = observer;
    }

    public boolean isStop() {
        return mIsStop;
    }

    public void setStop(boolean isStop) {
        this.mIsStop = isStop;
    }

    @Override
    public void run() {
        while (!mIsStop) {
            int i = mRandom.nextInt(Operate.GET_SIZE.value + 1);
            Operate operate = Operate.valueOf(i);
            String log = "";
            switch (operate) {
                case ADD:
                    UUID putValue = UUID.randomUUID();
                    String putKey = DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_SS, Locale.getDefault());
                    mHashTable.put(putKey, putValue.toString());
                    mKeys.add(putKey);
                    log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, key: " + putKey + ", value: " + putValue.toString();
                    break;

                case DELETE:
                    if (mHashTable.size() <= 0) {
                        log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 但size < 0, 跳过.";
                        break;
                    }

                    int deleteKeyIndex = mRandom.nextInt(mKeys.size());
                    String removeKey = mKeys.get(deleteKeyIndex);
                    String removedValue = mHashTable.remove(removeKey);
                    mKeys.remove(removeKey);
                    log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 删除 key: " + removeKey + ", value: " + removedValue;
                    break;

                case SET:
                    if (mHashTable.size() <= 0) {
                        log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 但size < 0, 跳过.";
                        break;
                    }

                    int setKeyIndex = mRandom.nextInt(mKeys.size());
                    String setKey = mKeys.get(setKeyIndex);
                    String setValue = UUID.randomUUID().toString();
                    String oldValue = mHashTable.put(setKey, setValue);
                    log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 将key: " + setKey + "由" + oldValue + "替换为" + setValue;
                    break;

                case GET:
                    if (mHashTable.size() <= 0) {
                        log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 但size < 0, 跳过.";
                        break;
                    }

                    Logger.d("random, keys size: " + mKeys.size());
                    int getKeyIndex = mRandom.nextInt(mKeys.size());
                    String getKey = mKeys.get(getKeyIndex);
                    String getValue = mHashTable.get(getKey);
                    log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, 获取key: " + getKey + "value: " + getValue;

                    break;

                case GET_SIZE:
                    log = "线程" + Thread.currentThread().getId() + ", " + operate.toString() + "操作, size=" + mHashTable.size();
                    break;
            }

            final String finalLog = log;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (null != mObserver) {
                        mObserver.onPrintLog(finalLog);
                    }
                }
            });

            SystemClock.sleep(500);
        }
    }
}
