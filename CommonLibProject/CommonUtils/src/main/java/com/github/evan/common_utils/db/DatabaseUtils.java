package com.github.evan.common_utils.db;

import android.database.sqlite.SQLiteDatabase;
import com.github.evan.common_utils.db.annotation.DatabaseField;
import com.github.evan.common_utils.db.annotation.DatabaseTable;
import com.github.evan.common_utils.db.upgradeInfomation.DbUpgradable;
import com.github.evan.common_utils.utils.Logger;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2018/9/22.
 */
public class DatabaseUtils {
    private static final String ANDROID_PRIMARY_KEY_SQL = "'_id' integer primary key autoincrement";
    private static final String UNIQUE = "unique";
    private static final String NOT_NULL = "not null";
    private static final String DEFAULT = "default";


    /**
     * 创建表
     *
     * @param db
     * @param firstVersionTableInformation
     * @return
     */
    public static boolean createTables(SQLiteDatabase db, List<Class<? extends DbUpgradable>> firstVersionTableInformation) {
        if (null == db || null == firstVersionTableInformation || firstVersionTableInformation.size() == 0) {
            return false;
        }


//        List<Class<DbUpgradable>> firstVersionTableInformation = tables;
        if (null == firstVersionTableInformation || firstVersionTableInformation.size() == 0) {
            throw new RuntimeException("Null DbUpgradable!");
        }
        StringBuilder sBuilder = new StringBuilder();

        for (int i = 0; i < firstVersionTableInformation.size(); i++) {
            sBuilder.delete(0, sBuilder.length());
            Class<DbUpgradable> dbUpgradable = (Class<DbUpgradable>) firstVersionTableInformation.get(i);
            DatabaseTable databaseTable = dbUpgradable.getAnnotation(DatabaseTable.class);
            if (null == databaseTable) {
                //抛异常
                throw new RuntimeException("Can not find DatabaseTable annotation in DbUpgradable which " + dbUpgradable.getSimpleName());
            }


            String tableName = databaseTable.tableName();
            int sinceDbVersion = databaseTable.sinceDbVersion();
            boolean generatePrimaryKey = databaseTable.isGenerateAndroidPrimaryKey();
//            if (sinceDbVersion != 1) {
//                throw new RuntimeException("Your table which " + tableName + "is not since version 1, now is " + sinceDbVersion);
//            }
            //create table xxx ("_id" integer primary key auto_increment, "xxx" varchar, <等等等等>)
            sBuilder.append("create table " + tableName + " (" + (generatePrimaryKey ? ANDROID_PRIMARY_KEY_SQL : ""));

            Field[] fields = dbUpgradable.getDeclaredFields();
            if (null == fields || fields.length == 0) {
                throw new RuntimeException("Can not found any field in " + dbUpgradable.getClass().getName());
            }

            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
                if (null != databaseField) {
                    String columnName = databaseField.columnName();
                    String columnDataType = databaseField.columnDataType();
                    boolean isUnique = databaseField.isUnique();
                    boolean isNotNull = databaseField.isNotNull();
                    String defaultValue = databaseField.defaultValue();
                    boolean isPrimaryKey = databaseField.isPrimaryKey();
                    if (j > 0 || (j == 0 && generatePrimaryKey)) {
                        sBuilder.append(", ");
                    }

                    String primaryKey = "";
                    if (!generatePrimaryKey && isPrimaryKey) {
                        primaryKey = " primary key auto_increment";
                    }
                    String unique = isUnique ? " " + UNIQUE : "";
                    String notBeNull = isNotNull ? " " + NOT_NULL : "";
                    String defaultValueWithSQL = isNotNull ? DEFAULT + " " + defaultValue : "";
                    sBuilder.append("'" + columnName + "' " + columnDataType + primaryKey + unique + notBeNull + defaultValueWithSQL);
                }
            }
            sBuilder.append(")");
            String sql = sBuilder.toString();
            Logger.d(sql);
            db.execSQL(sql);
        }

