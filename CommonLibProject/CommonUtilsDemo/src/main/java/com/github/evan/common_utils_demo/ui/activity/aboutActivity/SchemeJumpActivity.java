package com.github.evan.common_utils_demo.ui.activity.aboutActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.RedirectUiActivity;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import com.github.evan.common_utils_demo.utils.RedirectUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/11.
 */

public class SchemeJumpActivity extends BaseLogCatActivity {
    @BindView(R.id.edit_text_url)
    EditText mEditText;





    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_scheme_jump, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }


    @OnClick({R.id.btn_jump, R.id.card_scheme_jump_array_list, R.id.card_scheme_jump_linked_list, R.id.card_scheme_jump_hash_set, R.id.card_scheme_jump_tree_set, R.id.card_scheme_jump_hash_map, R.id.card_scheme_jump_tree_map})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_jump:
                String jumpCode = mEditText.getText().toString();
                addLog("Scheme跳转: " + jumpCode);
                Intent intent = new Intent(this, RedirectUiActivity.class);
                intent.putExtra(RedirectUiActivity.EXTRAS_REDIRECT_CODE, jumpCode);
                startActivity(intent);
                break;

            case R.id.card_scheme_jump_array_list:
                mEditText.setText(RedirectUtils.URI_ARRAY_LIST);
                break;

            case R.id.card_scheme_jump_linked_list:
                mEditText.setText(RedirectUtils.URI_LINKED_LIST);
                break;

            case R.id.card_scheme_jump_hash_set:
                mEditText.setText(RedirectUtils.URI_HASH_SET);
                break;

            case R.id.card_scheme_jump_tree_set:
                mEditText.setText(RedirectUtils.URI_TREE_SET);
                break;

            case R.id.card_scheme_jump_hash_map:
                mEditText.setText(RedirectUtils.URI_HASH_MAP);
                break;

            case R.id.card_scheme_jump_tree_map:
                mEditText.setText(RedirectUtils.URI_TREE_MAP);
                break;
        }
    }
}
