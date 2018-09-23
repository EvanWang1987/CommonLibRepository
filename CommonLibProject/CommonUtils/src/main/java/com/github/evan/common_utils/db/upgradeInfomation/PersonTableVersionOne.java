package com.github.evan.common_utils.db.upgradeInfomation;

import com.github.evan.common_utils.db.annotation.DatabaseField;
import com.github.evan.common_utils.db.annotation.DatabaseTable;

/**
 * Created by Evan on 2018/9/22.
 */
@DatabaseTable(tableName = "person", sinceDbVersion = 1, isGenerateAndroidPrimaryKey = true)
public class PersonTableVersionOne implements DbUpgradable {
    @DatabaseField(columnName = "person", columnDataType = "varchar")
    private String name;
    @DatabaseField(columnName = "age", columnDataType = "varchar")
    private int age;

}
