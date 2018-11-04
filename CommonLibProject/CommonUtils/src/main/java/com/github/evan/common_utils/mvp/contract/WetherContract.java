package com.github.evan.common_utils.mvp.contract;

import com.github.evan.common_utils.mvp.m.WetherModel;
import com.github.evan.common_utils.mvp.p.BaseMvpPresenter;
import com.github.evan.common_utils.mvp.p.IMvpPresenter;
import com.github.evan.common_utils.mvp.v.IMvpView;

import java.util.List;

/**
 * Created by Evan on 2018/10/31.
 */

public class WetherContract {

    public static class WetherPresenter extends BaseMvpPresenter<WetherView, WetherModel>{

        public void getWether(){

        }

    }

    public interface WetherView extends IMvpView{

        void refreshWether(WetherModel model);

    }
}
