package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.utils.FragmentUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.fragment.CustomEditTextFragment;
import com.github.evan.common_utils_demo.ui.fragment.FlagViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.HomeFragment;
import com.github.evan.common_utils_demo.ui.fragment.TintFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/9.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String[] fragmentNames = {HomeFragment.class.getName(), CustomEditTextFragment.class.getName(), FlagViewFragment.class.getName(), TintFragment.class.getName()};

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open_desc, R.string.drawer_close_desc);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer(Gravity.LEFT);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        FragmentUtil.addAllFragmentAndShowSomeOne(this, getSupportFragmentManager(), R.id.fragmentContainer, fragmentNames, true, null, HomeFragment.class.getName());
        mNavigationView.setCheckedItem(R.id.functionHome);
        mToolBar.setSubtitle(ResourceUtil.getString(R.string.home));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        UiUtil.setPopupToolBarMenuWithIcon(menu);
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean isHandled = switchFragment(item.getItemId());
        toggleDrawer(Gravity.LEFT);
        return isHandled;
    }

    private void toggleDrawer(int gravity){
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(gravity);
        if(drawerOpen){
            mDrawerLayout.closeDrawer(Gravity.LEFT, true);
        }else{
            mDrawerLayout.openDrawer(Gravity.LEFT, true);
        }
    }

    private boolean switchFragment(int selectedMenuItemId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment visibleFragment = null;
        Fragment[] inVisibleFragment = new Fragment[3];

        switch (selectedMenuItemId){
            case R.id.functionHome:
                visibleFragment = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
                inVisibleFragment[0] = fragmentManager.findFragmentByTag(CustomEditTextFragment.class.getName());
                inVisibleFragment[1] = fragmentManager.findFragmentByTag(FlagViewFragment.class.getName());
                inVisibleFragment[2] = fragmentManager.findFragmentByTag(TintFragment.class.getName());
                mToolBar.setSubtitle(ResourceUtil.getString(R.string.home));
                break;

            case R.id.functionCustomEditText:
                visibleFragment = fragmentManager.findFragmentByTag(CustomEditTextFragment.class.getName());
                inVisibleFragment[0] = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
                inVisibleFragment[1] = fragmentManager.findFragmentByTag(FlagViewFragment.class.getName());
                inVisibleFragment[2] = fragmentManager.findFragmentByTag(TintFragment.class.getName());
                mToolBar.setSubtitle(ResourceUtil.getString(R.string.edit_text));
                break;

            case R.id.functionFlagView:
                visibleFragment = fragmentManager.findFragmentByTag(FlagViewFragment.class.getName());
                inVisibleFragment[0] = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
                inVisibleFragment[1] = fragmentManager.findFragmentByTag(CustomEditTextFragment.class.getName());
                inVisibleFragment[2] = fragmentManager.findFragmentByTag(TintFragment.class.getName());
                mToolBar.setSubtitle(ResourceUtil.getString(R.string.flag_view));
                break;

            case R.id.functionTint:
                visibleFragment = fragmentManager.findFragmentByTag(TintFragment.class.getName());
                inVisibleFragment[0] = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
                inVisibleFragment[1] = fragmentManager.findFragmentByTag(CustomEditTextFragment.class.getName());
                inVisibleFragment[2] = fragmentManager.findFragmentByTag(FlagViewFragment.class.getName());
                mToolBar.setSubtitle(ResourceUtil.getString(R.string.tint));
                break;

            default:
                return false;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(visibleFragment);
        int N = inVisibleFragment.length;
        for (int index = 0; index < N; index++) {
            Fragment fragment = inVisibleFragment[index];
            transaction.hide(fragment);
        }
        transaction.commit();
        return true;
    }

}
