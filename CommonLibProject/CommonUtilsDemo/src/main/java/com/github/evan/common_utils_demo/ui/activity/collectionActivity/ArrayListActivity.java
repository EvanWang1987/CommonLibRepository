package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.comparator.DictionaryOrderComparator;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.dialog.ScrollMessageMaterialDialog;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */
public class ArrayListActivity extends BaseLogCatActivity {
    private ArrayList<String> mArrayList = new ArrayList<>();
    private DictionaryOrderComparator mComparator;
    private AlertDialog mFeaturesDialog;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_array_list, null);
        ButterKnife.bind(this, root);
        mComparator = new DictionaryOrderComparator(Locale.US);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_features_array_list, R.id.card_add_array_list ,R.id.card_remove_array_list ,R.id.card_set_array_list ,R.id.card_get_array_list ,R.id.card_go_through_array_list ,R.id.card_go_through_by_iterator_array_list, R.id.card_go_through_by_list_iterator_array_list, R.id.card_clear_array_list, R.id.card_to_string_array_list ,R.id.card_sort_array_list ,R.id.card_multi_thread_array_list})
    void onClick(View v){
        int size = mArrayList.size();
        switch (v.getId()){

            case R.id.card_features_array_list:
                if(null == mFeaturesDialog){
                    String title = "ArrayList特性";
                    String message = getString(R.string.features_array_list);
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

            case R.id.card_add_array_list:
                UUID uuid = UUID.randomUUID();
                mArrayList.add(uuid.toString());
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_remove_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }
                String removeE = mArrayList.remove(size - 1);
                addLog("删除元素: " + removeE);
                break;

            case R.id.card_set_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }
                int index = size / 2;
                String s = "Element by set" + index;
                mArrayList.set(index, s);
                addLog("把index: " + index + "的元素改为" + s);
                break;

            case R.id.card_get_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }
                String lastE = mArrayList.get(size - 1);
                addLog("最后一个元素为: " + lastE);
                break;

            case R.id.card_go_through_by_iterator_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }

                Iterator<String> iterator = mArrayList.iterator();
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

            case R.id.card_go_through_by_list_iterator_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }

                ListIterator<String> stringListIterator = mArrayList.listIterator();
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

            case R.id.card_go_through_array_list:
                if(size <= 0){
                    addLog("ArrayList没有元素!");
                    return;
                }

                addLog("---开始遍历---");
                for (int i = 0; i < size; i++) {
                    String e = mArrayList.get(i);
                    addLog("index: " + i + ", element: " + e);
                }
                addLog("---结束遍历---");
                break;



            case R.id.card_clear_array_list:
                mArrayList.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_array_list:
                printToString();
                break;

            case R.id.card_sort_array_list:
                Collections.sort(mArrayList, mComparator);
                addLog("排序完毕");
                break;

            case R.id.card_multi_thread_array_list:
                ToastUtil.showToastWithShortDuration("ArrayList 不支持多线程读写操作!");
                break;
        }
    }

    private void printToString(){
        addLog("当前ArrayList: " + mArrayList.toString());
    }
}
