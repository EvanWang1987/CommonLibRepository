package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */
public class SparseArrayActivity extends BaseLogCatActivity {
    private SparseArray<String> mSparseArray = new SparseArray<>();
    private int mKey;
    private AlertDialog mFeaturesDialog;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_sparse_array, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.card_features_sparse_array, R.id.card_add_sparse_array ,R.id.card_remove_sparse_array ,R.id.card_set_sparse_array ,R.id.card_get_sparse_array ,R.id.card_go_through_sparse_array ,R.id.card_go_through_by_iterator_sparse_array, R.id.card_go_through_by_list_iterator_sparse_array, R.id.card_clear_sparse_array, R.id.card_to_string_sparse_array ,R.id.card_sort_sparse_array ,R.id.card_multi_thread_sparse_array})
    void onClick(View v){
        int size = mSparseArray.size();
        switch (v.getId()){

            case R.id.card_features_sparse_array:
                if(null == mFeaturesDialog){
                    String title = "SparseArray特性";
                    String message = getString(R.string.features_sparse_array);
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

            case R.id.card_add_sparse_array:
                UUID uuid = UUID.randomUUID();
                mSparseArray.put(mKey, uuid.toString());
                mKey++;
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_remove_sparse_array:
                if(size <= 0){
                    addLog("SparseArray没有元素!");
                    return;
                }

                String removeValue = mSparseArray.get(--mKey);
                mSparseArray.remove(mKey);
                mKey--;
                addLog("删除元素: " + removeValue);
                break;

            case R.id.card_set_sparse_array:
                if(size <= 0){
                    addLog("SparseArray没有元素!");
                    return;
                }

                UUID newValue = UUID.randomUUID();
                int index = size / 2;
                String oldValue = mSparseArray.get(index);
                mSparseArray.put(index, newValue.toString());
                addLog("把index: " + index + "的元素由"+ oldValue +"改为" + newValue.toString());
                break;

            case R.id.card_get_sparse_array:
                if(size <= 0){
                    addLog("SparseArray没有元素!");
                    return;
                }
                int middleIndex = size / 2;
                String middleE = mSparseArray.get(middleIndex);
                addLog("middleIndex: " + middleIndex + ", 元素: " + middleE);
                break;

            case R.id.card_go_through_by_iterator_sparse_array:
                addLog("SparseArray不支持Iterator!");
                break;

            case R.id.card_go_through_by_list_iterator_sparse_array:
                addLog("SparseArray不支持ListIterator!");
                break;

            case R.id.card_go_through_sparse_array:
                if(size <= 0){
                    addLog("SparseArray没有元素!");
                    return;
                }

                addLog("---开始遍历---");
                for (int i = 0; i < size; i++) {
                    String e = mSparseArray.get(i);
                    addLog("index: " + i + ", element: " + e);
                }
                addLog("---结束遍历---");
                break;



            case R.id.card_clear_sparse_array:
                mSparseArray.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_sparse_array:
                printToString();
                break;

            case R.id.card_sort_sparse_array:
                addLog("SparseArray自动升序排序！");
                break;

            case R.id.card_multi_thread_sparse_array:
                ToastUtil.showToastWithShortDuration("SparseArray 不支持多线程读写操作!");
                break;
        }
    }

    private void printToString(){
        int size = mSparseArray.size();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        if(size > 0){
            stringBuilder.append("\r\n");
        }
        for (int i = 0; i < size; i++) {
            String value = mSparseArray.get(i);
            stringBuilder.append(i + " --- " + value);
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("}");
        addLog("当前SparseArray: " + stringBuilder.toString());
    }
}
