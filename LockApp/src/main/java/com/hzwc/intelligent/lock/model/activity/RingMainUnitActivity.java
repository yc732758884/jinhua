package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.NewArea;
import com.hzwc.intelligent.lock.model.bean.NewAreaInfo;
import com.hzwc.intelligent.lock.model.bean.OCRBean;
import com.hzwc.intelligent.lock.model.bean.TypeBean;
import com.hzwc.intelligent.lock.model.spinner.NiceSpinner;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.NewBuildPresenter;
import com.hzwc.intelligent.lock.model.view.view.NewBuildView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.yanzhenjie.album.AlbumFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@CreatePresenter(NewBuildPresenter.class)
public class RingMainUnitActivity extends AbstractMvpBaseActivity<NewBuildView, NewBuildPresenter> implements NewBuildView {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_ring_name)
    EditText etRingName;
    @BindView(R.id.tv_ring_location)
    TextView tvRingLocation;
    @BindView(R.id.rl_ring_location)
    RelativeLayout rlRingLocation;
    @BindView(R.id.np_state)
    NiceSpinner npState;
    @BindView(R.id.tv_photo)
    ImageView tvPhoto;
    @BindView(R.id.iv_image)
    ImageView ivImage;


    private String locationX;
    private String locationY;
    private String addressName;
    private Double mLocationX;
    private Double mLocationY;
    //    private String adcode;
    private int areaId;

    private Spinner sp;

    private int typeId = 1;
    private List<String> listType;


    private static int REQUEST_THUMBNAIL = 3;// 请求缩略图信号标识
    private String sdPath;//SD卡的路径
    private String picPath;//图片存储路径

    private  String newAreaId;
    private  NewAreaInfo  nai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_main_unit);
        sp=findViewById(R.id.sp);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newAreaId=nai.getData().get(position).getAreaId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ButterKnife.bind(this);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        tvTitleText.setText(R.string.title_new_ring);

        getMvpPresenter().typeRequest(SpUtils.getString(RingMainUnitActivity.this, "token", ""));


        ivTitleReturn.setVisibility(View.VISIBLE);
        tvSearch.setVisibility(View.VISIBLE);
        tvSearch.setText(R.string.tv_update);
        //获取SD卡的路径
        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + "temp.png";
        Log.e("awj path=", sdPath);

    }

    @Override
    protected void initData() {

       getMvpPresenter().getNewArea(SpUtils.getString(RingMainUnitActivity.this, "token", "-1"));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            addressName = data.getStringExtra("addressName");
            mLocationX = data.getDoubleExtra("latitude", 0);
            mLocationY = data.getDoubleExtra("longitude", 0);
            areaId = data.getIntExtra("areaId", -1);
            Log.e("awj", "X =" + mLocationX);
            Log.e("awj", "Y =" + mLocationY);
            Log.e("awj", "areaId =" + areaId);
            tvRingLocation.setText(addressName);

        }

        if (requestCode == 0) {
            File file = new File(picPath);
            Uri uri = Uri.fromFile(file);
//                          iv_CameraImg.setImageURI(uri);
            Log.e("awj", "uri：" + uri);
        } else if (requestCode == 1) {
            Log.e("awj", "默认content地址：" + data.getData());
//                         iv_CameraImg.setImageURI(data.getData());
        } else if (requestCode == 3) {

            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
//            mImageView.setImageBitmap(bitmap);

//            Log.e("awj", "222uri：" + bitmap.toString());
//
//
//            File saveBitmapFile = saveBitmapFile(bitmap, FILE_PATH);
//            List<File> file = new ArrayList<>();
//            file.add(saveBitmapFile);




            ivImage.setImageBitmap(bitmap);
//            File file = null;
            File saveBitmapFile = saveBitmapFile(bitmap,picPath);
Log.e("awj","saveBitmapFile_size="+saveBitmapFile.length());




            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), saveBitmapFile);
