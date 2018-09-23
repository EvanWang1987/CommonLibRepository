package com.github.evan.common_utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.evan.common_utils.db.upgradeInfomation.DbUpgradable;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableVersionOne;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableVersionTwo;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableVersionOne;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableVersionThree;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableVersionTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evan on 2018/9/18.
 */

public class PersonDbHelper extends BaseSQLiteHelper {
    public static final String DB_FILE_NAME = "person.db";
    public static final int DB_VERSION = 2;
    private static final Class<? extends DbUpgradable>[] version_one_tables = new Class[]{PersonTableVersionOne.class, StudentTableVersionOne.class};



    private static PersonDbHelper mInstance = null;

    public static PersonDbHelper getInstance(Context context){
        if(null == mInstance){
            synchronized (PersonDbHelper.class){
                if(null == mInstance){
                    mInstance = new PersonDbHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseUtils.createTables(db, Arrays.asList(version_one_tables));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1){
            DatabaseUtils.updateTables(db, PersonTableVersionOne.class, PersonTableVersionTwo.class);
            DatabaseUtils.updateTables(db, StudentTableVersionOne.class, StudentTableVersionTwo.class);
        }else if(oldVersion == 2){
            DatabaseUtils.updateTables(db, StudentTableVersionTwo.class, StudentTableVersionThree.class);
        }
    }

    private PersonDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private PersonDbHelper(Context context){
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }


}
