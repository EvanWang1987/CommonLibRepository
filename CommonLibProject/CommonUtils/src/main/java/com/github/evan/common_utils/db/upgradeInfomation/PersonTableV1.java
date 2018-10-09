package com.github.evan.common_utils.db.upgradeInfomation;

import com.github.evan.common_utils.db.annotation.DatabaseField;
import com.github.evan.common_utils.db.annotation.DatabaseTable;

/**
 * Created by Evan on 2018/9/22.
 */
@DatabaseTable(tableName = "person", sinceDbVersion = 1, isGenerateAndroidPrimaryKey = true)
public class PersonTableV1 implements DbUpgradable {
    @DatabaseField(columnName = "person", columnDataType = "varchar")
    private String name;
    @DatabaseField(columnName = "age", columnDataType = "varchar")
    private int age;

    public PersonTableV1() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonTableV1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
