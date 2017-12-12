package com.github.evan.common_utils.api;

import java.io.IOException;
import java.util.List;

/**
 * Created by Evan on 2017/12/12.
 */

public interface ApiGroupCallback {

    void onRequestSuccess(List<BaseApi> apis);

    void onRequestFail(List<BaseApi> successApi, List<BaseApi> failedApi, List<IOException> e);

}
