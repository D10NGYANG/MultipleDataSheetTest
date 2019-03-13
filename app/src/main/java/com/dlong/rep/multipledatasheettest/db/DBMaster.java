package com.dlong.rep.multipledatasheettest.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dlong.rep.multipledatasheettest.db.DBConfig.DB_NAME;
import static com.dlong.rep.multipledatasheettest.db.DBConfig.DB_VERSION;

/**
 * 数据库总操作类
 * @author  dlong
 * created at 2019/3/13 11:29 AM
 */
public class DBMaster {

    /** 上下文 */
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBOpenHelper mDbOpenHelper;

    /** 数据表操作类实例化 */
    public PhoneDBDao mPhoneDBDao;
    public CompanyDBDao mCompanyDBDao;

    public DBMaster(Context context){
        mContext = context;
        mPhoneDBDao = new PhoneDBDao(mContext);
        mCompanyDBDao = new CompanyDBDao(mContext);
    }

    /**
     * 打开数据库
     */
    public void openDataBase() {
        mDbOpenHelper = new DBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase();//获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase();//获取只读数据库
        }
        // 设置数据库的SQLiteDatabase
        mPhoneDBDao.setDatabase(mDatabase);
        mCompanyDBDao.setDatabase(mDatabase);
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    /** 创建该数据库下phone表的语句 */
    private static final String mPhoneSqlStr = "create table if not exists " + PhoneDBDao.TABLE_NAME + " (" +
            PhoneDBDao.KEY_ID + " integer primary key autoincrement , " +
            PhoneDBDao.KEY_BRAND + " text not null , " +
            PhoneDBDao.KEY_MODEL + " text not null , " +
            PhoneDBDao.KEY_PRICE + " integer );";

    /** 创建该数据库下Company表的语句 */
    private static final String mCompanySqlStr = "create table if not exists " + CompanyDBDao.TABLE_NAME + " (" +
            CompanyDBDao.KEY_ID + " integer primary key autoincrement , " +
            CompanyDBDao.KEY_NAME + " text not null , " +
            CompanyDBDao.KEY_CEO + " text not null , " +
            CompanyDBDao.KEY_YEAR + " integer );";

    /** 删除该数据库下phone表的语句 */
    private static final String mPhoneDelSql = "DROP TABLE IF EXISTS " + PhoneDBDao.TABLE_NAME;

    /** 删除该数据库下Company表的语句 */
    private static final String mCompanyDelSql = "DROP TABLE IF EXISTS " + CompanyDBDao.TABLE_NAME;

    /**
     * 数据表打开帮助类
     */
    public static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mPhoneSqlStr);
            db.execSQL(mCompanySqlStr);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(mPhoneDelSql);
            db.execSQL(mCompanyDelSql);
            onCreate(db);
        }
    }
}
