package com.github.evan.common_utils_demo.ui.activity.aboutFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import com.github.evan.common_utils_demo.ui.fragment.fragmentBackStack.FragmentA;
import com.github.evan.common_utils_demo.ui.fragment.fragmentBackStack.FragmentB;
import com.github.evan.common_utils_demo.ui.fragment.fragmentBackStack.FragmentC;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/27.
 */
public class FragmentBackStackActivity extends BaseLogCatActivity implements FragmentManager.OnBackStackChangedListener {
    public static final String EXTRA_FROM_LAST_FRAGMENT = "extra_from_last_fragment";
    private FragmentA mFragmentA = new FragmentA();
    private FragmentB mFragmentB = new FragmentB();
    private FragmentC mFragmentC = new FragmentC();

    @BindView(R.id.btn_jump_next_fragment)
    Button mBtnJumpNext;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_fragment_back_stack, null);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);
        return root;
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if(backStackEntryCount > 0){
            mFragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    @OnClick({R.id.btn_jump_next_fragment, R.id.btn_jump_back_fragment, R.id.btn_print_fragment_stack})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_jump_next_fragment:
                addStack();
                break;

            case R.id.btn_jump_back_fragment:
                popStack();
                break;

            case R.id.btn_print_fragment_stack:
                printStack();
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        printStack();
    }

    private void addStack(){
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if(backStackEntryCount >  3){
            ToastUtil.showToastWithShortDuration("只演示3个Fragment");
            return;
        }
        Fragment dst = null;
        String extraString = null;
        String fragmentName = null;
        switch (backStackEntryCount){
            case 0:
                dst = mFragmentA;
                fragmentName = "FragmentA";
                break;

            case 1:
                dst = mFragmentB;
                fragmentName = "FragmentB";
                extraString = "从FragmentA中携带来的数据";
                break;

            case 2:
                dst = mFragmentC;
                fragmentName = "FragmentC";
                extraString = "从FragmentB中携带来的数据";
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FROM_LAST_FRAGMENT, extraString);
        dst.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.replace(R.id.fragment_container_fragment_back_stack, dst);
        fragmentTransaction.commit();
    }

    private void popStack(){
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if(backStackEntryCount <= 0){
            ToastUtil.showToastWithShortDuration("空栈");
            return;
        }

        mFragmentManager.popBackStack();
    }

    private void printStack(){
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        addLog("Fragment stack count: " + backStackEntryCount);
        if(backStackEntryCount > 0){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = backStackEntryCount - 1; i >= 0; i--) {
                FragmentManager.BackStackEntry backStackEntryAt = mFragmentManager.getBackStackEntryAt(i);
                int id = backStackEntryAt.getId();
                String name = backStackEntryAt.getName();
                stringBuilder.append("BackStackEntry: " + id + " --- " + name);
                stringBuilder.append("\r\n");
            }
            addLog(stringBuilder.toString());
        }
    }
}
