package com.dlong.rep.multipledatasheettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dlong.rep.multipledatasheettest.app.MyApplication;
import com.dlong.rep.multipledatasheettest.model.CompanyBean;
import com.dlong.rep.multipledatasheettest.model.PhoneBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtPhone, txtCompany;

    /** phone列表 */
    private List<PhoneBean> mPhoneList;
    /** Company列表 */
    private List<CompanyBean> mCompanyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPhone = findViewById(R.id.txt_phone);
        txtCompany = findViewById(R.id.txt_company);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库
        MyApplication.mDBMaster.closeDataBase();
    }

    /**
     * 点击插入PHONE按钮
     * @param view
     */
    public void InsertPhone(View view){
        // 新实例化一个PHONE
        PhoneBean phone = new PhoneBean();
        phone.brand = "Google";
        phone.model = "Pixel 3";
        phone.price = 4999;
        // 插入数据库
        MyApplication.mDBMaster.mPhoneDBDao.insertData(phone);
        updatePhoneTxt();
    }

    /**
     * 点击删除PHONE按钮
     * @param view
     */
    public void DeletePhone(View view){
        // 删除最老的一个数据
        if (null != mPhoneList && mPhoneList.size()>0){
            MyApplication.mDBMaster.mPhoneDBDao.deleteData(mPhoneList.get(0).id);
            updatePhoneTxt();
        }
    }

    /**
     * 点击插入COMPANY按钮
     * @param view
     */
    public void InsertCompany(View view){
        // 新实例化一个COMPANY
        CompanyBean company = new CompanyBean();
        company.name = "谷歌";
        company.ceo = "桑达尔·皮查伊";
        company.year = 1998;
        // 插入数据库
        MyApplication.mDBMaster.mCompanyDBDao.insertData(company);
        updateCompanyTxt();
    }

    /**
     * 点击删除COMPANY按钮
     * @param view
     */
    public void DeleteCompany(View view){
        // 删除最老的一个数据
        if (null != mCompanyList && mCompanyList.size()>0){
            MyApplication.mDBMaster.mCompanyDBDao.deleteData(mCompanyList.get(0).id);
            updateCompanyTxt();
        }
    }

    /**
     * 更新Phone列表
     */
    private void updatePhoneTxt(){
        // 查询数据库里的所有数据
        mPhoneList = MyApplication.mDBMaster.mPhoneDBDao.queryDataList();
        // 数据为空，也不能让列表为null
        if (null == mPhoneList) mPhoneList = new ArrayList<>();
        // 将数据转为字符串
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mPhoneList.size(); i++) {
            builder.append(mPhoneList.get(i).toString()).append("\r\n");
        }
        // 显示数据
        txtPhone.setText(builder.toString());
    }

    /**
     * 更新Company列表
     */
    private void updateCompanyTxt(){
        // 查询数据库里的所有数据
        mCompanyList = MyApplication.mDBMaster.mCompanyDBDao.queryDataList();
        // 数据为空，也不能让列表为null
        if (null == mCompanyList) mCompanyList = new ArrayList<>();
        // 将数据转为字符串
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mCompanyList.size(); i++) {
            builder.append(mCompanyList.get(i).toString()).append("\r\n");
        }
        // 显示数据
        txtCompany.setText(builder.toString());
    }
}
