package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.comparator.DictionaryOrderComparator;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */

public class LinkedListActivity extends BaseLogCatActivity {
    private LinkedList<String> mLinkedList = new LinkedList<>();
    private DictionaryOrderComparator mComparator;
    private AlertDialog mFeaturesDialog;

    @Override
    public SlideExitActivityConfig onCreateConfig() {
        SlideExitActivityConfig config = new SlideExitActivityConfig();
        config.setBackgroundColor(ResourceUtil.getColor(com.github.evan.common_utils.R.color.White));
        config.setSlidingPercentRelativeActivityWhenNotExit(0.3f);
        config.setExitDirection(SlideExitDirection.LEFT_TO_RIGHT);
        config.setExitDuration(200);
        config.setRollBackDuration(300);
        return config;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_linked_list, null);
        ButterKnife.bind(this, view);
        mComparator = new DictionaryOrderComparator(Locale.US);
        return view;
    }

    @Override
    public void onSlideExit(SlideExitDirection direction, View dst, Activity activity) {
        activity.finish();
    }


    @OnClick({R.id.card_features_linked_list, R.id.card_add_linked_list, R.id.card_add_first_linked_list, R.id.card_add_last_linked_list, R.id.card_remove_linked_list, R.id.card_remove_first_linked_list, R.id.card_remove_last_linked_list, R.id.card_set_linked_list ,R.id.card_get_linked_list ,R.id.card_go_through_linked_list ,R.id.card_go_through_by_iterator_linked_list, R.id.card_go_through_by_list_iterator_linked_list, R.id.card_clear_linked_list, R.id.card_to_string_linked_list ,R.id.card_sort_linked_list ,R.id.card_multi_thread_linked_list})
    void onClick(View v){
        int size = mLinkedList.size();
        switch (v.getId()){

            case R.id.card_features_linked_list:
                if(null == mFeaturesDialog){
                    String title = "LinkedList特性";
                    String message = getString(R.string.features_linked_list);
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

            case R.id.card_add_linked_list:
                UUID uuid = UUID.randomUUID();
                mLinkedList.add(uuid.toString());
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_add_first_linked_list:
                UUID uuidFirst = UUID.randomUUID();
                mLinkedList.addFirst(uuidFirst.toString());
                addLog("增加头部元素, " + uuidFirst.toString());
                break;

            case R.id.card_add_last_linked_list:
                UUID uuidLast = UUID.randomUUID();
                mLinkedList.addLast(uuidLast.toString());
                addLog("增加尾部元素, " + uuidLast.toString());
                break;

            case R.id.card_remove_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }
                String removeE = mLinkedList.remove(size / 2);
                addLog("删除元素: " + removeE);
                break;

            case R.id.card_remove_first_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }
                String removeFirstE = mLinkedList.removeFirst();
                addLog("删除头部元素: " + removeFirstE);
                break;

            case R.id.card_remove_last_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }
                String removeLastE = mLinkedList.removeLast();
                addLog("删除尾部元素: " + removeLastE);
                break;

            case R.id.card_set_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }
                int index = size / 2;
                String s = "Element by set" + index;
                mLinkedList.set(index, s);
                addLog("把index: " + index + "的元素改为" + s);
                break;

            case R.id.card_get_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }
                String lastE = mLinkedList.get(size - 1);
                addLog("最后一个元素为: " + lastE);
                break;

            case R.id.card_go_through_by_iterator_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }

                Iterator<String> iterator = mLinkedList.iterator();
                addLog("---开始通过Iterator遍历---");
                boolean hasNext = iterator.hasNext();
                addLog("Iterator.hasNest: " + hasNext);
                while (hasNext){
                    String next = iterator.next();
                    addLog("next element: " + next);
                    hasNext = iterator.hasNext();
                    addLog("Iterator.hasNest: " + hasNext);
                }
                addLog("---结束遍历---");

                break;

            case R.id.card_go_through_by_list_iterator_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }

                ListIterator<String> stringListIterator = mLinkedList.listIterator();
                addLog("---开始通过ListIterator遍历");
                boolean iteratorHasNext = stringListIterator.hasNext();
                addLog("ListIterator.hasNest: " + iteratorHasNext);
                while (iteratorHasNext){
                    String next = stringListIterator.next();
                    addLog("next element: " + next);
                    iteratorHasNext = stringListIterator.hasNext();
                    addLog("ListIterator.hasNest: " + iteratorHasNext);
                }
                addLog("---结束遍历---");
                break;

            case R.id.card_go_through_linked_list:
                if(size <= 0){
                    addLog("LinkedList没有元素!");
                    return;
                }

                addLog("---开始遍历---");
                for (int i = 0; i < size; i++) {
                    String e = mLinkedList.get(i);
                    addLog("index: " + i + ", element: " + e);
                }
                addLog("---结束遍历---");
                break;



            case R.id.card_clear_linked_list:
                mLinkedList.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_linked_list:
                printToString();
                break;

            case R.id.card_sort_linked_list:
                Collections.sort(mLinkedList, mComparator);
                addLog("排序完毕");
                break;

            case R.id.card_multi_thread_linked_list:
                ToastUtil.showToastWithShortDuration("LinkedList 不支持多线程读写操作!");
                break;
        }
    }


    private void printToString(){
        addLog("当前LinkedList: " + mLinkedList.toString());
    }
}
