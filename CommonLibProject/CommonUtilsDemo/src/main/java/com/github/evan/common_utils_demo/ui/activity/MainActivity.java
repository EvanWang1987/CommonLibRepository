package com.github.evan.common_utils_demo.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.BaseFragmentActivity;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.utils.PackageUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.fragment.AboutFragmentFragment;
import com.github.evan.common_utils_demo.ui.fragment.ArithmeticFragment;
import com.github.evan.common_utils_demo.ui.fragment.CalculatorFragment;
import com.github.evan.common_utils_demo.ui.fragment.CollectionFragment;
import com.github.evan.common_utils_demo.ui.fragment.CustomEditTextFragment;
import com.github.evan.common_utils_demo.ui.fragment.DeviceInformationFragment;
import com.github.evan.common_utils_demo.ui.fragment.FlagViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.HandlerFragment;
import com.github.evan.common_utils_demo.ui.fragment.HomeFragment;
import com.github.evan.common_utils_demo.ui.fragment.HorNestVerScrollViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.ListGridViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.NetworkManagerFragment;
import com.github.evan.common_utils_demo.ui.fragment.PullLayoutFragment;
import com.github.evan.common_utils_demo.ui.fragment.QRCodeFragment;
import com.github.evan.common_utils_demo.ui.fragment.SlideExitActivityFragment;
import com.github.evan.common_utils_demo.ui.fragment.ThreadFragment;
import com.github.evan.common_utils_demo.ui.fragment.VerNestHorScrollViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.RecyclerViewFragment;
import com.github.evan.common_utils_demo.ui.fragment.DebugFragment;
import com.github.evan.common_utils_demo.ui.fragment.TintFragment;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerFragment;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerNestListViewFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/9.
 */
public class MainActivity extends BaseFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final String[] fragmentNames = {HomeFragment.class.getName(), DeviceInformationFragment.class.getName(), CalculatorFragment.class.getName(), ArithmeticFragment.class.getName(), CollectionFragment.class.getName(), ThreadFragment.class.getName(), NetworkManagerFragment.class.getName(), AboutFragmentFragment.class.getName(), HandlerFragment.class.getName(), ListGridViewFragment.class.getName(), RecyclerViewFragment.class.getName(), VerNestHorScrollViewFragment.class.getName(), HorNestVerScrollViewFragment.class.getName(), ViewPagerFragment.class.getName(), ViewPagerNestListViewFragment.class.getName(), PullLayoutFragment.class.getName(), SlideExitActivityFragment.class.getName(), QRCodeFragment.class.getName(), CustomEditTextFragment.class.getName(), FlagViewFragment.class.getName(), TintFragment.class.getName(), DebugFragment.class.getName()};

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

    AlertDialog mUnrequestedAllPermissionDialog;


    @Override
    protected List<String> getFragmentName() {
        List<String> strings = Arrays.asList(fragmentNames);
        return strings;
    }

    @Override
    protected String showWhichFragmentWhenInitialized() {
        return HomeFragment.class.getName();
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }

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
//        FragmentUtil.addAllFragmentAndShowSomeOne(this, getSupportFragmentManager(), R.id.fragmentContainer, fragmentNames, true, null, HomeFragment.class.getName());
        mNavigationView.setCheckedItem(R.id.functionHome);
        List<String> unRequestedPermission = PackageUtil.checkPermission(Manifest.permission.VIBRATE, Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? Manifest.permission.READ_EXTERNAL_STORAGE : null);
        if (!unRequestedPermission.isEmpty()) {
            String[] permissions = new String[unRequestedPermission.size()];
            permissions = unRequestedPermission.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            List<String> unRequestedPermission = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    unRequestedPermission.add(permissions[i]);
                }
            }

            if (!unRequestedPermission.isEmpty()) {
                StringBuilder sBuilder = new StringBuilder();
                for (int i = 0; i < unRequestedPermission.size(); i++) {
                    String s = unRequestedPermission.get(i);
                    sBuilder.append("\r\n");
                    sBuilder.append(s);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.warning);
                builder.setMessage(ResourceUtil.getString(R.string.you_do_not_give_the_permission, sBuilder.toString()));
                builder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                });
                builder.setCancelable(false);
                mUnrequestedAllPermissionDialog = builder.create();
                mUnrequestedAllPermissionDialog.setCanceledOnTouchOutside(false);
                mUnrequestedAllPermissionDialog.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            toggleDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        DeskIconManager.getInstance(this).release();
        super.onDestroy();
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
            loadActivity(AboutActivity.class);
            return true;
        }else if(item.getItemId() == R.id.actionBtn_open_logcat){
            DeskIconManager.getInstance(this).showLogCatIcon();
            return true;
        }else if(item.getItemId() == R.id.actionBtn_close_logcat){
            DeskIconManager.getInstance(this).dismissLogCatIcon();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        BaseActivityConfig config = new BaseActivityConfig();
        config.isPressTwiceToExit = true;
        return config;
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
                visibleFragment = fragmentManager.findFragmentByTag(DeviceInformationFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionCalculator:
                visibleFragment = fragmentManager.findFragmentByTag(CalculatorFragment.class.getName());
                isExpand = false;
                break;
            case R.id.functionArithmetic:
                visibleFragment = fragmentManager.findFragmentByTag(ArithmeticFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionAboutCollection:
                visibleFragment = fragmentManager.findFragmentByTag(CollectionFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionAboutThread:
                visibleFragment = fragmentManager.findFragmentByTag(ThreadFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionNetwork:
                visibleFragment = fragmentManager.findFragmentByTag(NetworkManagerFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionAboutFragment:
                visibleFragment = fragmentManager.findFragmentByTag(AboutFragmentFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionAboutHandler:
                visibleFragment = fragmentManager.findFragmentByTag(HandlerFragment.class.getName());
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

            case R.id.functionVerNestHorScrollView:
                visibleFragment = fragmentManager.findFragmentByTag(VerNestHorScrollViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionHorNestVerScrollView:
                visibleFragment = fragmentManager.findFragmentByTag(HorNestVerScrollViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionViewPager:
                visibleFragment = fragmentManager.findFragmentByTag(ViewPagerFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionViewPagerNestMultiHorizontalScroll:
                visibleFragment = fragmentManager.findFragmentByTag(ViewPagerNestListViewFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionPullLayout:
                visibleFragment = fragmentManager.findFragmentByTag(PullLayoutFragment.class.getName());
                isExpand = true;
                break;

            case R.id.functionSlideExitActivity:
                visibleFragment = fragmentManager.findFragmentByTag(SlideExitActivityFragment.class.getName());
                isExpand = false;
                break;

            case R.id.functionQRCode:
                visibleFragment = fragmentManager.findFragmentByTag(QRCodeFragment.class.getName());
                isExpand = false;
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
