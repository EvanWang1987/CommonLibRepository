package com.github.evan.common_utils_demo.ui.activity.aboutDataSave;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.DialogMode;
import com.github.evan.common_utils.ui.dialog.InputDialog;
import com.github.evan.common_utils.utils.SpUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/17.
 */
public class SharedPreferenceActivity extends BaseLogCatActivity {
    private int mClickedId;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_shared_preference, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDialogConfirmButtonClick(DialogInterface dialog, DialogMode mode) {
        if(mode == DialogMode.INPUT_DIALOG){
            InputDialog inputDialog = (InputDialog) dialog;
            String fileName = null;
            String key = null;
            String value = null;
            switch (mClickedId){
                case R.id.card_create_sp:
                    String string = inputDialog.getEditText(0).toString();
                    boolean result = SpUtil.getIns().initSharedPreference(false, string);
                    String filePath = Environment.getDataDirectory() + "/shared_prefs/" + string + ".xml";
                    addLog(result ? "Create success! File name is " + string + ". Path is " + filePath : "Create failed!");
                    break;

                case R.id.card_commit_sp:
                    fileName = inputDialog.getEditText(0).toString();
                    key = inputDialog.getEditText(1).toString();
                    value = inputDialog.getEditText(2).toString();
                    boolean isSuccess = SpUtil.getIns().commitString(key, value, false, fileName);
                    addLog(isSuccess ? "Commit success!" : "Commit failed!");
                    break;

                case R.id.card_apply_sp:
                    fileName = inputDialog.getEditText(0).toString();
                    key = inputDialog.getEditText(1).toString();
                    value = inputDialog.getEditText(2).toString();
                    boolean success = SpUtil.getIns().commitString(key, value, false, fileName);
                    final SharedPreferences sharePreference = SpUtil.getIns().getSharePreference(fileName, false);
                    sharePreference.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                        @Override
                        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                            addLog("onSharedPreferenceChanged! apply success! key: " + key);
                            sharePreference.unregisterOnSharedPreferenceChangeListener(this);
                        }
                    });
                    addLog(success ? "Apply!" : "Apply failed!");
                    break;

                case R.id.card_get_sp:
                    fileName = inputDialog.getEditText(0).toString();
                    key = inputDialog.getEditText(1).toString();
                    value = SpUtil.getIns().getString(key, "未找到", false, fileName);
                    addLog("Query value from " + fileName + ", value: " + value);
                    break;

                case R.id.card_view_sp_doc:
                    fileName = inputDialog.getEditText(0).toString();
                    File file = new File(Environment.getDataDirectory() + "/data/"+getPackageName()+"/shared_prefs/" + fileName + ".xml");
                    addLog("-----Start query document of " + fileName + ".xml");
                    try {
                        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser pullParser = xmlPullParserFactory.newPullParser();
                        pullParser.setInput(new FileInputStream(file), "UTF-8");
                        int eventType = pullParser.getEventType();
                        while (eventType != XmlPullParser.END_DOCUMENT){
                            switch (eventType){
                                case XmlPullParser.START_TAG:
                                    if("string".equals(pullParser.getName())){
                                        String name = pullParser.getAttributeValue(0);
                                        String v = pullParser.nextText();
                                        addLog("Query key: " + name + ", value: " + v);
                                    }
                                    break;
                            }
                            eventType = pullParser.next();
                        }
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    addLog("-----End query document of " + fileName + ".xml");


                    break;
            }

        }
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_create_sp, R.id.card_commit_sp, R.id.card_apply_sp, R.id.card_get_sp, R.id.card_view_sp_doc})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_create_sp:
                mClickedId = R.id.card_create_sp;
                showInputDialog(getString(R.string.warning), new String[]{getString(R.string.input_shared_preference_file_name)}, 1, new int[]{5}, getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_commit_sp:
                mClickedId = R.id.card_commit_sp;
                String[] hints = new String[]{getString(R.string.input_shared_preference_file_name), getString(R.string.input_string_key), getString(R.string.input_string_value)};
                showInputDialog(getString(R.string.warning), hints, 3, new int[]{1, 1, 1}, getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_apply_sp:
                mClickedId = R.id.card_apply_sp;
                String[] hint = new String[]{getString(R.string.input_shared_preference_file_name), getString(R.string.input_string_key), getString(R.string.input_string_value)};
                showInputDialog(getString(R.string.warning), hint, 3, new int[]{1, 1, 1}, getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_get_sp:
                mClickedId = R.id.card_get_sp;
                String[] h = new String[]{getString(R.string.input_shared_preference_file_name), getString(R.string.input_string_key)};
                showInputDialog(getString(R.string.warning), h, 2, new int[]{1, 1, 1}, getString(R.string.confirm), getString(R.string.cancel));
                break;

            case R.id.card_view_sp_doc:
                mClickedId = R.id.card_view_sp_doc;
                showInputDialog(getString(R.string.warning), new String[]{getString(R.string.input_file_name)}, 1, new int[]{1}, getString(R.string.confirm), getString(R.string.cancel));
                break;
        }
    }
}
