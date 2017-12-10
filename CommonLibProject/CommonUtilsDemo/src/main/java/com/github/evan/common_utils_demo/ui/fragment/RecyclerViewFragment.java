package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.itemDecoration.GridDecoration;
import com.github.evan.common_utils.ui.itemDecoration.ListDecoration;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

/**
 * Created by Evan on 2017/11/20.
 */
public class RecyclerViewFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private static final String[] STAGGERED_IMAGE = {"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=766881540,2545592682&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=859659734,30821133&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=26890901,3717469767&fm=27&gp=0.jpg", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3793268867,2253056783&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3617658691,1266352621&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=449224247,2721763316&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1305044176,1000394676&fm=27&gp=0.jpg", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=876784344,2672678338&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3500537501,96033095&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3341468609,1267172742&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2838846681,2406154032&fm=27&gp=0.jpg", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1329527429,4206454328&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3432984473,4220412296&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3098561018,1240663525&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3535935581,749405034&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=906875671,995099983&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3951944368,2310462383&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2811995463,1316736047&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2386538619,1388062622&fm=27&gp=0.jpg", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=174473989,1257616128&fm=27&gp=0.jpg"};


    @BindView(R.id.tab_layout_recycler_view_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_pager_recycler_view_fragment)
    LoadingPager mLoadingPager;
    private RecyclerAdapter mRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, null);
        ButterKnife.bind(this, root);
        mTabLayout.addOnTabSelectedListener(this);
        return root;
    }


    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
            onTabSelected(tab);
            mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.IDLE);
            mLoadingPager.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View childAt = tabStrip.getChildAt(i);
                childAt.setClickable(true);
            }
        }
    }

    @Override
    protected void loadData() {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View childAt = tabStrip.getChildAt(i);
            childAt.setClickable(false);
        }
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                int N = STAGGERED_IMAGE.length;
                List<String> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(STAGGERED_IMAGE[i]);
                }

                mRecyclerAdapter = new RecyclerAdapter(getContext());
                mRecyclerAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        };

        ThreadManager.getInstance().getIOThreadPool().execute(runnable);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case 0:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_VERTICAL);
                mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 1:
                RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(horizontalLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_HORIZONTAL);
                mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 2:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                mRecyclerView.setLayoutManager(gridLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.GRID_MODE);
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 3:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.STAGGERED_GRID_MODE);
                mRecyclerView.setAdapter(mRecyclerAdapter);

                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
