package com.github.evan.common_utils.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.evan.common_utils.db.PersonDbHelper;
import com.github.evan.common_utils.db.annotation.DatabaseField;
import com.github.evan.common_utils.db.annotation.DatabaseTable;
import com.github.evan.common_utils.db.upgradeInfomation.PersonTableV1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Evan on 2018/9/23.
 */
public class PersonV1Dao extends BaseDao<PersonDbHelper, PersonTableV1, String> {
    private static PersonV1Dao mInstance = null;

    public static PersonV1Dao getInstance(PersonDbHelper helper){
        if(null == mInstance){
            synchronized (PersonV1Dao.class){
                if(null == mInstance){
                    mInstance = new PersonV1Dao(helper);
                }
            }
        }

        return mInstance;
    }

    private PersonV1Dao(PersonDbHelper helper) {
        super(helper);
    }

    @Override
    public boolean insert(PersonTableV1 personTableV1) {
        String name = personTableV1.getName();
        int age = personTableV1.getAge();
        String sql = "insert into ";
        Class<? extends PersonTableV1> aClass = personTableV1.getClass();
        DatabaseTable databaseTable = aClass.getAnnotation(DatabaseTable.class);
        if(null != databaseTable){
            String tableName = databaseTable.tableName();
            sql += tableName + " (";
        }
        Field[] declaredFields = aClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            DatabaseField annotation = declaredField.getAnnotation(DatabaseField.class);
            if(null != annotation){
                String columnName = annotation.columnName();
                sql += columnName + (i == declaredFields.length - 1 ? ") " : ", ");
            }
        }

        sql += "values ('" + name + "', '" + age + "')";

        if(isCacheInMemory()){
            mMemoryCache.put(name, personTableV1);
            saveToDbInBackground(sql);
        }else{
            saveToDb(sql);
        }
        return true;
    }

    @Override
    public boolean remove(String removeKey) {
        String sql = "delete from person where person=" + removeKey;
        if(isCacheInMemory()){
            mMemoryCache.remove(removeKey);
            saveToDbInBackground(sql);
        }else{
            saveToDb(sql);
        }
        return true;
    }

    @Override
    public boolean update(String updateKey, PersonTableV1 personTableV1) {
        String sql = "update person set person=" + personTableV1.getName() + ", age=" + personTableV1.getAge() + " where person=" + updateKey;
        if(isCacheInMemory()){
            mMemoryCache.put(updateKey, personTableV1);
            saveToDbInBackground(sql);
        }else{
            saveToDb(sql);
        }
        return true;
    }

    @Override
    public PersonTableV1 query(String queryKey) {
        if(isCacheInMemory()){
            return mMemoryCache.get(queryKey);
        }
        String sql = "select (person, age) from person where name=" + queryKey;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);
        PersonTableV1 person = new PersonTableV1();
        while (cursor.moveToNext()){
            String name = cursor.getColumnName(0);
            int age = cursor.getInt(1);
            person.setName(name);
            person.setAge(age);
        }

        return person;
    }

    @Override
    public List<PersonTableV1> queryAll() {
        if(isCacheInMemory()){
            Collection<PersonTableV1> values = mMemoryCache.values();
            List<PersonTableV1> value = new ArrayList<>(values);
            return value;
        }
        return null;
    }

    @Override
    public boolean isCacheInMemory() {
        return true;
    }

    @Override
    protected Hashtable<String, PersonTableV1> onCacheIntoMemory() {
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from person", null);
        Hashtable<String, PersonTableV1> value = new Hashtable<>();
        while (cursor.moveToNext()){
            String name = cursor.getColumnName(cursor.getColumnIndex("person"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            PersonTableV1 person = new PersonTableV1();
            person.setName(name);
            person.setAge(age);
            value.put(name, person);
        }
        return value;
    }
}
