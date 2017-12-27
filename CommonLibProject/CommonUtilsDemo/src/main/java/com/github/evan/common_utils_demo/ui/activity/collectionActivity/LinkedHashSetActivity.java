package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils_demo.R;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */

public class LinkedHashSetActivity extends BaseLogCatActivity {
    private LinkedHashSet<String> mLinkedHashSet = new LinkedHashSet<>();
    private AlertDialog mFeaturesDialog;


    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_linked_hash_set, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.card_features_linked_hash_set, R.id.card_add_linked_hash_set, R.id.card_remove_linked_hash_set, R.id.card_set_linked_hash_set, R.id.card_get_linked_hash_set, R.id.card_go_through_linked_hash_set, R.id.card_go_through_by_iterator_linked_hash_set, R.id.card_go_through_by_list_iterator_linked_hash_set, R.id.card_clear_linked_hash_set, R.id.card_to_string_linked_hash_set, R.id.card_sort_linked_hash_set, R.id.card_multi_thread_linked_hash_set})
    void onClick(View v) {
        int size = mLinkedHashSet.size();
        switch (v.getId()) {

            case R.id.card_features_linked_hash_set:
                if(null == mFeaturesDialog){
                    String title = "LinkedHashSet特性";
                    String message = getString(R.string.features_linked_hash_set);
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

            case R.id.card_add_linked_hash_set:
                UUID uuid = UUID.randomUUID();
                mLinkedHashSet.add(uuid.toString());
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_remove_linked_hash_set:
                if (size <= 0) {
                    addLog("LinkedHashSet没有元素!");
                    return;
                }

                Iterator<String> iterator1 = mLinkedHashSet.iterator();
                boolean hasNext1 = iterator1.hasNext();
                if (hasNext1) {
                    String next = iterator1.next();
                    mLinkedHashSet.remove(next);
                    addLog("删除元素: " + next);
                }

                break;

            case R.id.card_set_linked_hash_set:
                addLog("LinkedHashSet不支持按index更改");
                break;

            case R.id.card_get_linked_hash_set:
                addLog("LinkedHashSet不支持按index查询");

                break;

            case R.id.card_go_through_by_iterator_linked_hash_set:
                if (size <= 0) {
                    addLog("LinkedHashSet没有元素!");
                    return;
                }

                Iterator<String> iterator = mLinkedHashSet.iterator();
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

            case R.id.card_go_through_by_list_iterator_linked_hash_set:
                addLog("Set不支持ListIterator");
                break;

            case R.id.card_go_through_linked_hash_set:
                addLog("Set不支持按index遍历");
                break;


            case R.id.card_clear_linked_hash_set:
                mLinkedHashSet.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_linked_hash_set:
                printToString();
                break;

            case R.id.card_sort_linked_hash_set:
                addLog("LinkedHashSet不支持排序");
                break;

            case R.id.card_multi_thread_linked_hash_set:
                addLog("LinkedHashSet不同步，不支持多线程读写操作!");
                break;
        }
    }


    private void printToString(){
        addLog("当前LinkedHashSet: " + mLinkedHashSet.toString());
    }
}
