package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.activity.SinglePhotoActivity;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.ArrayListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.ArrayMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.HashMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.HashSetActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedHashMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedHashSetActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.SparseArrayActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.TreeMapActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.TreeSetActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/25.
 */
public class CollectionFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ic_collection_extends, R.id.card_hash_set, R.id.card_linked_hash_set, R.id.card_array_list, R.id.card_linked_list, R.id.card_tree_set, R.id.card_hash_map, R.id.card_linked_hash_map, R.id.card_tree_map, R.id.card_sparse_array, R.id.card_array_map})
    void onClick(View view){
        switch (view.getId()){
            case R.id.ic_collection_extends:
                Bundle bundle = new Bundle();
                bundle.putInt(SinglePhotoActivity.PHOTO_RES_ID, R.mipmap.img_collection_extends);
                loadActivity(SinglePhotoActivity.class, bundle, false, -1);
                break;

            case R.id.card_array_list:
                loadActivity(ArrayListActivity.class);
                break;

            case R.id.card_linked_list:
                loadActivity(LinkedListActivity.class);
                break;

            case R.id.card_hash_set:
                loadActivity(HashSetActivity.class);
                break;

            case R.id.card_linked_hash_set:
                loadActivity(LinkedHashSetActivity.class);
                break;

            case R.id.card_tree_set:
                loadActivity(TreeSetActivity.class);
                break;

            case R.id.card_hash_map:
                loadActivity(HashMapActivity.class);
                break;

            case R.id.card_linked_hash_map:
                loadActivity(LinkedHashMapActivity.class);
                break;

            case R.id.card_tree_map:
                loadActivity(TreeMapActivity.class);
                break;

            case R.id.card_sparse_array:
                loadActivity(SparseArrayActivity.class);
                break;

            case R.id.card_array_map:
                loadActivity(ArrayMapActivity.class);
                break;
        }
    }
}
