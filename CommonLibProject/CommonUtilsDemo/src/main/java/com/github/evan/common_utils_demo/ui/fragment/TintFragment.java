package com.github.evan.common_utils_demo.ui.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/12.
 */
public class TintFragment extends BaseFragment {
    @BindView(R.id.imgUnTint)
    AppCompatImageView mImgUnTint;

    @BindView(R.id.imgTint_src)
    AppCompatImageView mImgSrcTintMode;
    @BindView(R.id.imgTintDST)
    AppCompatImageView mImgDSTTintMode;

    @BindView(R.id.imgTint_srcIn)
    AppCompatImageView mImgSrcInTintMode;
    @BindView(R.id.imgTint_dstIn)
    AppCompatImageView mImgDstInTintMode;

    @BindView(R.id.imgTint_srcOut)
    AppCompatImageView mImgSrcOutTintMode;
    @BindView(R.id.imgTint_dstOut)
    AppCompatImageView mImgDstOutTintMode;

    @BindView(R.id.imgTint_srcOver)
    AppCompatImageView mImgSrcOverTintMode;
    @BindView(R.id.imgTint_dstOver)
    AppCompatImageView mImgDSTOverTintMode;

    @BindView(R.id.imgTint_srcAtop)
    AppCompatImageView mImgSrcAtopTintMode;
    @BindView(R.id.imgTint_dstAtop)
    AppCompatImageView mImgDstAtopTintMode;

    @BindView(R.id.imgTint_darken)
    AppCompatImageView mImgDarkenTintMode;
    @BindView(R.id.imgTint_lighten)
    AppCompatImageView mImgLightenTintMode;

    @BindView(R.id.imgTint_xor)
    AppCompatImageView mImgXorTintMode;
    @BindView(R.id.imgTint_screen)
    AppCompatImageView mImgScreenTintMode;

    @BindView(R.id.imgTint_multiply)
    AppCompatImageView mImgMultiplyTintMode;
    @BindView(R.id.imgTintAdd)
    AppCompatImageView mImgAddTintMode;

    @BindView(R.id.imgTintClear)
    AppCompatImageView mImgClearTintMode;
    @BindView(R.id.imgTint_overly)
    AppCompatImageView mImgOverlyTintMode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tint, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.btnTint_clear, R.id.btnTint_src, R.id.btnTint_DST, R.id.btnTint_add, R.id.btnTint_srcOver, R.id.btnTint_dstOver, R.id.btnTint_screen, R.id.btnTint_srcIn, R.id.btnTint_dstIn, R.id.btnTint_multiply, R.id.btnTint_srcOut, R.id.btnTint_dstOut, R.id.btnTint_xor, R.id.btnTint_srcAtop, R.id.btnTint_dstAtop, R.id.btnTint_darken, R.id.btnTint_lighten, R.id.btnTint_overly})
    void onClick(View v) {
        tintImage(v, ResourceUtil.getColor(R.color.Orange));
    }

    private void tintImage(View view, @ColorInt int tintColor) {
        Logger.d("tintImage: clickId: " + view.getId());
        Drawable drawable = mImgUnTint.getDrawable();
        Drawable wrap = DrawableCompat.wrap(drawable);

        switch (view.getId()) {
            case R.id.btnTint_clear:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.CLEAR);
                mImgClearTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_src:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC);
                mImgSrcTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_DST:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DST);
                mImgDSTTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_add:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.ADD);
                mImgAddTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_srcOver:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_OVER);
                mImgSrcOverTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_dstOver:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DST_OVER);
                mImgDSTOverTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_screen:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SCREEN);
                mImgScreenTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_srcIn:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_IN);
                mImgSrcInTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_dstIn:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DST_IN);
                mImgDstInTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_multiply:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.MULTIPLY);
                mImgMultiplyTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_srcOut:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_OUT);
                mImgSrcOutTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_dstOut:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DST_OUT);
                mImgDstOutTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_xor:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.XOR);
                mImgXorTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_srcAtop:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_ATOP);
                mImgSrcAtopTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_dstAtop:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DST_ATOP);
                mImgDstAtopTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_darken:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.DARKEN);
                mImgDarkenTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_lighten:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.LIGHTEN);
                mImgLightenTintMode.setImageDrawable(wrap);
                break;

            case R.id.btnTint_overly:
                DrawableCompat.setTint(wrap, ResourceUtil.getColor(R.color.Pink));
                DrawableCompat.setTintMode(wrap, PorterDuff.Mode.OVERLAY);
                mImgOverlyTintMode.setImageDrawable(wrap);
                break;
        }
    }
}
