package com.dlong.rep.multipledatasheettest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dlong.rep.multipledatasheettest.model.PhoneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机数据表操作类
 * @author  dlong
 * created at 2019/3/13 11:39 AM
 */
public class PhoneDBDao {

    /** 数据表名称 */
    public static final String TABLE_NAME = "phone_info";

    /** 表的字段名 */
    public static String KEY_ID = "id";
    public static String KEY_BRAND = "brand";
    public static String KEY_MODEL = "model";
    public static String KEY_PRICE = "price";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public PhoneDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }

    /**
     * 插入一条数据
     * @param phone
     * @return
     */
    public long insertData(PhoneBean phone) {
        ContentValues values = new ContentValues();
        values.put(KEY_BRAND, phone.brand);
        values.put(KEY_MODEL, phone.model);
        values.put(KEY_PRICE, phone.price);
        return mDatabase.insert(TABLE_NAME, null, values);
    }

    /**
     * 删除一条数据
     * @param id
     * @return
     */
    public long deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    /**
     * 删除所有数据
     * @return
     */
    public long deleteAllData() {
        return mDatabase.delete(TABLE_NAME, null, null);
    }

    /**
     * 更新一条数据
     * @param id
     * @param phone
     * @return
     */
    public long updateData(int id, PhoneBean phone) {
        ContentValues values = new ContentValues();
        values.put(KEY_BRAND, phone.brand);
        values.put(KEY_MODEL, phone.model);
        values.put(KEY_PRICE, phone.price);
        return mDatabase.update(TABLE_NAME, values, KEY_ID + "=" + id, null);
    }

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    public List<PhoneBean> queryData(int id) {
        if (!DBConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_BRAND,
                        KEY_MODEL,
                        KEY_PRICE},
                KEY_ID + "=" + id , null, null, null, null);
        return convertUtil(results);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<PhoneBean> queryDataList() {
        if (!DBConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_BRAND,
                        KEY_MODEL,
                        KEY_PRICE},
                null, null, null, null, null);
        return convertUtil(results);
    }

    /**
     * 查询结果转换
     * @param cursor
     * @return
     */
    private List<PhoneBean> convertUtil(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<PhoneBean> mList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            PhoneBean phone = new PhoneBean();
            phone.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            phone.brand = cursor.getString(cursor.getColumnIndex(KEY_BRAND));
            phone.model = cursor.getString(cursor.getColumnIndex(KEY_MODEL));
            phone.price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
            mList.add(phone);
            cursor.moveToNext();
        }
        return mList;
    }
}
