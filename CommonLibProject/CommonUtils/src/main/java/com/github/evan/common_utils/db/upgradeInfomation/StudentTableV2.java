package com.github.evan.common_utils.db.upgradeInfomation;

import com.github.evan.common_utils.db.annotation.DatabaseField;
import com.github.evan.common_utils.db.annotation.DatabaseTable;

/**
 * Created by Evan on 2018/9/22.
 */
@DatabaseTable(tableName = "student", sinceDbVersion = 2, isSaveLastVersionTableData = true)
public class StudentTableV2 implements DbUpgradable {

    @DatabaseField(columnName = "name", columnDataType = "varchar", isUnique = false, isNotNull = false)
    private String name;

    @DatabaseField(columnName = "age", columnDataType = "integer", isUnique = false, isNotNull = false)
    private String age;

    @DatabaseField(columnName = "country", columnDataType = "varchar", isUnique = false, isNotNull = false)
    private String country;

    @DatabaseField(columnName = "score", columnDataType = "integer", isUnique = false, isNotNull = false)
    private String score;

    @DatabaseField(columnName = "school", columnDataType = "varchar", isUnique = false, isNotNull = false)
    private String school;
}
