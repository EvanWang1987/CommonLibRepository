package com.github.evan.common_utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.evan.common_utils.db.upgradeInfomation.DbUpgradable;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableV1;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableV2;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableV1;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableV3;
import com.github.evan.common_utils.db.upgradeInfomation.StudentTableV2;

import java.util.Arrays;

/**
 * Created by Evan on 2018/9/18.
 */

public class PersonDbHelper extends BaseSQLiteHelper {
    public static final String DB_FILE_NAME = "person.db";
    public static final int DB_VERSION = 2;
    private static final Class<? extends DbUpgradable>[] version_one_tables = new Class[]{PersonTableV1.class, StudentTableV1.class};



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
            DatabaseUtils.updateTables(db, PersonTableV1.class, PersonTableV2.class);
            DatabaseUtils.updateTables(db, StudentTableV1.class, StudentTableV2.class);
        }else if(oldVersion == 2){
            DatabaseUtils.updateTables(db, StudentTableV2.class, StudentTableV3.class);
        }
    }

    private PersonDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private PersonDbHelper(Context context){
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }


}
