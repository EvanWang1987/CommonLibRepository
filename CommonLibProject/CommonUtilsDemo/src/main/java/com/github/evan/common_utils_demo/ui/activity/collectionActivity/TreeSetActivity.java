package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.comparator.DictionaryOrderComparator;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;
import java.util.UUID;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/26.
 */
public class TreeSetActivity extends BaseLogCatActivity {
    private DictionaryOrderComparator mComparator = new DictionaryOrderComparator(Locale.US);
    private TreeSet<String> mTreeSet = new TreeSet<>(mComparator);

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_tree_set, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_add_tree_set, R.id.card_remove_tree_set, R.id.card_set_tree_set, R.id.card_get_tree_set, R.id.card_go_through_tree_set, R.id.card_go_through_by_iterator_tree_set, R.id.card_go_through_by_list_iterator_tree_set, R.id.card_clear_tree_set, R.id.card_to_string_tree_set, R.id.card_sort_tree_set, R.id.card_multi_thread_tree_set})
    void onClick(View v) {
        int size = mTreeSet.size();
        switch (v.getId()) {
            case R.id.card_add_tree_set:
                UUID uuid = UUID.randomUUID();
                mTreeSet.add(uuid.toString());
                addLog("增加元素, " + uuid.toString());
                break;

            case R.id.card_remove_tree_set:
                if (size <= 0) {
                    addLog("TreeSet没有元素!");
                    return;
                }

                Iterator<String> iterator1 = mTreeSet.iterator();
                boolean hasNext1 = iterator1.hasNext();
                if (hasNext1) {
                    String next = iterator1.next();
                    mTreeSet.remove(next);
                    addLog("删除元素: " + next);
                }

                break;

            case R.id.card_set_tree_set:
                addLog("TreeSet不支持按index更改");
                break;

            case R.id.card_get_tree_set:
                addLog("TreeSet不支持按index查询");

                break;

            case R.id.card_go_through_by_iterator_tree_set:
                if (size <= 0) {
                    addLog("TreeSet没有元素!");
                    return;
                }

                Iterator<String> iterator = mTreeSet.iterator();
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

            case R.id.card_go_through_by_list_iterator_tree_set:
                addLog("Set不支持ListIterator");
                break;

            case R.id.card_go_through_tree_set:
                addLog("Set不支持按index遍历");
                break;


            case R.id.card_clear_tree_set:
                mTreeSet.clear();
                addLog("清除完毕");
                break;

            case R.id.card_to_string_tree_set:
                printToString();
                break;

            case R.id.card_sort_tree_set:
                addLog("TreeSet自动排序,但元素需要实现Comparable接口，或者构造TreeSet时，传递Comparator对象");
                break;

            case R.id.card_multi_thread_tree_set:
                addLog("TreeSet不同步，不支持多线程读写操作!");
                break;
        }
    }

    private void printToString() {
        addLog("当前TreeSet: " + mTreeSet.toString());
    }

}
