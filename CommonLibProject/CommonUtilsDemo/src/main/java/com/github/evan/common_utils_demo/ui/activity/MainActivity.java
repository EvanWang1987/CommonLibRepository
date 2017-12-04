package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.github.evan.common_utils.utils.UiUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.fragment.CustomEditTextFragment;
import com.github.evan.common_utils_demo.ui.fragment.FlagViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.HomeFragment;
import com.github.evan.common_utils_demo.ui.fragment.HorNestVerScrollViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.ListGridViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.PullToRefreshFragment;
import com.github.evan.common_utils_demo.ui.fragment.VerNestHorScrollViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.RecyclerViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.ScreenInformationFragment;
import com.github.evan.common_utils_demo.ui.fragment.DebugFragment;
import com.github.evan.common_utils_demo.ui.fragment.TintFragment;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/9.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String[] fragmentNames = {HomeFragment.class.getName(), ScreenInformationFragment.class.getName(), ListGridViewFragment.class.getName(), RecyclerViewFragment.class.getName(), ViewPagerFragment.class.getName(), VerNestHorScrollViewFragment.class.getName(), HorNestVerScrollViewFragment.class.getName(), PullToRefreshFragment.class.getName(), CustomEditTextFragment.class.getName(), FlagViewFragment.class.getName(), TintFragment.class.getName(), DebugFragment.class.getName()};

    @BindView(R.id.mainActivity_appBar)
    public AppBarLayout mAppbarLayout;
    @BindView(R.id.collapsingLayout)
    public CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.toolBar)
    public Toolbar mToolBar;
    @BindView(R.id.drawerLayout)
    public DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView)
    public NavigationView mNavigationView;
    @BindView(R.id.coordinatorLayout)
    public CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.btn_share)
    public FloatingActionButton mBtnShare;


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
    }

    @OnClick(R.id.btn_share)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        UiUtil.setPopupToolBarMenuWithIcon(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionBtn_about) {
            loadActivity(TestActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean isHandled = switchFragment(item.getItemId());
        toggleDrawer(Gravity.LEFT);
        return isHandled;
    }

    private void toggleDrawer(int gravity) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(gravity);
        if (drawerOpen) {
            mDrawerLayout.closeDrawer(Gravity.LEFT, true);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT, true);
        }
    }

    private boolean switchFragment(int selectedMenuItemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment visibleFragment = null;
        boolean isExpand = false;

        switch (selectedMenuItemId) {
            case R.id.functionHome:
                visibleFragment = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionScreenAdaptation:
                visibleFragment = fragmentManager.findFragmentByTag(ScreenInformationFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionListView:
                visibleFragment = fragmentManager.findFragmentByTag(ListGridViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionRecyclerView:
                visibleFragment = fragmentManager.findFragmentByTag(RecyclerViewFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionViewPager:
                visibleFragment = fragmentManager.findFragmentByTag(ViewPagerFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionVerNestHorScrollView:
                visibleFragment = fragmentManager.findFragmentByTag(VerNestHorScrollViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionHorNestVerScrollView:
                visibleFragment = fragmentManager.findFragmentByTag(HorNestVerScrollViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionPullToRefresh:
                visibleFragment = fragmentManager.findFragmentByTag(PullToRefreshFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionCustomEditText:
                visibleFragment = fragmentManager.findFragmentByTag(CustomEditTextFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionFlagView:
                visibleFragment = fragmentManager.findFragmentByTag(FlagViewFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionTint:
                visibleFragment = fragmentManager.findFragmentByTag(TintFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionTest:
                visibleFragment = fragmentManager.findFragmentByTag(DebugFragment.class.getName());
                isExpand = false;
                break;

            default:
                return false;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int N = fragmentNames.length;
        for (int index = 0; index < N; index++) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentNames[index]);
            transaction.hide(fragment);
        }
        transaction.show(visibleFragment);
        transaction.commit();
        mAppbarLayout.setExpanded(isExpand, true);
        return true;
    }

}
