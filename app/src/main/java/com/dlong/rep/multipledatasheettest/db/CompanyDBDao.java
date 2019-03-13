package com.dlong.rep.multipledatasheettest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dlong.rep.multipledatasheettest.model.CompanyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司数据表操作类
 * @author  dlong
 * created at 2019/3/13 11:46 AM
 */
public class CompanyDBDao {

    /** 数据表名称 */
    public static final String TABLE_NAME = "company_info";

    /** 表的字段名 */
    public static String KEY_ID = "id";
    public static String KEY_NAME = "name";
    public static String KEY_CEO = "ceo";
    public static String KEY_YEAR = "year";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public CompanyDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }

    /**
     * 插入一条数据
     * @param company
     * @return
     */
    public long insertData(CompanyBean company) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, company.name);
        values.put(KEY_CEO, company.ceo);
        values.put(KEY_YEAR, company.year);
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
     * @param company
     * @return
     */
    public long updateData(int id, CompanyBean company) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, company.name);
        values.put(KEY_CEO, company.ceo);
        values.put(KEY_YEAR, company.year);
        return mDatabase.update(TABLE_NAME, values, KEY_ID + "=" + id, null);
    }

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    public List<CompanyBean> queryData(int id) {
        if (!DBConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_NAME,
                        KEY_CEO,
                        KEY_YEAR},
                KEY_ID + "=" + id , null, null, null, null);
        return convertUtil(results);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<CompanyBean> queryDataList() {
        if (!DBConfig.HaveData(mDatabase,TABLE_NAME)){
            return null;
        }
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_NAME,
                        KEY_CEO,
                        KEY_YEAR},
                null, null, null, null, null);
        return convertUtil(results);
    }

    /**
     * 查询结果转换
     * @param cursor
     * @return
     */
    private List<CompanyBean> convertUtil(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<CompanyBean> mList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            CompanyBean company = new CompanyBean();
            company.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            company.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            company.ceo = cursor.getString(cursor.getColumnIndex(KEY_CEO));
            company.year = cursor.getInt(cursor.getColumnIndex(KEY_YEAR));
            mList.add(company);
            cursor.moveToNext();
        }
        return mList;
    }
}
