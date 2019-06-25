package com.github.evan.common_utils_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutMedia.MediaPlayerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_media_player, R.id.card_sound_pool, R.id.card_surface_view, R.id.card_texture_view})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.card_media_player:
                Intent intent = new Intent(getContext(), MediaPlayerActivity.class);
                startActivity(intent);
                break;

            case R.id.card_sound_pool:

                break;

            case R.id.card_surface_view:

                break;

            case R.id.card_texture_view:

                break;
        }

    }

}