        return true;
    }

    /**
     * 删除表
     *
     * @param db
     * @param tables
     * @return
     */
    public static boolean dropTables(SQLiteDatabase db, List<Class<? extends DbUpgradable>> tables) {
        if (null == db || null == tables || tables.size() == 0) {
            return false;
        }

        for (int i = 0; i < tables.size(); i++) {
            Class<DbUpgradable> dbUpgradableClass = (Class<DbUpgradable>) tables.get(i);
            DatabaseTable table = dbUpgradableClass.getAnnotation(DatabaseTable.class);
            String tableName = table.tableName();
            String sql = "drop table " + tableName;
            db.execSQL(sql);
        }

        return true;
    }

    public static boolean updateTables(SQLiteDatabase db, Class<? extends DbUpgradable> sourceTable, Class<? extends DbUpgradable> dstTable) {
        if (null == db || null == sourceTable || null == dstTable) {
            return false;
        }

        DatabaseTable databaseTable = sourceTable.getAnnotation(DatabaseTable.class);
        String sourceTableName = databaseTable.tableName();
        int oldVersion = databaseTable.sinceDbVersion();
        boolean saveLastVersionTableData = databaseTable.isSaveLastVersionTableData();
        Field[] declaredFields = sourceTable.getDeclaredFields();
        List<DatabaseField> sourceTableColumns = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            DatabaseField databaseField = declaredField.getAnnotation(DatabaseField.class);
            if (null != databaseField) {
                sourceTableColumns.add(databaseField);
            }
        }


        DatabaseTable dstDatabaseTable = dstTable.getAnnotation(DatabaseTable.class);
        String dstTableName = dstDatabaseTable.tableName();
        int newVersion = dstDatabaseTable.sinceDbVersion();
        Field[] dstDeclaredFields = dstTable.getDeclaredFields();
        List<DatabaseField> dstTableColumns = new ArrayList<>();
        for (int i = 0; i < dstDeclaredFields.length; i++) {
            Field declaredField = dstDeclaredFields[i];
            declaredField.setAccessible(true);
            DatabaseField databaseField = declaredField.getAnnotation(DatabaseField.class);
            if (null != databaseField) {
                dstTableColumns.add(databaseField);
            }
        }

        if (oldVersion + 1 != newVersion) {
            throw new RuntimeException("不能夸版本升级");
        }

        if (!sourceTableName.equals(dstTableName)) {
            throw new RuntimeException("表名不一致");
        }

        if (saveLastVersionTableData) {
            db.execSQL("alter table " + sourceTableName + " rename to " + sourceTableName + "_temp");
            List<Class<? extends DbUpgradable>> createTable = new ArrayList<>();
            createTable.add(dstTable);
            createTables(db, createTable);
            List<String> targetCopyColumn = new ArrayList<>();
            for (int i = 0; i < sourceTableColumns.size(); i++) {
                DatabaseField field = sourceTableColumns.get(i);
                String sourceColumn = field.columnName();
                for (int j = 0; j < dstTableColumns.size(); j++) {
                    DatabaseField dstDatabaseField = dstTableColumns.get(j);
                    String dstColumn = dstDatabaseField.columnName();
                    if (sourceColumn.equals(dstColumn)) {
                        targetCopyColumn.add(sourceColumn);
                    }
                }
            }
            //insert into table1 (column1, column2) select column1, column2 from table 2;
            String sql = "insert into " + dstTableName + " (";
            for (int i = 0; i < targetCopyColumn.size(); i++) {
                String column = targetCopyColumn.get(i);
                sql += column;
                if (i == targetCopyColumn.size() - 1 || targetCopyColumn.size() == 1) {
                    sql += ") ";
                } else {
                    sql += ", ";
                }
            }

            sql += "select ";
            for (int i = 0; i < targetCopyColumn.size(); i++) {
                String column = targetCopyColumn.get(i);
                sql += column;
                if (i == targetCopyColumn.size() - 1 || targetCopyColumn.size() == 1) {
                    sql += " from " + sourceTableName + "_temp";
                } else {
                    sql += ", ";
                }
            }

            db.execSQL(sql);
            db.execSQL("drop table " + sourceTableName + "_temp");
        } else {
            List<Class<? extends DbUpgradable>> oldTable = new ArrayList<>();
            oldTable.add(sourceTable);
            dropTables(db, oldTable);
            List<Class<? extends DbUpgradable>> newTable = new ArrayList<>();
            newTable.add(dstTable);
            createTables(db, newTable);
        }
        return true;
    }
}
