package com.github.evan.common_utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.evan.common_utils.db.upgradeInfomation.DbUpgradable;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableVersionOne;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableVersionTwo;

import java.util.List;

/**
 * Created by Evan on 2018/9/18.
 */
public abstract class BaseSQLiteHelper extends SQLiteOpenHelper {



    public BaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
