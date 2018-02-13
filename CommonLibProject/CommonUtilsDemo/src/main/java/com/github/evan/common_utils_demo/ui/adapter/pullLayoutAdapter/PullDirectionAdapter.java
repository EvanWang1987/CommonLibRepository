package com.github.evan.common_utils_demo.ui.adapter.pullLayoutAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by Evan on 2018/2/13.
 */
public class PullDirectionAdapter extends ArrayAdapter<String> {

    public PullDirectionAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }
}
