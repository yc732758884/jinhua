package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CompanyListBean;
import com.hzwc.intelligent.lock.model.bean.DepartmentListBean;
import com.hzwc.intelligent.lock.model.bean.PostListBean;
import com.hzwc.intelligent.lock.model.bean.areaBean;
import com.hzwc.intelligent.lock.model.bean.cityBean;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.utils.UniqueIDUtils;
import com.hzwc.intelligent.lock.model.view.persenter.RegisterNextPresenter;
import com.hzwc.intelligent.lock.model.view.view.RegisterNextView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/22.
 */

@CreatePresenter(RegisterNextPresenter.class)
public class RegisterNextActivity extends AbstractMvpBaseActivity<RegisterNextView, RegisterNextPresenter> implements RegisterNextView {
    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_search_over)
    TextView tvSearchOver;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    //    @BindView(R.id.tv_company)
//    NiceSpinner npCompany;
//    @BindView(R.id.tv_register_department)
//    NiceSpinner npRegisterDepartment;
//    @BindView(R.id.tv_register_post)
//    NiceSpinner npRegisterPost;
    @BindView(R.id.sp_company)
    Spinner spCompany;

    @BindView(R.id.sp_department)
    Spinner spDepartment;
    @BindView(R.id.sp_post)
    Spinner spPost;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.sp_area)
    Spinner spArea;
    private List<CompanyListBean.CompanyBean> dataCompany;
    private List<DepartmentListBean.DepartmentsBean> dataDepartment;
    private List<PostListBean.PostsBean> dataPost;
    private List<cityBean.city>  cities=new ArrayList<>();
    private List<areaBean.area>  areas=new ArrayList<>();

    private int postId = -1;
    private int companyId = -1;
    private int departmentId = -1;
    private  String cityid;
    private   String  areaid;
    private String mobile;
    private String password;
    private String verifyCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        ButterKnife.bind(this);

    }

    @Override
    protected void initView() {
        tvTitleText.setText(getString(R.string.title_register));
        tvSearch.setVisibility(View.VISIBLE);
        tvSearch.setText(getString(R.string.tv_over));
        ivTitleReturn.setVisibility(View.VISIBLE);

        spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                companyId = dataCompany.get(position).getCompanyId();
                getMvpPresenter().getDepartment(companyId + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentId = dataDepartment.get(position).getDepartmentId();
                getMvpPresenter().getPost(departmentId + "");


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        spPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                postId = dataPost.get(position).getPostId();


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 cityid=cities.get(position).getCityId();
                 getMvpPresenter().getArea(cityid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaid=areas.get(position).getAreaId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        password = intent.getStringExtra("password");
      verifyCode = intent.getStringExtra("verifyCode");

//        mobile = "18375325478";
//        password = "123456";
//        verifyCode ="2394";

        getMvpPresenter().getCompany();
        getMvpPresenter().getCity();

        dataCompany = new LinkedList<>();
        dataDepartment = new LinkedList<>();
        dataPost = new LinkedList<>();



    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void registerSuccess(BaseBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "registerSuccess =" + jsonStr);
        if (result.getCode() == 0) {
            ToastUtil.show(RegisterNextActivity.this, "注册成功");
            Intent intent = new Intent("com.hzwc.person.lock.register");
            sendBroadcast(intent);
            SpUtils.setBoolean(RegisterNextActivity.this, "isLogin", false);
            finish();
        } else {
            ToastUtil.show(RegisterNextActivity.this, result.getMsg());
        }

    }

    @Override
    public void companySuccess(CompanyListBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "companySuccess =" + jsonStr);

        if (result.getCode() == 0) {

            List<String> listCompany = new ArrayList<>();

            for (int i = 0; i < result.getCompany().size(); i++) {

                dataCompany.add(result.getCompany().get(i));
                listCompany.add(result.getCompany().get(i).getCompanyName());
            }
            if (listCompany.size() > 0) {
                companyId = result.getCompany().get(0).getCompanyId();

                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCompany);
                spCompany.setAdapter(spinnerAdapter);
            }
        } else {
            ToastUtil.show(RegisterNextActivity.this, result.getMsg());
        }

    }

    @Override
    public void departmentSuccess(DepartmentListBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "departmentSuccess =" + jsonStr);

        if (result.getCode() == 0) {

            List<String> list = new ArrayList<>();

            for (int i = 0; i < result.getDepartments().size(); i++) {

                dataDepartment.add(result.getDepartments().get(i));
                list.add(result.getDepartments().get(i).getDepartmentName());
            }
            if (list.size() > 0) {
                //  departmentId = result.getDepartments().get(0).getDepartmentId();
                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                spDepartment.setAdapter(spinnerAdapter);
            }
        } else {
            ToastUtil.show(RegisterNextActivity.this, result.getMsg());
        }
    }

    @Override
    public void postSuccess(PostListBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "postSuccess =" + jsonStr);

        if (result.getCode() == 0) {

            List<String> list = new ArrayList<>();

            for (int i = 0; i < result.getPosts().size(); i++) {

                dataPost.add(result.getPosts().get(i));
                list.add(result.getPosts().get(i).getPostName());
            }
            if (list.size() > 0) {
                // postId = result.getPosts().get(0).getPostId();
                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                spPost.setAdapter(spinnerAdapter);
            }
        } else {
            ToastUtil.show(RegisterNextActivity.this, result.getMsg());
        }
    }

    @Override
    public void dataFailure(String result) {

    }

    @Override
    public void city(cityBean cb) {
        cities=cb.getData();


        List<String> strings = new ArrayList<>();
        for (int i = 0; i < cb.getData().size(); i++) {
            strings.add(cb.getData().get(i).getCity());
        }

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        spCity.setAdapter(spinnerAdapter);


    }

    @Override
    public void area(areaBean areaBean) {
        areas=areaBean.getData();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < areaBean.getData().size(); i++) {
            strings.add(areaBean.getData().get(i).getArea());
        }

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        spArea.setAdapter(spinnerAdapter);
    }

    @OnClick({R.id.iv_title_return, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;


            case R.id.tv_search:

                if (FunctionUtils.isFastClick()) {
                    return;
                }

//                isEmpty();

                String userName = etRegisterName.getText().toString().trim();
                getMvpPresenter().register(mobile, password, userName, Integer.parseInt(areaid), postId,verifyCode, UniqueIDUtils.getUniqueID(this));



            default:
                break;
        }
    }

    public boolean isEmpty() {
        if (TextUtils.isEmpty(etRegisterName.getText().toString().trim())) {
            ToastUtil.show(RegisterNextActivity.this, "用户名不能为空");
            return true;
        }
        if (companyId == -1) {
            ToastUtil.show(RegisterNextActivity.this, "请选择单位");
        }
        if (departmentId == -1) {
            ToastUtil.show(RegisterNextActivity.this, "请选择部门");
        }
        if (postId == -1) {
            ToastUtil.show(RegisterNextActivity.this, "请选择岗位");
        }

        return false;
    }


}
