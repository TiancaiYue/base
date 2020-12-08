package cn.user_db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.user_db.UserCache;
import cn.utils.DBKey;

/**
 * Mola Android
 * Created by saye on 16/6/23.
 */
public class MolaModel {
    public static final String curUserIdKey = "_cur_user_id";
    @DBKey(key = curUserIdKey)
    long _uid;

    public MolaModel() {
        _uid = UserCache.getCurrentUser().getUserId();
    }

    ContentValues getDbContentValues() {
        ContentValues values = new ContentValues();
        List<Field> curClassFields = getAllFields();
        for (Field field : curClassFields) {
            DBKey dbkey = field.getAnnotation(DBKey.class);
            if (dbkey == null) continue;

            String key = dbkey.key();
            try {
                contentValueSet(values, key, field);
            } catch (Exception e) {
                Log.e("FileInfo", "DBKey标注了数据库不支持的类型 " + field.getType());
            }
        }
        return values;
    }

    @NonNull
    private List<Field> getAllFields() {
        ArrayList<Field> curClassFields = new ArrayList<>();
        curClassFields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
        Class<?> parentClass = this.getClass().getSuperclass();
        while (parentClass != null) {
            List<Field> parentClassFields = Arrays.asList(parentClass.getDeclaredFields());
            curClassFields.addAll(parentClassFields);
            parentClass = parentClass.getSuperclass();
        }
        return curClassFields;
    }

    void contentValueSet(ContentValues values, String key, Field field) throws Exception {
        Class<?> type = field.getType();
        field.setAccessible(true);
        try {
            if (type.equals(Long.TYPE)) {
                long value = field.getLong(this);
                values.put(key, value);
            } else if (type.equals(Integer.TYPE)) {
                int value = field.getInt(this);
                values.put(key, value);
            } else if (type.equals(Short.TYPE)) {
                short value = field.getShort(this);
                values.put(key, value);
            } else if (type.equals(String.class)) {
                String value = (String) field.get(this);
                values.put(key, value);
            } else if (type.equals(Boolean.TYPE)) {
                boolean value = field.getBoolean(this);
                values.put(key, value);
            } else if (type.equals(Float.TYPE)) {
                float value = field.getFloat(this);
                values.put(key, value);
            } else if (type.equals(Double.TYPE)) {
                double value = field.getDouble(this);
                values.put(key, value);
            } else if (type.equals(Byte.TYPE)) {
                byte value = field.getByte(this);
                values.put(key, value);
            } else if (type.equals(Byte[].class)) {
                byte[] value = (byte[]) field.get(this);
                values.put(key, value);
            } else {
                throw new Exception();
            }
        } catch (IllegalAccessException e) {
            Log.v("MolaModel", "contentValueSet can't access field");
        }
    }

    public void loadFromCursor(Cursor c) {
        List<Field> fields = getAllFields();
        for (Field field : fields) {
            DBKey key = field.getAnnotation(DBKey.class);
            if (key == null) continue;
            int index = c.getColumnIndex(key.key());
            if (index == -1) continue;
            Class<?> type = field.getType();
            field.setAccessible(true);
            try {
                if (type.equals(Long.TYPE)) {
                    long value = c.getLong(index);
                    field.setLong(this, value);
                } else if (type.equals(Integer.TYPE)) {
                    int value = c.getInt(index);
                    field.setInt(this, value);
                } else if (type.equals(Short.TYPE)) {
                    short value = c.getShort(index);
                    field.setShort(this, value);
                } else if (type.equals(Double.TYPE)) {
                    double value = c.getDouble(index);
                    field.setDouble(this, value);
                } else if (type.equals(Float.TYPE)) {
                    float value = c.getFloat(index);
                    field.setFloat(this, value);
                } else if (type.equals(boolean.class)) {
                    boolean value = c.getInt(index) != 0;
                    field.setBoolean(this, value);
                } else if (type.equals(String.class)) {
                    String value = c.getString(index);
                    field.set(this, value);
                } else if (type.equals(Byte[].class)) {
                    byte[] value = c.getBlob(index);
                    field.set(this, value);
                } else if (type.isAssignableFrom(String.class)) {
                    String value = c.getString(index);
                    field.set(this, value);
                } else {
                    Log.v("MolaModel", "can't set field " + field.getName() +
                            " with " + c.getString(index));
                }
            } catch (IllegalAccessException e) {
                Log.v("MolaModel", "can't access" + field.getName());
            }
        }
    }

    public long insertOrRelace(SQLiteDatabase writeableDb) {
        ContentValues values = getDbContentValues();
        long rowId = writeableDb.replace(
                getDBTableName(),
                null,
                values
        );
        return rowId;
    }

    /**
     * 关联的数据库表名, 需要保存到数据库的model需要实现本方法
     *
     * @return 表名
     */
    public String getDBTableName() {
        return null;
    }
}
