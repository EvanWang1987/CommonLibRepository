package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.comparator.DictionaryOrderComparator;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils_demo.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */

public class HashSetActivity extends BaseLogCatActivity {
    private HashSet<String> mHashSet = new HashSet<>();
    private AlertDialog mFeaturesDialog;


    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_hash_set, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_features_hash_set, R.id.card_add_hash_set, R.id.card_remove_hash_set, R.id.card_set_hash_set, R.id.card_get_hash_set, R.id.card_go_through_hash_set, R.id.card_go_through_by_iterator_hash_set, R.id.card_go_through_by_list_iterator_hash_set, R.id.card_clear_hash_set, R.id.card_to_string_hash_set, R.id.card_sort_hash_set, R.id.card_multi_thread_hash_set})
    void onClick(View v) {
        int size = mHashSet.size();
        switch (v.getId()) {

            case R.id.card_features_hash_set:
                if(null == mFeaturesDialog){
                    String title = "HashSet特性";
                    String message = getString(R.string.features_hash_set);
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

            case R.id.card_add_hash_set:
                UUID uuid = UUID.randomUUID();
                mHashSet.add(uuid.toString());
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_remove_hash_set:
                if (size <= 0) {
                    addLog("HashSet没有元素!");
                    return;
                }

                Iterator<String> iterator1 = mHashSet.iterator();
                boolean hasNext1 = iterator1.hasNext();
                if (hasNext1) {
                    String next = iterator1.next();
                    mHashSet.remove(next);
                    addLog("删除元素: " + next);
                }

                break;

            case R.id.card_set_hash_set:
                addLog("HashSet不支持按index更改");
                break;

            case R.id.card_get_hash_set:
                addLog("HashSet不支持按index查询");

                break;

            case R.id.card_go_through_by_iterator_hash_set:
                if (size <= 0) {
                    addLog("HashSet没有元素!");
                    return;
                }

                Iterator<String> iterator = mHashSet.iterator();
                addLog("---开始通过Iterator遍历---");
                boolean hasNext = iterator.hasNext();
                addLog("Iterator.hasNest: " + hasNext);
                while (hasNext) {
                    String next = iterator.next();
                    addLog("next element: " + next);
                    hasNext = iterator.hasNext();
                    addLog("Iterator.hasNest: " + hasNext);
                }
                addLog("---结束遍历---");

                break;

            case R.id.card_go_through_by_list_iterator_hash_set:
                addLog("Set不支持ListIterator");
                break;

            case R.id.card_go_through_hash_set:
                addLog("Set不支持按index遍历");
                break;


            case R.id.card_clear_hash_set:
                mHashSet.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_hash_set:
                printToString();
                break;

            case R.id.card_sort_hash_set:
                addLog("HashSet不支持排序");
                break;

            case R.id.card_multi_thread_hash_set:
                addLog("HashSet不同步，不支持多线程读写操作!");
                break;
        }
    }


    private void printToString(){
        addLog("当前HashSet: " + mHashSet.toString());
    }
}
