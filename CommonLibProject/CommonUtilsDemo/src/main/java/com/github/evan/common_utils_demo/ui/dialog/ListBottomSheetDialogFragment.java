package com.github.evan.common_utils_demo.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.github.evan.common_utils.bean.MusicInfo;
import com.github.evan.common_utils.ui.dialog.BaseBottomSheetDialogFragment;
import com.github.evan.common_utils.ui.itemDecoration.ListDecoration;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.MyApplication;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutMedia.MediaPlayerActivity;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.MusicAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {
    @BindView(R.id.txt_title)
    public TextView mTxtTitle;
    @BindView(R.id.btn_close)
    public ImageButton mBtnClose;
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    private List<MusicInfo> mMusicInfo = new ArrayList<>();
    private MusicAdapter mMusicAdapter;

    @Override
    protected View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.dialog_list_bottom, null);
        ButterKnife.bind(this, view);
        Logger.i("mRecyclerView: " + mRecyclerView);
        return view;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if(activity instanceof MediaPlayerActivity){
            MediaPlayerActivity mediaPlayerActivity = (MediaPlayerActivity) activity;
            List<MusicInfo> musicInfo = mediaPlayerActivity.getMusicInfo();
            setData(musicInfo);
        }
    }

    public void setData(List<MusicInfo> musicInfo){
        mMusicInfo.clear();
        mMusicInfo.addAll(musicInfo);

        if(null == mMusicAdapter){
            mMusicAdapter = new MusicAdapter(MyApplication.getApplication());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
//            mRecyclerView.addItemDecoration(new ListDecoration(R.color.divider_color_gray, DensityUtil.dp2px(0.5f)));
            mRecyclerView.setAdapter(mMusicAdapter);
        }
        mMusicAdapter.addAll(mMusicInfo);
        mMusicAdapter.notifyDataSetChanged();
        mTxtTitle.setText(mMusicInfo.size() + "首歌曲");
    }


}