//
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("imageFile", saveBitmapFile.getName(), requestFile);
            getMvpPresenter().imageRequest(SpUtils.getString(RingMainUnitActivity.this, "token", ""), body);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 上传json文本 和 多张file文件，使用map提交
     *
     * @param files
     * @return
     */
    public static Map<String, RequestBody> filesToRequestBodyMap(List<File> files) {
        Map<String, RequestBody> map = new HashMap<>(files == null ? 0 : files.size());
        if (files != null && files.size() > 0) {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                map.put("files\"; imageFile=\"" + file.getName(), requestBody);
            }
        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
//        map.put(valueKey, requestBody);
        return map;
    }


    /**
     * 把batmap 转file
     *
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.rl_ring_location, R.id.np_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.np_state:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                npState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        typeId = dataType.get(position).getTypeId();
                        Toast.makeText(RingMainUnitActivity.this, position + "======" + typeId, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.tv_search:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                Log.e("awj", "X =" + mLocationX);
                Log.e("awj", "Y =" + mLocationY);
                String id;
                if (TextUtils.isEmpty(newAreaId)){
                   id=areaId+"";
                }else {
                    id=newAreaId;
                }

                getMvpPresenter().clickRequest(SpUtils.getString(RingMainUnitActivity.this, "token", ""),
                        tvRingLocation.getText().toString().trim(), mLocationY, mLocationX, etRingName.getText().toString().trim(),
                        id, typeId);
                break;
            case R.id.rl_ring_location:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(RingMainUnitActivity.this, ChoseLocationActivity.class);
                startActivityForResult(intent, 0);

                break;
        }
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void newBuildSuccess(BaseBean result) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj newBuildSuccess =", jsonStr);
        ifEmpty();
        if (result.getCode() == 0) {
            ToastUtil.show(RingMainUnitActivity.this, "创建成功");
            finish();
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(RingMainUnitActivity.this, "isLogin", false);
            Toast.makeText(RingMainUnitActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(RingMainUnitActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(RingMainUnitActivity.this, result.getMsg());
        }

    }

    private List<TypeBean.CabinetTypeBean> dataType;

    @Override
    public void newTypeSuccess(TypeBean result) {

        if (result.getCode() == 0) {

            listType = new ArrayList<>();
            dataType = new ArrayList<>();

            for (int i = 0; i < result.getCabinetType().size(); i++) {

                dataType.add(result.getCabinetType().get(i));
                listType.add(result.getCabinetType().get(i).getTypeName());
            }
            if (listType.size() > 0) {
                typeId = result.getCabinetType().get(0).getTypeId();
                npState.attachDataSource(listType);
            }
        } else {
            ToastUtil.show(RingMainUnitActivity.this, result.getMsg());
        }


    }

    @Override
    public void newImageSuccess(OCRBean result) {
        Log.e("awj", "newImageSuccess code = " + result.getCode());
        Log.e("awj", "newImageSuccess msg = " + result.getMsg());
        if(result.getCode() == 0){
            etRingName.setText(result.getOcrText());
        }
        ToastUtil.show(RingMainUnitActivity.this,result.getMsg());
    }

    private boolean ifEmpty() {
        if (TextUtils.isEmpty(FunctionUtils.replaceBlank(etRingName.getText().toString().trim()))) {
            Toast.makeText(RingMainUnitActivity.this, getString(R.string.install_rim_name), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(tvRingLocation.getText().toString())) {
            Toast.makeText(RingMainUnitActivity.this, getString(R.string.install_rim_location), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void resultFailure(String result) {
        ToastUtil.show(RingMainUnitActivity.this, "服务器异常");
        ToastUtil.show(RingMainUnitActivity.this, result);
    }

    @Override
    public void getNewArea(NewAreaInfo area) {



        List<String> areaList = new ArrayList();
        NewArea na=new NewArea();
        na.setArea("无");
        na.setAreaId("");
        area.getData().add(0,na);
        nai=area;
        for (int i=0;i<area.getData().size();i++) {
            areaList.add(area.getData().get(i).getArea());

        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, areaList);
       sp.setAdapter(adapter);

    }

    private static final String FILE_PATH = "/sdcard/pic_rac.jpg";
    private static final String FILE__PATH = "/sdcard/a.jpg";

    @OnClick(R.id.tv_photo)
    public void onViewClicked() {


        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 启动相机
        startActivityForResult(intent1, REQUEST_THUMBNAIL);


    }


}
