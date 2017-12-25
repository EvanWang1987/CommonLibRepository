package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.ArrayListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedListActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.LinkedVectorActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.VectorActivity;

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

    @OnClick({R.id.card_vector, R.id.card_array_list, R.id.card_linked_list, R.id.card_linked_vector})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_vector:
                loadActivity(VectorActivity.class);
                break;

            case R.id.card_linked_vector:
                loadActivity(LinkedVectorActivity.class);
                break;

            case R.id.card_array_list:
                loadActivity(ArrayListActivity.class);
                break;

            case R.id.card_linked_list:
                loadActivity(LinkedListActivity.class);
                break;
        }
    }
}
